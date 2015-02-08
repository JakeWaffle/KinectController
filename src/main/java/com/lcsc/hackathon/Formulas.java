package com.lcsc.hackathon;

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
	
	// Get the distance between two joints.
	public static double getDistance(double[] jointA, double[] jointB) {
		return Math.sqrt(Math.pow(jointB[0]-jointA[0], 2) + Math.pow(jointB[1]-jointA[1], 2) + Math.pow(jointB[2]-jointA[2], 2));
	}
	
	// Get the angle at a vertex given three joints.
	public static double getAngle(double[] jointA, double[] vertex, double[] jointB) { 
		double distA = getDistance(vertex, jointA);
		double distB = getDistance(vertex, jointB);
		double distC = getDistance(jointA, jointB);
		return Math.toDegrees(Math.acos((Math.pow(distA, 2)+Math.pow(distB, 2)-Math.pow(distC, 2))/(2*distA*distB)));
	}
}