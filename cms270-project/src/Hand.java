import java.util.ArrayList;

public class Hand {

	private int totalValue;
	private int totalNumOfCards;
	private ArrayList<Card> cards;
	
	public Hand() {
		totalValue = 0;
		totalNumOfCards = 0;
	}
	
	public void addHand(Card c) {
		totalNumOfCards++;
		totalValue += c.getCardValue();
	}
	
	public int numOfCards() {
		return totalNumOfCards;
	}
	public int checkHand() {
		return totalValue;
	}
}
