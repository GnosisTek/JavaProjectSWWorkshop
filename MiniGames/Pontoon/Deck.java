package com.bham.pij.assignments.pontoon;

/* Martin Golding-Quigley
 */

import java.util.*;

public class Deck {
	
	ArrayList<Card> deck = new ArrayList<Card>(); 
	Random ran = new Random();
	
	public Deck() {
		reset();
	}
	
	public void reset() {
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Value value : Card.Value.values()) {
				deck.add(new Card(suit, value));
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public Card getCard(int i) {
		return deck.get(i);
	}
	
	public Card dealRandomCard() {
		Card randcard = deck.get(ran.nextInt(size()));
		deck.remove(randcard);
		return randcard;
	}
	
	public int size() {
		return deck.size();
	}

}
