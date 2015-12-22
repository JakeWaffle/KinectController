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
package com.wafflesoft.kinectcontroller.emulation.reactions;

import com.wafflesoft.kinectcontroller.Conversions;
import com.wafflesoft.kinectcontroller.emulation.reactions.config.ReactionConfig;

import java.awt.Robot;
import java.awt.AWTException;

/**
 * Created by jake on 10/15/2015.
 */
public class ButtonReaction implements Reaction {
    private static Robot _rob;

    static {
        try {
            _rob = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private ReactionConfig<String, Object> 	_config;

    public ButtonReaction(ReactionConfig config) {
        _config = config;
    }
	
	public ReactionConfig getConfig() {
		return _config;
	}

    public void trigger() {
        switch ((String)_config.get("DeviceType")) {
            case "keyboard":
                int keyId = Conversions.getKeyId((String) _config.get("ButtonId"));
                _rob.keyPress(keyId);
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                _rob.keyRelease(keyId);
                break;
            case "mouse":
                //rob.mousePress(Conversions.getMouseButtonId((String)_config.get("ButtonId")));
                break;
        }
    }
}
