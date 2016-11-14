
public class PlayState implements PlayerState {
	
	//the player is in the process of playing; cannot place a bet anymore.
	//if th eplayer has blackjack, player automatically moves into finishedState
	//if the player stands or busts, the player moves into the finishedState

	public PlayState() {
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
