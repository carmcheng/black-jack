import java.text.DecimalFormat;

/**
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version 
 * This class provides a money pot for Blackjack
 * that contains a player's bet and winnings from the game.
 * It includes methods to check, add and subtract money from the pot.
 * 
 */
public class Pot {
	
	private double totalPot;
	private DecimalFormat money = new DecimalFormat("$0.00");
	
	/**
	 * Constructor that creates an initial money pot of 0.00 dollars.
	 */
	public Pot(){
		totalPot = 0.0;
	}
	/**
	 * This method checks the current total money in the pot.
	 * @return - a double representation of the total money in the pot.
	 */
	public double checkPot(){
		return totalPot;
	}
	
	/**
	 * This method adds the given bet to the money pot.
	 * @param bet - The given bet from the player. 
	 */
	public void addPot(double bet){
		totalPot += bet;
		System.out.println("Total money in pot: " + money.format(checkPot()));
	}
	
	public void subPot(double bet) {
		totalPot -= bet;
	}
	/**
	 * This method subtracts the given amount of money from the pot.
	 * @param amount - The amount of money to subtract from the pot. 
	 */
	public void emptyPot() {
		totalPot = 0;
	}
}
