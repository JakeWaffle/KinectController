package com.lcsc.hackathon.events;

public class DistanceYRule {
	private String id = "";
	private int joint1;
	private int joint2;
    private double distance;
    
	
	public DistanceYRule(	String id,
							int joint1,
							int joint2,
							double distance) {
		this.id = id;
        this.joint1 = joint1;
        this.joint2 = joint2;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public int getJoint1() {
		return this.joint1;
	}
	
	public void setJoint1(int joint) {
		this.joint1 = joint;
	}
	
	public int getJoint2() {
		return this.joint2;
	}
	
	public void setJoint2(int joint) {
		this.joint2 = joint;
	}
    
    public double getDistance() {
		return this.distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
}