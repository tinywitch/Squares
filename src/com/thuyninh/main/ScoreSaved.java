package com.thuyninh.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.Properties;

public class ScoreSaved
{
	private static Properties props = new Properties();
	static 
	{
		try
		{
			File f = new File("properties");
			FileInputStream in = new FileInputStream(f);
			props.load(in);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	static Integer getProperty(String key) 
	{
		String temp;
		BigInteger keyCode = new BigInteger(key.getBytes());
		temp= props.getProperty(keyCode.toString());
		BigInteger  valueCode = new BigInteger(temp);
		String value = new String(valueCode.toByteArray());
		return Integer.parseInt(value);
	}
	
	static void setProperty(String key, String value)
	{
		Properties props2 = new Properties();
		try
		{
			File f = new File("score.properties");
			FileOutputStream out = new FileOutputStream( f );
			BigInteger keyCode = new BigInteger(key.getBytes());
			BigInteger valueCode = new BigInteger(value.getBytes());
		    props2.put(keyCode.toString(), valueCode.toString());
			props2.store(out, "hihi, I am Thuy Ninh :D");
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
