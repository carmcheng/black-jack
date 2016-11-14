import java.util.*;

public class Deck {
	private ArrayList<Card> deck;
	private static Deck cardDeck;

	private String cardSuits[] = {"Spade", "Clover", "Heart", "Diamond"};
	private String cardNames[] = {"2", "3", "4", "5", "6", "7",
								  "8", "9", "10", "J", "Q", "K", "A"};

	/**
	 * Private constructor ensures only one Deck object is made
	 */
	private Deck() {							  
		deck = new ArrayList<Card>();
		
		for(int i = 0; i < 3; i++) {
			for (String suit : cardSuits) {
				int value = 1;
				for (String name : cardNames) {
					if (name.equals("J") || name.equals("Q") || 
							name.equals("K") || name.equals("10")) {
						value = 10;
					} else if (name.equals("A")) {
						// Set initial value of Ace to 11
						value = 11;
					} else {
						value++;
					}
					deck.add(new Card(value, name, suit));
				}
			}
		}
	}
	
	public static Deck getInstance() {
		if(cardDeck == null) {
			cardDeck = new Deck();
		} 
		return cardDeck;
	}


	// Used cards are placed back into the deck and then reassigned
	// to a random index. Card in random index replaces original card.
	public void shuffle() {
		for (int i = 0; i < 156; i++) {
			Random rand = new Random();
			int n = rand.nextInt(156);
			Card temp = deck.get(i);
			temp = deck.get(n);
			deck.set(i, deck.get(n));
			deck.set(n, temp);
		}

	}
	
	public Card dealCard() {
		if (deck.size() == 156)
			shuffle();
		Card card = deck.get(0);
		deck.remove(0);
		
		return card;
	}

	public int cardsLeft() {
		return deck.size();

	}
}
			

