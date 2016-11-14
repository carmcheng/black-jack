
import java.util.ArrayList;

public class HandIterator implements Iterator {
	ArrayList <Card> cards;
	int position = 0;
	
	public HandIterator(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public Card next() {
		Card currentCard = cards.get(position);
		position = position + 1;
		return currentCard;
	}
	
	public boolean hasNext() {
		if(position >= cards.size() || cards.get(position) == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
