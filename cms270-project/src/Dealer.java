/**
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version 
 * This class provides a dealer for a game of Blackjack
 * and includes methods appropriate for a dealer to have 
 * such as checking for a soft seventeen hand, printing dealer's hand and
 * making a move on their turn. 
 */
public class Dealer {
	private static Dealer dealer;
	private Hand hand = new Hand();
	private Card ace;

	/**
	 * Private constructor to insure that only one Dealer object is made
	 */
	private Dealer() {}

	/**
	 * Method creates new Dealer object if not already initialized
	 * If Dealer object does already exist, it returns the already initialized Dealer object
	 * 
	 * @return the one instance of the Dealer object
	 */
	public static Dealer getInstance() {
		if(dealer == null) {
			dealer = new Dealer(); 
		} 
		return dealer;

	}
	/**
	 * This method determines if the dealer holds an Ace in their hand
	 * when the hand value is equal to 17.  
	 * 
	 * @return a boolean representation 
	 */
	public boolean checkSoftSeventeen() {
		if(hand.numOfCards() == 2 && hand.checkHandValue() == 17 && findAce()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method iterates through the dealer's hand and looks for an
	 * Ace card.
	 * @return a boolean representation
	 */
	public boolean findAce() {
		Iterator handIterator = new HandIterator(hand.getCards());
		while (handIterator.hasNext()) {
			Card c = (Card) handIterator.next();
			if (c.getCardName().equals("A"))
				return true;
		}
		return false;
	}

	/**
	 * This method checks the dealer's hand to see if it holds an Ace
	 * card and total hand value has busted. It then changes the value
	 * of an Ace card from 11 to 1.
	 */
	public void aceChecker() {
		if (findAce() && hand.checkHandValue() > 21) {
			Iterator handIterator = new HandIterator(hand.getCards());
			Card temp = null;
			while (handIterator.hasNext()) {
				Card c = (Card) handIterator.next();
				if (c.getCardName().equals("A")) {
					temp = c;
					hand.remove(c);
				}
			}
			hand.addCard(new Card(1, "A", temp.getCardSuit()));
		}
	}

	/**
	 * This method accesses to dealer's hand.
	 * 
	 * @return a Hand representation for the dealer
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * This method prints the dealer's hand, but hides the second card
	 * from the players.
	 */
	public void printHiddenHand() {
		System.out.println("Dealer's hand: ");
		System.out.println("\t" + dealer.getHand().getCards().get(0) + "\n\tHidden Card");
	}

	/**
	 * This method prints the dealer's hand using an iterator pattern.
	 */
	public void printHand() {
		Iterator handIterator = new HandIterator(hand.getCards());
		System.out.println("\nDealer's hand:");
		while(handIterator.hasNext()) {
			Card card = (Card) handIterator.next();
			System.out.println("\t" + card);
		}
		System.out.println("\tHand value: " + hand.checkHandValue());
	}
}
