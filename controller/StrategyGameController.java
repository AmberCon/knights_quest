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
		return model.getTile(row, col).hasPlayer();
	}
	
	public String getPieceSprite(int row, int col) {
		return model.getTile(row, col).getPiece().getSpriteFileName();
	}
	
	public int getPieceHealth(int row, int col) {
		return model.getTile(row, col).getPiece().getHealth();
	}
	
	public Team getPieceTeam(int row, int col) {
		return model.getTile(row, col).getPiece().getTeam();
	}
	
	public int getMoveDistanceRemaining(int row, int col) {
		return model.getTile(row, col).getPiece().getMoveDistanceRemaining();
	}
	
	public boolean hasAttackedOrDefended(int row, int col) {
		return model.getTile(row, col).getPiece().hasAttackedOrDefended();
	}

	public boolean isDefended(int row, int col) {
		return model.getTile(row, col).getPiece().isDefended();
	}
	
	public Team getTurn() {
		return model.getTurn();
	}
	
	public void attack(int byRow, int byCol, int againstRow, int againstCol) {
		model.getTile(byRow, byCol).getPiece().attack(model, againstRow, againstCol);
		model.setUpNotifyObservers();
	}
	
	public void move(int fromRow, int fromCol, int toRow, int toCol) {
		model.getTile(toRow, toCol).setPiece(model.getTile(fromRow, fromCol).getPiece());
		model.getTile(fromRow, fromCol).setPiece(null);
		model.getTile(toRow, toCol).getPiece().move(1);
		model.setUpNotifyObservers();
	}
	
	public void defend(int row, int col) {
		model.getTile(row, col).getPiece().defend();
		model.setUpNotifyObservers();
	}
	
	public List<int[]> getValidAttacks(int row, int col) {
		List<int[]> validMoves = new ArrayList<int[]>();
		try {
			int[] coords1 = {row + 1, col};
			if (model.getTile(row + 1, col).hasPlayer()) {
				validMoves.add(coords1);
			}
		} catch (Exception e) {}
		try {
			int[] coords2 = {row - 1, col};
			if (model.getTile(row - 1, col).hasPlayer()) {
				validMoves.add(coords2);
			}
		} catch (Exception e) {}
		try {
			int[] coords3 = {row, col + 1};
			if (model.getTile(row, col + 1).hasPlayer()) {
				validMoves.add(coords3);
			}
		} catch (Exception e) {}
		try {
			int[] coords4 = {row, col - 1};
			if (model.getTile(row, col - 1).hasPlayer()) {
				validMoves.add(coords4);
		}
		} catch (Exception e) {}
		return validMoves;
	}

	public List<int[]> getValidMoves(int row, int col) {
		List<int[]> validAttacks = new ArrayList<int[]>();
		int[] coords1 = {row + 1, col};
		validAttacks.add(coords1);
		int[] coords2 = {row - 1, col};
		validAttacks.add(coords2);
		int[] coords3 = {row, col + 1};
		validAttacks.add(coords3);
		int[] coords4 = {row, col - 1};
		validAttacks.add(coords4);
		return validAttacks;
	}
	
	public boolean isOver() {
		return true;
	}
	
	public Team getWinner() {
		return Team.HUMAN;
	}
	
	public int getBoardWidth() {
		return model.getBoardWidth();
	}
	
	public int getBoardLength() {
		return model.getBoardLength();
	}
	
	public String getBackgroundImageFileName() {
		return model.getBackgroundImageFileName();
	}
	
	public void nextTurn() {
		model.nextTurn();
		for (int row = 0; row < model.getBoardLength(); row++) {
			for (int col = 0; col < model.getBoardWidth(); col++) {
				if (model.getTile(row, col).hasPlayer()) {
					model.getTile(row, col).getPiece().resetTurn();
				}
			}
		}
		model.setUpNotifyObservers();
	}
	
	public void saveGame(String fileName) {
		// nothing
	}

}