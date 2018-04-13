package com.faldmo.tdbq.conn;

public class MomConnection {
	String apmUser="Guest";
	String apmpw="Guest";
	String mom="localhost";
	String port="5001";
	//Though these are not connection information it is helpful to store this here.
	String minutes = "10";
	String query = "type:errorsnapshot";
	String hours = "4";
	private boolean rawOutput=false;
	
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		if (minutes == null || minutes == ""){
			this.minutes = "10";
		} else {
			this.minutes = minutes;
		}
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getApmUser() {
		return apmUser;
	}
	public void setApmUser(String apmUser) {
		this.apmUser = apmUser;
	}
	public String getApmpw() {
		return apmpw;
	}
	public void setApmpw(String apmpw) {
		this.apmpw = apmpw;
	}
	public String getMom() {
		return mom;
	}
	public void setMom(String mom) {
		this.mom = mom;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setRawOutput(String property) {
		if (property.equals("true") || property.equals("True") || property.equals("TRUE")){
			this.rawOutput = true;
		} else {
			this.rawOutput = false;
		}
		
	}
	public boolean getRawOutput() {
		return this.rawOutput;
	}
	

}
