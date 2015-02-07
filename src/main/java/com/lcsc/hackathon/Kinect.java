package com.lcsc.hackathon;

import edu.ufl.digitalworlds.j4k.J4KSDK; 
import edu.ufl.digitalworlds.j4k.DepthMap; 
import edu.ufl.digitalworlds.j4k.Skeleton; 
import edu.ufl.digitalworlds.j4k.VideoFrame; 

public class Kinect extends J4KSDK {

	VideoFrame videoTexture;
	
	public Kinect() {
		super();
		videoTexture = new VideoFrame();
	}
	
    @Override 
    public void onDepthFrameEvent(short[] depth_frame, byte[] player_index, float[] XYZ, float[] UV) { 
         
        DepthMap map=new DepthMap(getDepthWidth(),getDepthHeight(),XYZ); 
        if(UV!=null) map.setUV(UV);
        System.out.println("DepthFrameEvent");
    } 

    @Override 
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation, byte[] joint_status) { 

        Skeleton skeletons[]=new Skeleton[getMaxNumberOfSkeletons()]; 
        for(int i=0;i<getMaxNumberOfSkeletons();i++) 
        	skeletons[i]=Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status, this);    
        System.out.println("SkeletonFrameEvent");
    } 
	
    @Override 
    public void onColorFrameEvent(byte[] data) {     

        videoTexture.update(getColorWidth(), getColorHeight(), data); 
        System.out.println("ColorFrameEvent");
    }
    
	public static void main(String[] args) {
		System.out.println("Main");
	}

}
