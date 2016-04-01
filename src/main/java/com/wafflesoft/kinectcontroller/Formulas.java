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

import com.primesense.nite.Point3D;

public class Formulas {
	/**
	 * Gets the distance between two joint positions on a single axis. This is reused for the x-, y- and z-axis'.
	 * @param jointPosA	The first joint's position on whatever axis we're dealing with.
	 * @param jointPosB The second joint's position on whatever axis we're dealing with.
     * @return The distance between the two joint positions. Note that this value will never be negative as it is a
	 * magnitude!
     */
	public static double getDistanceAxis(Float jointPosA, Float jointPosB) {
		return Math.abs(jointPosA-jointPosB);
	}

	/**
	 * This gets the distance between two joint positions over 3 axis'.
	 * Note: This uses the distance formula.
     *
     * Point3D is a OpenNI data type -- refer to the documentation directory within this project --
     * with Point3D(Float x, Float y, Float z) being the constructor.
	 *
	 * @param jointA This is the first joint's position.
	 * @param jointB This is the second joint's position.
     * @return The distance between the two joints. Note that this value will never be negative as it is a magnitude!
     */
	public static double getDistance(Point3D<Float> jointA, Point3D<Float> jointB) {
		return Math.sqrt(Math.pow(jointB.getX()-jointA.getX(), 2) + Math.pow(jointB.getY()-jointA.getY(), 2) + Math.pow(jointB.getZ()-jointA.getZ(), 2));
	}

	/**
	 * This gets the distance between a point and some joint.
	 * Note: This uses the distance formula.
     *
     * Point3D is a OpenNI data type -- refer to the documentation directory within this project --
     * with Point3D(Float x, Float y, Float z) being the constructor.
	 *
	 * @param point This is an array of double values that represents a point's position in 3D space. So there must be
	 *              3 values in the array AND they should follow the x, y, z ordering.
     *
     *              Important Note: It is assumed that this argument ALWAYS has 3 items within it. This function is called
     *              very often and it should already be ensured that the given point is valid.
     *
     * @param joint This is the joint's position.
	 * @return The distance between the point and joint. Note that this value will never be negative as it is a magnitude!
	 */
	public static double getDistance(double[] point, Point3D<Float> joint) {
		return Math.sqrt(Math.pow(joint.getX()-point[0], 2) + Math.pow(joint.getY()-point[1], 2) + Math.pow(joint.getZ()-point[2], 2));
	}

	/**
	 * Get the angle at a vertex given three joints.
     *
     * Note: This uses the law of cosines to determine the angle.
     *
     *
	 * @param jointA End1 of the angle.
	 * @param vertex The vertex of the angle.
	 * @param jointB End2 of the angle.
	 * @return A double of the angle between these three joints, in degrees.
	 */
	public static double getAngle(Point3D<Float> jointA, Point3D<Float> vertex, Point3D<Float> jointB) {
		double distA = getDistance(vertex, jointA);
		double distB = getDistance(vertex, jointB);
		double distC = getDistance(jointA, jointB);
		return Math.toDegrees(Math.acos((Math.pow(distA, 2)+Math.pow(distB, 2)-Math.pow(distC, 2))/(2*distA*distB)));
	}
}