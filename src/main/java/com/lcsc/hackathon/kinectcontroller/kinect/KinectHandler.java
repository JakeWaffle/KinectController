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

import com.lcsc.hackathon.kinectcontroller.controller.ControllerStateMachine;
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
public class KinectHandler implements UserTracker.NewFrameListener{
    private static final Logger                 _logger = LoggerFactory.getLogger(KinectHandler.class);
    public         final KinectDebugWindow      kinectWindow;
    private              UserTracker            _tracker;
    private        final ControllerStateMachine _csm;

    public KinectHandler(ControllerStateMachine csm, boolean debug) {
        _csm = csm;

        System.out.println("Initializing the things.");
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
            kinectWindow = new KinectDebugWindow(UserTracker.create());
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
        //TODO Pull the posturerules Event Beans from the current ControllerState, fill those beans with data and give them to Esper.
        for (Rule rule : _csm.getCurrentRules()) {
            //Update that rule!
        }
    }
}
