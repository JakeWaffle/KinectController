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
    List<DistanceXRule> distXRules = new ArrayList<DistanceXRule>();
    List<DistanceYRule> distYRules = new ArrayList<DistanceYRule>();
    List<DistanceZRule> distZRules = new ArrayList<DistanceZRule>();
    List<AbsoluteDistance> absDistRules = new ArrayList<AbsoluteDistance>();
    List<AbsoluteDistX> absDistXRules = new ArrayList<AbsoluteDistX>();
    List<AbsoluteDistY> absDistYRules = new ArrayList<AbsoluteDistY>();
    List<AbsoluteDistZ> absDistZRules = new ArrayList<AbsoluteDistZ>();
    
    public EventFactory() {
    }
    
    public void addAngleRule(AngleRule rule) {
        angleRules.add(rule);
    }
    
    public void addDistanceRule(DistanceRule rule) {
        distRules.add(rule);
    }
    
    public void addDistXRules(DistanceXRule rule) {
        distXRules.add(rule);
    }
    
    public void addDistYRules(DistanceYRule rule) {
        distYRules.add(rule);
    }
    
    public void addDistZRules(DistanceZRule rule) {
        distZRules.add(rule);
    }
    
    public void addAbsDistRules(AbsoluteDistance rule) {
        absDistRules.add(rule);
    }
    
    public void addAbsDistXRules(AbsoluteDistX rule) {
        absDistXRules.add(rule);
    }
    
    public void addAbsDistYRules(AbsoluteDistY rule) {
        absDistYRules.add(rule);
    }
    
    public void addAbsDistZRules(AbsoluteDistZ rule) {
        absDistZRules.add(rule);
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
            //log.info(String.format("Distance: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceXRule rule : this.distXRules) {
            //update them with information from the current skeleton.
            double joint1 = skele.get3DJoint(rule.getJoint1())[0];
            double joint2 = skele.get3DJoint(rule.getJoint2())[0];
            
            double distance = Formulas.getDistanceX(joint1, joint2);
            //log.info(String.format("DistanceX: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceYRule rule : this.distYRules) {
            //update them with information from the current skeleton.
            double joint1 = skele.get3DJoint(rule.getJoint1())[1];
            double joint2 = skele.get3DJoint(rule.getJoint2())[1];
            
            double distance = Formulas.getDistanceY(joint1, joint2);
            //log.info(String.format("DistanceY: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceZRule rule : this.distZRules) {
            //update them with information from the current skeleton.
            double joint1 = skele.get3DJoint(rule.getJoint1())[2];
            double joint2 = skele.get3DJoint(rule.getJoint2())[2];
            
            double distance = Formulas.getDistanceZ(joint1, joint2);
            //log.info(String.format("DistanceZ: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (AbsoluteDistance rule : this.absDistRules) {
            //update them with information from the current skeleton.
            double[] point = rule.getAbsPoint();
            double[] joint = skele.get3DJoint(rule.getJointId());
            
            double distance = Formulas.getDistance(point, joint);
            log.info(String.format("AbsDistance: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (AbsoluteDistX rule : this.absDistXRules) {
            //update them with information from the current skeleton.
            double point = rule.getAbsPointX();
            double joint = skele.get3DJoint(rule.getJointId())[0];
            
            double distance = Formulas.getDistanceX(point, joint);
            //log.info(String.format("AbsDistanceX: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (AbsoluteDistY rule : this.absDistYRules) {
            //update them with information from the current skeleton.
            double point = rule.getAbsPointY();
            double joint = skele.get3DJoint(rule.getJointId())[1];
            
            double distance = Formulas.getDistanceY(point, joint);
            log.info(String.format("AbsDistanceY: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (AbsoluteDistZ rule : this.absDistZRules) {
            //update them with information from the current skeleton.
            double point = rule.getAbsPointZ();
            double joint = skele.get3DJoint(rule.getJointId())[2];
            
            double distance = Formulas.getDistanceZ(point, joint);
            //log.info(String.format("AbsDistanceZ: %f", distance));
            rule.setDistance(distance);
            
            //Then pass all of the rules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
    }
}