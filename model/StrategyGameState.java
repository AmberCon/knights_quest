package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * A representation of the a state for a StrategyGame.
 * 
 * Serializable in order to create game saves.
 * @author Ember Chan
 *
 */
public class StrategyGameState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Team currentTurn;
	protected String backgroundImageFileName;
	protected Tile[][] board;
	
}
