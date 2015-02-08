package com.lcsc.hackathon;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class KinectTest extends J4KSDK{

	public KinectTest() {
        super();
    }
    
    @Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] player_index, float[] XYZ, float[] UV) {
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] flags, float[] positions, float[] orientations, byte[] state) {
		for(int i=0;i<getSkeletonCountLimit();i++)
		{
			//skeletons[i]=Skeleton.getSkeleton(i, flags,positions, orientations,state,this);
		}
	}

	@Override
	public void onColorFrameEvent(byte[] data) {
	}
}
