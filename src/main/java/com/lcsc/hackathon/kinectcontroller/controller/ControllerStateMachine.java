/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.lcsc.hackathon.kinectcontroller.controller;

import com.lcsc.hackathon.kinectcontroller.esper.EsperHandler;
import com.lcsc.hackathon.kinectcontroller.emulation.EmulationController;
import com.lcsc.hackathon.kinectcontroller.esper.EventListener;
import com.lcsc.hackathon.kinectcontroller.posturerules.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jake on 5/17/2015.
 * This will keep track of the different states of the controller. Each state should represent a different
 * area within the game where the controls possibly change.
 */
public class ControllerStateMachine {
    public final    EsperHandler                    esperHandler 	= new EsperHandler();

    //This maps a state id to a Controller state. This mapping is defined in the config file.
    private         Map<String, ControllerState>    _states;
    private         ControllerState                 _curState 		= null;

	private         EventListener					_eventListener;
	private         EmulationController				_emulationController;

    public ControllerStateMachine() {
        _states 				= new HashMap<String, ControllerState>();
		_emulationController	= new EmulationController();
		_eventListener			= new EventListener(_emulationController);
    }

    /**
     * This interface allows Javacc to pass in ControllerStates into the state machine.
     * @param newState A state containing all the information needed for handling a particular state of the Controller.
     */
    public void addState(ControllerState newState) {
        _states.put(newState.stateId, newState);
    }

    /**
     * This will change the current state of the controller. Javacc will call this to set the initial state of
	 * the program.
     * @param stateId This is the id of the state we're switching to.
     * @return True if the stateId existed, false if it doesn't exist in the state machine.
     */
    public boolean changeState(String stateId) {
        boolean success = false;
        if (_states.containsKey(stateId)) {
			_emulationController.done();
            _curState   = _states.get(stateId);
			_emulationController.start();
            loadGestures();
            success     = true;
        }
        return success;
    }

    /**
     * The csm needs to be a mediator between the current state and the KinectUserTracker. That way there are no problems
     * when the state changes.
     * @return A collection of the posturerule event beans. Cast each item with the Rule interface.
     */
    public Collection<Rule> getCurrentRules() {
        return _curState.getRules();
    }

    /**
     * This will load gestures into Esper using the current state's Gestures.
     */
    private void loadGestures() {
        Collection<Gesture> gestures = _curState.getGestures();
        for (Gesture gesture : gestures) {
            esperHandler.setPattern(gesture.gestureId, gesture.getEsperQuery());
            _eventListener.loadReactions(gesture.gestureId, gesture.getReactions());
            esperHandler.addListener(gesture.gestureId, _eventListener);
        }
    }

    /**
     * @return This returns the current state of the state machine.
     */
    public ControllerState state() {
        return _curState;
    }
}
