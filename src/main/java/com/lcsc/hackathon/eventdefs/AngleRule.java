package com.lcsc.hackathon.eventdefs;

public class AngleRule {
	private int end1;
	private int shoulderID;
	private int end2;
	
	public AngleRule(int end1, int shoulderID, int end2) {
        this.end1 = end1;
        this.shoulderID = shoulderID;
        this.end2 = end2;
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