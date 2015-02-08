package com.lcsc.hackathon.events;

import com.lcsc.hackathon.EsperHandler;
import com.lcsc.hackathon.Formulas;

import java.util.List;
import java.util.ArrayList;

import edu.ufl.digitalworlds.j4k.Skeleton;

import org.apache.log4j.Logger;

public class EventFactory {
    static Logger log = Logger.getRootLogger();
    
    //These lists of rules are instantiated based off of the config file.
    //They are meant to be updated periodically by the Kinect and then
    //passed to Esper to be processed.
    List<AngleRule> angleRules = new ArrayList<AngleRule>();
    List<DistanceRule> distRules = new ArrayList<DistanceRule>();
    
    public EventFactory() {
    }
    
    public void addAngleRule(AngleRule rule) {
        angleRules.add(rule);
    }
    
    public void addDistanceRule(DistanceRule rule) {
        distRules.add(rule);
    }
    
    public void getEventData(Skeleton skele, EsperHandler eHandler) {
        //loop through all of the angle/distRules and 
        for (AngleRule rule : this.angleRules) {
            //update them with information from the current skeleton.
            double[] end1 = skele.get3DJoint(rule.getEnd1());
            double[] end2 = skele.get3DJoint(rule.getEnd2());
            double[] vertex = skele.get3DJoint(rule.getVertex());
            
            rule.setAngle(Formulas.getAngle(end1, vertex, end2));
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceRule rule : this.distRules) {
            //update them with information from the current skeleton.
            double[] joint1 = skele.get3DJoint(rule.getJoint1());
            double[] joint2 = skele.get3DJoint(rule.getJoint2());
            
            double distance = Formulas.getDistance(joint1, joint2);
            log.info(String.format("Distance: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
    }
}