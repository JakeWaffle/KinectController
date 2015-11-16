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

import com.primesense.nite.Point3D;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DistanceFromPoint implements Rule {
	private String 			id = "";
	private Point3D<Float> 	point;
	private int 			jointId;
    private double 			distance;
    
	
	public DistanceFromPoint(Point3D<Float> point,
							 int jointId,
							 double distance) {
        this.point 		= point;
		this.jointId 	= jointId;
		this.distance 	= distance;
		this.id 		= getHash();
	}

	private String getHash() {
		String hash = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = String.format("%s:%f:%f:%f:%d", getType().alias, point.getX(), point.getY(), point.getZ(), jointId);

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

	public void setId(String id) {
		this.id = id;
	}

	public RuleType getType() {
		return RuleType.DISTANCE_FROM_POINT;
	}
    
	public Point3D<Float> getPoint() {
		return this.point;
	}
	
	public void setPoint(Point3D<Float> point) {
		this.point = point;
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
	
	public void setDistance(double _distance) {
		this.distance = _distance;
	}
}