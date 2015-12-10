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

package com.lcsc.hackathon.kinectcontroller.emulation;

/**
 * Created by Jake on 6/2/2015.
 */
public enum ReactionType {
    KEY_DOWN_UP("Key_Down_Up"),
    KEY_DOWN("Key_Down"),
    KEY_UP("Key_Up"),
    SHUTDOWN("SHUTDOWN");


    //This is the alias that is used in the config file by the users.
    public final String alias;

    ReactionType(String alias) {
        this.alias      = alias;
    }

    public static ReactionType fromString(String alias) {
        if (alias != null) {
            for (ReactionType r : ReactionType.values()) {
                if (alias.equalsIgnoreCase(r.alias)) {
                    return r;
                }
            }
        }
        return null;
    }
}
