package com.lcsc.hackathon;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.log4j.Logger;

//This class is meant to store information about the things that the listeners are triggering.
//  That information couldn't be transferred over with Esper so it's stored here.
public class Triggers {
    private static Logger log = Logger.getRootLogger();
    //This maps a trigger id1 to a list of specific triggers.
    private static Map<String, Trigger> triggers = new HashMap<String, Trigger>();
    
    public static void addTrigger(String triggerId, Trigger trigger) {
        Triggers.triggers.put(triggerId, trigger);
    }
    
    public static Trigger getTrigger(String triggerId) {
        return Triggers.triggers.get(triggerId);
    }
}