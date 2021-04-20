package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.StrategyGameModel;
/**
 * 
 * @author Saul Weintraub
 *
 */

public class StrategyGameController{
	StrategyGameModel model;
	

	public StrategyGameController(StrategyGameModel model) {
		this.model = model;
	}
	
	public boolean hasPlayer(int row, int col) {
		Tile tile = model.getTile(row, col);
		return tile.hasPlayer();
	}
	
	public String getPieceSprite(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			return piece.getSpriteFileName();
		}
		return "";
	}
	
	public int getPieceHealth(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			return piece.getHealth();
		}
		return 0;
	}
	
	public Team getPieceTeam(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			return piece.getTeam();
		}
		return null;
	}
	
	public int getMoveDistanceRemaining(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			return piece.getMoveDistanceRemaining();
		}
		return 0;
	}
	
	public boolean hasAttackedOrDefended(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			return piece.hasAttackedOrDefended();
		}
		return false;
	}
	
	public boolean isDefended(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			return piece.isDefended();
		}
		return false;
	}
	
	public Team getTurn() {
		return model.getTurn();
	}
	
	public void attack(int byRow, int byCol, int againstRow, int againstCol) {
		List<int[]> possible = getValidAttacks(byRow, byCol);
		int[] against = {againstRow, againstCol};
		if(possible.contains(against)) {
			Piece by = model.getTile(byRow, byCol).getPiece();
			by.attack(model, againstRow, againstCol);
			model.setUpNotifyObservers();
		}
	}
	
	public void move(int fromRow, int fromCol, int toRow, int toCol) {
		List<int[]> possible = getValidMoves(fromRow, fromCol);
		int[] to= {toRow, toCol};
		if(possible.contains(to)) {
			Tile toTile = model.getTile(toRow, toCol);
			Tile fromTile = model.getTile(fromRow, fromCol);
			Piece fromPiece = fromTile.getPiece();
			fromPiece.move(findShortestMove(fromRow, fromCol, toRow, toCol));
			toTile.setPiece(fromPiece);
			fromTile.removePiece();
			model.setUpNotifyObservers();
		}
	}
	
	private int findShortestMove(int fromRow, int fromCol, int toRow, int toCol) {
		Piece piece = model.getTile(fromRow, fromCol).getPiece();
		int distanceRemaining = piece.getMoveDistanceRemaining();
		List<Integer> distances = new ArrayList<Integer>();
		int[] destination = {toRow, toCol};
		
		findShortestMoveHelper(fromRow, fromCol, destination, 0, distanceRemaining, distances);
		distances.sort(null);
		
		return distances.get(0);
	}
	
	private void findShortestMoveHelper(int curRow, int curCol, int[] destination, int curDistance, int distanceRemaining, List<Integer> distances) {
		int width = getBoardWidth();
		int length = getBoardLength();
		if((curRow >= 0) && (curRow < length) && (curCol >= 0) && (curCol < width)) {
			if(distanceRemaining > curDistance) {
				int[] curCoord = {curRow, curCol};
				if(Arrays.equals(curCoord, destination)) {
					distances.add(curDistance);
				}
				findShortestMoveHelper(curRow + 1, curCol, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow + 1, curCol + 1, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow + 1, curCol - 1, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow, curCol - 1, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow, curCol + 1, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow - 1, curCol, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow - 1, curCol + 1, destination, curDistance + 1, distanceRemaining, distance);
				findShortestMoveHelper(curRow - 1, curCol - 1, destination, curDistance + 1, distanceRemaining, distance);
			}
		}
	}
	
	public void defend(int row, int col) {
		if(hasPlayer(row, col)) {
			Tile tile = model.getTile(row, col);
			Piece piece = tile.getPiece();
			piece.defend();
		}
	}
	
	//TODO
	public void computerTurn() {
		
	}
	
	
	public List<int[]> getValidAttacks(int row, int col){
		List<int[]> possible = new ArrayList<int[]>();
		Piece piece = model.getTile(row, col).getPiece();
		int range = piece.getAttackDistance();
		Team team = piece.getTeam();
		
		getValidAttacksHelper(row, col, range, team, possible);
		
		return possible;
	}
	
	// Currently range attacks dont have to follow a straight line, will change later
	// Ranged attackers can currently shoot through anything, will change later
	private void getValidAttacksHelper(int row, int col, int range, Team team, List<int[]> possible) {
		int width = getBoardWidth();
		int length = getBoardLength();
		if((row >= 0) && (row < length) && (col >= 0) && (col < width)) {
			if(model.getTile(row, col).hasPlayer()) {
				if(!model.getTile(row, col).getPiece().getTeam.equals(team)) {
					int[] coord = {row, col};
					if(!possible.contains(coord)) {
						possible.add(coord);
					}
				}
			}
			if(range > 0) {
				getValidAttacksHelper(row + 1, col, range - 1, team, possible);
				getValidAttacksHelper(row + 1, col, range - 1, team, possible);
				getValidAttacksHelper(row + 1, col - 1, range - 1, team, possible);
				getValidAttacksHelper(row - 1, col, range - 1, team, possible);
				getValidAttacksHelper(row - 1, col + 1, range - 1, team, possible);
				getValidAttacksHelper(row - 1, col - 1, range - 1, team, possible);
				getValidAttacksHelper(row, col + 1, range - 1, team, possible);
				getValidAttacksHelper(row, col - 1, range - 1, team, possible);
			}
			
		}
	}
	
	public List<int[]> getValidMoves(int row, int col){
		int speed = getMoveDistanceRemaining(row, col);
		
		List<int[]> possible = new ArrayList<int[]>();
		
		getValidMovesHelper(row, col, speed, possible);
		
		return possible;
	}
	
	//TODO simplify this by a lot, make it similar to getValidAttacksHelper
	private void getValidMovesHelper(int row, int col, int remaining, List<int[]> possible) {
		int width = getBoardWidth();
		int length = getBoardLength();
		if(remaining > 0) {
		/* move up
		 * if(can move up):
		 * possible.add([row - 1, col])
		 * getValidMovesHelper(row - 1, col, remaining - 1, possible)
		*/
			if(((row - 1) >= 0) && ((row - 1) < length) && (col >= 0) && (col < width)) {
				if(model.getTile(row - 1, col).canMoveInto()) {
					int[] coordinate = {row - 1, col};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row - 1, col, remaining - 1, possible);
				}
			}
		
		/* move up right
		 * if(can move up right):
		 * possible.add([row - 1, col + 1])
		 * getValidMovesHelper(row - 1, col + 1, remaining - 1, possible)
		*/
			if(((row - 1) >= 0) && ((row - 1) < length) && ((col + 1) >= 0) && ((col + 1) < width)) {
				if(model.getTile(row - 1, col + 1).canMoveInto()) {
					int[] coordinate = {row - 1, col + 1};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row - 1, col + 1, remaining - 1, possible);
				}
			}
			
		/* move right
		 * if(can move right):
		 * possible.add([row, col + 1])
		 * getValidMovesHelper(row, col + 1, remaining - 1, possible)
		*/
			if((row >= 0) && (row < length) && ((col + 1) >= 0) && ((col + 1) < width)) {
				if(model.getTile(row, col + 1).canMoveInto()) {
					int[] coordinate = {row, col + 1};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row, col + 1, remaining - 1, possible);
				}
			}
			
		/* move down right
		 * if(can move down right):
		 * possible.add([row + 1, col + 1])
		 * getValidMovesHelper(row + 1, col + 1, remaining - 1, possible)
		*/
			if(((row + 1) >= 0) && ((row + 1) < length) && ((col + 1) >= 0) && ((col + 1) < width)) {
				if(model.getTile(row + 1, col + 1).canMoveInto()) {
					int[] coordinate = {row + 1, col + 1};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row + 1, col + 1, remaining - 1, possible);
				}
			}	
		
		/* move down
		 * if(can move down):
		 * possible.add([row + 1, col])
		 * getValidMovesHelper(row + 1, col, remaining - 1, possible) 
		*/
			if(((row + 1) >= 0) && ((row + 1) < length) && (col >= 0) && (col < width)) {
				if(model.getTile(row + 1, col).canMoveInto()) {
					int[] coordinate = {row + 1, col};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row + 1, col, remaining - 1, possible);
				}
			}
			
		
		/* move down left
		 * if(can move down left):
		 * possible.add([row + 1, col - 1])
		 * getValidMovesHelper(row + 1, col - 1, remaining - 1, possible) 
		*/
			if(((row + 1) >= 0) && ((row + 1) < length) && ((col - 1) >= 0) && ((col - 1) < width)) {
				if(model.getTile(row + 1, col - 1).canMoveInto()) {
					int[] coordinate = {row + 1, col - 1};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row + 1, col - 1, remaining - 1, possible);
				}
			}
			
		
		/* move left
		 * if(can move left):
		 * possible.add([row, col - 1])
		 * getValidMovesHelper(row, col - 1, remaining - 1, possible)
		 */
			if((row >= 0) && (row < length) && ((col - 1) >= 0) && ((col - 1) < width)) {
				if(model.getTile(row, col - 1).canMoveInto()) {
					int[] coordinate = {row, col - 1};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row, col - 1, remaining - 1, possible);
				}
			}
			
		
		/* move up left
		 * if(can move up left):
		 * possible.add([row - 1, col - 1])
		 * getValidMovesHelper(row - 1, col - 1, remaining - 1, possible)
		 */
			if(((row - 1) >= 0) && ((row - 1) < length) && ((col - 1) >= 0) && ((col - 1) < width)) {
				if(model.getTile(row - 1, col - 1).canMoveInto()) {
					int[] coordinate = {row - 1, col - 1};
					if(!possible.contains(coordinate)) {
						possible.add(coordinate);
					}
					getValidMovesHelper(row - 1, col - 1, remaining - 1, possible);
				}
			}
			
		}
	}
	
	
	public boolean isOver() {
		boolean hasHuman = false;
		boolean hasComputer = false;
		int width = getBoardWidth();
		int length = getBoardLength();
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < length; y++) {
				Tile tile = model.getTile(x, y);
				if(tile.hasPlayer()) {
					Piece piece = tile.getPiece();
					if(piece.getTeam() == Team.HUMAN) {
						hasHuman = true;
					} else if(piece.getTeam() == Team.COMPUTER) {
						hasComputer = true;
					}
				}
				if((hasHuman) && (hasComputer)) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public Team getWinner() {
		int width = getBoardWidth();
		int length = getBoardLength();
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < length; y++) {
				Tile tile = model.getTile(x, y);
				if(tile.hasPlayer()) {
					Piece piece = tile.getPiece();
					if(piece.getTeam() == Team.HUMAN) {
						return Team.HUMAN;
					} else if(piece.getTeam() == Team.COMPUTER) {
						return Team.COMPUTER;
					}
				}
			}
		}
		
		// IF NEITHER TEAMS HAVE ANY PIECES:
		return Team.COMPUTER;
	}
	
	public int getBoardWidth() {
		return model.getBoardWidth();
	}
	
	public int getBoardLength() {
		
		return model.getBoardHeight();
	}
	
	public String getBackgroundImageFileName() {
		return model.getBackgroundImageFileName();
	}
	
	public void nextTurn() {
		model.nextTurn();
	}
	
	public void saveGame(String saveFileName) {
		try {
	    	File newFile = new File(saveFileName);
			newFile.createNewFile();
	    	FileOutputStream saveToFile = new FileOutputStream(saveFileName);
		    ObjectOutputStream outputStream = new ObjectOutputStream(saveToFile);
			outputStream.writeObject(model.getState());
			outputStream.close();
		} catch (IOException e) {}
	}

}