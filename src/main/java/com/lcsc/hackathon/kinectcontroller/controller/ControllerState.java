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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    //This will hold all of the java bean posturerules that will be updated by the KinectUserTracker and given to Esper.
    //These Rule objects will correspond with the types of Rules that we're looking for in the Esper patterns for this
    //ControllerState's gestures. So if an Esper pattern checks the angle property of an Angle rule with vertex=2, then
    //this container should hold an Angle object with vertex=2.
    //It maps a SHA256 hash to a java bean that generated the hash.
    //The hash makes it so that there are no duplicate posturerules for separate gestures.
    private Map<String, Rule>     _rules;

    //This will hold the gestures that the above posturerules are meant for.
    //It maps a gestureId (user defined) to a Gesture object.
    //This is where the Esper Listeners will get reaction information for the gesture that was triggered.
    private Map<String, Gesture>    _gestures;

    public ControllerState(String stateId, ControllerStateMachine csm) {
        this.stateId    = stateId;
        this.csm        = csm;
        _rules 			= new HashMap<>();
        _gestures       = new HashMap<>();
    }

    /**
     * This allows the config.jj file's generated code to add empty Rule objects into a container.
     * This container will keep track of the Rules that we're interested in sending to Esper. Only Rules
     * used in Esper patterns will be updated by the Kinect and sent to Esper!
     * @param rule This is an empty Rule object that probably identifies the skeleton joints the Rule is concerned with
     *             and some other information that will narrow down the information that needs to be pulled from the Kinect.
     */
    public void addRule(Object rule) {
        String ruleId = ((Rule)rule).getId();
        _rules.put(ruleId, (Rule)rule);
    }

    /**
     * This is for getting the posturerule event beans for the ControllerState so that they can be updated with Kinect data
     * and given to Esper.
     * @return A collection of the posturerule event beans. Cast each item with the Rule interface.
     */
    public Collection<Rule> getRules() {
        return _rules.values();
    }

    /**
     * This allows the config.jj file's generated code to add a gesture to this ControllerState. This gesture will define
     * some Esper pattern and will have some reaction that will be triggered when the pattern is matched.
     * @param gestureId The id of the gesture. It's used in the Esper pattern and lets the EventListener know which gesture
     *                  has been matched.
     * @param gesture   This is the actual Gesture object that's associated with the gestureId.
     */
    public void addGesture(String gestureId, Gesture gesture) {
        _gestures.put(gestureId, gesture);
    }

    /**
     * This just returns a Collection of gestures for this ControllerState. The ControllerStateMachine uses it
     * during the loading of Esper patterns, because the gestures' reactions need loaded into the EventListener.
     * @return
     */
    public Collection<Gesture> getGestures() {
        return _gestures.values();
    }
}
