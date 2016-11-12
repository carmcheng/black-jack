
public class Dealer {
	
	private static Dealer dealer;
	private Hand hand;
	
	/**
	 * Private constructor to insure that only one Dealer object is made
	 */
	private Dealer() {
		this.hand = new Hand(); 
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
	
	public void addCard(Card c) {
		
	}
	
	public void printHand() {
		
	}
	

}
