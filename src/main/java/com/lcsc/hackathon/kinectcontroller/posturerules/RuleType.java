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

package com.lcsc.hackathon.kinectcontroller.posturerules;

/**
 * Created by Jake on 5/28/2015.
 */
public enum RuleType {
    DISTANCE_FROM_POINT("Distance_From_Point", "DistanceFromPoint"),
    POSITIONX("PositionX", "PositionX"),
    POSITIONY("PositionY", "PositionY"),
    POSITIONZ("PositionZ", "PositionZ"),
    ANGLE("Angle", "Angle"),
    DISTANCE("Distance", "Distance"),
    DISTANCEX("DistanceX", "DistanceX"),
    DISTANCEY("DistanceY", "DistanceY"),
    DISTANCEZ("DistanceZ", "DistanceZ");

    //This will link to the class the rule belongs to.
    public final String className;

    //This is the alias that is used in the config file by the users.
    public final String alias;

    RuleType(String alias, String className) {
        this.alias      = alias;
        this.className  = className;
    }

    public static RuleType fromString(String alias) {
        if (alias != null) {
            for (RuleType r : RuleType.values()) {
                if (alias.equalsIgnoreCase(r.alias)) {
                    return r;
                }
            }
        }
        return null;
    }
}
