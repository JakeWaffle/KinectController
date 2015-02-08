package com.lcsc.hackathon;

import java.util.Map;
import java.util.HashMap;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class EsperHandler {
    private EPServiceProvider engine;
    private Map<String, EPStatement> patterns;
    
    public EsperHandler() {
        this.patterns = new HashMap<String, EPStatement>();
        config();
        
        //Register all of the classes here.
        //this.engine.setEventType(alias, className);
    }
    
    private void config() {
        Configuration config = new Configuration();
        config.getEngineDefaults().getExecution().setPrioritized(true);
        config.getEngineDefaults().getEventMeta().setDefaultEventRepresentation(Configuration.EventRepresentation.MAP);
        config.addEventType("AngleRule", "com.lcsc.hackathon.events.AngleRule");
        config.addEventType("DistanceRule", "com.lcsc.hackathon.events.DistanceRule");
        config.addEventType("DistanceXRule", "com.lcsc.hackathon.events.DistanceXRule");
        config.addEventType("DistanceYRule", "com.lcsc.hackathon.events.DistanceYRule");
        config.addEventType("DistanceZRule", "com.lcsc.hackathon.events.DistanceZRule");
        config.addEventType("AbsoluteDistZ", "com.lcsc.hackathon.events.AbsoluteDistZ");
        config.addEventType("AbsoluteDistX", "com.lcsc.hackathon.events.AbsoluteDistX");
        config.addEventType("AbsoluteDistY", "com.lcsc.hackathon.events.AbsoluteDistY");
        config.addEventType("AbsoluteDistance", "com.lcsc.hackathon.events.AbsoluteDistance");
        
        this.engine = EPServiceProviderManager.getDefaultProvider(config);
        this.engine.initialize();
    }
    
    public void setPattern(String patternId, String pattern) {
        EPStatement statement = this.engine.getEPAdministrator().createEPL(pattern);
        
        this.patterns.put(patternId, statement);
    }
    
    public void addListener(String patternId, UpdateListener listener) {
        this.patterns.get(patternId).addListener(listener);
    }
    
    public void sendEvent(Object event) {
        this.engine.getEPRuntime().sendEvent(event);
    }
}