import java.util.*;

public class Deck {
	private Card deck[];
	private final int DECK_SIZE = 52;
	private int cardsUsed;
	private final int TOP = 0;

	public Deck() {
		deck = new Card[DECK_SIZE];
		String cardSuits[] = {"Spade", "Clover", "Heart", "Diamond"};
		char cardNames[] = {'A', '2', '3', '4', '5', '6', 
							'7', '8', '9', 'J', 'Q', 'K'};
		int cardIndex = 0;

		for (String suit : cardSuits) {
			int value = 1;
			for (char name : cardNames) {
				deck[cardIndex] = new Card(value, name, suit);
				value++;
				cardIndex++;
			}
		}
		cardsUsed = 0;
	}

	// Used cards are placed back into the deck and then reassigned
	// to a random index. Card in random index replaces original card.
	public void shuffle() {
		for (int i = 0; i < 52; i++) {
			Random rand = new Random();
			int n = rand.nextInt(52);
			Card temp = deck[i];
			deck[i] = deck[n];
			deck[n] = temp;
		}
		cardsUsed = 0;
	}

	public Card dealCard() {
		if (cardsUsed == 52) {
			shuffle();
		}
		cardsUsed++;
		return deck[cardsUsed - 1];
	}

	// Function returns the number of cards left
	public int cardsLeft() {
		return DECK_SIZE - cardsUsed;
	}

}
