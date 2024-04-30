package com.bham.pij.assignments.rps;

import javax.swing.*;
import java.util.ArrayList;

import java.util.Random;
import java.util.Scanner;

public class FalloutTerminal {

	/**
	 * This method creates a single line of the terminal display.
	 * @param startAddress The first address of the line.
	 * @param wordProbability If less than or equal to 0.6 the line should contain one word.
	 * @return The line for display.
	 */
	public String buildDisplayLine(int startAddress, float wordProbability) {


		String word = null;
		String ret;
		String asc = "[]!\"$\\~#+@.<>,?{}%*+=:;^&()-_/|`";
		Random ran = new Random();

		// Creates string of 60 ASCII symbols
		StringBuilder syms = new StringBuilder("");
		for (int i = 0; i<60; i++){
			syms.append(asc.charAt(ran.nextInt(asc.length())));
		}

		// Produces secondary address
		int address2 = startAddress + 240;
		String hexStart = "0x" + Integer.toHexString(startAddress) + " ";
		String hexEnd = " 0x" + Integer.toHexString(address2);

		// Randomly chooses value 0 or 1 to determine which side word will appear on
		int side = ran.nextInt(2);

		// Checks if word appears and inserts it it symbol string within boundaries
		if (wordProbability <= 0.6) {
			word = getRandomWord();
			addSelectedWord(word);
			if (side == 0)
				syms.insert(ran.nextInt(23),word);
			else
				syms.insert(53 - ran.nextInt(23),word);
			syms.setLength(60);
		}

		ret = hexStart + syms.insert(30, hexEnd + " ");

		return ret.toUpperCase();
	}

/*
 ***************************************************************************************	
 */

	private static final int START_ADDRESS = 0x9380;
	private static final int DISPLAY_HEIGHT = 20;
	private static final int NUM_CHARACTERS_PER_ROW = 60;
	private static final float WORD_PROB_1 = 0.6f;
	private static final int WORD_LENGTH = 8;
	private static final int NUM_GUESSES_ALLOWED = 4;
	private static final int BYTE_SIZE = 8;
	private static Random rand;
	private static final String[] words = {
			"flourish",
			"appendix",
			"separate",
			"unlawful",
			"platform",
			"shoulder",
			"marriage",
			"attitude",
			"reliable",
			"contempt",
			"prestige",
			"evaluate",
			"division",
			"birthday",
			"orthodox",
			"appetite",
			"perceive",
			"pleasant",
			"surprise",
			"elephant",
			"incident",
			"medieval",
			"absolute",
			"dominate",
			"designer",
			"misplace",
			"possible",
			"graduate",
			"solution",
			"governor"
	};
	
	private static String password;

	private static ArrayList<String> selectedWords = new ArrayList<String>();
	
	private void run() {
		
		System.out.println(buildDisplay());
		
		Scanner in = new Scanner(System.in);
		
		int guessCount = NUM_GUESSES_ALLOWED;
		
		boolean done = false;
		
		do {
			System.out.println("> Password required.");
			System.out.println("> Attempts remaining = " + guessCount);
			System.out.println(">");
			String guess = in.nextLine();
			
			if (guess.equalsIgnoreCase(getPassword())) {
				System.out.println("> Access granted.");
				done = true;
			}
			
			else {
				--guessCount;
				System.out.println("> Access denied.");
				System.out.println("> Likeness = " + getCorrectCount(guess, password));
			}	
		} while (guessCount > 0 && !done);
		
		if (guessCount == 0) {
			System.out.println("> Initiating lockout");
		}
		
		in.close();
	}
	
	private int getCorrectCount(String guess, String password) {
		
		int count = 0;
		
		for (int i = 0; i < guess.length(); i++) {			
			if (guess.charAt(i) == password.charAt(i)) {
				count++;
			}
		}
		
		return count;
	}
		
	private String getRandomWord() {
		return words[rand.nextInt(words.length)];
	}
	
	private String buildDisplay() {
		
		String ret = "";
		
		int address = START_ADDRESS;
						
		for (int i = 0; i < DISPLAY_HEIGHT; i++) {
											
			float rf = rand.nextFloat();
			
			String line = buildDisplayLine(address, rf);

			ret += line + "\n";
			
			address += BYTE_SIZE * NUM_CHARACTERS_PER_ROW;
		}
		
		setRandomPassword();
		
		return ret;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pw) {
		password = pw;
	}

	private void addSelectedWord(String word) {
		selectedWords.add(word);
	}
	
	public void setRandomPassword() {
		password = selectedWords.get(rand.nextInt(selectedWords.size())).toLowerCase();
	}

	public FalloutTerminal() {
		rand = new Random(System.currentTimeMillis());	
	}
	
	public static void main(String[] args) {
		new FalloutTerminal().run();
	}
}
