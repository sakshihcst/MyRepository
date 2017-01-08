package com.searshc.mpuwebservice.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * This class is a property reader class which extends <code>PropertyPlaceholderConfigurer</code>. 
 * This class overrides the loadProperties() method and also provides a getProperty() methods 
 * to read the values from properties file.
 * 
 * @author Tata Consultancy Services
 *
 */
public class PropertyUtils extends PropertyPlaceholderConfigurer {
	private static Map<String, String> properties = new HashMap<String, String>();

	/**
	 * This method is used to read the value from the properties file by passing the key as parameter.
	 * @param name
	 * 			This is the name of the property key.
	 * @return A <code>String</code> object containing the value of the key.
	 */
	public static String getProperty(final String name) {
		
		return properties.get(name);
	}

	/**
	 * This method is a overridden method from <code>PropertyPlaceholderConfigurer</code> class.
	 * This method calls the loadProperties() method of class <code>PropertyPlaceholderConfigurer</code>.
	 * @param props
	 * 			This is the <code>Properties</code> object which is the properties file.
	 * @throws IOException
	 * 			If the properties is not readable.
	 */
	@Override
	protected void loadProperties(final Properties props) throws IOException {
		super.loadProperties(props);
		for (final Object key : props.keySet()) {
			properties.put((String) key, props.getProperty((String) key));
		}
	}
}