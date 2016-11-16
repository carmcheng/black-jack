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
	private Hand hand;
	private Table table;
	private Iterator handIterator;
	
	/**
	 * Private constructor to insure that only one Dealer object is made
	 */
	private Dealer() {
		hand = new Hand(); 
	}


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
	 * This method looks for an Ace in their hand, if present, and
	 * changes its value to 1 if the dealer busts
	 */
	public void aceValueChecker() {
		if (hand.checkHandValue() > 21) {
			handIterator = new HandIterator(hand.getCards());
			while (handIterator.hasNext()) {
				Card card = (Card) handIterator.next();
				if (card.getCardName().equals("A"))
					card.setCardValue(1);
			}
		}
	}

	/**
	 * This method determines if the dealer holds an Ace in their hand
	 * when the hand value is equal to 17.  
	 * @return a boolean representation 
	 */
	public boolean checkSoftSeventeen() {
		if(hand.numOfCards() == 2 && hand.checkHandValue() == 17 &&
				(hand.getCards().get(0).getCardName().equals("A") ||
						hand.getCards().get(1).getCardName().equals("A"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method accesses to dealer's hand.
	 * @return a Hand representation for the dealer
	 */
	public Hand getHand() {
		return hand;
	}

	public void printHiddenHand() {
		System.out.println("\nDealer's hand: ");
		System.out.println("\t" + dealer.getHand().getCards().get(0) + "\n\tHidden Card");
	}

	/**
	 * This method prints the dealer's hand using an iterator pattern.
	 */
	public void printHand() {
		handIterator = new HandIterator(hand.getCards());
		System.out.println("\nDealer's hand:");
		while(handIterator.hasNext()) {
			Card card = (Card) handIterator.next();
			System.out.println("\t" + card);
		}
		System.out.println("\tHand value: " + hand.checkHandValue());

	}
}
