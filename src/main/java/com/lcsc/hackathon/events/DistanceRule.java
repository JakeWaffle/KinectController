package com.lcsc.hackathon.events;

public class DistanceRule {
	private String id = "";
	private int joint1;
	private int joint2;
    private int distance;
    
	
	public DistanceRule(String id, int joint1, int joint2, int distance) {
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
	
	public void setJoint1(int j1) {
		this.joint1 = j1;
	}
	
	public int getJoint2() {
		return this.joint2;
	}
	
	public void setJoint2(int j2) {
		this.joint2 = j2;
	}
}