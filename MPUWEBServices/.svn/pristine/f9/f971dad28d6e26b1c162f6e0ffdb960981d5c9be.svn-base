package com.searshc.mpuwebservice.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.searshc.mpuwebservice.exception.GenericUtilException;

/**
 * This class reads a property file and loads it into an internal map. The
 * location of the property file can be sent by calling the
 * <code>setPropertyFileLocation(String)</code> method, if used in a Spring 3.x
 * project by having a property (in a file that is read by Spring) of
 * globalVariables.propertyFileLocation, or by passing in the location to the
 * constructor.
 * 
 */
@Service
public class ExternalPropertyMapServiceImpl
{
	private String propertyFileLocation;

	private static Properties props = null;

	public void init() throws Exception
	{
		loadMap();
	}

	public ExternalPropertyMapServiceImpl()
	{
	}

	public ExternalPropertyMapServiceImpl(String propertyFileLocation)
	{
		setPropertyFileLocation(propertyFileLocation);
	}

	public String getPropertyFileLocation()
	{
		return propertyFileLocation;
	}

	public void setPropertyFileLocation(String propertyFileLocation)
	{
		this.propertyFileLocation = propertyFileLocation;
	}

	/**
	 * Returns the value field from the external property file. Will return NULL
	 * if not found
	 * 
	 * @param key
	 * @return
	 * @throws GenericUtilException
	 */
	public String getValueById(String key) throws GenericUtilException
	{
		String value = null;

		if (null != props && props.containsKey(key))
		{
			value = props.getProperty(key);
		}
		else
		{
			loadMap();
			if (props.containsKey(key))
			{
				value = props.getProperty(key);
			}
		}
		return value;
	}

	/**
	 * Returns the Int value field from the external property file. If the value
	 * cannot be converted to an int then a GenericUtilException will be thrown
	 * 
	 * @param key
	 * @return
	 * @throws GenericUtilException
	 */
	public int getIntValueById(String key) throws GenericUtilException
	{
		String value = null;

		if (null != props && props.containsKey(key))
		{
			value = props.getProperty(key);
		}
		else
		{
			loadMap();
			if (props.containsKey(key))
			{
				value = props.getProperty(key);
			}
		}
		try
		{
			return Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			throw new GenericUtilException("Cannot convert " + value
					+ " to an integer", e);
		}
	}

	/**
	 * loads the static class Map
	 * 
	 * @throws GeneralFulfillmentException
	 */
	public void loadMap() throws GenericUtilException
	{
		props = new Properties();
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(new File(propertyFileLocation));
			props.load(fis);
			fis.close();
		}
		catch (Exception e)
		{
			throw new GenericUtilException(e);
		}finally {
			try{
		    	if(fis != null)
		    	{
		    		fis.close();
		    	}
		    } catch (IOException ioex) {
		    }
		}

	}

	/***
	 * OverWrite the property
	 * @param Key
	 * @param value
	 */
	public void overWriteProps(String Key, String value) throws GenericUtilException
	{
		try {
			if (props == null) {
				loadMap();
			}
			props.setProperty(Key, value);
			setProps(props);
			writeToPropertyFile(Key, value);
		} catch (Exception e) {
			throw new GenericUtilException(e);
		} 
	}

	/***
	 * Update Kisok printer id
	 * @throws IOException
	 */
	public void writeToPropertyFile(String key, String value)
	{
		BufferedReader reader = null;
		BufferedWriter writer = null;
		File inputFile = null;
		File tempFile = null;
		try {
			inputFile = new File(propertyFileLocation);
			tempFile = new File(propertyFileLocation + ".temp");

			reader = new BufferedReader(new FileReader(inputFile));

			writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if (trimmedLine.indexOf(key) != -1) {
					continue;
				}
				writer.write(currentLine + "\n");
			}
			writer.write(key + "=" + value);
			writer.flush();
			reader.close();
			writer.close();
			inputFile.delete();
			tempFile.renameTo(new File(propertyFileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public static void setProps(Properties props)
	{
		ExternalPropertyMapServiceImpl.props = props;
	}
}
