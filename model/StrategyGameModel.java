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
	
	private Tile[][] board;
	private Team currentTurn;
	private String backgroundImageFileName;
	
	/**
	 * Returns the tile at the given row and column
	 * @param row the row of the tile to get, as an int
	 * @param col the column of the tile to get, as an int
	 * @return the Tile at the given row and column
	 */
	Tile getTile(int row, int col){
		return board[row][col];
	}
	
	/**
	 * Returns the Team whoose turn it currently is
	 * @return the team whoose turn it currently is, as a Team
	 */
	public Team getTurn() {
		return currentTurn;
	}
	
	/**
	 * Gives control of the current turn over to the next
	 * player.
	 */
	public void nextTurn() {
		if(currentTurn.equals(Team.HUMAN)) {
			currentTurn = Team.COMPUTER;
		} else {
			currentTurn = Team.HUMAN;
		}
	}
	
	//TODO setPiece() if not moved to Tile
}