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
import com.lcsc.hackathon.kinectcontroller.posturerules.Angle;
import com.lcsc.hackathon.kinectcontroller.posturerules.Rule;
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
    private              UserTracker            _tracker;
    private        final ControllerStateMachine _csm;
    private              short                  _userId = -1;

    public KinectUserTracker(ControllerStateMachine csm, boolean debug) {
        _csm = csm;

        _logger.info("Initializing OpenNi and Nite.");
        OpenNI.initialize();
        NiTE.initialize();

        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.size() == 0) {
            _logger.error("No Kinect device is connected!");
            System.exit(1);
        }

        Device device = Device.open(devicesInfo.get(0).getUri());
        _tracker = UserTracker.create();

        if (debug) {
            kinectWindow = new KinectDebugWindow(_tracker);
        }
        else {
            kinectWindow = null;
        }

        _tracker.addNewFrameListener(this);
    }

    /**
     * This delegate method will be called by the _tracker periodically.
     * @param tracker The tracker that called the method probably. Examples I've seen don't even utilize it because
     *                there is already a copy of it saved in the class' private scope.
     */
    public void onNewFrame(UserTracker tracker) {
        UserTrackerFrameRef frame   = tracker.readFrame();
        UserData userData           = null;

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
                        _tracker.startSkeletonTracking(_userId);
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
            if (userData.isLost()) {
                _logger.info(String.format("Lost User: %d", _userId));
                _tracker.stopSkeletonTracking(_userId);
                _userId = -1;
                return;
            }
        }

        Skeleton skeleton = userData.getSkeleton();
        if (skeleton.getState() == SkeletonState.TRACKED) {
            //Note: If there is no ControllerState assigned to the ControllerStateMachine's curState, then
            //the following will throw a null pointer exception! We will prevent this by making sure there is
            //a current state when the config file is loaded.

            //Here we will get the posturerule event beans from the current ControllerState.
            //We'll then update them with data from the user that's being tracked and we'll pass
            //those objects over to Esper for processing.
            for (Rule rule : _csm.getCurrentRules()) {
                switch (rule.getType()) {
                    case ANGLE:
                        Angle angRule           = (Angle) rule;
                        SkeletonJoint end1      = skeleton.getJoint(JointType.fromNative(angRule.getEnd1()));
                        SkeletonJoint vertex    = skeleton.getJoint(JointType.fromNative(angRule.getVertex()));
                        SkeletonJoint end2      = skeleton.getJoint(JointType.fromNative(angRule.getEnd2()));

                        double angle = Formulas.getAngle(end1.getPosition(), vertex.getPosition(), end2.getPosition());
                        angRule.setAngle(angle);

                        //Send that rule object over to Esper now for some event processing and pattern matching.
                        _csm.esperHandler.sendEvent(rule);
                        break;
                }
            }
        }

        if (kinectWindow != null) {
            kinectWindow.repaint();
        }
    }
}
