package com.lcsc.hackathon.eventdefs;

public class AngleRule {
	private String type = "";
	private String id = "";
	private int end1;
	private int vertex;
	private int end2;
    private int angle
	
	public AngleRule(String type, String id, int end1, int vertex, int end2, int angle) {
		this.type = type;
		this.id = id;
        this.end1 = end1;
        this.vertex = vertex;
        this.end2 = end2;
    }
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getEnd1() {
		return this.end1;
	}
	
	public void setEnd1(int e1) {
		this.end1 = e1;
	}
	
	public int getShoulderID() {
		return this.shoulderID;
	}
	
	public void setShoulderID(int shoulder) {
		this.shoulderID = shoulder;
	}
	
	public int getEnd2() {
		return this.end2;
	}
	
	public void setEnd2(int e2) {
		this.end2 = e2;
	}
}