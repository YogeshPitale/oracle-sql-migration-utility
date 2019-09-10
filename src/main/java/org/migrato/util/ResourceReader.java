package org.migrato.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceReader {

	private static Properties properties=new Properties();
	
	public static Properties getProperties() {
		return properties;
	}
	
	static {
		try (InputStream input = ResourceReader.class.getClassLoader().getResourceAsStream("application.properties")) {
			properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            properties=null;
        }
	}
}
