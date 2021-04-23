package computer_player;

import controller.StrategyGameController;
import model.BadSaveException;
import model.StrategyGameModel;

public class ComputerPlayerTest{
	
	public static void main(String[] args) {
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("../levels/amber.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
	}
}