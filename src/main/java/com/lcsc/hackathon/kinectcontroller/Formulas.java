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

package com.lcsc.hackathon.kinectcontroller;

import com.primesense.nite.Point3D;

public class Formulas {
    //If joint A is greater that joint B on the chosen axis, returns true otherwise false.
	//Accepts 0,1,2 as X,Y,Z
	public static boolean getRelationship(double[] jointA, double[] jointB, int axis) {
		if (jointA[axis] > jointB[axis]) {
			return true; 
		} else {
			return false;
		}
	}
	
	// Get distance between two joints (can be negative)
	public static double getDistanceX(Float jointXA, Float jointXB) {
		return jointXA-jointXB;
	}
	
	// Get distance between two joints (can be negative)
	public static double getDistanceY(Float jointYA, Float jointYB) {
		return jointYA-jointYB;
	}
	
	// Get distance between two joints (can be negative)
	public static double getDistanceZ(Float jointZA, Float jointZB) {
		return jointZA-jointZB;
	}
	
	// Get the distance between two joints.
	public static double getDistance(Point3D<Float> jointA, Point3D<Float> jointB) {
		return Math.sqrt(Math.pow(jointB.getX()-jointA.getX(), 2) + Math.pow(jointB.getY()-jointA.getY(), 2) + Math.pow(jointB.getZ()-jointA.getZ(), 2));
	}

	// Get the distance between a point and a joint.
	public static double getDistance(double[] point, Point3D<Float> joint) {
		return Math.sqrt(Math.pow(joint.getX()-point[0], 2) + Math.pow(joint.getY()-point[1], 2) + Math.pow(joint.getZ()-point[2], 2));
	}
	
	// Get the angle at a vertex given three joints.
	public static double getAngle(Point3D<Float> jointA, Point3D<Float> vertex, Point3D<Float> jointB) {
		double distA = getDistance(vertex, jointA);
		double distB = getDistance(vertex, jointB);
		double distC = getDistance(jointA, jointB);
		return Math.toDegrees(Math.acos((Math.pow(distA, 2)+Math.pow(distB, 2)-Math.pow(distC, 2))/(2*distA*distB)));
	}
}