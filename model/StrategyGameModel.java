package model;

/**
 * This class represents the model in the MCV architecture for
 * this strategy game.
 * 
 * The coordinates of the board for the game are as such:
 * 
 * 0 1 2 3 4 5 6 . . . col
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * .
 * .
 * .
 * row
 * 
 * 
 * @author Ember Chan
 *
 */
public class StrategyGameModel{
	
	private StrategyGameState state;
	
	/**
	 * Constructor for the model
	 * Creates a new model from a StrategyGameState
	 * 
	 * Throws an exception if a null StrategyGameState is passed, or it's board has a 
	 * 0 width/height
	 * @param state the StrategyGameState to load the model from
	 *
	 */
	public StrategyGameModel(StrategyGameState state) {
		this.state = state;
		if(this.state == null || this.getBoardHeight() == 0 || this.getBoardWidth() == 0) {
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Returns the tile at the given row and column
	 * @param row the row of the tile to get, as an int
	 * @param col the column of the tile to get, as an int
	 * @return the Tile at the given row and column
	 */
	Tile getTile(int row, int col){
		return state.board[row][col];
	}
	
	/**
	 * Returns the Team whoose turn it currently is
	 * @return the team whoose turn it currently is, as a Team
	 */
	public Team getTurn() {
		return state.currentTurn;
	}
	
	/**
	 * Gives control of the current turn over to the next
	 * player.
	 */
	public void nextTurn() {
		if(state.currentTurn.equals(Team.HUMAN)) {
			state.currentTurn = Team.COMPUTER;
		} else {
			state.currentTurn = Team.HUMAN;
		}
	}
	
	/**
	 * Returns the width (number of columns) of the board
	 * @return the width of the board as an int
	 */
	public int getBoardWidth() {
		return state.board[0].length;
	}
	
	/**
	 * Returns the height (number of rows) of the board
	 * @return the height of the board as an int
	 */
	public int getBoardHeight() {
		return state.board.length;
	}
	
	/**
	 * Returns the filename for the background image of the current map
	 * @return the filename for the background image of the current map, as a String
	 */
	public String getBackgroundImageFileName() {
		return state.backgroundImageFileName;
	}
	
	/**
	 * Returns the state of the model, for saving purposes
	 * @return the state of the model as a StrategyGameState
	 */
	public StrategyGameState getState() {
		return state;
	}
	
	//TODO setPiece() if not moved to Tile
}