
public class Dealer {
	
	private static Dealer dealer;
	private Hand hand;
	
	/**
	 * Private constructor to insure that only one Dealer object is made
	 */
	private Dealer() {
		hand = new Hand(); 
	}
	
	/**
	 * Method creates new Dealer object if not already initialized
	 * If Dealer object does already exist, it returns the already initialized Dealer object
	 * @return 
	 */
	public static Dealer getInstance() {
		if(dealer == null) {
			dealer = new Dealer(); 
		} 
		return dealer;
	
	}
	
	public boolean checkSoftSeventeen() {
		if(hand.numOfCards() == 2 && hand.checkHandValue() == 17 &&
				(hand.getCards().get(0).getCardName().equals("A") ||
						hand.getCards().get(1).getCardName().equals("A"))) {
			return true;
		} else {
			return false;
		}
	}
	

	public void addCard() {
		
	}
	public void makeMove() {
		
	}
	public Hand getHand() {
		return hand;
	}
	
	public void printHand() {
		Iterator handIterator = new HandIterator(hand.getCards());
		while(handIterator.hasNext()) {
			Card card = (Card) handIterator.next();
			System.out.println("\t" + card);
		}
		System.out.println("\tHand value: " + hand.checkHandValue());
	}
	
}
