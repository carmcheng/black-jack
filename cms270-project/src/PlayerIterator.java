
import java.util.ArrayList;

public class PlayerIterator implements Iterator {
	ArrayList<Player> players;
	int position = 0;
	
	public PlayerIterator(ArrayList<Player> players) {
		this.players = players;
	}
	
	public Player next() {
		Player currentPlayer = players.get(position);
		position = position + 1;
		return currentPlayer;
	}
	
	public boolean hasNext() {
		if(position >= players.size() || players.get(position) == null) {
			return false;
		} else {
			return true;
		}
	}
}
