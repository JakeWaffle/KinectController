/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

The Kinect Controller makes use of the J4K library and Esper and we have done
nothing to change their source.

By using J4K we are required to site their research article:
A. Barmpoutis. 'Tensor Body: Real-time Reconstruction of the Human Body and Avatar Synthesis from RGB-D',
IEEE Transactions on Cybernetics, Special issue on Computer Vision for RGB-D Sensors: Kinect and Its
Applications, October 2013, Vol. 43(5), Pages: 1347-1356.

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

import com.espertech.esper.client.UpdateListener;
import com.lcsc.hackathon.kinectcontroller.EsperHandler;
import com.lcsc.hackathon.kinectcontroller.emulation.EmulationController;
import com.lcsc.hackathon.kinectcontroller.esperlisteners.EventListener;

import java.util.Collection;
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
    private ControllerState                 _curState 		= null;
    private EsperHandler                    _esperHandler 	= new EsperHandler();
	private EventListener					_eventListener;
	private EmulationController				_emulationController;

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
     * This will change the current state of the controller.
     * @param stateId This is the id of the state we're switching to.
     * @return True if the stateId existed, false if it doesn't exist in the state machine.
     */
    public boolean changeState(String stateId) {
        boolean success = false;
        if (_states.containsKey(stateId)) {
			_emulationController.stop();
            _curState   = _states.get(stateId);
			_emulationController.start();
            loadGestures();
            success     = true;
        }
        return success;
    }

    /**
     * This will load gestures into Esper using the current state's Gestures.
     */
    private void loadGestures() {
        Collection<Gesture> gestures = _curState.getGestures();
        for (Gesture gesture : gestures) {
            _esperHandler.setPattern(gesture.gestureId, gesture.getEsperQuery());
			//TODO Use gesture.getReactions() to load reactions into the eventlistener!
            _esperHandler.addListener(gesture.gestureId, _emulationController);
        }
    }
    /**
     * @return This returns the current state of the state machine.
     */
    public ControllerState state() {
        return _curState;
    }
}
