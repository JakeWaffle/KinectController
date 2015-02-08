package com.lcsc.hackathon;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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
            
            List<String> rulePattern = new ArrayList<String>();
            
            Object[] rules = (Object[])gesture.get("rules");
            for (Object ruleObj : rules) {
                Map<String, Object> rule = (Map<String, Object>)ruleObj;
                String ruleId   = (String)rule.get("id");
                String ruleType = (String)rule.get("type");
                
                if (!ruleType.equals("AngleRule") && !ruleType.equals("DistanceRule")) {
                    log.warn("RuleType is invalid: "+ruleType);
                }
                
                List<int> jointIds = new ArrayList<int>();
                
                Map<String, String> attributes = (Map<String, String>)rule.get("attributes");
                if (ruleType.equals("AngleRule")) {
                    //int end1 = attributes.get("endpoint1");
                    //int end2 = attributes.get("endpoint2");
                    //int vertex = attributes.get("vertex");
                    
                    //int minAngle = Integer.parseInt(attributes.get("min-angle"));
                    //int maxAngle = Integer.parseInt(attributes.get("max-angle"));
                    
                    /*
                    rulePattern.add(String.format("AngleRule(end1=%d, vertex=%d, end2=%d, angle > %d, angle < %d)", end1, 
                                                                                                                    vertex, 
                                                                                                                    end2, 
                                                                                                                    minAngle,
                                                                                                                    maxAngle));
                    */
                }
                else if (ruleType.equals("DistanceRule")) {
                    //int end1 = attributes.get("endpoint1");
                    //int end2 = attributes.get("endpoint2");
                    
                    //int minDist = Integer.parseInt(attributes.get("min-dist"));
                    //int maxDist = Integer.parseInt(attributes.get("max-dist"));
                    
                    /*
                    rulePattern.add(String.format("DistanceRule(joint1=%d, joint2=%d, distance > %d, distance < %d)", end1,
                                                                                                          end2,
                                                                                                          minDist,
                                                                                                          maxDist));
                    */
                }
                else {
                    log.warn("RuleType is invalid: "+ruleType);
                }
            }
        }
        
        //populate the Esper Handler with patterns and listeners.
        
        //Pass some information about the rules into the EventFactory
        
        //return the Event Factory so it can retrieve information from the Kinect.
        return new EventFactory();
    }
}
