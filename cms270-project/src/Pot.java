
public class Pot {
	
	private double totalPot;
	
	public Pot(){
		totalPot = 0.0;
	}
	
	public double checkPot(){
		return totalPot;
	}
	
	public void addPot(double bet){
		totalPot+=bet;
	}
	
	public void subPot(double amount){
		totalPot-=amount;
	}
}
