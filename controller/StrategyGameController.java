package controller;

import model.StrategyGameModel;

public class StrategyGameController{
	StrategyGameModel model;
	
	public StrategyGameController(StrategyGameModel model) {
		
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
	
	//public List<int[]> getValidMoves(int row, int col, Team team){
	
	//}
	
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