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

package com.lcsc.hackathon;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class SkeletonTest extends J4KSDK {
	Skeleton skeletons[];
	
	//If joint A is greater that joint B on the chosen axis, returns true otherwise false.
	//Accepts 0,1,2 as X,Y,Z
	private boolean getRelationship(double[] jointA, double[] jointB, int axis) {
		if (jointA[axis] > jointB[axis]) {
			return true; 
		} else {
			return false;
		}
	}
	
	// Get the distance between two joints.
	private double getDistance(double[] jointA, double[] jointB) {
		return Math.sqrt(Math.pow(jointB[0]-jointA[0], 2) + Math.pow(jointB[1]-jointA[1], 2) + Math.pow(jointB[2]-jointA[2], 2));
	}
	
	// Get the angle at a vertex given three joints.
	private double getAngle(double[] jointA, double[] vertex, double[] jointB) { 
		double distA = getDistance(vertex, jointA);
		double distB = getDistance(vertex, jointB);
		double distC = getDistance(jointA, jointB);
		return Math.toDegrees(Math.acos((Math.pow(distA, 2)+Math.pow(distB, 2)-Math.pow(distC, 2))/(2*distA*distB)));
	}
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		for (int i=0; i<skeletons.length;i++){
			skeletons[i]=Skeleton.getSkeleton(i,skeleton_tracked,positions, orientations, joint_status,this);
			if (skeletons[i].isTracked()){
				//System.out.println(this.getAngle(skeletons[i].get3DJoint(4),skeletons[i].get3DJoint(5),skeletons[i].get3DJoint(6))); // SEW Left
				System.out.println(skeletons[i].get3DJoint(0)[2]);
				//System.out.println(this.getAngle(skeletons[i].get3DJoint(8),skeletons[i].get3DJoint(9),skeletons[i].get3DJoint(10))); // SEW Right
			}
		}
	}

	public void run() {
		this.start(J4KSDK.NONE|J4KSDK.NONE|J4KSDK.SKELETON);
		skeletons = new Skeleton[6];
		this.showViewerDialog();
		
		//Sleep for 20 seconds.
		try {Thread.sleep(20000);} catch (InterruptedException e) {}
		this.stop();
	}
	
	
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		//We are not using this!
	}

	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
		//We are not using this!
	}
}