package com.lcsc.hackathon;

import java.util.Map;

import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.StringBuilder;

import java.io.IOException;
import java.io.FileNotFoundException;

import org.eclipse.jetty.util.ajax.JSON;
import org.apache.log4j.Logger;

public class ConfigParser {
    static Logger log = Logger.getRootLogger();
    private EsperHandler eHandler;
    public ConfigParser(EsperHandler eHandler) {
        this.eHandler = eHandler;
    }
    
    public EventFactory parseConfigFile(String configPath) {
        //Load the config file
        Map<String, Object> config = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configPath));
            config = (Map<String, Object>)JSON.parse(reader);
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
            
        }
        
        Object[] gestures = (Object[])config.get("gestures");
        
        for (Object gestureObj : gestures) {
            Map<String, Object> gesture = (Map<String, Object>)gestureObj;
            String gestureId = (String)gesture.get("id");
            
            Object[] rules = (Object[])gesture.get("rules");
            for (Object ruleObj : rules) {
                Map<String, Object> rule = (Map<String, Object>)ruleObj;
                String ruleId   = (String)rule.get("id");
                String ruleType = (String)rule.get("type");
                Map<String, String> attributes = (Map<String, String>)rule.get("attributes");
                for (Map.Entry<String, String> attribute : attributes.entrySet()) {
                    if (ruleType.equals("AngleRule")) {
                        
                    }
                    else if (ruleType.equals("DistanceRule")) {
                        
                    }
                    else {
                        log.warn("RuleType is invalid: "+ruleType);
                    }
                }
            }
        }
        
        
        
        //parse it into a map
        
        //populate the Esper Handler with patterns and listeners.
        
        //Pass some information about the rules into the EventFactory
        
        //return the Event Factory so it can retrieve information from the Kinect.
        return new EventFactory();
    }
}