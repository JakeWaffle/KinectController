package com.lcsc.hackathon;

import com.lcsc.hackathon.listeners.KeyPress;
import com.lcsc.hackathon.events.AngleRule;

import org.apache.log4j.Logger;

public class Main {
    static Logger log = Logger.getRootLogger();
    
    public static void main(String[] args) {
        log.info("asdfasdf");
        EsperHandler eHandler = new EsperHandler();
        
        //This has the values for the KeyEvents
        //http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_PAGE_DOWN
        //VK_W = 87
        //eHandler.setPattern("pat1", "select 87 as keyID from AngleRule");
        //eHandler.addListener("pat1", new KeyPress());
        
        eHandler.setPattern("pat1", "select 87 as keyID from AngleRule");
        eHandler.addListener("pat1", new KeyPress());
        //eHandler.sendEvent(new AngleRule(1, 1, 1));
    }
}