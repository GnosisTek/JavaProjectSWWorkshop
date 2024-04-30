package com.bham.pij.assignments.pontoon;

/* Martin Golding-Quigley
 */

import java.util.ArrayList;

public class Card {
	
	public static enum Suit {CLUBS, HEARTS, DIAMONDS, SPADES};
	public static enum Value {ACE, TWO, THREE, FOUR, FIVE, SIX,
		SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
	
	Suit suit;
	Value value;
	
	public Card(Suit suit, Value value) {
		setSuit(suit);
		setValue(value);
	}
	
	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public ArrayList<Integer> getNumericalValue(){
		ArrayList<Integer> numval = new ArrayList<Integer>();
		
		switch (value) {
			case ACE:
				numval.add(1);
				numval.add(11);
				break;
			case JACK:
			case QUEEN:
			case KING:
				numval.add(10);
				break;
			default:
				numval.add(value.ordinal() + 1);
				break;
		}
		
		return numval;
	}	
}
