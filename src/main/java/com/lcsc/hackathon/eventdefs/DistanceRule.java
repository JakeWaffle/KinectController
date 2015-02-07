package com.lcsc.hackathon.eventdefs;

public class DistanceRule {
	private int end1;
	private int end2;
    
	
	public DistanceRule(int end1, int end2) {
        this.end1 = end1;
        this.end2 = end2;
    }
    
	public int getEnd1() {
		return this.end1;
	}
	
	public void setEnd1(int e1) {
		this.end1 = e1;
	}
	
	public int getEnd2() {
		return this.end2;
	}
	
	public void setEnd2(int e2) {
		this.end2 = e2;
	}
}