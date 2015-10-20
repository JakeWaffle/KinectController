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

package com.lcsc.hackathon.kinectcontroller.esperlisteners;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import com.lcsc.hackathon.kinectcontroller.emulation.EmulationController;
import com.lcsc.hackathon.kinectcontroller.emulation.reactions.Reaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//////////////////////////////////////////////////////////////////////
//!!!TODO Create a SINGLE listener that checks for some event and tells the EmulationController when needed.
//
//This could work by having the UpdateListener keep track of a dictionary of Reaction objects. Then
//the patterns could reference each of those Reaction objects by their id. Reaction objects should be loaded
//into the singleton every time a ControllerState has been switched to.
//////////////////////////////////////////////////////////////////////


public class EventListener implements UpdateListener {
    private static final Logger             	_logger = LoggerFactory.getLogger(KeyPress.class);
    private              EmulationController	_emulationController;
    private              Map<String, Reaction>  _reactions;
    
	//TODO Support a sequence of reactions for any given UpdateListener
    public KeyPress(EmulationController emualtionController) {
        _emulationController  	= emualtionController;
        _reactions				= new HashMap<String, Reaction>();
    }
	
	public void addReaction(String reactionId, Reaction reaction) {
		_reactions.put(reactionId, reaction);
	}

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        for (EventBean event : newEvents) {
			String reactionId = (String)event.get("reactionId");
			_emulationController.scheduleReaction(_reactions.get(reactionId));
        }
    }
}