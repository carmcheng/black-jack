import java.util.*;

public class Deck {
	private ArrayList<Card> deck;
	private final int SIZE_OF_DECK = 54;

	public Deck() {
		deck = new ArrayList<Card>(SIZE_OF_DECK);
		String cardSuits[] = {"Spade", "Clover", "Heart", "Diamond"};
		String cardNames[] = {"2", "3", "4", "5", "6", "7",
							  "8", "9", "10", "J", "Q", "K", "A"};
							  

		for (String suit : cardSuits) {
			int value = 1;
			for (char name : cardNames) {
				if (name == 'J' || name == 'Q' || name == 'K') {
					value = 10;
				} else if (name == 'A') {
					// Set initial value of Ace to 11
					value = 11;
				} else {
					value++;
				}
				Card c = new Card(value, name, suit);
				deck.add(c);
			}
		}
	}
	

	// Used cards are placed back into the deck and then reassigned
	// to a random index. Card in random index replaces original card.
	public void shuffle() {
		for (int i = 0; i < 52; i++) {
			Random rand = new Random();
			int n = rand.nextInt(52);
			Card temp = deck.get(i);
			deck.get(i) = deck.get(n);
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
