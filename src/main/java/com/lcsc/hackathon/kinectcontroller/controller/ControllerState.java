package com.lcsc.hackathon.kinectcontroller.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jake on 5/17/2015.
 * This state is loaded up by Javacc and contains the rules that are to be passed to Esper for this state.
 * Each state has their own gestures and respective reactions.
 */
public class ControllerState {
    public final String stateId;

    //This will hold all of the java bean rules that will be updated by the Kinect and given to Esper.
    //It maps a SHA256 hash to a java bean that generated the hash.
    //The hash makes it so that there are no duplicate rules for separate gestures.
    private Map<String, Object>     _rules;

    //This will hold the gestures that the above rules are meant for.
    //It maps a gestureId (user defined) to a Gesture object.
    //This is where the Esper Listeners will get reaction information for the gesture that was triggered.
    private Map<String, Gesture>    _gestures;

    public ControllerState(String stateId) {
        this.stateId = stateId;
        _rules = new HashMap<String, Object>();
    }
}
