
public class Player {
	private String name ="";
	private double totalMoney;
	
	public Player(String name, double bet, Hand hand) {
		this.name = name;
		this.bet = bet;
		this.hand = hand;
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
