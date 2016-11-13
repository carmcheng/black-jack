import java.util.*;

public class Table {
	private Dealer dealer;
	private static int numPlayers;
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public Table(){
		dealer = Dealer.getInstance();
		numPlayers = 0;
		//initializing the deck. Maybe use singleton pattern (only one at the table)??
		Deck cardDeck = new Deck();
	}
	
	//Adding a player to the table. Will add the player to the arraylist
	//and increment numPlayers
	public static void addPlayer(Player player){
		players.add(player);
		numPlayers++;
	}
	
	//Deleting player from table. Removes player from arraylist
	//and decrements numPlayers
	public void delPlayer(Player player) {
		players.remove(player);
		numPlayers--;
	}
	
	//have getNumPlayers method that deal method in dealer class can call  
	//when dealing cards
	public int getNumPlayers(){
		return numPlayers;
	}
	
	public Iterator createIterator() {
		return new PlayerIterator(players);
	}
	
	/**
	 * Starts table with certain number of Player objects
	 * 
	 */
	
	public void startGame() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many people are there?");
		int number = scan.nextInt();
		int count = 1;
		do {
		System.out.println("New Player?"); 
		String name = scan.next();
		System.out.println("How much money do you have?");
		double money = scan.nextDouble();
		Player player = new Player(name, money);
		count ++;
		players.add(player);
		numPlayers++;
		} while ( count <= number);
	}
	
	public void playGame() {
		
	}
	
	public static void main(String[] args){
		Table table = new Table();
	}
	
}
