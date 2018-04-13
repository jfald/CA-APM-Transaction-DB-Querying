package com.faldmo.tdbq;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.faldmo.tdbq.conn.MomConnection;

public class TdbqGetPropertyValues {

	public MomConnection setPropValues() throws IOException {
		return setPropValues("config.properties");
	}
	
	public MomConnection setPropValues(String pf) throws IOException {
		
		Properties prop = new Properties();
		String propFileName = pf;
		MomConnection mc = new MomConnection();
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			prop.load(inputStream);
			
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found.");
		}
		
		mc.setApmUser(prop.getProperty("apmUser"));
		mc.setApmpw(prop.getProperty("apmpw"));
		mc.setMom(prop.getProperty("mom"));
		mc.setPort(prop.getProperty("port"));
		mc.setMinutes(prop.getProperty("queryEveryMinutes"));
		mc.setQuery(prop.getProperty("query"));
		mc.setHours(prop.getProperty("previousHours"));
		mc.setRawOutput(prop.getProperty("rawOutput"));
		
		return mc;
		
	}
	
}
