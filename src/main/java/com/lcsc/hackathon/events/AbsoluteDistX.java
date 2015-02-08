package com.lcsc.hackathon.events;

public class AbsoluteDistX {
	private String id = "";
	private double[] absolutePoint;
	private int jointId;
    private double distance;
    
	
	public AbsoluteDistX(	String id,
							double[] absolutePoint,
							int jointId,
							double distance) {
		this.id = id;
        this.absolutePoint = absolutePoint;
        this.jointId = jointId;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public double[] getAbsolutePoint() {
		return this.absolutePoint;
	}
	
	public void setAbsolutePoint(double[] array) {
		this.absolutePoint = array;
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