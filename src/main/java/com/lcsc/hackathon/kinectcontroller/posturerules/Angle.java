/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.lcsc.hackathon.kinectcontroller.posturerules;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Angle implements Rule{
	private String id = "";
	private int end1;
	private int vertex;
	private int end2;
    private double angle;
	
	public Angle(int end1, int vertex, int end2, double angle) {
        this.end1 = end1;
        this.vertex = vertex;
        this.end2 = end2;
        this.angle = angle;
		this.id = getHash();
    }

	private String getHash() {
		String hash = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = String.format("%s:%d:%d:%d:%f", id, end1, vertex, end2, angle);

			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			hash = new String(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hash;
	}
	
	public String getId() {
		return this.id;
	}

	public RuleType getType() {
		return RuleType.ANGLE;
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
    
    public double getAngle() {
		return this.angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
}