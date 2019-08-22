package com.avaya.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceReader {

	private static Properties properties=new Properties();
	
	public static Properties getProperties() {
		return properties;
	}
	
	static {
		try (InputStream input = new FileInputStream("application.properties")) {
			properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            properties=null;
        }
	}
}
