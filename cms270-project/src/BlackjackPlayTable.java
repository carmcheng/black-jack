import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackPlayTable {

	private Dealer dealer;
	private static int numPlayers;
	private ArrayList<Player> players;
	private Deck cardDeck;
	private Player player;
	private Pot pot;
	private final int MAX_PLAYERS = 6;
	private static int roundCount;
	private DecimalFormat money = new DecimalFormat("$0.00");
	private Scanner scan = new Scanner(System.in);
	private Iterator playerIterator;
	
	public void checkForAceValue(Card c) {
		if (c.getCardName().equals("A")) {
			System.out.println("You received an Ace card. Value = 1 or 11?");
			int ans = scan.nextInt();
			while (ans != 1 && ans != 11) {
				System.out.println("Invalid input. Try again.");
				ans = scan.nextInt();
			}
			if (ans == 1)
				c.setCardValue(1);
		}
	}
	public void hit() {
	currentPlayer.getHand().addCard(cardDeck.dealCard());
	currentPlayer.aceChanger();
	currentPlayer.printHand();
	if(currentPlayer.isBusted()) {
		System.out.println("You've busted."); // if they bust
		break;
	}
	}
//	public  void startRound() {
//		playerIterator = new PlayerIterator(players);
//		while(playerIterator.hasNext()) {
//			Player currentPlayer = (Player) playerIterator.next();
//			if (currentPlayer.getHand().checkBlackjack() && playerIterator.hasNext()) {
//				currentPlayer = (Player) playerIterator.next();
//			} else if (currentPlayer.getHand().checkBlackjack() && players.size() == 1) {
//				break;
//			}
//			System.out.println("\nIt's your turn, " + currentPlayer.getName() + ".");
//			//Asks player if s/he wants to change ace card value in first hand
//			//Automatically changes ace to 1 if player holds two aces
//			currentPlayer.aceChanger();
//			currentPlayer.printHand();
//			// Double down option
//			System.out.println("Would you like to double down? Yes/No?");
//			String answer = scan.next();
//
//			while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
//				System.out.println("Enter a valid response.");
//				answer = scan.next();
//			}
//
//			// Did player double down
//			if (answer.equalsIgnoreCase("No") ||
//					currentPlayer.getMoney() < currentPlayer.getSetBet()) {
//				String s = answer.equalsIgnoreCase("Yes") ? 
//						"Not enough money to double down." : "You have chosen NOT to double down.";
//				System.out.println(s);
//				System.out.println("\nChoose to hit or stand.");
//				String move = scan.next();
//				while (move.equalsIgnoreCase("hit")) {
//					currentPlayer.getHand().addCard(cardDeck.dealCard());
//					currentPlayer.aceChanger();
//					currentPlayer.printHand();
//					if(currentPlayer.isBusted()) {
//						System.out.println("You've busted."); // if they bust
//						break;
//					}
//					System.out.println("Hit or stand?");
//					move = scan.next();
//				}				
//			} else if (answer.equalsIgnoreCase("Yes")) {
//				currentPlayer.doubleDown();
//				//Asks player for value of ace card, if ace is dealt
//				Card c = cardDeck.dealCard();
//				checkForAceValue(c);
//				currentPlayer.getHand().addCard(c);
//				currentPlayer.printHand();
//				if(currentPlayer.isBusted()) {
//					System.out.println("You've busted."); // if they bust
//				}
//			}
//		}
//		roundCount++;
//	}
}
