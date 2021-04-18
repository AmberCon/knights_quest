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
		Tile[][] newBoard = {{new OpenTile(), new OpenTile(new Archer(Team.COMPUTER)), new OpenTile(), new OpenTile(), new OpenTile()},
				{new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile()},
				{new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile()},
				{new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile()},
				{new OpenTile(), new OpenTile(), new OpenTile(), new OpenTile(new Archer(Team.HUMAN)), new OpenTile()}};
		board = newBoard;
		team = Team.COMPUTER;
		backgroundImageFileName = "assets/level_background.jpg";
	}
	
	public Tile getTile(int row, int col) {
		return board[row][col];
	}
	
	public Team getTurn() {
		return team;
	}
	
	public void nextTurn() {
		if (team == Team.HUMAN) {
			team = Team.COMPUTER;
		} else {
			team = Team.HUMAN;
		}
	}
	
	public int getBoardWidth() {
		return board[0].length;
	}
	
	public int getBoardLength() {
		return board.length;
	}
	
	public String getBackgroundImageFileName() {
		return backgroundImageFileName;
	}

	public void setUpNotifyObservers() {
		setChanged();
		notifyObservers();
	}
	
}