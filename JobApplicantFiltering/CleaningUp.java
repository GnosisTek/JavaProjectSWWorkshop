package com.bham.pij.assignments.candidates;

/* Martin Golding-Quigley
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;


public class CleaningUp {
	
	String idNum;
	ArrayList<ArrayList<String>> fileInfo = new ArrayList<ArrayList<String>>();
	
	
	public void cleanUpFile() {
		Path readPath = Paths.get("dirtycv.txt").toAbsolutePath();
		Path writePath = Paths.get("cleancv.txt").toAbsolutePath();
		
		if (!Files.isReadable(readPath)) {
			System.err.println("dirtycv.txt not readable");
			System.exit(1);
		}
		if (!Files.isWritable(writePath))
			try {
				Files.createFile(writePath);
			}
			catch (IOException e) {
				System.err.println("cleancv.txt not writeable");
				System.exit(1);
			}

		getFileInfo(readPath);
		writeToFile(writePath);	
	}
	

	
	
	public void getFileInfo(Path fileIn) {

		File dirtycv = new File(fileIn.toString());
		
		try {
			BufferedReader getInfo = new BufferedReader(
					new FileReader(dirtycv));
			
			String line = getInfo.readLine();
			
			while(line != null) {
				ArrayList<String> dataArray = new ArrayList<String>();
				
				while(!line.equals("End")) {
					if ((line.toLowerCase().charAt(0) == 'c') && (line.toLowerCase().charAt(1) == 'v')) {
						idNum = "000" + line.charAt(3);
					}
					else {
						dataArray.add(getData(line));
					}
					line = getInfo.readLine();
				}
				line = getInfo.readLine();
				fileInfo.add(dataArray);
			}
			getInfo.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Couldn't Find File");
			System.exit(0);
		}
		catch(IOException e) {
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		}
	}
	
	
	
	
	public void writeToFile(Path fileOut) {
		
		try (BufferedWriter writer = Files.newBufferedWriter(fileOut);
			)
			{
			for(int i = 0; i<fileInfo.size(); i++) {
				String lineToWrite = ""; 
				
				for (int j = 0; j < fileInfo.get(i).size(); j++) {
					if (fileInfo.get(i).get(j) != null) {
						lineToWrite += fileInfo.get(i).get(j);
						lineToWrite += ",";
					}
				}
				writer.write(lineToWrite);
				if (i != fileInfo.size()-1) {
					writer.newLine();
				}
			}
		}
			
		catch(IOException e) {
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		}
	}
	
	
	

	public String getData(String line) {
		
		String dataField = "", data = "";
		Boolean required = false;
		int index = 0;
		
		while(line.charAt(index) != ':') {
			dataField+=line.charAt(index);
			index+=1;
		}
		
		index+=1;
		while(index < line.length()) {
			data+=line.charAt(index);
			index+=1;
		}
		
		if (dataField.toLowerCase().equals("surname")) {
			data+=idNum;
		}
		else {
			switch(dataField.toLowerCase()) {
				case "qualification":
				case "position":
				case "experience":
				case "email":
					required = true;
					break;
				default:
					break;
				}
			if (required == false) {
				data = null;
			}
		}
			
		return data;
	}
}
