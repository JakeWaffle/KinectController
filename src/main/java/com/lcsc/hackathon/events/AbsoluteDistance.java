package com.lcsc.hackathon.events;

public class AbsoluteDistance {
	private String id = "";
	private double[] absPoint;
	private int jointId;
    private double distance;
    
	
	public AbsoluteDistance(	String id,
								double[] absPoint,
								int jointId,
								double distance) {
		this.id = id;
        this.absPoint = absPoint;
        this.jointId = jointId;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public double[] getAbsPoint() {
		return this.absPoint;
	}
	
	public void setAbsPoint(double[] array) {
		this.absPoint = array;
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