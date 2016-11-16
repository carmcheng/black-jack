
import java.util.ArrayList;
/**
 * 
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version
 * This class implements the Iterator interface to traverse
 * specifically through an ArrayList of Player objects
 */
public class PlayerIterator implements Iterator {
	ArrayList<Player> players;
	int position = 0;
	/**
	 * Constructor that creates an Iterator to traverse through ArrayList
	 * @param an ArrayList of Player objects
	 */
	public PlayerIterator(ArrayList<Player> players) {
		this.players = players;
	}
	
	/**
	 * Traverses to next Player in collection
	 * 
	 * @return the next Player object in collection
	 */
	public Player next() {
		Player currentPlayer = players.get(position);
		position = position + 1;
		return currentPlayer;
	}
	
	/**
	 * Checks if another Player exists in the next index 
	 * 
	 * @return boolean value if next Player object is not null
	 */
	public boolean hasNext() {
		if(position >= players.size() || players.get(position) == null) {
			return false;
		} else {
			return true;
		}
	}
}
