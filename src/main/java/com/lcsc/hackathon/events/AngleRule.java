package com.lcsc.hackathon.events;

public class AngleRule {
	private String id = "";
	private int end1;
	private int vertex;
	private int end2;
    private int angle;
	
	public AngleRule(String id, int end1, int vertex, int end2, int angle) {
		this.id = id;
        this.end1 = end1;
        this.vertex = vertex;
        this.end2 = end2;
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
	
	public int getVertex() {
		return this.vertex;
	}
	
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	public int getEnd2() {
		return this.end2;
	}
	
	public void setEnd2(int e2) {
		this.end2 = e2;
	}
}