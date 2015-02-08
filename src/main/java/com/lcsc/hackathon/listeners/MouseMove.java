package com.lcsc.hackathon.listeners;

import com.lcsc.hackathon.events.DistanceXRule;
import com.lcsc.hackathon.events.DistanceYRule;

import com.lcsc.hackathon.Triggers;
import com.lcsc.hackathon.Trigger;

import java.util.Map;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import org.apache.log4j.Logger;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;

import java.lang.Thread;
import java.lang.InterruptedException;

public class MouseMove implements UpdateListener {
    static Logger log = Logger.getRootLogger();
    private Robot rob;
    
    public MouseMove() {
        try {
            this.rob = new Robot();
        } catch (AWTException e) {
            log.error("", e);
        }
    }
    
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        for (EventBean event : newEvents) {
            String direction = (String)event.get("direction");
            //log.info("DIrection: "+direction);
            if (direction.equals("LEFT")) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                int x = (int) b.getX();
                int y = (int) b.getY();
                
                log.info(String.format("X: %d Y: %d", x, y));
                
                rob.mouseMove(x-5, y);
            }
        }
    }
}