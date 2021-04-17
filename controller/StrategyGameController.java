package controller;

import java.util.ArrayList;
import java.util.List;

import model.StrategyGameModel;

public class StrategyGameController{
	StrategyGameModel model;
	
	public StrategyGameController(StrategyGameModel model) {
		this.model = model;
	}
	
	public boolean hasPlayer(int row, int col) {
		
		return false;
	}
	
	public String getPieceSprite(int row, int col) {
		
		return "";
	}
	
	public int getPieceHealth(int row, int col) {
		
		return 0;
	}
	
	//public enum getPieceTeam(int row, int col) {
		
	//}
	
	public int getMoveDistanceRemaining(int row, int col) {
		
		return 0;
	}
	
	public boolean hasAttackedOrDefended(int row, int col) {
		
		return false;
	}
	
	public boolean isDefended(int row, int col) {
		
		return false;
	}
	
	public String getTurn() {
		
		return "";
	}
	
	public void attack(int byRow, int byCol, int againstRow, int againstCol) {
		
	}
	
	public void move(int fromRow, int fromCol, int toRow, int toCol) {
		
	}
	
	public void defend(int row, int col) {
		
	}
	
	public void computerTurn() {
		
	}
	
	//public List<int[]> getValidAttacks(int row, int col, Team team){
		
	//}
	
	public List<int[]> getValidMoves(int row, int col/*, Team team*/){
		int speed = getMoveDistanceRemaining(row, col);
		
		List<int[]> possible = new ArrayList<int[]>();
		
		getValidMovesHelper(row, col, speed, possible);
		
		return possible;
	}
	
	private void getValidMovesHelper(int row, int col, int remaining, List<int[]> possible) {
		if(remaining > 0) {
		/* move up
		 * if(can move up):
		 * possible.add([row - 1, col])
		 * getValidMovesHelper(row - 1, col, remaining - 1, possible)
		*/
		
		/* move up right
		 * if(can move up right):
		 * possible.add([row - 1, col + 1])
		 * getValidMovesHelper(row - 1, col + 1, remaining - 1, possible)
		*/
		
		/* move right
		 * if(can move right):
		 * possible.add([row, col + 1])
		 * getValidMovesHelper(row, col + 1, remaining - 1, possible)
		*/
		
		/* move down right
		 * if(can move down right):
		 * possible.add([row + 1, col + 1])
		 * getValidMovesHelper(row + 1, col + 1, remaining - 1, possible)
		*/
		
		/* move down
		 * if(can move down):
		 * possible.add([row + 1, col])
		 * getValidMovesHelper(row + 1, col, remaining - 1, possible) 
		*/
		
		/* move down left
		 * if(can move down left):
		 * possible.add([row + 1, col - 1])
		 * getValidMovesHelper(row + 1, col - 1, remaining - 1, possible) 
		*/
		
		/* move left
		 * if(can move left):
		 * possible.add([row, col - 1])
		 * getValidMovesHelper(row, col - 1, remaining - 1, possible)
		 */
		
		/* move up left
		 * if(can move up left):
		 * possible.add([row - 1, col - 1])
		 * getValidMovesHelper(row - 1, col - 1, remaining - 1, possible)
		 */
		}
	}
	
	public boolean isOver() {
		
		return false;
	}
	
	//public enum getWinner() {
		
	//}
	
	public int getBoardWidth() {
		
		return 0;
	}
	
	public int getBoardLength() {
		
		return 0;
	}
	
	public String getBackgroundImageFileName() {
		
		return "";
	}
	
	public void nextTurn() {
		
	}
	
	public void saveGame() {
		
	}
}