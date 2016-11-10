import java.util.ArrayList;

public class Hand {

	private int totalValue;
	private int totalNumOfCards;
<<<<<<< HEAD
	private ArrayList<Card> cards;

	//Hand constructor does not take any arguments
	//Each new Hand object starts with a totalValue of 0 and totalNumOfCards of 0
=======
	private Card cards[];
	
>>>>>>> branch 'master' of https://github.com/rollins-cms270/project-team-vegas.git
	public Hand() {
		totalValue = 0;
		totalNumOfCards = 0;
	}
	//addHand method increments totalNumOfCards by 1
	//Adds the card's value to the total value and then adds the card to the array list that hand holds
	public void addHand(Card c) {
		totalNumOfCards++;
		totalValue += c.getCardValue();
		this.cards.add(c);
	}
	
	//numOfCards method returns the total number of cards that hand holds
	public int numOfCards() {
		return totalNumOfCards;
	}
	
	//checkHand method returns totalValue of cards in hand object
	public int checkHand() {
		return totalValue;
		
	}
}//Use state pattern to change behaviour depending on the state of the hand??
