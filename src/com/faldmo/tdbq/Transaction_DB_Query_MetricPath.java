package com.faldmo.tdbq;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;

import com.faldmo.tdbq.conn.MomConnection;

import java.text.*;


// Attempting to query the transactions db
public class Transaction_DB_Query_MetricPath {

	/*
	 * It is expected that
	 */
	Transaction_DB_Query_MetricPath(MomConnection pmc, String pStartDate, String pStartTime, String pEndDate,  String pEndTime, String pquery) {		
		this.passedStartDate = pStartDate;
		this.passedEndDate = pEndDate;
		this.passedStartTime = pStartTime;
		this.passedEndTime = pEndTime;
		this.passedQuery = pquery;
		this.minutes = Integer.parseInt(pmc.getMinutes());
		cDate = getNow();
		mc = pmc;
	}

	String passedStartDate;
	String passedEndDate;
	String passedStartTime;
	String passedEndTime;
	String passedQuery;
	String cDate; 
	String fname = "tdbq-logs";
	int minutes;

	MomConnection mc;
	TdbqGetPropertyValues gpv = new TdbqGetPropertyValues();

	boolean printRaw = true; //Set in TdbqStart

	private String getNow() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmm");

		return ft.format(dNow);
	}

	public void READ_SQL() throws Exception {

		boolean reachedEnd = false;
		Statement stmt = null;
		ResultSet rset = null;
		WriteOutputToFile wotf2 = new WriteOutputToFile();
		WriteOutputToFile wotf = null;
		if (printRaw){
			wotf = new WriteOutputToFile();
		}

		SimpleDateFormat qdf = new SimpleDateFormat("MM/dd/yy HH:mm");
		Date date = qdf.parse(passedStartDate + " " + passedStartTime);
		Date dateEnd = qdf.parse(passedEndDate + " " + passedEndTime);
		Calendar calQueryBeg = Calendar.getInstance();
		Calendar calQueryEnd = Calendar.getInstance();
		Calendar calDateEnd = Calendar.getInstance();

		Class.forName("com.wily.introscope.jdbc.IntroscopeDriver");

		wotf2.openToWrite(fname+"-"+cDate+".csv");
		wotf2.writeLine("Server,Process,Time,URL or Method,Metric path,Error1,Error2");

		if (printRaw){
			wotf.openToWrite(fname+"-raw"+cDate+".txt");
		}

		calQueryBeg.setTime(date);
		calQueryEnd.setTime(date);
		calQueryEnd.add(Calendar.MINUTE, minutes);
		calDateEnd.setTime(dateEnd);
		String queryStartTime = "";
		String queryEndTime = "";
		int maxTrys = 10;
		int tries = 0;
		
		//While the interval end time has not exceeded the overall query time. 
		// and the tries have not exceeded the maximum number of tries.
		// The "tries" are there because of "nullPointerExceptions" re. I don't want it to run away with trying to connect.  
		while(!reachedEnd && tries < maxTrys){
			Connection conn = DriverManager.getConnection("jdbc:introscope:net//"+mc.getApmUser()+":"+mc.getApmpw()+"@"+mc.getMom()+":"+mc.getPort());

			try {		
				stmt = conn.createStatement();

				while(!reachedEnd){

					queryStartTime = qdf.format(calQueryBeg.getTime());
					queryEndTime = qdf.format(calQueryEnd.getTime());
					String equery = "select * from traces where timestamp between '" + queryStartTime + ":00' and '"
							+ queryEndTime +":00' and query='" + passedQuery + "'";

					rset = stmt.executeQuery(equery);

					while (rset.next()) {
						if (printRaw){
							wotf.writeLine(rset.getString(1) + "," + rset.getString(2) + "," + rset.getString(3) + "," + rset.getString(4) + "," + 
									rset.getString(5) + "," + rset.getString(6));
						}
						// create an empty array, with non-null values
						String[] limited = parseMetricPath(rset.getString(6));
						
						if (limited[1].equals(limited[2])){
							limited[2]="";
						}
						String lineToWrite = "";
						lineToWrite = rset.getString(2) + "," + rset.getString(4) + "," + rset.getString(5) + "," +
								limited[3]+ "," + limited[0]+ ",\"" + limited[1] + "\",\"" + limited[2] + "\"";

						wotf2.writeLine(lineToWrite);
					}

					//Check Dates
					calQueryEnd.add(Calendar.MINUTE, minutes);
					calQueryBeg.add(Calendar.MINUTE, minutes);

					if (calDateEnd.before(calQueryEnd)){
						reachedEnd = true;
					}

				}//End While
			} //end try
			catch (ArrayIndexOutOfBoundsException ex){
				wotf2.writeLine("ArrayIndexOutOfBoundsException Exception Occured. Did not finish processing the results between " + queryStartTime + " and " + queryEndTime + ". ");
				ex.printStackTrace();
			}catch (Exception ex){
				wotf2.writeLine("Tool,Introscope," + queryStartTime + ",None,Random Exception Occured. Did not finish processing the results between " + queryStartTime + " and " + queryEndTime + ". ");
				ex.printStackTrace();
			}
			finally {
				try {
					rset.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
				try {
					stmt.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
			}
			try {
				conn.close();
			} catch (Exception ignore) {
				wotf2.writeLine("Exception Occured closing connection.");
				ignore.printStackTrace();
			}
			tries++;
			Thread.sleep(2000); //wait two seconds before trying to connect again.  
		} // End  while(!reachedEnd && tries < maxTrys){
		if (printRaw){
			wotf.closeToWrite();
		}

		wotf2.closeToWrite();	
	}

	public boolean isPrintRaw() {
		return printRaw;
	}

	public void setPrintRaw(boolean printRaw) {
		this.printRaw = printRaw;
	}

	private String[] parseMetricPath(String xml) {
		// I really just need the first "MetricPath" and the last two error messages.
		String metricPath = "";
		String errorMessage1 = "";
		String errorMessage2 = "";
		String operationname = "";
		boolean firstFound = false;
		int foundTwo = 0;
		// look in xml for specific stuff.
		while(xml.length() > 2) {

			String subxml = xml.substring(xml.indexOf("<"), xml.indexOf(">") + 1);
			// Match context for the details we are looking for.
			if (subxml.contains("<CalledComponent ")) {
				if (!firstFound){
					firstFound=true;
					int startss=subxml.indexOf("MetricPath=\"") + 12;
					int endss=subxml.indexOf("Duration=\"")-2;
					metricPath=subxml.substring(startss, endss);
				}
			} else if (subxml.contains("<Parameter Name=\"Error Message\" ")) {
				if (foundTwo < 2){
					int startss=subxml.indexOf("Value=\"") + 7;
					int endss=subxml.indexOf("\"/>");
					errorMessage2 = errorMessage1;
					errorMessage1 = subxml.substring(startss, endss);
					foundTwo++;
				}
			} else if (subxml.contains("Name=\"operationname\"")) {
				operationname = subxml.substring(subxml.indexOf("Value=\"") + 7, subxml.indexOf("\"/>"));
			} else if (subxml.contains("Name=\"Normalized URL\"")) {
				operationname = subxml.substring(subxml.indexOf("Value=\"") + 7, subxml.indexOf("\"/>"));
			}

			// remove the first context and search again.
			xml = xml.substring(xml.indexOf(">") + 1);
		}
		String[] sa = {metricPath, errorMessage1, errorMessage2, operationname};
		return sa;
	}
}
