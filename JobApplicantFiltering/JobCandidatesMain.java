package com.bham.pij.assignments.candidates;

/* Martin Golding-Quigley
 */

public class JobCandidatesMain {

	public static void main(String[] args) {
		CleaningUp test = new CleaningUp();
		test.cleanUpFile();
		CandidatesToInterview test1 = new CandidatesToInterview();
		test1.findCandidates();
		test1.candidatesWithExperience();
		test1.createCSVFile();
		test1.createReport();
	}

}
