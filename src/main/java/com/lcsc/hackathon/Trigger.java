package com.lcsc.hackathon;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Trigger {
    //This is corresponds with 'trigger: {type:triggerType}' in the config file.
    //It defines the Trigger as a key press, mouse movement or whatever.
    private String type;
    private List<Map<String, String>> definition;
    
    public Trigger(String type, List<Map<String, String>> defintion) {
        this.type = type;
        this.definition = definition;
    }
    
    public String getType() {
        return this.type;
    }
    
    public List<Map<String, String>> getDefinition() {
        return definition;
    }
}