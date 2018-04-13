package com.faldmo.tdbq;

import java.util.Calendar;
import java.util.Date;

public class ValuesInput {

		ValuesInput (String startDate, String startTime, String endDate, String endTime, String query){
			this.startDate = startDate;
			this.startTime = startTime;
			this.endDate = endDate;
			this.endTime = endTime;
			this.query = query;
		}
		
		ValuesInput(){
		
		}
		
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getQuery() {
			return query;
		}
		public void setQuery(String query) {
			this.query = query;
		}

		String startDate ="";
		String startTime="";
		String endDate="";
		String endTime="";
		String query="";
		
		public void checkQuery() {
			if (this.query == "" || this.query == null){
				this.query = "type:errorsnapshot";
				System.out.println("Using default query of \"type:errorsnapshot\"");
			}
		}
		
		public void checkTime(String phours) {
			boolean defaultRange = false;
			int hours = Integer.parseInt(phours) * -1;
			if (this.startDate == null || this.startDate == ""){
				defaultRange = true;
			}
			else if (this.startTime == null || this.startTime == ""){
				defaultRange = true;
			}
			else if (this.endDate == null || this.endDate == ""){
				defaultRange = true;
			}
			else if (this.endTime == null || this.endTime == ""){
				defaultRange = true;
			}
			
			if (defaultRange){
				Date today = Calendar.getInstance().getTime();
				Calendar cal = Calendar.getInstance();
				cal.setTime(today);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int year = cal.get(Calendar.YEAR);
				int hour = cal.get(Calendar.HOUR);
				int minute = cal.get(Calendar.MINUTE);
				
				cal.add(Calendar.HOUR_OF_DAY, hours);
				int ymonth = cal.get(Calendar.MONTH) + 1;
				int yday = cal.get(Calendar.DAY_OF_MONTH);
				int yyear = cal.get(Calendar.YEAR);
				int yhour = cal.get(Calendar.HOUR);
				int yminute = cal.get(Calendar.MINUTE);
				
				this.startDate = ymonth + "/" + yday + "/" + yyear;
				this.startTime = yhour + ":" + yminute;
				this.endTime = hour + ":" + minute;
				this.endDate = month + "/" + day + "/" + year;
				System.out.println("Using configured duration of " + phours +" hours ago.");
			}
			
		}
		
}
