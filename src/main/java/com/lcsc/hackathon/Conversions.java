//class loader help:
//	http://www.javaworld.com/article/2077260/learn-java/learn-java-the-basics-of-java-class-loaders.html?null
package com.lcsc.hackathon;

public class Conversions {
	public Field[] keyEventFields;
	
    public Conversions() {
    }
	
	public void getKeys() {
		CustomClassLoader loader = new CustomClassLoader();
		Class c;
		c = loader.loadClass("java.awt.event.KeyEvent");
		this.keyEventFields = c.getFields();
	}
}