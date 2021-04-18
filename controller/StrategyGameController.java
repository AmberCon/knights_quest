package controller;

import java.util.ArrayList;
import java.util.List;

import model.StrategyGameModel;
import model.StrategyGameModel.Team;

public class StrategyGameController{
	
	StrategyGameModel model;
	
	public StrategyGameController(StrategyGameModel model) {
		this.model = model;
	}
	
	public boolean hasPlayer(int row, int col) {
		return false;
	}
	
	public String getPieceSprite(int row, int col) {
		return null;
	}
	
	public int getPieceHealth(int row, int col) {
		return 50;
	}
	
	public Team getPieceTeam(int row, int col) {
		return null;
	}
	
	public int getMoveDistanceRemaining(int row, int col) {
		return 1;
	}
	
	public boolean hasAttackedOrDefended(int row, int col) {
		return false;
	}

	public boolean isDefended(int row, int col) {
		return false;
	}
	
	public Team getTurn() {
		return null;
	}
	
	public void attack(int byRow, int byCol, int againstRow, int againstCol) {
		model.setUpNotifyObservers();
	}
	
	public void move(int fromRow, int fromCol, int toRow, int toCol) {
		model.setUpNotifyObservers();
	}
	
	public void defend(int row, int col) {
		model.setUpNotifyObservers();
	}
	
	public List<int[]> getValidAttacks(int row, int col) {
		List<int[]> validMoves = new ArrayList<int[]>();
		return validMoves;
	}

	public List<int[]> getValidMoves(int row, int col) {
		List<int[]> validAttacks = new ArrayList<int[]>();
		return validAttacks;
	}
	
	public boolean isOver() {
		return false;
	}
	
	public Team getWinner() {
		return null;
	}
	
	public int getBoardWidth() {
		return 5;
	}
	
	public int getBoardLength() {
		return 5;
	}
	
	public String getBackgroundImageFileName() {
		return model.getBackgroundImageFileName();
	}
	
	public void nextTurn() {
		model.setUpNotifyObservers();
	}
	
	public void saveGame(String fileName) {
	}

}