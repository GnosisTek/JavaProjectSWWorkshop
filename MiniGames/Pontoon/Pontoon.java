package com.bham.pij.assignments.pontoon;

/* Martin Golding-Quigley
 */

public class Pontoon extends CardGame {
	
	public Pontoon(int nplayers) {
		super(nplayers);
	}
	
	public void dealInitialCards() {
		for (int i = 0; i < super.getNumPlayers(); i++) {
			for (int j = 0; j < 2; j++) {
				super.getPlayer(i).dealToPlayer(super.getDeck().dealRandomCard());
			}
		}
	}
	
	public int compareHands(Player hand1, Player hand2) {
		int winner;
		int handval1 = hand1.getBestNumericalHandValue(), handval2 = hand2.getBestNumericalHandValue();
		boolean pontoon1 = pontoon(hand1), pontoon2 = pontoon(hand2),
				fct1 = fiveCardTrick(hand1), fct2 = fiveCardTrick(hand2);
		
		if ((handval1 > handval2) && (handval1 != handval2)) {
			winner = -1;
		}
		else {
			if (handval1 == handval2) {
				if ((pontoon1 = true) && (pontoon2 = false)) {
					winner = -1;
				}
				else {
					if ((pontoon1 = false) && (pontoon2 = true)) {
						winner = 1;
					}
					else {
						if ((fct1 = true) && (fct2 = false)) {
							winner = -1;
						}
						else {
							if ((fct1 = false) && (fct2 = true)) {
								winner = 1;
							}
							else {
								winner = 0;
							}
						}
					}
				}
			}
			else {
				winner = 1;
			}
		}
		
		
		return winner;
	}
	
	public boolean pontoon(Player hand) {
		if ((hand.getHandSize() == 2) && (hand.getBestNumericalHandValue() == 21)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean fiveCardTrick(Player hand) {
		if ((hand.getHandSize() == 5) && (hand.getBestNumericalHandValue() == 21)) {
			return true;
		}
		else {
			return false;
		}
	}
}
