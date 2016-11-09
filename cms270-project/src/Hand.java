import java.util.ArrayList;

public class Hand {

	private int totalValue;
	private int totalNumOfCards;
	private Card cards[];
	
	public Hand() {
		totalValue = 0;
		totalNumOfCards = 0;
	}
	
	public void addHand(Card c) {
		
	}
	
	public int numOfCards() {
		return totalNumOfCards;
	}
	public int checkHand() {
		return totalValue;
	}
}//Use state pattern to change behaviour depending on the state of the hand??
