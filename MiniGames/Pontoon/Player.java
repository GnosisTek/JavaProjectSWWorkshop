package com.bham.pij.assignments.pontoon;

/* Martin Golding-Quigley
 */

import java.util.ArrayList;

public class Player {
	
	String name;
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player (String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void dealToPlayer(Card card) {
		hand.add(card);
	}
	
	public void removeCard(Card card) {
		hand.remove(card);
	}
	
	public ArrayList<Integer> getNumericalHandValue() {
		
		ArrayList<Integer> handval = new ArrayList<Integer>();
		handval.add(0);
		for (int i = 0; i < hand.size(); i++) {
			if(!hand.get(i).getValue().equals(Card.Value.ACE)) {
				handval.set(0, hand.get(i).getNumericalValue().get(0) + handval.get(0));
			}
		}
		for (int i = 0; i < hand.size(); i++) {
			if(hand.get(i).getValue().equals(Card.Value.ACE)) {
				handval.add(handval.get(handval.size()-1));	
				for (int j = 0; j < handval.size()-1; j++ ) {
					handval.set(j, handval.get(j) + hand.get(i).getNumericalValue().get(0));
				}
				handval.set(handval.size()-1, handval.get(handval.size()-1) + hand.get(i).getNumericalValue().get(1));
			}
		}
		return handval;
	}

	public int getBestNumericalHandValue() {
		ArrayList<Integer> handval = getNumericalHandValue();
		int bestval = 0;
		for (Integer val : handval) {
			if (val < 22) {
				bestval = val;
			}
			else {
				break;
			}
		}	
		return bestval;
	}
	
	public ArrayList<Card> getCards() {
		return hand;
	}
	
	public int getHandSize() {
		return hand.size();
	}	
}
