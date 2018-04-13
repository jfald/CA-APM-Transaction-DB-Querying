package com.faldmo.tdbq;

import java.io.*;

import com.faldmo.tdbq.conn.MomConnection;

public class TdbqStart {
	
	// Version 0.1 first go at it as a jar file after testing
	// v0.2 added access to usage() and changed a few words, fixed argument placement
	// v0.3 updated to work with CA APM 9.7 (There were changes in the order of the xml contexts.) 
	  // Changed where the connection to Introscope is made. Done every query instead of once.
	  // This will make it so only a bit of information is lost, not the rest of the report.
	// v1.0 added properties files, updated command line argument requirements.
	// v1.2 changed how connection to Introscope is made again. Added rawOutput to config.properties file.
	  // Added metric path and another error to output file. 
	  // Added a max number of connection re-attempts. 
	
	public static void main(String[] args) {
		String version = "1.2";
		
		boolean useHardCoded = false; 		//If set to true then the hardCoded values below are used. 
		boolean printRawOutput = false; 	//Useful for troubleshooting
		//boolean propertiesFileFound = false; //New Feature
		
		MomConnection mc = new MomConnection();
		Transaction_DB_Query_MetricPath tdbq = null;
		ValuesInput vi = new ValuesInput();
		TdbqGetPropertyValues gpv = new TdbqGetPropertyValues();
		
		//Get connection information from properties file
		try{
			mc = gpv.setPropValues();
			printRawOutput=mc.getRawOutput();
			vi.setQuery(mc.getQuery());
			//System.out.println("Using values in properties file");
		} catch (FileNotFoundException fnfe){
			System.out.println("Problem getting properties File. Using default Connection Information. FileNotFoundException " + fnfe.getMessage());
			mc = new MomConnection();
		} catch (IOException fnfe){
			System.out.println("Problem getting properties File. Using localhost. IOException " + fnfe.getMessage());
			mc = new MomConnection();
		}
		
		//System.out.println("Args length: " + args.length);
		if(useHardCoded){
			//Input needed only in Eclipse
			System.out.println("Using hard coded values.");
			vi.setStartDate("01/19/16");
			vi.setStartTime("12:00");
			vi.setEndDate("01/19/16");
			vi.setEndTime("14:00");
			vi.setQuery("type:errorsnapshot AND (host:vanhimul*)");			
			printRawOutput=true;

		}  else if (args.length == 5){
			GetFromArgs gfa = new GetFromArgs();
			vi = gfa.getFromArgs(args, vi);
			
		} else if (args.length == 1){
			//Don't really care what it is :)
			TdbqUtilities tu = new TdbqUtilities();
			tu.usage(version);
			System.exit(0);
		} else{
			//Print Usage instead or use defaults?
			System.out.println("The proper number of arguments were not given. Using the hour duration and query from the properies file.");
		}
		
		//####################################################################################
		// All information should be gathered. Do final checks and start the actual query process. 
		System.out.println("Args length: " + args.length + ". Arguments used " + vi.getStartDate() + " " + vi.getStartTime() + " " + vi.getEndDate() + " " + vi.getEndTime() + " " + vi.getQuery());
		vi.checkTime(mc.getHours());
		vi.checkQuery();
		System.out.println("Staring query process...");
		tdbq = new Transaction_DB_Query_MetricPath(mc, vi.getStartDate(), vi.getStartTime(), vi.getEndDate(), vi.getEndTime(), vi.getQuery());
		tdbq.setPrintRaw(printRawOutput);
		
		//###########################################################################################
		// The only way you should have gotten here is if you have all the dates, times, and query.
		// Otherwise the program should have exited by now.
		try {
			tdbq.READ_SQL();
		} catch (Exception e) {
			System.err.print("\"tdbq.READ_SQL()\"" + e.getMessage() );
			e.printStackTrace();
		}
		
		System.out.println("... done now");
	}	

}
