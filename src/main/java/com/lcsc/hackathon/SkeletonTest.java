package com.lcsc.hackathon;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class SkeletonTest extends J4KSDK {
	Skeleton skeletons[];
	
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
				System.out.println(this.getAngle(skeletons[i].get3DJoint(4),skeletons[i].get3DJoint(5),skeletons[i].get3DJoint(6))); // SEW Left
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