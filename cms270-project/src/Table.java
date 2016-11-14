import java.util.*;

public class Table {
	private Dealer dealer;
	private int numPlayers;
	
	public Table(){
		dealer = Dealer.getInstance();
		numPlayers = 0;
		//initializing the deck. Maybe use singleton pattern (only one at the table)??
		Deck cardDeck = new Deck();
	}
	
	//have arraylist keeping track of number of players
	private ArrayList<Player> players = new ArrayList<Player>();
	
	//Adding a player to the table. Will add the player to the arraylist
	//and increment numPlayers
	public void addPlayer(Player player){
		players.add(player);
		numPlayers++;
	}
	
	//have getNumPlayers method that deal method in dealer class can call  
	//when dealing cards
	public int getNumPlayers(){
		return numPlayers;
	}
	
	public Iterator createIterator() {
		return new PlayerIterator(players);
	}
	
	public static void main(String[] args){
		Table table = new Table();
	}
	
}
