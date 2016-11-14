import java.util.*;
import java.text.*;
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

	public void firstDeal() {
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			currentPlayer.getHand().addCard(cardDeck.dealCard());
			currentPlayer.getHand().addCard(cardDeck.dealCard()); //adds two cards to player's hand on first deal
			System.out.println(currentPlayer.getName() + "'s hand: ");
			currentPlayer.printHand();
			if (currentPlayer.getHand().checkBlackjack()) {
				System.out.println("You got blackjack!");
			}
		}
		dealer.getHand().addCard(cardDeck.dealCard());
		dealer.getHand().addCard(cardDeck.dealCard());
		if(dealer.getHand().checkBlackjack()) {
			System.out.println("Dealer got blackjack.");
			return;
		}
		System.out.println("Dealer's hand: ");
		System.out.println("\t" + dealer.getHand().getCards().get(0) + "\n\tHidden Card");
	}
	
	public void dealerTurn() {
		// Dealer conditions go here, soft17 and hard17 mechanisms
	}

	public void printMoneyLeft() {
		DecimalFormat money = new DecimalFormat("$0.00");
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			System.out.println(currentPlayer.getName() + ", you have " +
					money.format(currentPlayer.getMoney()) + " left." );
		}
	}
	
	public void startRound() {
		playerIterator = new PlayerIterator(players);
		while(playerIterator.hasNext()) {
			Player currentPlayer = (Player) playerIterator.next();
			if (currentPlayer.getHand().checkBlackjack() && playerIterator.hasNext()) {
				currentPlayer = (Player) playerIterator.next();
			}
			System.out.println("\nIt's your turn, " + currentPlayer.getName() + ".");

			// Double down option
			System.out.println("Would you like to double down? Yes/No?");
			String answer = scan.next();

			while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
				System.out.println("Enter a valid response.");
				answer = scan.next();
			}

			boolean flag = false;
			// Did player double down
			if(answer.equalsIgnoreCase("Yes")) {		// make sure player has enough money to double down
				currentPlayer.doubleDown();
				currentPlayer.getHand().addCard(cardDeck.dealCard());
				System.out.println("Your hand: ");
				currentPlayer.printHand();
				flag = true;
				// Duplicate code; any way of encapsulating this in a method?
				if(currentPlayer.getHand().checkHandValue() == 21) {
					System.out.println("You have 21!"); // if they have 21
					break;
				} else if(currentPlayer.getHand().checkHandValue() > 21) {
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
					// Trying to test Ace value.... setter method won't work?
//					Card c = new Card(11, "A", "Test");
//					currentPlayer.getHand().addCard(c);
//					if (c.getCardName().equals("A")) {
//						System.out.println("You got an Ace card. Value = 1 or 11?");
//						int ans = scan.nextInt();
//						if (ans == 1) {
//							c.setCardValue(1);
//						} else {
//							break;
//						}
//					}
					currentPlayer.getHand().addCard(cardDeck.dealCard());
					System.out.println("Your hand:");
					currentPlayer.printHand();
					if(currentPlayer.getHand().checkHandValue() == 21) {
						System.out.println("You have 21!"); // if they have 21
						break;
					} else if(currentPlayer.getHand().checkHandValue() > 21) {
						System.out.println("You've busted."); // if they bust
						break;
					}
					System.out.println("Hit or stand?");
					move = scan.next();
				}
			}
		}
		dealerTurn();
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
		startRound();
		printMoneyLeft();
	}

	public static void main(String[] args){
		Table table = new Table();
		table.startGame();
		table.playGame();
	}

}
