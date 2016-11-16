import java.util.*;
import java.text.*;
/**
 * 
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version
 * This class provides a table for a game of Blackjack where players 
 * are able to join the table (that have enough money to play )and set a bet
 * to play a game. It also includes methods that handles the mechanisms for 
 * both a dealer and a player.
 *
 */
public class Table {

	private Dealer dealer;
	private static int numPlayers;
	private ArrayList<Player> players;
	private Deck cardDeck;
	private Player player;
	private Pot pot;
	private final int MAX_PLAYERS = 6;
	private DecimalFormat money = new DecimalFormat("$0.00");
	private Scanner scan = new Scanner(System.in);
	private Iterator playerIterator;

	public Table(){
		dealer = Dealer.getInstance();
		numPlayers = 0;
		cardDeck = Deck.getInstance();
		pot = new Pot();
		players = new ArrayList<Player>();
	}


	/**
	 * This method accesses the number of players at the table.
	 * @return The number of players
	 */
	public int getNumPlayers(){
		return numPlayers;
	}
	/**
	 * This method implements an iterator pattern to go through the
	 * players at the table.
	 * @return The players
	 */
	public Iterator createIterator() {
		return new PlayerIterator(players);
	}

	/**
	 * Starts table with certain number of Player objects. Checks to make sure
	 * the table does not have an invalid amount of players. A player is able to enter
	 * their name and appropriate money to start the game.
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
			System.out.println("How much money do you have? (Dollars)");
			double money = scan.nextDouble();
			if(money <= 0) {
				System.out.println("You do not have enough money to play the game");
			} else {
				player = new Player(name, money);
				players.add(player);
				numPlayers++;
				count++;
			}
		} while (count <= number);
	}

	public void restartGame() {
		playerIterator = new PlayerIterator(players);
		String response;
		boolean valid = true;
		dealer.getHand().reset();
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			System.out.println(currentPlayer.getName() + ", would you like to keep playing? Yes/No?");
			do {
				response = scan.next();
				if(response.equalsIgnoreCase("Yes")) {
					currentPlayer.getHand().reset(); 
				} else if(response.equalsIgnoreCase("No")) {
					players.remove(currentPlayer);
				} else {
					System.out.println("Enter a valid response");
					valid = false;
				}
			} while(!valid);
		}
		//if player is the last person playing
		if(players.size() == 1) {
			System.out.println(players.get(0).getName() + 
					", would you like to end the game? Yes/No?");
			response = scan.next(); 
			if(response.equalsIgnoreCase("Yes")) {
				System.out.println("Ending game...");
				return;
			} else {
				players.get(0).getHand().reset();
			}
		}
	}
	/**
	 * This method implements the first deal of the game where a player is given two cards
	 * for their hand and checks if they received Blackjack in the first deal. The same applies
	 * for a dealer
	 */
	public void firstDeal() {
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			currentPlayer.getHand().addCard(cardDeck.dealCard());
			currentPlayer.getHand().addCard(cardDeck.dealCard()); //adds two cards to player's hand on first deal
			if (currentPlayer.getHand().checkBlackjack()) {
				currentPlayer.printHand();
				System.out.println("You got blackjack!");
			}
		}
		dealer.getHand().addCard(cardDeck.dealCard());
		dealer.getHand().addCard(cardDeck.dealCard());
		if(dealer.getHand().checkBlackjack()) {
			dealer.printHand();
			System.out.println("Dealer got blackjack.");
			return;
		}


		// dealer.printHiddenHand();
		// System.out.println("Dealer's hand: ");
		// System.out.println("\t" + dealer.getHand().getCards().get(0) + "\n\tHidden Card");

		dealer.printHiddenHand();

	}
	/**
	 * This method prints the amount of money left the players hold.
	 */
	public void printMoneyLeft() {
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			System.out.println(currentPlayer.getName() + ", you have " +
					money.format(currentPlayer.getMoney()) + " left." );
		}
	}

	/**
	 * This method starts a new round of Blackjack using the player iterator interface.
	 * It also takes into account if the player has Blackjack, busted or their choice
	 * of doubling down.
	 */
	public void startRound() {
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			if (currentPlayer.getHand().checkBlackjack() && playerIterator.hasNext()) {
				currentPlayer = (Player) playerIterator.next();
			}
			System.out.println("\nIt's your turn, " + currentPlayer.getName() + ".");
			currentPlayer.printHand();
			// Double down option
			System.out.println("Would you like to double down? Yes/No?");
			String answer = scan.next();

			while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
				System.out.println("Enter a valid response.");
				answer = scan.next();
			}

			boolean flag = false;
			// Did player double down
			if(answer.equalsIgnoreCase("Yes")) { // make sure player has enough money to double down
				currentPlayer.doubleDown();
				currentPlayer.getHand().addCard(cardDeck.dealCard());
				currentPlayer.printHand();
				flag = true;
				if(currentPlayer.getHand().checkHandValue() > 21) {
					System.out.println("You've busted."); // if they bust
					break;
				}
			} else if (answer.equalsIgnoreCase("No")) {
				System.out.println("You have chosen NOT to double down");
			} 

			// Player did not double down and gets to choose hit or stand
			if (!flag) {
				System.out.println("\nChoose to hit or stand.");
				String move = scan.next();
				while (move.equalsIgnoreCase("hit")) {
					Card c = cardDeck.dealCard();
					if (c.getCardName().equals("A")) {
						System.out.println("You got an Ace card. Value = 1 or 11?");
						int ans = scan.nextInt();
						while (ans != 1 && ans != 11) {
							System.out.println("Invalid input. Try again.");
							ans = scan.nextInt();
						}
						if (ans == 1) {
							c.setCardValue(1);
						} else if (ans == 11) {
							break;
						}
					}
					currentPlayer.getHand().addCard(c);
					currentPlayer.printHand();

					if(currentPlayer.getHand().checkHandValue() > 21) {
						System.out.println("You've busted."); // if they bust
						break;
					}

					System.out.println("Hit or stand?");
					move = scan.next();
				}
			}
		}
	}

	/**
	 * This method determines if the player has won against the dealer. If 
	 * so, their bet and potential extra winnings is added to their total 
	 * money. 
	 */
	public void distributeMoney(){

		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()){
			Player currentPlayer = (Player) playerIterator.next();
			int playerValue = currentPlayer.getHand().checkHandValue();
			int dealerValue = dealer.getHand().checkHandValue();
			if(playerValue<= 21){
				if(playerValue>dealerValue || dealerValue>21){
					System.out.println("Congratulations " + currentPlayer.getName()
					+ "! You have won againts the dealer!");
					currentPlayer.collectWinnings();
				}else if(currentPlayer.getHand().checkBlackjack()){
					System.out.println("Congratulations " + currentPlayer.getName()
					+ "! You have won againts the dealer!");
					currentPlayer.collectWinnings();
				}
			}
		}
	}


	/**
	 * This method starts a new game of Blackjack and uses our player iterator interface.
	 * A player is able to set a bet but can not set a bet higher than the amount of money they hold.
	 */
	public void playGame() {
		playerIterator = new PlayerIterator(players);
		do {
			// Each player sets their bet
			while(playerIterator.hasNext()) {
				Player currentPlayer = (Player) playerIterator.next();
				System.out.println(currentPlayer.getName() + ", place your starting bet.");
				double bet = scan.nextDouble();
				while(bet > currentPlayer.getMoney()) {
					System.out.println("You can't bet more money than you have!");
					System.out.println("You have: " + money.format(currentPlayer.getMoney()) + "\nSet your bet.");
					bet = scan.nextDouble();
				}
				currentPlayer.setBet(bet);
			}

			// Starting hands are dealt
			firstDeal();
			startRound();
			//dealer plays after the iterator has gone through all the players

			while(!dealer.checkSoftSeventeen() && dealer.getHand().checkHandValue()<17){
				Card c = cardDeck.dealCard();
				dealer.getHand().addCard(c);
			}

			dealer.printHand();
			distributeMoney();
			printMoneyLeft();
			restartGame();
			startGame();


		} while (!players.isEmpty());
		// Starting hands are dealt
		firstDeal();
		startRound();
		//dealer plays after the iterator has gone through all the players
		while(dealer.checkSoftSeventeen() || dealer.getHand().checkHandValue()<17){
			Card c = cardDeck.dealCard();
			if(c.getCardName().equals("A")){
				c.setCardValue(1);
			}
			dealer.getHand().addCard(c);
		}
		dealer.printHand();
		distributeMoney();
		printMoneyLeft();
	}
	/**
	 * Main method, where we are able to run our program.
	 * @param args
	 */
	public static void main(String[] args){
		Table table = new Table();
		table.startGame();
		table.playGame();
	}

}