package model;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class StrategyGameModel extends Observable {
	public enum Team {
		HUMAN, COMPUTER
	}
	Tile[][] board;
	Team team;
	String backgroundImageFileName;
	
	public StrategyGameModel(String levelFileName) {
	}
	
	public Tile getTile(int row, int col) {
		return board[row][col];
	}
	
	public Team getTurn() {
		return team;
	}
	
	public void nextTurn() {
		
	}
	
	public int getBoardWidth() {
		return 0;
	}
	
	public int getBoardLength() {
		return 0;
	}
	
	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setUpNotifyObservers() {
		setChanged();
		notifyObservers();
	}
	
}