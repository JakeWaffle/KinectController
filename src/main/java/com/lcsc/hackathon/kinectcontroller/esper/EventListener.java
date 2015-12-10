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

package com.lcsc.hackathon.kinectcontroller.esper;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import com.lcsc.hackathon.kinectcontroller.emulation.EmulationController;
import com.lcsc.hackathon.kinectcontroller.emulation.reactions.Reaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListener implements UpdateListener {
    private static final Logger             	        _logger = LoggerFactory.getLogger(EventListener.class);
    private              EmulationController	        _emulationController;
    //Maps a gestureId to a list of reactions.
    private              Map<String, List<Reaction>>    _reactions;

    public EventListener(EmulationController emualtionController) {
        _emulationController  	= emualtionController;
        _reactions				= new HashMap<String, List<Reaction>>();
    }

    /**
     * This is used to reset the reactions that are loaded into the EventListener. This will happen every time
     * the ControllerState has been changed in the ControllerStateMachine.
     */
    public void clearReactions() {
        _reactions.clear();
    }

    /**
     * This is for loading the valid reactions for the current ControllerState's gestures. This will happen every time
     * the ControllerState has been changed in the ControllerStateMachine.
     * @param gestureId The given reactions belong to the gesture identified by this id.
     * @param reactions These are the reactions that belong to some gesture. When the gesture is matched by
     *                  Esper, then these reactions will be looked up and triggered.
     */
    public void loadReactions(String gestureId, List<Reaction> reactions){
        _reactions.put(gestureId, reactions);
    }

    /**
     * Esper uses this method to inform the EventListener when a gesture has been matched.
     *
     * @param newEvents When a gesture's pattern is matched, Esper sends this method a newEvent.
     * @param oldEvents The previous newEvents that have already been processed.
     */
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        for (EventBean event : newEvents) {
			String gestureId = (String)event.get("gestureId");
            _logger.debug("Gesture Activated: "+gestureId);
            for (Reaction reaction : _reactions.get(gestureId)) {
                _emulationController.scheduleReaction(reaction);
            }
        }                           
    }
}