package com.lcsc.hackathon;

import com.lcsc.hackathon.events.*;
import com.lcsc.hackathon.listeners.*;

import com.espertech.esper.client.UpdateListener;

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
    
    public ConfigParser() {
    }
    
    public EventFactory parseConfigFile(String configPath, EsperHandler eHandler) {
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
        
        EventFactory eFactory = new EventFactory(eHandler);
        
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
                
                Map<String, String> attributes = (Map<String, String>)rule.get("attributes");
                if (ruleType.equals("AngleRule")) {
                    //int end1 = attributes.get("endpoint1");
                    //int end2 = attributes.get("endpoint2");
                    //int vertex = attributes.get("vertex");
                    
                    //AngleRule angRule = new AngleRule();
                    
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
                    
                    //DistanceRule distRule = new DistanceRule();
                    
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
            
            String pattern = "select ";
            Trigger trigger = null;
            
            //This is getting the trigger from the config file.
            Map<String, Object> triggerMap = (Map<String, Object>)gesture.get("trigger");
            String triggerType = (String)triggerMap.get("type");
            
            if (triggerType.equals("keyPress")) {
                List<Map<String, String>> keyDefs = new ArrayList<Map<String,String>>();
                
                Object[] keys = (Object[])triggerMap.get("keys");
                for (Object keyObj : keys) {
                    Map<String, String> key = (Map<String, String>)keyObj;
                    keyDefs.add(key);
                }
                
                trigger = new Trigger(triggerType, keyDefs);
            }
            else if (triggerType.equals("mouseMove")) {
                //TODO
            }
            
            //The gestureId will identify the trigger and be accessible in the pattern.
            Triggers.addTrigger(gestureId, trigger);
            pattern += gestureId+" as triggerId from pattern[";
            
            for (int i=0; i<rulePattern.size(); i++) {
                pattern += rulePattern.get(i);
                if (i != rulePattern.size()-1) {
                    pattern += " and ";
                }
                else {
                    pattern += "]";
                }
            }
            
            log.info("Pattern:\n"+pattern);
            
            //Sets up the patterns and listeners for this gesture!
            eHandler.setPattern(gestureId, pattern);
            if (triggerType.equals("keyPress")) {
                eHandler.addListener(gestureId, (UpdateListener)new KeyPress());
            }
            else if (triggerType.equals("mouseMove")) {
                //TODO
            }
        }
        
        
        //Pass some information about the rules into the EventFactory
        
        //return the Event Factory so it can retrieve information from the Kinect.
        return eFactory;
    }
}
