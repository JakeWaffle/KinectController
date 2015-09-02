package com.lcsc.hackathon.kinectcontroller.rules;

/**
 * Created by Jake on 5/28/2015.
 */
public enum RuleType {
    ABS_DISTANCE("Absolute_DistanceXYZ", "AbsoluteDistance"),
    ABS_DISTANCEX("Absolute_DistanceX", "AbsoluteDistX"),
    ABS_DISTANCEY("Absolute_DistanceY", "AbsoluteDistY"),
    ABS_DISTANCEZ("Absolute_DistanceZ", "AbsoluteDistZ"),
    ANGLE("Angle", "Angle"),
    DISTANCE("DistanceXYZ", "Distance"),
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
