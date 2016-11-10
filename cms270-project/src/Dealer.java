
public class Dealer {
	
	private static Dealer dealer;
	
	DealerState hardSeventeenState;
	DealerState softAndBelowState; 
	DealerState dealingState; 
	
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
		} else {
			return dealer;
		}
	}
	
	public void addCard() {
	
}
