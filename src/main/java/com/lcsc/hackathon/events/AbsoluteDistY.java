package com.lcsc.hackathon.events;

public class AbsoluteDistY {
	private String id = "";
	private double absPointY;
	private int jointId;
    private double distance;
    
	
	public AbsoluteDistY(	String id,
							double absPointY,
							int jointId,
							double distance) {
		this.id = id;
        this.absPointY = absPointY;
        this.jointId = jointId;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public double getAbsPointY() {
		return this.absPointY;
	}
	
	public void setAbsPointY(double absPointY) {
		this.absPointY = absPointY;
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