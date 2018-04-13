package com.faldmo.tdbq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TdbqUtilities {
	public boolean checkPW(String trim) {
		// Not sure what to check.
		return true;
	}

	public boolean checkUserName(String trim) {
		// Not sure what to check.
		return true;
	}

	public boolean checkPort(String trim) {
		try{
			int nbr = Integer.parseInt(trim);
			System.out.println("Using "+nbr);
			return true;
		}
		catch (NumberFormatException nfe){
			System.out.println("Couldn't parse number:" + trim);
			System.out.println("Number Format Exception" + nfe.getMessage());
		}
		return false;
	}

	public boolean checkMom(String trim) {
		boolean isIP = false;
		final String PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";         
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(trim);
		isIP = matcher.matches();             
		if (isIP){
			System.out.println("Using IP");
			return true;
		} else if (trim.length()>1 && trim.length()<255){
			System.out.println("Using " + trim);
			return true;
		}
		
		return false;
	}

	public boolean checkManual(String trim) {
		if (trim.equals("m") || trim.equals("M") || trim.equals("d") || trim.equals("D") ){
			return true;
		}
		System.err.println("Please enter an \"m\" or \"d\".");
		return false;
	}

	
	/********
	 * Make sure it is MM/DD/YY
	 * @param pdate
	 * @return
	 */
	public boolean properDate(String pdate) {
		if (pdate.length() != 8) {
			System.err.println("Date given " + pdate + " should be in the format of MM/DD/YY");
			return false;
		}

		try {
			int day = Integer.parseInt(pdate.substring(3, 5));
			int month = Integer.parseInt(pdate.substring(0, 2));
			int year = Integer.parseInt(pdate.substring(6));

			// Check date, I'm not going to try too hard here.
			if (day < 0 || day > 31) {
				System.err.println("The day given is not acceptable: " + day);
				return false;
			}
			if (month < 0 || month > 12) {
				System.err.println("The month given is not acceptable: " + month);
				return false;
			}
			if (year < 13) {
				System.err.println("The year given is not acceptable. It shouldn't be less than 13: " + year);
				return false;
			}
		} catch (Exception e) {
			System.err.println("An excpetion occured: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	
	/*******
	 *  check if the time passed is from 00 to 23
	 * @param ptime
	 * @return
	 */
	public boolean properTime(String ptime) {
		try {
		int hour = Integer.parseInt(ptime.substring(0, 2));
		int min = Integer.parseInt(ptime.substring(3));

		if (hour < 0 || hour >= 24) {
			System.err.println("The hour passed needs to be from 0 to 23. You passed: " + hour);
			return false;
		}
		if (min != 0) {
			System.err.println("Right now this program only goes to the hour. Whatever you put in the minutes is ignored and replaced by \"00\".");
		}

		return true;
		} catch (NumberFormatException npe){
			System.err.println("Didn't get a number:" + npe.getMessage());
			return false;
		}
	}
	
	
	/****
	 *  Check for Type and/or host in the query. 
	 * @param pquery
	 * @return
	 */
	public boolean checkQuery(String pquery) {
		// found type
		boolean typeFound = pquery.contains("type:");
		// found host
		boolean typeHost = pquery.contains("host:");

		if (!typeFound && !typeHost) {
			return false;
		}

		return true;
	}
	
	/**************************
	 * Usage message
	 */
	public void usage(String version) {
		String jarName = "tdbq.jar";
		System.out.println("------------ Version " + version + "------------");
		System.out.println("- This program is like the \"Query History Event\" window in Workstation. It was");
		System.out.println("designed to get around the limit of returning 500 results per query.");
		System.out.println("The number of results will depend on what you set in the configuration file.");
		System.out.println("The default is set to a 10 minute period.");
		System.out.println("I suggest you test your query out in the \"Query History Event\" window first. ");
		System.out.println("------------");
		System.out.println("- This will output a text file. Each line represents an event in the following format:");
		System.out.println("Server,Process,Time,URL or Method,Error");
		System.out.println("------------");
		System.out.println("Usage: You can run this by using command line arguments---");
		System.out.println("java -jar " + jarName + " [start date] [start hour] [end date] [end hour] [\"query\"]");
		System.out.println("Example usage: ./java -jar " + jarName+ " 12/05/13 08:00 12/05/13 10:00 \"type:errorsnapshot AND host:myserver\"");
		System.out.println("");
		System.out.println("If you don't use arguments then the timeframe set in the config.properties file will be used.");
		System.out.println("java -jar " + jarName);
		System.out.println("------------");
	}
}
