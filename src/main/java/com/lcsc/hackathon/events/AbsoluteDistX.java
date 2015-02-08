package com.lcsc.hackathon.events;

public class AbsoluteDistX {
	private String id = "";
	private double[] absoluteJoint;
	private int jointId;
    private double distance;
    
	
	public AbsoluteDistX(	String id,
							double[] absoluteJoint,
							int jointId,
							double distance) {
		this.id = id;
        this.absolutejoint = absolutejoint;
        this.jointId = jointId;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public int getAbsoluteJoint() {
		return this.absolutejoint;
	}
	
	public void setAbsoluteJoint(double[] array) {
		this.AbsoluteJoint = array;
	}
	
	public int getjointId() {
		return this.jointId;
	}
	
	public void setjointId(int jnt) {
		this.jointId = jnt;
	}
    
    public double getDistance() {
		return this.distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
}