import java.util.ArrayList;
/**
 * 
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version 
 * This class provides a hand for a player and a dealer
 * in a game of Blackjack. It includes methods appropriate for
 * dealing cards to a player's/dealer's hand.
 */
public class Hand {

	private int totalValue;
	private int totalNumOfCards;
	private ArrayList<Card> cards;
	
	/**
	 * Constructor that creates an initial Hand of no cards with no values.
	 */
	public Hand() {
		totalValue = 0;
		totalNumOfCards = 0;
		cards = new ArrayList<Card>(); 
	}
	/**
	 * This method adds the card's value to the total value
	 * and then adds the card to the array list that hand holds.
	 * @param c - The card to add to total value and array list.
	 */
	public void addCard(Card c) {
		totalNumOfCards++; 				//increments totalNumOfCards by 1
		totalValue += c.getCardValue();
		cards.add(c);
	}
	/**
	 * This method accesses the current set of cards in the array list.
	 * @return an array list representation of the current cards. 
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}
	/**
	 * This method accesses the total number of cards a hand holds.
	 * @return an integer representation of the total number of cards. 
	 */
	public int numOfCards() {
		return totalNumOfCards;
	}
	/**
	 * This method checks the total hand value of either player or dealer.
	 * @return an integer representation of totalValue of cards in hand object
	 */
	public int checkHandValue() {
		return totalValue;
		
	}
	/**
	 * This method determines if a hand is Blackjack.
	 * @return a boolean representation to check for Blackjack.
	 */
	public boolean checkBlackjack() {
		if(totalNumOfCards == 2 && totalValue == 21) {
			return true;
		} else {
			return false;
		}
	}
	public void reset() {
		totalNumOfCards = 0;
		totalValue = 0;
		cards = new ArrayList<Card>();
	}
	/**
	 * This method creates an iterator pattern to go through
	 * the cards in a hand.
	 * @return an iterator representation of the cards in the hand.
	 */
	public Iterator createIterator() {
		return new HandIterator(cards);
	}
	
}//Use state pattern to change behavior depending on the state of the hand??
