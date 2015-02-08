package com.lcsc.hackathon.events;

public class AbsoluteDistZ {
	private String id = "";
	private double absPointZ;
	private int jointId;
    private double distance;
    
	
	public AbsoluteDistZ(	String id,
							double absPointZ,
							int jointId,
							double distance) {
		this.id = id;
        this.absPointZ = absPointZ;
        this.jointId = jointId;
        this.distance = distance;
    }
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public double getAbsPointZ() {
		return this.absPointZ;
	}
	
	public void setAbsPointZ(double absPointZ) {
		this.absPointZ = absPointZ;
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