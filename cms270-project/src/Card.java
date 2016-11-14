/**
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version 
 * This class provides the aspects of all the cards in a deck
 * such as the names, suits and values. It also includes
 * accessor methods to these aspects in order to play
 * a game of Blackjack.
 * 
 */
public class Card {

	private int cardValue;
	private String cardName;
	private String cardSuit;
	
	/**
	 * Constructor that creates a card for the deck.
	 * @param value - The appropriate value to assign to the card.
	 * @param name - The appropriate name for the card.
	 * @param suit - The appropriate suit for the card. 
	 */
	public Card(int value, String name, String suit) {
		cardValue = value;
		cardName = name;
		cardSuit = suit;
	}
	/**
	 * This method accesses the name of the card.
	 * @return a String representation of the name of the card.
	 */
	public String getCardName() {
		return cardName;
	}
	/**
	 * This method accesses the value of the card.
	 * @return an integer representation of the card value.
	 */
	public int getCardValue() {
		return cardValue;
	}
	/**
	 * This method sets a card value to a given value.
	 * For example, an Ace can have a value of 1 or 11.
	 * @param newValue - The value to change the card value to.
	 */
	public void setCardValue(int newValue) {
		cardValue = newValue;
	}
	/**
	 * This method accesses the card suit.
	 * @return a String representation of the card suit.
	 */
	public String getCardSuit() {
		return cardSuit;
	}
	/**
	 * This method prints the card's name, suit and value as a String.
	 */
	public String toString() {
		return cardName + ", " + cardSuit;
	}
}
