package com.bham.pij.assignments.candidates;

/* Martin Golding-Quigley
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CandidatesToInterview {
	
	String [] keywordsDegree = {"Degree in Computer Science", "Masters in Computer Science"};
	String [] keywordsExperience = {"Data Analyst", "Programmer", "Computer programmer", "Operator"};
	ArrayList<ArrayList<String>> toInterview = new ArrayList<ArrayList<String>>();
	
	public void findCandidates() {
		Path readPath = Paths.get("cleancv.txt").toAbsolutePath();
		Path writePath = Paths.get("to-interview.txt").toAbsolutePath();
		
		readTest(readPath);
		writeTest(writePath);
		
		extractData(readPath);
		writeToFile(writePath);	
	}
	
	
	

	
	public void extractData(Path readPath) {
		
		File cleancv = new File(readPath.toString());
		
		try {
			BufferedReader getInfo = new BufferedReader(
					new FileReader(cleancv));
			
			String line = getInfo.readLine();
			
			while(line != null) {
				ArrayList<String> dataArray = dataArrayGenerator(line);
				
				if((meetsRequirements(dataArray, keywordsDegree)) && (meetsRequirements(dataArray, keywordsExperience))) {
					toInterview.add(dataArray);
				}
				
				line = getInfo.readLine(); 
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
	
	
	
	
	
	public void writeToFile(Path writePath) {
		
		try (BufferedWriter writer = Files.newBufferedWriter(writePath);
			)
			{
			for(int i = 0; i < toInterview.size(); i++) {
				String lineToWrite = ""; 
				
				for (int j = 0; j < toInterview.get(i).size(); j++) {
					
					lineToWrite += toInterview.get(i).get(j);
					lineToWrite += " ";
					
				}
				writer.write(lineToWrite);
				if (i != toInterview.size()-1) {
					writer.newLine();
				}
			}
		}
			
		catch(IOException e) {
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		}
	}
	
	
	
	
	
	public boolean meetsRequirements(ArrayList<String> dataArray, String [] keywords) {
		boolean gotRequirement = false;
		
		for (String i : keywordsDegree) {
			if (gotRequirement == true) {
				break;
			}
			else {
				if (dataArray.contains(i)) {
					gotRequirement = true;
				}
			}
		}
		return gotRequirement;
	}
	
	
	
	
	
	public void candidatesWithExperience() {
		Path writePath = Paths.get("to-interview-experience.txt").toAbsolutePath();
		
		writeTest(writePath);
		
		try (BufferedWriter writer = Files.newBufferedWriter(writePath);
			)
			{
			String lineToWrite = "";
			
			for(ArrayList<String> i : toInterview) {
				if (Integer.parseInt(i.get(3)) > 5) {
					lineToWrite = i.get(0) + " " + i.get(3);
				}
				if (!lineToWrite.equals("")) {
					writer.write(lineToWrite);
					if (i != toInterview.get(toInterview.size()-1)) {
						writer.newLine();
					}
				}
			}
		}
				
		catch(IOException e) {
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		}
	}
	
	
	
	
	
	public void createCSVFile() {
		Path writePath = Paths.get("to-interview-table-format.csv").toAbsolutePath();
		
		writeTest(writePath);
		
		try (BufferedWriter writer = Files.newBufferedWriter(writePath);
			)
			{
			String lineToWrite;
			
			int count = 0;
			for (ArrayList<String> i : toInterview) {
				if (i.size() > count) {
					count = i.size();
				}
			}
			
			lineToWrite = "Identifier,Qualification,";
			for(int i = 1; i < (count-3)/2+1; i++) {
				lineToWrite += "Position" + i + ",Experience" + i + ",";
			}
			lineToWrite += "eMail,";
			
			writer.write(lineToWrite);
			writer.newLine();
			
			lineToWrite = "";
			
			for (ArrayList<String> i : toInterview) {
				for (int j = 0; j < i.size()-1; j++) {
					lineToWrite += i.get(j) + ",";
				}
				int commas = count - i.size();
				for (int j = 0; j < commas; j++) {
					lineToWrite += ",";
				}
				lineToWrite += i.get(i.size()-1) + ",";
				writer.write(lineToWrite);
				if (i != toInterview.get(toInterview.size()-1)) {
					writer.newLine();
				}
				lineToWrite = "";
			}
		}
					
		catch(IOException e) {
			System.out.println("An I/O Error Occurred");
			System.exit(0);
		}
	}
	
	
	
	
	
	public void createReport() {
		Path readPath = Paths.get("to-interview-table-format.csv").toAbsolutePath();
		
		File table = new File(readPath.toString());
		
		try {
			BufferedReader getInfo = new BufferedReader(
					new FileReader(table));
			

			String line = getInfo.readLine();
			ArrayList<String> dataArray = dataArrayGenerator(line);
			System.out.print(dataArray.get(0) + "\t");
			System.out.print(dataArray.get(1) + "\t\t\t");
			System.out.print("Position\t");
			System.out.print("Experience\t");
			System.out.print(dataArray.get(dataArray.size()-1));
			System.out.println();
			line = getInfo.readLine(); 
			
			
			while(line != null) {
				dataArray = dataArrayGenerator(line);
				for (int i = 0; i < 4; i++) {
					System.out.print(dataArray.get(i) + "\t");
				}
				System.out.print("\t" + dataArray.get(dataArray.size()-1));
				System.out.println();
				line = getInfo.readLine(); 
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
	
	
	
	
	public ArrayList<String> dataArrayGenerator(String line){
		String data = "";
		ArrayList<String> dataArray = new ArrayList<String>();
		
		
		
		for(int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != ',') {
				data+=line.charAt(i);
			}
			else {
				dataArray.add(data);
				data = "";
			}
		}
		return dataArray;
	}
	
	
	
	
	
	public void readTest(Path readPath) {
		if (!Files.isReadable(readPath)) {
			System.err.println("cleancv.txt not readable");
			System.exit(1);
		}
	}
	
	
	
	
	
	public void writeTest(Path writePath) {
		if (!Files.isWritable(writePath))
			try {
				Files.createFile(writePath);
			}
			catch (IOException e) {
				System.err.println("to-interview.txt not writeable");
				System.exit(1);
			}
	}
}

