package com.lcsc.hackathon.kinectcontroller.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 6/2/2015.
 * A Gesture is basically just a place to put information about the gestures that were loaded into Esper.
 * The Esper listener classes are going to have to be using these to determine what key was pressed, which
 * joint is being tracked for the mouse and stuff like that.
 */
public class Gesture {
    public final String gestureId;

    //Keys that are to be pressed when this gesture is activated.
    private List<Integer> _keysPress;
    //Keys that are to be unselected when this gesture is activated.
    private List<Integer> _keysUp;
    //Keys that are to be pressed down when this gesture is activated.
    private List<Integer> _keysDown;

    //TODO Some identifier for the mouse needs to go hear. Left_Arm and Right_Arm?

    public Gesture(String gestureId) {
        this.gestureId  = gestureId;

        _keysPress      = new ArrayList<Integer>();
        _keysUp         = new ArrayList<Integer>();
        _keysDown       = new ArrayList<Integer>();
    }

    public void addKeysPress(List<Integer> keysPress) {
        _keysPress = keysPress;
    }

    public void addKeysUp(List<Integer> keysUp) {
        _keysUp = keysUp;
    }

    public void addKeysDown(List<Integer> keysDown) {
        _keysDown = keysDown;
    }

    public List<Integer> getKeysPress() {
        return _keysPress;
    }

    public List<Integer> getKeysUp() {
        return _keysUp;
    }

    public List<Integer> getKeysDown() {
        return _keysDown;
    }
}
