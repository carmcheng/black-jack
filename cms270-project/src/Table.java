import java.util.*;

public class Table {
	
	private int numPlayers;
	
	public Table(){
		numPlayers = 0;
		//initializing the deck. Maybe use singleton pattern (only one at the table)??
		Deck cardDeck = new Deck();
	}
	
	//have arraylist keeping track of number of players
	private ArrayList<String> players = new ArrayList<String>();
	
	//Adding a player to the table. Will add the player to the arraylist
	//and increment numPlayers
	public void addPlayer(Player player){
		players.add(player.getName());
		numPlayers++;
	}
	
	//have getNumPlayers method that deal method in dealer class can call  
	//when dealing cards
	public int getNumPlayers(){
		return numPlayers;
	}
}
