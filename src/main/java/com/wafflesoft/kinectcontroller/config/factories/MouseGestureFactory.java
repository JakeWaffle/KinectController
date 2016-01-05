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

package com.wafflesoft.kinectcontroller.config.factories;

import com.wafflesoft.kinectcontroller.config.installers.AngleRuleInstaller;
import com.wafflesoft.kinectcontroller.controller.ControllerState;
import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.emulation.reactions.config.MouseReactionConfig;
import com.wafflesoft.kinectcontroller.emulation.reactions.persistent.MouseReaction;
import com.wafflesoft.kinectcontroller.posturerules.Angle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jake on 12/24/2015.
 * This creates a Gesture instance that contains all of the rules and reactions needed for moving the mouse around with
 * a gesture.
 */
public class MouseGestureFactory implements GestureFactory {
    private static final Logger             _logger = LoggerFactory.getLogger(MouseGestureFactory.class);

    private String _armId;

    public MouseGestureFactory() {
    }

    /**
     * This basically is just used to identify the arm that is in use at the moment.
     * @param config The MouseGestureFactory expects the config to contain:
     *                  - armId - Either 'left' or 'right'.
     * @return A boolean to say whether the config was valid or not.
     */
    public boolean loadConfig(Map<String,String> config) {
        boolean success = true;

        if (config.containsKey("armId")) {
            _armId = config.get("armId");
            if (!"left".equals(_armId) && !"right".equals(_armId)) {
                _logger.error("Incorrect 'armId' for MouseGestureFactory's config: "+_armId);
                success = false;
            }
        }

        return success;
    }

    /**
     * This adds posturerules as well as Gesture objects to the given ControllerState.
     * @param state The ControllerState that this Gesture will be installed into. Meaning that posutrerules will be
     *              given to the _state to be updated -- by the Kinect -- and the ControllerState will be able to
     */
    public void installGesture(ControllerState state) {
        String  gestureId   = "mouse using "+_armId;
        Gesture gesture     = new Gesture(gestureId, state);

        System.out.println("Mouse Gesture: " + gestureId);

        //Grabs the direction that the user chose for the mouse control gesture.
        //(Specifies which arm will be used for the mouse controlling.)
        String dir      = "LEFT";
        String oppDir   = "RIGHT";

        int minArmAngle;

        //This is pixels per millisecond and it is supposed to be negative for the left arm and positive for the right arm.
        int maxXMouseSpeed;

        int minXAngle;
        int maxXAngle;

        int maxYMouseSpeed;
        int minYAngle;
        int maxYAngle;

        //Thanks to the tokens of javacc, armId will always be either left_arm or right_arm.
        //_armId is assumed to be either 'left' or 'right' because of loadConfig()'s validation.
        if (_armId.equals("left")) {
            dir = "LEFT";
            oppDir = "RIGHT";

            maxXMouseSpeed = -1;
            maxYMouseSpeed = -1;
        }
        else {
            dir = "RIGHT";
            oppDir = "LEFT";

            maxXMouseSpeed = 1;
            maxYMouseSpeed = -1;
        }

        minArmAngle = 130;

        minXAngle = 80;
        maxXAngle = 140;

        minYAngle = 60;
        maxYAngle = 120;

        //Three angles are needed in order to tell if an arm is in the activation area for controlling the mouse.
        //The activation area is when the arm is fully extended and in front of the user, basically.

        //Checks for the arm being straight.
        Angle armAngle = AngleRuleInstaller.install(gesture, dir+"_HAND", dir+"_ELBOW", dir+"_SHOULDER", minArmAngle, 180);

        //Checks if arm is horizontally pointing forward.
        Angle armX = AngleRuleInstaller.install(gesture, dir+"_HAND", dir+"_SHOULDER", oppDir+"_SHOULDER", minXAngle, maxXAngle);

        //Checks if arm is vertically pointing forward.
        Angle armY = AngleRuleInstaller.install(gesture, dir+"_HAND", dir+"_SHOULDER", dir+"_HIP", minYAngle, maxYAngle);

        gesture.addPersistentReaction(new MouseReaction("mouse_control", new MouseReactionConfig(maxXMouseSpeed, maxYMouseSpeed, armAngle, minArmAngle, armX, minXAngle, maxXAngle, armY, minYAngle, maxYAngle)));

        state.addGesture(gestureId, gesture);
    }
}
