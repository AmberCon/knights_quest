package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * A 
 * @author Ember Chan
 *
 */
public class StrategyGameState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Team currentTurn;
	protected Scenario scenario;
	protected Tile[][] board;
	
	public StrategyGameState(Scenario scenario) {
		
	}
	
	
	
	
	
}
