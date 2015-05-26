package com.lcsc.hackathon.kinectcontroller.controller;

import com.lcsc.hackathon.kinectcontroller.controller.ControllerState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jake on 5/17/2015.
 * This will keep track of the different states of the controller. Each state should represent a different
 * area within the game where the controls possibly change.
 */
public class ControllerStateMachine {
    //This maps a state id to a Controller state. This mapping is defined in the config file.
    private Map<String, ControllerState>    _states;
    private ControllerState                 _curState = null;

    public ControllerStateMachine() {
        _states = new HashMap<String, ControllerState>();
    }

    /**
     * This interface allows Javacc to pass in ControllerStates into the state machine.
     * @param newState A state containing all the information needed for handling a particular state of the Controller.
     */
    public void addState(ControllerState newState) {
        _states.put(newState.stateId, newState);
    }


    /**
     * This will change the current state of the controller.
     * @param stateId This is the id of the state we're switching to.
     * @return True if the stateId existed, false if it doesn't exist in the state machine.
     */
    public boolean changeState(String stateId) {
        boolean success = false;
        if (_states.containsKey(stateId)) {
            _curState   = _states.get(stateId);
            success     = true;
        }
        return success;
    }

    /**
     * @return This returns the current state of the state machine.
     */
    public ControllerState state() {
        return _curState;
    }
}
