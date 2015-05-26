package com.lcsc.hackathon.kinectcontroller.controller;

/**
 * Created by Jake on 5/17/2015.
 * This state is loaded up by Javacc and contains the rules that are to be passed to Esper for this state.
 * Each state has their own gestures and respective reactions.
 */
public class ControllerState {
    public final String stateId;
    public ControllerState(String stateId) {
        this.stateId = stateId;
    }
}
