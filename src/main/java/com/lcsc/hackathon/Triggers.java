package com.lcsc.hackathon;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Triggers {
    //This maps a trigger id1 to a list of specific triggers.
    private static Map<String, Trigger> triggers = new HashMap<String, Trigger>();
    
    public static void addTrigger(String triggerId, Trigger trigger) {
        triggers.put(triggerId, trigger);
    }
    
    public static Trigger getTrigger(String triggerId) {
        return triggers.get(triggerId);
    }
}