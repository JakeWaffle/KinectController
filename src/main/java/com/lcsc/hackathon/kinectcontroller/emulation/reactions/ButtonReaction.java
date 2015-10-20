/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

The Kinect Controller makes use of the J4K library and Esper and we have done
nothing to change their source.

By using J4K we are required to site their research article:
A. Barmpoutis. 'Tensor Body: Real-time Reconstruction of the Human Body and Avatar Synthesis from RGB-D',
IEEE Transactions on Cybernetics, Special issue on Computer Vision for RGB-D Sensors: Kinect and Its
Applications, October 2013, Vol. 43(5), Pages: 1347-1356.

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

package com.lcsc.hackathon.kinectcontroller.emulation.reactions;

import com.lcsc.hackathon.kinectcontroller.emulation.reactions.config.ReactionConfig;

import java.awt.Robot;
import java.awt.AWTException;

/**
 * Created by jake on 10/15/2015.
 */
public class ButtonReaction implements Reaction {
	private String							_id;
    private ReactionConfig<String, Object> 	_config;

    public ButtonReaction(String id, ReactionConfig config) {
        _config = config;
    }
	
	public String getId() {
		return _id;
	}

    public void trigger() {
        try {
            Robot rob = new Robot();
            switch ((String)_config.get("DeviceType")) {
                case "keyboard":
                    rob.keyPress((int)_config.get("ButtonId"));
                    break;
                case "mouse":
                    rob.mousePress((int)_config.get("ButtonId"));
                    break;
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
