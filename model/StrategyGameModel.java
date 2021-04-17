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
}