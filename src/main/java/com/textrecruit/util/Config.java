package com.textrecruit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
	private static Properties properties = new Properties();
	
	private Config() {
		
	}
	
	public final static String getSharedProperties(String propKey) {
		try { 
			synchronized (properties) {
				if(properties.isEmpty()) {
					InputStream in = Config.class.getResourceAsStream("/config.properties");
					properties = new Properties();
					properties.load(in);
					in.close();
				}
			}
		} catch(IOException exception) {
			throw new RuntimeException(exception);
		}
		return properties.getProperty(propKey);
	}
}
