
public class Player {
	private String name = "";
	private double bet;
	private double totalMoney;
	private Hand hand;
	
	public Player(String name, Hand hand, double totalmoney){
	//Player will have a unique name, a unique hand and an amount of money to play Blackjack.
		this.name = name;
		this.hand = hand;
		this.totalmoney = totalmoney;
	}
	public void startGame(Table table){
	//Start a game of blackjack with a table.	
	}
	public void setBet(double bet){
		this.bet = bet;
	}
	public void push(){
	//When player and dealer have the score in hands.	
	}
	public void hit(){
	//When player wants another card from the deck.
	}
	public void stand(){
	//When player no longer wants another card from the deck.
		
	}
	public void doubleDown(double bet){
	//When player wants to double their bet, a
	//and can no longer hit in current round of the game.
	
		bet = bet * 2;
	}
	public void quit(){
	//If player desires the quit the game.
	}
	
}