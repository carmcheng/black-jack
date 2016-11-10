
public class Player {
	private String name = "";
	private double bet;
	private double totalMoney;
	private Hand hand;
	
	public Player(String name, double totalmoney){
	//Player will have a unique name, a unique hand and an amount of money to play Blackjack.
		this.name = name;
		hand = new Hand();
		this.totalMoney = totalmoney;
	}
	
	public String getName(){
	//Returns name to be added to the list of players at the table
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public double getMoney() {
		return totalMoney;
	}
	
	public void startGame(Table table){
	//Start a game of blackjack with a table.
	}
	
	public void setBet(double bet){
		this.bet = bet;
	}
	
	public void doubleDown(){
	//When player wants to double their bet, a
	//and can no longer hit in current round of the game.
		setBet(bet * 2);
	}
	
	public void quit(){
	//If player desires the quit the game.
	}
	
}