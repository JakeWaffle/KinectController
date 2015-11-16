/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.lcsc.hackathon.kinectcontroller.posturerules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

public class EventFactory {
    private static final Logger _logger      = LoggerFactory.getLogger(EventFactory.class);
    
    //These lists of posturerules are instantiated based off of the config file.
    //They are meant to be updated periodically by the KinectUserTracker and then
    //passed to Esper to be processed.
    List<Angle> angleRules = new ArrayList<Angle>();
    List<Distance> distRules = new ArrayList<Distance>();
    List<DistanceX> distXRules = new ArrayList<DistanceX>();
    List<DistanceY> distYRules = new ArrayList<DistanceY>();
    List<DistanceZ> distZRules = new ArrayList<DistanceZ>();
    List<DistanceFromPoint> absDistRules = new ArrayList<DistanceFromPoint>();
    List<PositionX> absDistXRules = new ArrayList<PositionX>();
    List<PositionY> absDistYRules = new ArrayList<PositionY>();
    List<PositionZ> absDistZRules = new ArrayList<PositionZ>();
    
    public EventFactory() {
    }
    
    public void addAngleRule(Angle rule) {
        angleRules.add(rule);
    }
    
    public void addDistanceRule(Distance rule) {
        distRules.add(rule);
    }
    
    public void addDistXRules(DistanceX rule) {
        distXRules.add(rule);
    }
    
    public void addDistYRules(DistanceY rule) {
        distYRules.add(rule);
    }
    
    public void addDistZRules(DistanceZ rule) {
        distZRules.add(rule);
    }
    
    public void addAbsDistRules(DistanceFromPoint rule) {
        absDistRules.add(rule);
    }
    
    public void addAbsDistXRules(PositionX rule) {
        absDistXRules.add(rule);
    }
    
    public void addAbsDistYRules(PositionY rule) {
        absDistYRules.add(rule);
    }
    
    public void addAbsDistZRules(PositionZ rule) {
        absDistZRules.add(rule);
    }

    /*
    //TODO Modify this to work with the new KinectUserTracker SDK
    public void getEventData(Skeleton skele, EsperHandler eHandler) {
        //loop through all of the angle/distRules and 
        for (AngleRule rule : this.angleRules) {
            //update them with information from the current skeleton.
            double[] end1 = skele.get3DJoint(rule.getEnd1());
            double[] end2 = skele.get3DJoint(rule.getEnd2());
            double[] vertex = skele.get3DJoint(rule.getVertex());
            double angle = Formulas.getAngle(end1, vertex, end2);
            //log.info(String.format("Angle: %f", angle));
            double oldAngle = rule.getAngle();
            rule.setAngle(angle*0.5 + oldAngle*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceRule rule : this.distRules) {
            //update them with information from the current skeleton.
            double[] joint1 = skele.get3DJoint(rule.getJoint1());
            double[] joint2 = skele.get3DJoint(rule.getJoint2());
            
            double distance = Formulas.get_distance(joint1, joint2);
            //log.info(String.format("Distance: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceXRule rule : this.distXRules) {
            //update them with information from the current skeleton.
            double joint1 = skele.get3DJoint(rule.getJoint1())[0];
            double joint2 = skele.get3DJoint(rule.getJoint2())[0];
            
            double distance = Formulas.getDistanceX(joint1, joint2);
            //log.info(String.format("DistanceX: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceYRule rule : this.distYRules) {
            //update them with information from the current skeleton.
            double joint1 = skele.get3DJoint(rule.getJoint1())[1];
            double joint2 = skele.get3DJoint(rule.getJoint2())[1];
            
            double distance = Formulas.getDistanceY(joint1, joint2);
            //log.info(String.format("DistanceY: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceZRule rule : this.distZRules) {
            //update them with information from the current skeleton.
            double joint1 = skele.get3DJoint(rule.getJoint1())[2];
            double joint2 = skele.get3DJoint(rule.getJoint2())[2];
            
            double distance = Formulas.getDistanceZ(joint1, joint2);
            //log.info(String.format("DistanceZ: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (DistanceFromPoint rule : this.absDistRules) {
            //update them with information from the current skeleton.
            double[] point = rule.getAbsPoint();
            double[] joint = skele.get3DJoint(rule.get_jointId());
            
            double distance = Formulas.get_distance(point, joint);
            //log.info(String.format("AbsDistance: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (PositionX rule : this.absDistXRules) {
            //update them with information from the current skeleton.
            double point = rule.getAbsPointX();
            double joint = skele.get3DJoint(rule.get_jointId())[0];
            
            double distance = Formulas.getDistanceX(point, joint);
            //log.info(String.format("AbsDistanceX: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (PositionY rule : this.absDistYRules) {
            //update them with information from the current skeleton.
            double point = rule.getAbsPointY();
            double joint = skele.get3DJoint(rule.get_jointId())[1];
            
            double distance = Formulas.getDistanceY(point, joint);
            //log.info(String.format("AbsDistanceY: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
        
        for (PositionZ rule : this.absDistZRules) {
            //update them with information from the current skeleton.
            double point = rule.getAbsPointZ();
            double joint = skele.get3DJoint(rule.get_jointId())[2];
            
            double distance = Formulas.getDistanceZ(point, joint);
            //log.info(String.format("AbsDistanceZ: %f", distance));
            double oldDist = rule.get_distance();
            rule.set_distance(distance*0.5 + oldDist*.5);
            
            //Then pass all of the posturerules to eHandler using sendEvent().
            eHandler.sendEvent(rule);
        }
    }*/
}