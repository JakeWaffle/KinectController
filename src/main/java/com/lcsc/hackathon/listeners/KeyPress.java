package com.lcsc.hackathon.listeners;

import com.lcsc.hackathon.Triggers;
import com.lcsc.hackathon.Trigger;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import org.apache.log4j.Logger;

import java.awt.Robot;
import java.awt.AWTException;

public class KeyPress implements UpdateListener {
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
            Trigger trigger = Triggers.getTrigger(event.get("triggerId"));
            
            for (Map<String, String> attributes : trigger.getDefinition()) {
                String type = attributes.get("type");
                if (type == "KeyDownUp") {
                    this.rob.keyPress(Conversions.getKeyId(attributes.get("key")));
                    this.rob.keyRelease(Conversions.getKeyId(attributes.get("key")));
                }
                else if (type == "KeyDown") {
                    this.rob.keyPress(Conversions.getKeyId(attributes.get("key")));
                }
                else if (type == "KeyUp") {
                    this.rob.keyRelease(Conversions.getKeyId(attributes.get("key")));
                }
            }
        }
    }
}