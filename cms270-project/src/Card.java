
public class Card {

	private int cardValue;
	private char cardName;
	private String cardSuit;
	
	public Card(int value, char name, String suit) {
		cardValue = value;
		cardName = name;
		cardSuit = suit;
	}
	
	public char getCardName() {
		return cardName;
	}
	
	public int getCardValue() {
		return cardValue;
	}
	
	public String getCardSuit() {
		return cardSuit;
	}
}
