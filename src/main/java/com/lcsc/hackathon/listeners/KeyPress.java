package com.lcsc.hackathon.listeners;

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
        this.rob.keyPress(((Integer)newEvents[0].get("keyID")).intValue());
        this.rob.keyRelease(((Integer)newEvents[0].get("keyID")).intValue());
    }
}