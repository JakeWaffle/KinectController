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

package com.lcsc.hackathon.kinectcontroller;

import java.awt.event.*;
import java.lang.reflect.*;

public class Conversions {
	
	public Conversions() {
    }
	
	public static int getKeyId(String key) {
		String fieldName = String.format("VK_%s", key);
		
		int value = -1;
		try {
			value = KeyEvent.class.getDeclaredField(fieldName).getInt(KeyEvent.class);
		} catch (IllegalArgumentException e) {
			// if the specified object is not an instance of the class or
			// interface declaring the underlying field (or a subclass or
			// implementor thereof)
			e.printStackTrace();
		} catch (SecurityException e) {
			// if a security manager, s, is present [and restricts the access to
			// the field]
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// if the underlying field is inaccessible
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// if a field with the specified name is not found
			e.printStackTrace();
		}
		return value;
	}

	//TODO This needs to be adapted to whatever Kinect SDK we switch to.
	public static int getJointId(String key) {
		String fieldName = String.format(key);
		int value = -1;
		/*
		try {
			value = Skeleton.class.getDeclaredField(fieldName).getInt(Skeleton.class);
		} catch (IllegalArgumentException e) {
			// if the specified object is not an instance of the class or
			// interface declaring the underlying field (or a subclass or
			// implementor thereof)
			e.printStackTrace();
		} catch (SecurityException e) {
			// if a security manager, s, is present [and restricts the access to
			// the field]
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// if the underlying field is inaccessible
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// if a field with the specified name is not found
			e.printStackTrace();
		}
		*/
		return value;
	}
}