
public class Card {

	private int cardValue;
	private String cardName;
	private String cardSuit;
	
	public Card(int value, String name, String suit) {
		cardValue = value;
		cardName = name;
		cardSuit = suit;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public int getCardValue() {
		return cardValue;
	}
	
	public void setCardValue(int newValue) {
		cardValue = newValue;
	}
	
	public String getCardSuit() {
		return cardSuit;
	}
	
	public String toString() {
		return cardName + ", " + cardSuit;
	}
}
