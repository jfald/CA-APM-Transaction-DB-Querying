package com.faldmo.tdbq;

import java.io.*;

public class WriteOutputToFile {
	
	FileWriter fstream;
	BufferedWriter out;
	
	public boolean openToWrite(){
		return openToWrite("out.txt");
	}
	
	public boolean openToWrite(String fileName){
		try{
			fstream = new FileWriter (fileName);
			out = new BufferedWriter(fstream);
			return true;
		}catch (Exception e){
			System.err.println("openToWrite Error: " + e.getMessage());
			return false;
		}
	}
	
	
	public boolean closeToWrite(){
		try{
			out.close();
			return true;
		}catch (Exception e){
			System.err.println("closeToWrite Error: " + e.getMessage());
			return false;
		}
	}
	
	public boolean writeLine(String line){
		try{
			out.write(line);
			out.newLine();

		}catch (Exception e){
			System.err.println("writeLine Error: " + e.getMessage() );
			return false;
		}
		return true;	
	}

}
