package com.lcsc.hackathon.events;

import com.lcsc.hackathon.EsperHandler;

import java.util.List;
import java.util.ArrayList;

public class EventFactory {
    List<AngleRule> angleRules = new ArrayList<AngleRule>();
    List<DistanceRule> distRules = new ArrayList<DistanceRule>();
    
    EsperHandler eHandler;
    
    public EventFactory(EsperHandler eHandler) {
        this.eHandler = eHandler;
    }
    
    public void addAngleRule(AngleRule rule) {
        angleRules.add(rule);
    }
    
    public void addDistanceRule(DistanceRule rule) {
        distRules.add(rule);
    }
    
    
}