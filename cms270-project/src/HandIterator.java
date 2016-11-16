
import java.util.ArrayList;
/**
 * 
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version 
 * This class implements the Iterator interface to traverse
 * specifically through an ArrayList of Card objects
 */
public class HandIterator implements Iterator {
	ArrayList <Card> cards;
	int position = 0;
	/**
	 * Constructor that creates an Iterator to traverse through ArrayList
	 * @param an ArrayList of Card objects
	 */
	public HandIterator(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	/**
	 * Traverses to next Card in collection
	 */
	public Card next() {
		Card currentCard = cards.get(position);
		position = position + 1;
		return currentCard;
	}
	/**
	 * Checks if another Card exists in the next index 
	 */
	public boolean hasNext() {
		if(position >= cards.size() || cards.get(position) == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
