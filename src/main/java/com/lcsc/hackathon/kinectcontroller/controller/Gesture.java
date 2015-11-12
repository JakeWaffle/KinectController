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

import com.lcsc.hackathon.kinectcontroller.emulation.reactions.Reaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 6/2/2015.
 * A Gesture is basically just a place to put information about the gestures that were loaded into Esper.
 * The Esper listener classes are going to have to be using these to determine what key was pressed, which
 * joint is being tracked for the mouse and stuff like that.
 */
public class Gesture {
    private static final Logger             _logger      = LoggerFactory.getLogger(Gesture.class);
    public         final String             gestureId;
    public         final ControllerState    state;

    //These are a bunch of pieces of the overall gesture query. Each piece is specifically for
    //a rule that makes up the gesture. These will be able to be assembled into a complete gesture query for esper
    //when needed.
    private 	 List<String>       _ruleConditions;
    private 	 List<String>       _negatedRuleConditions;
    private 	 List<Reaction> 	_reactions;

    //TODO Some identifier for the mouse needs to go hear. Left_Arm and Right_Arm?

    public Gesture(String gestureId, ControllerState state) {
        this.gestureId          = gestureId;
        this.state              = state;
        _ruleConditions         = new ArrayList<String>();
        _negatedRuleConditions  = new ArrayList<String>();
        _reactions              = new ArrayList<Reaction>();
    }

    /**
     * This will add a Rule to the Esper pattern for this gesture.
     * The Rule is made up of a positive and negative part.
     * @param ruleCondition         This denotes the rule's matched condition. When this condition is true, the rule is true.
     * @param negatedRuleCondition  This condition is true, when the rule is false.
     */
    public void addRuleToEsperPattern(String ruleCondition, String negatedRuleCondition) {
        _ruleConditions.add(ruleCondition);
        _negatedRuleConditions.add(negatedRuleCondition);
    }

    /**
     * This adds a reaction to the gesture. When the gesture's Esper pattern is matched, this reaction will be triggered
     * by the EmulationController.
     * @param reaction
     */
	public void addReaction(Reaction reaction) {
		_reactions.add(reaction);
	}

    /**
     * This will bundle up all of the separate _ruleQueries pieces for Esper.
     * @return Some sort of sql query for Esper that has the gestureId of this Gesture
     *         so the Listener can look up this Gesture's emulation.
     */
    public String getEsperPattern() {
        String pattern = null;
        if (_ruleConditions.size() > 0 && _negatedRuleConditions.size() > 0) {
            pattern = String.format("select '%s' as gestureId from pattern[every ((", gestureId);

            for (int i = 0; i < _negatedRuleConditions.size(); i++) {
                pattern += _negatedRuleConditions.get(i);
                if (i != _negatedRuleConditions.size() - 1) {
                    pattern += " or ";
                } else {
                    pattern += ") -> (";
                }
            }

            for (int i = 0; i < _ruleConditions.size(); i++) {
                pattern += _ruleConditions.get(i);
                if (i != _ruleConditions.size() - 1) {
                    pattern += " and ";
                } else {
                    pattern += ")) where timer:within(1 sec)]";
                }
            }
        }
        else {
            _logger.warn(String.format("No Rule Queries were loaded into Gesture: %s", gestureId));
        }
        _logger.debug("EsperQuery: "+pattern);

        return pattern;
    }

    /**
     * This allows the ControllerStateMachine to load this Gesture's reactions up into the EventListener.
     * @return A list of reactions that should be triggered when this gesture's Esper pattern has been matched.
     */
	public List<Reaction> getReactions() {
		return _reactions;
	}
}
