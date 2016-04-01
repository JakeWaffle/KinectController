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

package com.wafflesoft.kinectcontroller;

import com.primesense.nite.JointType;

import java.awt.event.*;
import java.lang.reflect.*;

public class Conversions {
	
	public Conversions() {
    }

	/**
	 * This is to convert the keyIds that are defined in the .gdef config file into an integer that's compatible
	 * with the Robot module.
	 * @param key This string can be the NAME of any field defined within the java.awt.event.KeyEvent class, assuming
	 *            that it has a "VK_" prefix (e.g. VK_COLON, VK_ENTER, VK_TAB.) DO NOT include the "VK_" prefix when
	 *            passing your key name to this function! The key name is also case insensitive (e.g. these key names are
	 *            valid: 'ENTER', 'enter', 'EnTer', 'Back_Space'.)
	 * @return An integer that conforms to the fields within java.awt.event.KeyEvent for the given key. Or if the given
	 * key name doesn't match the name of a field defined within java.awt.event.KeyEvent, then -1 is returned -- this means
	 * that getKeyId() failed to retrieve a key id.
	 */
	public static int getKeyId(String key) {
		String fieldName = String.format("VK_%s", key.toUpperCase());
		
		int value = -1;
		try {
			value = KeyEvent.class.getDeclaredField(fieldName).getInt(KeyEvent.class);
		} catch (Exception e) {
			// if the specified object is not an instance of the class or
			// interface declaring the underlying field (or a subclass or
			// implementor thereof)
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This allows the user's config file to use words instead of numbered joint ids. The config.jj generated code uses this
	 * to lookup the numbered id of a joint that was defined in the user's config file. The config.jj code then stores
	 * the numbered id instead of a string joint name.
	 *
	 * Note: Nite's JointType enumeration defines the compatible joint names for this method!
	 *
	 * @param jointName The name of the joint that we need the numbered id of. To clarify, this name must match the name
	 *                  of the constant defined in Nite's JointType enumeration (e.g. 'head', 'left_elbow', etc.) This
	 *                  jointName is also case insensitive!
	 *
	 * @return A numbered id of a joint as defined by Nite's JointType enumeration. Or -1 if the given jointName isn't
	 * listed in Nite's JointType enumeration.
	 */
	public static int getJointId(String jointName) {
		int value = -1;
		try {
			JointType.valueOf(jointName).toNative();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}