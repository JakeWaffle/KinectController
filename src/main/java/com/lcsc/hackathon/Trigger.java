package com.lcsc.hackathon;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Trigger {
    private String type;
    private List<Map<String, String>> definition;
    
    public Trigger(String type, List<Map<String, String>> defintion) {
        this.type = type;
        this.definition = definition;
    }
}