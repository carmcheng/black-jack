import java.util.*;

public class Deck {
	private char name;
	private int value;
	private String suit;
	private ArrayList<Card> deck;
	int final DECK_SIZE = 52;
	private int cardsUsed;

	public Deck() {
		deck = new ArrayList<Card>(DECK_SIZE);
		String cardSuits [] = {"Spade", "Clover", "Heart", "Diamond"};
		char cardNames [] = {'A', '2', '3', '4', '5', '6', '7', '8', '9,' '10'};
		
		for (int i = 0; i < cardSuits.length; i++) {
			for (String s : cardSuits) {
				// Initial value of Ace is set to 1
				int v = 1;
				for (char n : cardNames) {
					deck.add(new Card(v, n, s));
					v++;
				}
			}
		}
		cardsUsed = 0;
	}
	
	// Used cards are placed back into the deck and then reassigned
	// to a random index. Card in random index replaces original card.
	public void shuffle() {
		// Used cards are placed back into the deck
		for (int i = 0; i < 52; i++) {
			Random rand = new Random();
			int n = rand.nextInt(52);
			Card temp = deck.get(i);
			deck.set(i, deck.get(n));
			deck.set(n, temp);
		}
		cardsUsed = 0;
	}
	
	// Function returns the number of cards left
	public int cardsLeft() {
		return DECK_SIZE - cardsUsed;
	}
	
}
