//class loader help:
//	http://www.javaworld.com/article/2077260/learn-java/learn-java-the-basics-of-java-class-loaders.html?null
package com.lcsc.hackathon;

import java.awt.event.*;
import java.lang.reflect.*;

public class Conversions {
	
	public Conversions() {
    }
	
	public int getKey(String key) {
		try{
			//CustomClassLoader loader = new CustomClassLoader();
			//Class c;
			//c = loader.loadClass("java.awt.event.KeyEvent");
			//Field newKey = c.getField(String.format("VK_%s", key));
			//return newKey.getKeyCode();
			//int value = KeyEvent.class.getDeclaredField(String.format("VK_%s", key)).get();
			int value = 0;
			return value;
		}
		catch( IllegalArgumentException e ){
			System.out.println("conversions.getKey failed");
			return 0;
		}
	}
	
	public void printFields(String key) {
		String fieldName = String.format("VK_%s", key);
		
		Object value = null;
		try {
			value = KeyEvent.class.getDeclaredField(fieldName).get(KeyEvent.class);
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
		
		System.out.println(value);
	}
}