//class loader help:
//	http://www.javaworld.com/article/2077260/learn-java/learn-java-the-basics-of-java-class-loaders.html?null
package com.lcsc.hackathon;

import edu.ufl.digitalworlds.j4k.Skeleton;
import java.awt.event.*;
import java.lang.reflect.*;

public class Conversions {
	
	public Conversions() {
    }
	
	public static int getKeyId(String key) {
		String fieldName = String.format("VK_%s", key);
		
		int value = -1;
		try {
			value = KeyEvent.class.getDeclaredField(fieldName).getInt(KeyEvent.class);
		} catch (IllegalArgumentException e) {
			// if the specified object is not an instance of the class or
			// interface declaring the underlying field (or a subclass or
			// implementor thereof)
			e.printStackTrace();
		} catch (SecurityException e) {
			// if a security manager, s, is present [and restricts the access to
			// the field]
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// if the underlying field is inaccessible
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// if a field with the specified name is not found
			e.printStackTrace();
		}
		return value;
	}
	
	public static int getJointId(String key) {
		String fieldName = String.format(key);
		int value = -1;
		try {
			value = Skeleton.class.getDeclaredField(fieldName).getInt(Skeleton.class);
		} catch (IllegalArgumentException e) {
			// if the specified object is not an instance of the class or
			// interface declaring the underlying field (or a subclass or
			// implementor thereof)
			e.printStackTrace();
		} catch (SecurityException e) {
			// if a security manager, s, is present [and restricts the access to
			// the field]
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// if the underlying field is inaccessible
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// if a field with the specified name is not found
			e.printStackTrace();
		}
		return value;
	}
}