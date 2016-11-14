import java.util.*;

public class Table {
	private Dealer dealer;
	private static int numPlayers;
	private ArrayList<Player> players;
	private Deck cardDeck;
	
	public Table(){
		dealer = Dealer.getInstance();
		numPlayers = 0;
		//initializing the deck. Maybe use singleton pattern (only one at the table)??
		cardDeck = new Deck();
		players = new ArrayList<Player>();
	}
	
	//Adding a player to the table. Will add the player to the arraylist
	//and increment numPlayers
//	public static void addPlayer(Player player){
//		players.add(player);
//		numPlayers++;
//	}
	
	//Deleting player from table. Removes player from arraylist
	//and decrements numPlayers
//	public void delPlayer(Player player) {
//		players.remove(player);
//		numPlayers--;
//	}
	
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
			System.out.println("Place your starting bet");
			double bet = scan.nextDouble();
			player.setBet(bet);
			players.add(player);
			numPlayers++;
			count ++;
		} while ( count <= number);
	}
	
	public void firstDeal() {
		Iterator playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player player = (Player) playerIterator.next();
			player.getHand().addCard(cardDeck.dealCard());
			player.getHand().addCard(cardDeck.dealCard()); //adds two cards to player's hand on first deal
		}
	}
	
	public void playGame() {
		Scanner scan = new Scanner(System.in);
		Iterator playerIterator = new PlayerIterator(players);
		firstDeal();
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			currentPlayer.printHand();
			if(currentPlayer.getHand().checkBlackjack()) { // if they have blackjack (changes state)
				currentPlayer = (Player) playerIterator.next();
			} else {
				System.out.println("Would you like to double down? Yes/No?");
				String answer;
				do {
					answer = scan.next();
					if(answer.equalsIgnoreCase("Yes")) {
						currentPlayer.doubleDown();
					} else if (answer.equalsIgnoreCase("No")) {
						System.out.println("you have chosen to NOT double down");
					} else {
						System.out.println("Enter a valid response");
					}
				} while(!answer.equalsIgnoreCase("yes") || !answer.equalsIgnoreCase("no"));
				
				System.out.println("Choose to hit or stand");
				String move = scan.next();
				while(move.equalsIgnoreCase("hit")) {
					currentPlayer.getHand().addCard(cardDeck.dealCard());
					System.out.println("Hit or stand?");
					move = scan.next();
				} 
			}
			
			if(currentPlayer.getHand().checkHandValue() > 21) {
				System.out.println("You've busted"); // if they bust
			}
			if(currentPlayer.getHand().checkHandValue() == 21) {
				System.out.println("You have 21!"); // if they have 21
			}
		}
	}
	
	public static void main(String[] args){
		Table table = new Table();
		table.startGame();
		table.playGame();
	}
	
}
