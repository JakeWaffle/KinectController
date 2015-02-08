package com.lcsc.hackathon;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Trigger {
    //This is corresponds with 'trigger: {type:triggerType}' in the config file.
    //It defines the Trigger as a key press, mouse movement or whatever.
    private String type;
    private List<Map<String, String>> definition;
    
    private static Logger log = Logger.getRootLogger();
    
    public Trigger(String type, List<Map<String, String>> definition) {
        this.type = type;
        assert(definition != null);
        this.definition = definition;
    }
    
    public String getType() {
        return this.type;
    }
    
    public List<Map<String, String>> getDefinition() {
        //log.info(String.format("definition size: %d", this.definition.size()));
        return this.definition;
    }
}