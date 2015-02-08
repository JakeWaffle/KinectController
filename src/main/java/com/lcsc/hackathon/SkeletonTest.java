package com.lcsc.hackathon;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class SkeletonTest extends J4KSDK {

	int counter=0;
	long time=0;
	Skeleton skeletons[];
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		System.out.println("A new skeleton frame was received.");
		//System.out.println(join_status);
		skeletons[0]=Skeleton.getSkeleton(0,skeleton_tracked,positions, orientations, joint_status,this);
		skeletons[1]=Skeleton.getSkeleton(1,skeleton_tracked,positions, orientations, joint_status,this);
		skeletons[2]=Skeleton.getSkeleton(2,skeleton_tracked,positions, orientations, joint_status,this);
		skeletons[3]=Skeleton.getSkeleton(3,skeleton_tracked,positions, orientations, joint_status,this);
		skeletons[4]=Skeleton.getSkeleton(4,skeleton_tracked,positions, orientations, joint_status,this);
		skeletons[5]=Skeleton.getSkeleton(5,skeleton_tracked,positions, orientations, joint_status,this);
		System.out.println(skeletons[0].get3DJoint(9));
		System.out.println(skeletons[1].get3DJoint(9));
		System.out.println(skeletons[2].get3DJoint(9));
		System.out.println(skeletons[3].get3DJoint(9));
		System.out.println(skeletons[4].get3DJoint(9));
		System.out.println(skeletons[5].get3DJoint(9));
	}

	public void run(){

		System.out.println("This program will run for about 20 seconds.");
		this.start(J4KSDK.NONE|J4KSDK.NONE|J4KSDK.SKELETON);
		skeletons = new Skeleton[6];
		this.showViewerDialog();
		
		//Sleep for 20 seconds.
		try {Thread.sleep(20000);} catch (InterruptedException e) {}
				
		this.stop();		
		//System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}
	
	public static void main(String[] args)
	{		
		SkeletonTest skeletonTest;
		skeletonTest = new SkeletonTest();
		skeletonTest.run();
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