package com.lcsc.hackathon.listeners;

import com.lcsc.hackathon.Triggers;
import com.lcsc.hackathon.Trigger;

import java.util.Map;

import com.lcsc.hackathon.Conversions;

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
            String triggerId = (String)event.get("triggerId");
            //log.info(String.format("TriggerId: %s", triggerId));
            Trigger trigger = Triggers.getTrigger(triggerId);
            
            for (Map<String, String> attributes : trigger.getDefinition()) {
                String type = attributes.get("type");
                if (type.equals("KeyDownUp")) {
                    log.info(String.format("\n\nKeyPressRelease: %s %d\n\n", attributes.get("key"), Conversions.getKeyId(attributes.get("key"))));
                    this.rob.keyPress(Conversions.getKeyId(attributes.get("key")));
                    this.rob.keyRelease(Conversions.getKeyId(attributes.get("key")));
                }
                else if (type.equals("KeyDown")) {
                    log.info(String.format("\n\nKeyPress: %s %d\n\n", attributes.get("key"), Conversions.getKeyId(attributes.get("key"))));
                    this.rob.keyPress(Conversions.getKeyId(attributes.get("key")));
                }
                else if (type.equals("KeyUp")) {
                    log.info(String.format("\n\nKeyRelease: %s %d\n\n", attributes.get("key"), Conversions.getKeyId(attributes.get("key"))));
                    this.rob.keyRelease(Conversions.getKeyId(attributes.get("key")));
                }
                else {
                    log.error(String.format("Invalude Tigger Type: %s", type));
                }
            }
        }
    }
}