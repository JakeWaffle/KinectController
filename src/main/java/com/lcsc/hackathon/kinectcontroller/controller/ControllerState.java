package com.lcsc.hackathon.kinectcontroller.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.lcsc.hackathon.kinectcontroller.posturerules.Rule;

/**
 * Created by Jake on 5/17/2015.
 * This state is loaded up by Javacc and contains the posturerules that are to be passed to Esper for this state.
 * Each state has their own gestures and respective emulation.
 */
public class ControllerState {
    public final String                 stateId;
    public final ControllerStateMachine csm;

    //This will hold all of the java bean posturerules that will be updated by the Kinect and given to Esper.
    //It maps a SHA256 hash to a java bean that generated the hash.
    //The hash makes it so that there are no duplicate posturerules for separate gestures.
    private Map<String, Object>     _rules;

    //This will hold the gestures that the above posturerules are meant for.
    //It maps a gestureId (user defined) to a Gesture object.
    //This is where the Esper Listeners will get reaction information for the gesture that was triggered.
    private Map<String, Gesture>    _gestures;

    public ControllerState(String stateId, ControllerStateMachine csm) {
        this.stateId    = stateId;
        this.csm        = csm;
        _rules = new HashMap<String, Object>();
    }

    public void addRule(Object rule) {
        String ruleId = ((Rule)rule).getId();
        _rules.put(ruleId, rule);
    }

    public void addGesture(String gestureId, Gesture gesture) {
        _gestures.put(gestureId, gesture);
    }

    public Collection<Gesture> getGestures() {
        return _gestures.values();
    }
}
