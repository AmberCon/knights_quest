package saves;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import model.StrategyGameModel;

/**
 * A 
 * @author Ember Chan
 *
 */
public class StrategyGameSave implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Team currentTurn;
	private Scenario scenario;
	private Tile[][] board;
	
	public StrategyGameSave(StrategyGameModel model) {
		
	}
	
	
	
	
	
}
