package com.lcsc.hackathon.events;

public class AbsoluteDistX {
	private String id = "";
	private double absPointX;
	private int jointId;
    private double distance;
    
	
	public AbsoluteDistX(	String id,
							double absPointX,
							int jointId,
							double distance) {
		this.id = id;
        this.absPointX = absPointX;
        this.jointId = jointId;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public double getAbsPointX() {
		return this.absPointX;
	}
	
	public void setAbsPointX(double absPointX) {
		this.absPointX = absPointX;
	}
	
	public int getJointId() {
		return this.jointId;
	}
	
	public void setJointId(int jnt) {
		this.jointId = jnt;
	}
    
    public double getDistance() {
		return this.distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
}