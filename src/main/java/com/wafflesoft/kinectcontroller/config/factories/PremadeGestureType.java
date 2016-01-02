package com.wafflesoft.kinectcontroller.config.factories;

/**
 * Created by jake on 1/1/2016.
 *
 * This just gives a list of alias' for the different GestureFactory implementations.
 * All of the alias' used within this will be automatically supported and used by the config parser.
 */
public enum PremadeGestureType {
    MOUSE_GESTURE("mouse", "MouseGestureFactory");

    //This is the alias that is used in the config file by the users.
    public final String alias;

    //The name of the class that implements the GestureFactory for this premade gesture.
    public final String factoryClassName;

    PremadeGestureType(String alias, String factoryClassName) {
        this.alias              = alias;
        this.factoryClassName   = factoryClassName;
    }

    public static PremadeGestureType fromString(String alias) {
        if (alias != null) {
            for (PremadeGestureType p : PremadeGestureType.values()) {
                if (alias.equalsIgnoreCase(p.alias)) {
                    return p;
                }
            }
        }
        return null;
    }
}
