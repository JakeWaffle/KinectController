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

package com.wafflesoft.kinectcontroller.emulation;

import com.lcsc.hackathon.kinectcontroller.emulation.reactions.Reaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jake on 10/5/2015.
 * Esper's UpdateListeners will essentially tell this class that some reactions need to be emulated on the
 * keyboard, mouse or whatever else. Reactions may take several steps to emulate correctly though, so it may take
 * some reactions a few program cycles to complete. Each of the steps will be called in different program cycles
 * essentially.
 *
 * A queue also gives us the chance to allow a lazy execution just in case the user's computer may not be as fast
 * as we might hope. A priority queue could be used to improve the delay also.
 */
public class EmulationController extends Thread {
	private static final Logger 			_logger = LoggerFactory.getLogger(EmulationController.class);
    private 			 boolean         	_done;
	private 			 boolean         	_paused;
    private 			 Queue<Reaction> 	_reactions;

	//TODO Do we need persisting reactions that can be interacted with over time?

    public EmulationController() {
        _done       = false;
		_paused		= false;
        _reactions  = new ConcurrentLinkedQueue<Reaction>();
    }

    /**
     * This class will execute the tasks in the reaction queue.
     */
	@Override
    public void run() {
		_done = false;
        while (!_done) {
			if (_reactions.size() > 0) {
				Reaction reaction = _reactions.remove();
				reaction.trigger();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					_logger.error("Interrupted sleep", e);
				}
			}
			else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					_logger.error("Interrupted sleep", e);
				}
			}
        }
    }
	
	public synchronized void done() {
		_done = true;
	}
	
	/**
	 * Adds a reaction to the queue of reactions that are to be executed. The EventListener uses this to add reactions
	 * of the matched gestures to the reaction queue.
	 *
	 * @param reaction The reaction that is to be scheduled.
	 */
	public synchronized void scheduleReaction(Reaction reaction) {
		_reactions.add(reaction);
	}
}