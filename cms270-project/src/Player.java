/**
 * @author Zoe, Ebba, Carmen, Aruna
 * 
 * @version 
 * This class creates a player to play a game of Blackjack
 * and provides methods appropriate for a player of this game,
 * such as accessing name, hand, bet, the choice of double down
 * and printing the hand of the player. 
 */

import java.util.*;

public class Player {
	
	private String name = "";
	private double bet;
	private double totalMoney;
	private Hand hand;
	private Pot pot = new Pot();
	
	/**
	 * Constructor that creates a player with a unique name, a unique hand 
	 * and an amount of money they have to play Blackjack. 
	 * @param name - Name of the player
	 * @param totalmoney - Total amount of money for the player
	 */
	public Player(String name, double totalmoney){
		this.name = name;
		hand = new Hand();
		this.totalMoney = totalmoney;
	}
	/**
	 * This method accesses the name of a player.
	 * @return The name to be added to the list of players at the table.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method accesses the player's hand.
	 * @return a Hand for the player.
	 */
	public Hand getHand() {
		return hand;
	}
	
	/**
	 * This method accesses the player's total money.
	 * @return an integer value that represents player's total money.
	 */
	public double getMoney() {
		return totalMoney;
	}
	
	/**
	 * This method sets the player's bet to their input.
	 * @param bet - the amount of money the player wants to bet for the game.
	 */
	public void setBet(double bet){
		this.bet = bet;
		totalMoney -= bet;
		pot.addPot(bet);
	}
	/**
	 * This method is used when a player wants to double their bet, 
	 * and can no longer hit in the current round of the game.
	 */
	public void doubleDown(){
		totalMoney -= bet;
		pot.addPot(bet);
	}
	
	/**
	 * This method prints the player's hand using an iterator pattern.
	 */
	public void printHand() {
		Iterator handIterator = new HandIterator(hand.getCards());
		while(handIterator.hasNext()) {
			Card card = (Card) handIterator.next();
			System.out.println("\t" + card);
		}
		System.out.println("\tHand value: " + hand.checkHandValue());
	}
	
}