package com.lcsc.hackathon.kinectcontroller.controller;

import com.espertech.esper.client.UpdateListener;
import com.lcsc.hackathon.kinectcontroller.emulation.ReactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //TODO We need a better way to store/handle these (another class?)
    private Map<ReactionType, String> _reactions;

    //TODO Some identifier for the mouse needs to go hear. Left_Arm and Right_Arm?

    public Gesture(String gestureId, ControllerState state) {
        this.gestureId  = gestureId;
        this.state      = state;
        _ruleQueries    = new ArrayList<String>();
        _reactions      = new HashMap<ReactionType, String>();
    }

    public void addRuleQuery(String esperQuery) {
        _ruleQueries.add(esperQuery);
    }

    public void addKeyReaction(String keyId, ReactionType reactionType) {
        _reactions.put(reactionType, keyId);
    }

    /**
     * This will bundle up all of the separate _ruleQueries pieces for Esper.
     * @return Some sort of sql query for Esper that has the gestureId of this Gesture
     *         so the Listener can look up this Gesture's emulation.
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
        List<UpdateListener> listeners = new ArrayList<UpdateListener>();
        for (Map.Entry<ReactionType, String> reaction : _reactions.entrySet()){
            switch (reaction.getKey()) {
                case KEY_DOWN_UP:

                    break;
                case KEY_DOWN:
                    break;
                case KEY_UP:
                    break;
            }
        }
        return listeners;
    }
}
