package com.lcsc.hackathon;

import com.lcsc.hackathon.listeners.KeyPress;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello team!");
        
        EsperHandler eHandler = new EsperHandler();
        
        eHandler.setPattern("pat1", "select keyID from AngleEvent");
        eHandler.addListener("pat1", new KeyPress());
    }
}