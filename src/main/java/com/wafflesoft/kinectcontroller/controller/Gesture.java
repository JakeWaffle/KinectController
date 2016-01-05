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

package com.wafflesoft.kinectcontroller.controller;

import com.wafflesoft.kinectcontroller.emulation.reactions.persistent.PersistentReaction;
import com.wafflesoft.kinectcontroller.emulation.reactions.hapaxlegomenon.Reaction;
import com.wafflesoft.kinectcontroller.posturerules.Rule;
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
    private        final ControllerState    _state;

    //These are a bunch of pieces of the overall gesture query. Each piece is specifically for
    //a rule that makes up the gesture. These will be able to be assembled into a complete gesture query for esper
    //when needed.
    private List<String>                    _ruleConditions;
    private List<String>                    _negatedRuleConditions;
    private List<Reaction> 	                _reactions;
    private List<PersistentReaction>        _persistentReactions;

    public Gesture(String gestureId, ControllerState state) {
        this.gestureId          = gestureId;
        _state = state;
        _ruleConditions         = new ArrayList<String>();
        _negatedRuleConditions  = new ArrayList<String>();
        _reactions              = new ArrayList<Reaction>();
        _persistentReactions    = new ArrayList<PersistentReaction>();
    }

    /**
     *  - The Rule bean is what gets updated with data and is then given to Esper as an "event".
     *      Each Rule bean is probably unique, because it could have one of many posturerules class' and
     *      the default properties are going to define what data is retrieved from the user's skeleton data
     *      -- which is available through OpenNI and Nite.
     *
     *  - The Esper pattern is what is used to detect when an event is in a "matched" _state. It needs knowledge of the
     *      Rule beans so that
     *
     * So this basically just adds a Rule bean object to its ControllerState's list of event beans and then stores the
     * matched/unmatched conditions for the Esper pattern.
     *
     * @param rule                  This is an event bean object that will be updated with data and given to Esper. If
     *                              an identical event bean object exists, it will be used instead of this one and will be
     *                              returned.
     * @param ruleCondition         This denotes the rule's matched condition. When this condition is true, the rule is true.
     * @param negatedRuleCondition  This condition is true, when the rule is false.
     * @return If the given rule is a duplicate Rule object, then the existing Rule object will be returned.
     * Otherwise the given rule object will be returned.
     * (The Rule object that is in the ControllerState's _rules list will be returned.)
     */
    public Rule addRule(Rule rule, String ruleCondition, String negatedRuleCondition) {
        Rule existingRule = _state.getRule(rule.getId());
        if (existingRule == null) {
            _state.addRule(rule);
            existingRule = rule;
        }
        _ruleConditions.add(ruleCondition);
        _negatedRuleConditions.add(negatedRuleCondition);
        return existingRule;
    }

    /**
     * This adds a reaction to the gesture. When the gesture's Esper pattern is matched, this reaction will be triggered
     * by the EmulationController.
     * @param reaction A regular, one use reaction.
     */
	public void addReaction(Reaction reaction) {
		_reactions.add(reaction);
	}

    /**
     * This adds a reaction to the gesture. When the gesture's Esper pattern is matched, this reaction will be triggered
     * by the EmulationController.
     * @param reaction A persisting reaction.
     */
    public void addPersistentReaction(PersistentReaction reaction) {
        _persistentReactions.add(reaction);
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
    /**
     * This allows the ControllerStateMachine to load this Gesture's persistent reactions up into the EventListener.
     * @return A list of persistent reactions that should be triggered when this gesture's Esper pattern has been matched.
     */
    public List<PersistentReaction> getPersistentReactions() {
        return _persistentReactions;
    }

}
