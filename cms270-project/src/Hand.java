import java.util.ArrayList;

public class Hand {

	private int totalValue;
	private int totalNumOfCards;
	private ArrayList<Card> cards;
	
	public Hand() {
		totalValue = 0;
		totalNumOfCards = 0;
		cards = new ArrayList<Card>(); 
	}
	
	//addHand method increments totalNumOfCards by 1
	//Adds the card's value to the total value and then adds the card to the array list that hand holds
	public void addCard(Card c) {
		totalNumOfCards++;
		totalValue += c.getCardValue();
		cards.add(c);
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	//numOfCards method returns the total number of cards that hand holds
	public int numOfCards() {
		return totalNumOfCards;
	}
	
	//checkHand method returns totalValue of cards in hand object
	public int checkHandValue() {
		return totalValue;
		
	}
	
	public boolean checkBlackjack() {
		if(totalNumOfCards == 2 && totalValue == 21) {
			return true;
		} else {
			return false;
		}
	}
	
	public Iterator createIterator() {
		return new HandIterator(cards);
	}
	
}//Use state pattern to change behaviour depending on the state of the hand??
