package com.lcsc.hackathon.listeners;

import com.lcsc.hackathon.Triggers;
import com.lcsc.hackathon.Trigger;

import java.util.Map;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import org.apache.log4j.Logger;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;

import java.lang.Thread;
import java.lang.InterruptedException;

public class MouseMove implements UpdateListener {
    static Logger log = Logger.getRootLogger();
    private Robot rob;
    
    public KeyPress() {
        try {
            this.rob = new Robot();
        } catch (AWTException e) {
            log.error("", e);
        }
    }
    
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        for (EventBean event : newEvents) {
            String triggerId = (String)event.get("triggerId");
            log.info("mouse moving!");
        }
    }
}