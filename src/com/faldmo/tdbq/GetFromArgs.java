package com.faldmo.tdbq;

public class GetFromArgs {
	TdbqUtilities tu = new TdbqUtilities();
	
	public ValuesInput getFromArgs(String[] args, ValuesInput vi){
		if (!tu.properDate(args[0])) {
			System.out.println("---Start date not in proper format---");
			System.exit(1);
		} else {
			vi.setStartDate(args[0]);
		}
		
		if (!tu.properTime(args[1])) {
			System.out.println("---Start time not in proper format---");
			System.exit(1);
		}else {
			vi.setStartTime(args[1]);
		}

		// Make sure end date/time is in the proper format.
		if (!tu.properDate(args[2])) {
			System.out.println("---End date not in proper format---");
			System.exit(1);
		}else {
			vi.setEndDate(args[2]);
		}
		
		if (!tu.properTime(args[3])) {
			System.out.println("---End time not in proper format---");
			System.exit(1);
		} else {
			vi.setEndTime(args[3]);
		}
		

		//Another check that the end time is after the start time?	
		if (!tu.checkQuery(args[4])) {
			System.out.println("You passed a questionable query (I couldn't find \"type\" and/or \"host\" in query), but I'll use it anyway");
		} else {
			vi.setQuery(args[4]);
		}
		 
		return vi;
		 
	}


}
