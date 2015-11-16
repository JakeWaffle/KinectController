/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.lcsc.hackathon.kinectcontroller.kinect;

import com.lcsc.hackathon.kinectcontroller.Formulas;
import com.lcsc.hackathon.kinectcontroller.controller.ControllerStateMachine;
import com.lcsc.hackathon.kinectcontroller.posturerules.*;
import com.primesense.nite.*;
import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by Student on 10/29/2015.
 * This class will be tasked with keeping track of the libraries that are needed to talk to the Kinect. So this class will
 * be handing off the positions of joints to Esper and Esper will then use that data to detect gestures.
 */
public class KinectUserTracker implements UserTracker.NewFrameListener{
    private static final Logger                 _logger = LoggerFactory.getLogger(KinectUserTracker.class);

    public         final KinectDebugWindow      kinectWindow;
    public         final UserTracker            tracker;

    private        final ControllerStateMachine _csm;
    private              short                  _userId = -1;


    private              double                 _lastUpdateTime;

    //These are for tracking the kinect's update rate per second.
    //We're adding up the time in-between individual updates and going to later average those times.
    private              double                 _lastTime;
    private              double                 _totalUpdateRates;
    private              short                  _updates;

    public KinectUserTracker(ControllerStateMachine csm) {
        _csm                = csm;

        _lastTime           = 0;
        _totalUpdateRates   = 0;
        _updates            = -1;

        _logger.info("Initializing OpenNi and Nite.");
        OpenNI.initialize();
        NiTE.initialize();

        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.size() == 0) {
            _logger.error("No Kinect device is connected!");
            System.exit(1);
        }

        Device device = Device.open(devicesInfo.get(0).getUri());
        tracker = UserTracker.create();

        kinectWindow = new KinectDebugWindow(tracker);

        tracker.addNewFrameListener(this);
    }

    /**
     * This is used to keep track of an averaged update rate of the Kinect.
     */
    private void updateKinectUpdateRate() {
        if (_updates < 10) {
            _totalUpdateRates   += System.nanoTime() - _lastTime;
            _updates            += 1;
            _lastTime           = System.nanoTime();
        }
        else {
            System.out.println(String.format("Kinect Update Rate: %f", 1000000000/(_totalUpdateRates / _updates)));
            _totalUpdateRates   = 0;
            _updates            = -1;
        }
    }


    /**
     * This delegate method will be called by the tracker periodically.
     * @param tracker The tracker that called the method probably. This object gives us all the information we need
     *                concerning the Kinect, OpenNI and Nite.
     */
    public void onNewFrame(UserTracker tracker) {
        //updateKinectUpdateRate();

        UserData userData           = null;
        UserTrackerFrameRef frame   = tracker.readFrame();

        //These conditions below make it so that there is always only 1 user being tracked and we won't do
        //anything if there isn't a user being tracked.
        if (_userId == -1) {
            List<UserData> users = frame.getUsers();
            if (users.size() == 0) {
                return;
            }
            else {
                boolean userFound = false;
                for (UserData user : users) {
                    if (user.isNew()) {
                        _userId     = user.getId();
                        this.tracker.startSkeletonTracking(_userId);
                        userData    = user;
                        userFound   = true;
                        _logger.info(String.format("New User: %d", _userId));
                    }
                }
                if (!userFound) {
                    return;
                }
            }
        }
        else {
            userData = frame.getUserById(_userId);
            if (userData == null || userData.isLost()) {
                _logger.info(String.format("Lost User: %d", _userId));
                this.tracker.stopSkeletonTracking(_userId);
                _userId = -1;
                return;
            }
        }

        Skeleton skeleton = userData.getSkeleton();
        if (skeleton.getState() == SkeletonState.TRACKED) {
            //Note: If there is no ControllerState assigned to the ControllerStateMachine's curState, then
            //the following will throw a null pointer exception! We will prevent this by making sure there is
            //a current state when the config file is loaded.

            //Reusable variables for the below switch case.
            SkeletonJoint end1      = null;
            SkeletonJoint vertex    = null;
            SkeletonJoint end2      = null;
            double angle            = 0;


            SkeletonJoint joint1    = null;
            SkeletonJoint joint2    = null;
            double distance         = 0;
            double position         = 0;

            //Here we will get the posturerule event beans from the current ControllerState.
            //We'll then update them with data from the user that's being tracked and we'll pass
            //those objects over to Esper for processing.
            for (Rule rule : _csm.getCurrentRules()) {

                switch (rule.getType()) {
                    case ANGLE:
                        Angle angRule   = (Angle) rule;
                        end1            = skeleton.getJoint(JointType.fromNative(angRule.getEnd1()));
                        vertex          = skeleton.getJoint(JointType.fromNative(angRule.getVertex()));
                        end2            = skeleton.getJoint(JointType.fromNative(angRule.getEnd2()));

                        angle           = Formulas.getAngle(end1.getPosition(), vertex.getPosition(), end2.getPosition());
                        angRule.setAngle(angle);

                        //Send that rule object over to Esper now for some event processing and pattern matching.
                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case DISTANCE:
                        Distance distRule   = (Distance) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(distRule.getJoint1()));
                        joint2              = skeleton.getJoint(JointType.fromNative(distRule.getJoint2()));

                        distance            = Formulas.getDistance(joint2.getPosition(), joint1.getPosition());
                        distRule.setDistance(distance);

                        _logger.debug(String.format("Distance: %f", distance));

                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case DISTANCEX:
                        DistanceX distXRule = (DistanceX) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(distXRule.getJoint1()));
                        joint2              = skeleton.getJoint(JointType.fromNative(distXRule.getJoint2()));

                        distance            = Formulas.getDistanceX(joint2.getPosition().getX(), joint1.getPosition().getX());
                        distXRule.setDistance(distance);

                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case DISTANCEY:
                        DistanceY distYRule = (DistanceY) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(distYRule.getJoint1()));
                        joint2              = skeleton.getJoint(JointType.fromNative(distYRule.getJoint2()));

                        distance            = Formulas.getDistanceY(joint2.getPosition().getY(), joint1.getPosition().getY());
                        distYRule.setDistance(distance);

                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case DISTANCEZ:
                        DistanceZ distZRule = (DistanceZ) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(distZRule.getJoint1()));
                        joint2              = skeleton.getJoint(JointType.fromNative(distZRule.getJoint2()));

                        distance            = Formulas.getDistanceZ(joint2.getPosition().getZ(), joint1.getPosition().getZ());
                        distZRule.setDistance(distance);

                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case POSITIONX:
                        PositionX posXRule  = (PositionX) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(posXRule.getJointId()));

                        position            = Formulas.getDistanceX(joint1.getPosition().getX(), new Float(0.0));
                        posXRule.setPos((float)position);

                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case POSITIONY:
                        PositionY posYRule  = (PositionY) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(posYRule.getJointId()));

                        position            = Formulas.getDistanceY(joint1.getPosition().getY(), new Float(0.0));
                        posYRule.setPos((float)position);

                        _csm.esperHandler.sendEvent(rule);
                        break;
                    case POSITIONZ:
                        PositionZ posZRule  = (PositionZ) rule;
                        joint1              = skeleton.getJoint(JointType.fromNative(posZRule.getJointId()));

                        position            = Formulas.getDistanceZ(joint1.getPosition().getZ(), new Float(1500.0));
                        posZRule.setPos((float)position);

                        _csm.esperHandler.sendEvent(rule);
                        break;
                }
            }
        }

        kinectWindow.repaint();
    }
}
