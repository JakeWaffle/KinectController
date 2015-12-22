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

package com.wafflesoft.kinectcontroller.emulation.reactions;

import com.wafflesoft.kinectcontroller.emulation.reactions.config.ReactionConfig;

/**
 * Created by jake on 10/15/2015.
 * A very important interface that is used to interact with the many types of Reaction
 * objects. Reaction objects are capable of doing many different things in reaction to a gesture
 * that has been triggered.
 */
public interface Reaction {
    /**
     * @return A Map class with a special constructor that standardizes the data requirements for their
     * respective Reaction classes. Each Reaction's config Map will have the same keys no matter what!
     */
	ReactionConfig getConfig();

    /**
     * This is called to actually trigger the reaction. It doesn't matter what the Reaction does. The EmulationController
     * is only concerned with triggering the reaction.
     */
    void trigger();
}
