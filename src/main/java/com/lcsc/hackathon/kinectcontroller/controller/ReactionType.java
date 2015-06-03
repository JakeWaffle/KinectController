package com.lcsc.hackathon.kinectcontroller.controller;

/**
 * Created by Jake on 6/2/2015.
 */
public enum ReactionType {
    KEY_PRESS("Key_Press"),
    KEY_DOWN("Key_Down"),
    KEY_UP("Key_Up");


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
