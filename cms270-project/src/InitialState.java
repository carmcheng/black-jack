
public class InitialState implements PlayerState {

	//When the player has been dealt a hand but has not done anything yet; 
	//should be able to setBet. After the player sets the bet, player moves
	//into the playstate, where the hand is first checked 
	
	public InitialState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setBet(Double bet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doubleDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public Card dealCard() {
		// TODO Auto-generated method stub
		return null;
	}

}
