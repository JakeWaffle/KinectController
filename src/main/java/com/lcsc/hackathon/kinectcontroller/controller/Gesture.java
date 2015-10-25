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
import com.lcsc.hackathon.kinectcontroller.emulation.ReactionType;
import com.lcsc.hackathon.kinectcontroller.emulation.reactions.Reaction;

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

	public List<Reaction> getReactions() {
		List<Reaction> reactions = new ArrayList<Reaction>();
		
		//TODO Actually return some reaction objects.
		return reactions;
	}
}
