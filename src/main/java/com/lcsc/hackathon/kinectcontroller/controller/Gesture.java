package com.lcsc.hackathon.kinectcontroller.controller;

import com.espertech.esper.client.UpdateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 6/2/2015.
 * A Gesture is basically just a place to put information about the gestures that were loaded into Esper.
 * The Esper listener classes are going to have to be using these to determine what key was pressed, which
 * joint is being tracked for the mouse and stuff like that.
 */
public class Gesture {
    public final String             gestureId;
    public final ControllerState    state;

    //These are a bunch of pieces of the overall gesture query. Each piece is specifically for
    //a rule that makes up the gesture. These will be able to be assembled into a complete gesture query for esper
    //when needed.
    private List<String>  _ruleQueries;

    //Keys that are to be pressed when this gesture is activated.
    private List<Integer> _keysPress;
    //Keys that are to be unselected when this gesture is activated.
    private List<Integer> _keysUp;
    //Keys that are to be pressed down when this gesture is activated.
    private List<Integer> _keysDown;

    //TODO Some identifier for the mouse needs to go hear. Left_Arm and Right_Arm?

    public Gesture(String gestureId, ControllerState state) {
        this.gestureId  = gestureId;
        this.state      = state;
        _ruleQueries    = new ArrayList<String>();
        _keysPress      = new ArrayList<Integer>();
        _keysUp         = new ArrayList<Integer>();
        _keysDown       = new ArrayList<Integer>();
    }

    public void addRuleQuery(String esperQuery) {
        _ruleQueries.add(esperQuery);
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

    /**
     * This will bundle up all of the separate _ruleQueries pieces for Esper.
     * @return Some sort of sql query for Esper that has the gestureId of this Gesture
     *         so the Listener can look up this Gesture's reactions.
     */
    public String getEsperQuery() {
        String query = String.format("%s as gestureId from pattern[", gestureId);

        for (int i=0; i<_ruleQueries.size(); i++) {
            query += _ruleQueries.get(i);
            if (i != _ruleQueries.size()-1) {
                query += " and ";
            }
            else {
                query += "]";
            }
        }

        return query;
    }

    //TODO Instantiate a bunch of UpdateListeners and return them back to the ControllerStateMachine.
    //TODO Don't forget to pass the ControllerState to the UpdateListener.
    public List<UpdateListener> getUpdateListeners() {
        return null;
    }
}
