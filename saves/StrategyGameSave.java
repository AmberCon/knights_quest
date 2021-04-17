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
	
	/**
	 * Loads a saved StrategyGameSave
	 * @param filepath the filepath of the saved game as a String
	 * @return a StrategyGameSave loaded from the save
	 * @throws BadSaveException if the save couldn't be loaded
	 */
	public static StrategyGameSave load(String filepath) throws BadSaveException{
		try (ObjectInputStream objIn = 
				new ObjectInputStream(new FileInputStream(filepath));){
			StrategyGameSave game = (StrategyGameSave) objIn.readObject();
			return game;
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			throw new BadSaveException();
		} 
	}
	
	public StrategyGameSave(StrategyGameModel model) {
		
	}
	
	public void save(String filepath) {
		
	}
	
	
	
	
}
