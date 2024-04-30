package com.bham.pij.assignments.pontoon;

/* Martin Golding-Quigley
 */

import java.util.ArrayList;

public abstract class CardGame {
	
	int nplayers;
	Deck deck;
	ArrayList<Player> players = new ArrayList<Player>();
	
	public CardGame (int nplayers) {
		this.nplayers = nplayers;
		this.deck = new Deck();
		generatePlayers();
	}

	public abstract void dealInitialCards();
	
	public abstract int compareHands(Player hand1, Player hand2);
	
	public Deck getDeck() {
		return deck;
	}
	
	public Player getPlayer(int i) {
		return players.get(i);
	}
	
	public int getNumPlayers() {
		return nplayers;
	}
	
	public void generatePlayers(){
		for (int i = 0; i < nplayers; i++) {
			players.add(new Player("Player" + Integer.toString(i + 1)));
		}
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
}
