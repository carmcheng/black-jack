import java.util.*;

public class Table {

	private Dealer dealer;
	private static int numPlayers;
	private ArrayList<Player> players;
	private Deck cardDeck;
	private Player player;
	private Pot pot;
	private final int MAX_PLAYERS = 6;
	
	private Scanner scan = new Scanner(System.in);
	private Iterator playerIterator;
	
	public Table(){
		dealer = Dealer.getInstance();
		numPlayers = 0;
		cardDeck = Deck.getInstance();
		pot = new Pot();
		players = new ArrayList<Player>();
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
		System.out.println("Welcome to the table!");
		System.out.println("How many players will be in the game? (Max: 6)");
		int number = scan.nextInt();
		while (number > MAX_PLAYERS || number < 1) {
			System.out.println("The table can only accomodate for 1 - 6 players."
					+ " Please input a valid number of players.");
			number = scan.nextInt();
		}
		int count = 1;
		do {
		System.out.println("Enter player name."); 
		String name = scan.next();
		System.out.println("How much money do you have?");
		double money = scan.nextDouble();
		player = new Player(name, money);
		players.add(player);
		numPlayers++;
		count++;
		} while (count <= number);
	}
	
	public void firstDeal() {
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			currentPlayer.getHand().addCard(cardDeck.dealCard());
			currentPlayer.getHand().addCard(cardDeck.dealCard()); //adds two cards to player's hand on first deal
			System.out.println(currentPlayer.getName() + "'s hand: ");
			currentPlayer.printHand();
			System.out.println("\tHand value: " + currentPlayer.getHand().checkHandValue());
		}
	}
	
	public void playGame() {
		playerIterator = new PlayerIterator(players);
		
		// Each player sets their bet
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			System.out.println(currentPlayer.getName() + ", place your starting bet.");
			double bet = scan.nextDouble();
			while(bet > currentPlayer.getMoney()) {
				System.out.println("You can't bet more money than you have!");
				System.out.println("You have: " + currentPlayer.getMoney() + "\nSet your bet.");
				bet = scan.nextDouble();
			}
			currentPlayer.setBet(bet);
		}
		
		// Starting hands are dealt
		firstDeal();
		
		// Player's turn to move
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			System.out.println("It's your turn, " + currentPlayer.getName() + ".");
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
