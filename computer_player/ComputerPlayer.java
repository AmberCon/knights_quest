package computer_player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import controller.StrategyGameController;
import model.StrategyGameModel;
import model.Team;
import onboard.FriendlyFireException;
import onboard.InvalidMoveException;
import onboard.InvalidRemovalException;
import onboard.OutOfMovesException;
import onboard.Piece;
import onboard.Tile;

/**
 * This class implements the logic for the Computer player.
 * The main logic involves a Breadth-First search to find
 * 
 * 
 * @author Drake Sitaraman
 *
 */
public class ComputerPlayer{
	
	private StrategyGameController controller;
	private StrategyGameModel model;
	private ArrayList<Piece> pieces;
	private final int boardHeight;
	private final int boardWidth;
	
	
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	private final int UP_RIGHT = 4;
	private final int UP_LEFT = 5;
	private final int DOWN_RIGHT = 6;
	private final int DOWN_LEFT = 7;
	
	//These two fields are for testing shortest path.
	protected int shortestRow;
	protected int shortestCol;
	
	public ComputerPlayer(StrategyGameController controller, StrategyGameModel model) {
		this.controller = controller;
		this.model = model;
		this.boardHeight = model.getBoardHeight();
		this.boardWidth = model.getBoardWidth();
		this.getPieces();
	}
	
	
	
	private void getPieces() {
		pieces = new ArrayList<Piece>();
		for(int row = 0; row<boardHeight; row++) {
			for(int col = 0; col<boardWidth; col++) {
				Tile tile = model.getTile(row, col);
				Piece piece = tile.getPiece();
				if(piece != null && piece.getTeam().equals(Team.COMPUTER)) {
					pieces.add(piece);
				}
			}
		}
		
	}
	
	private Coordinate getCoordinate(int fromRow, int fromCol, int direction) {
		switch(direction) {
			case(UP): return new Coordinate(fromRow-1, fromCol);
			case(DOWN): return new Coordinate(fromRow+1, fromCol);
			case(LEFT): return new Coordinate(fromRow, fromCol-1);
			case(RIGHT): return new Coordinate(fromRow, fromCol+1);
			case(UP_RIGHT): return new Coordinate(fromRow-1, fromCol+1);
			case(UP_LEFT): return new Coordinate(fromRow-1, fromCol-1);
			case(DOWN_RIGHT): return new Coordinate(fromRow+1, fromCol+1);
		} //else DOWN_LEFT
		
		return new Coordinate(fromRow+1, fromCol-1);
	}
	
	private Tile coordToTile(int row, int col) {
		
		//Out of bounds
		if(col < 0 || col >= boardWidth || row<0 || row >= boardHeight) {
			return null;
		}
		
		return model.getTile(row, col);
	}
	
	protected void moveTowardHumanPiece(Piece piece, int compPieceRow, int compPieceCol) {

		Stack<Coordinate> shortestPath = shortestPath(compPieceRow, compPieceCol);
		
		if(shortestPath == null) {
			piece.defend();
			return;
		}
		
		int currRow = compPieceRow;
		int currCol = compPieceCol;
		
		while(shortestPath.size() > piece.getAttackDistance() ) {
			Coordinate nextMove = shortestPath.pop();
			Tile toMove = coordToTile(nextMove.row, nextMove.col);

			try {//TODO: Update this when the controller is tested
				//controller.move(currRow, currCol, nextMove.row, nextMove.col);
				toMove.setPiece(piece);
				piece.move(1);
				currRow = nextMove.row;
				currCol = nextMove.col;
				
			//OutOfMoves will probably happen. InvalidMove could happen
			//If there is a piece blocking.
			} catch (InvalidMoveException | OutOfMovesException e) {
				controller.defend(currRow, currCol);
				return;
			} 
		}
		

		
		//Pop until only the enemy's coordinates remains on the stack.
		while(shortestPath.size() > 1) {
			shortestPath.pop();
		}
		
		
		Coordinate potentialAttack = shortestPath.pop();


		
		try {
			controller.attack(currRow, currCol, potentialAttack.row, potentialAttack.col);
		} catch (FriendlyFireException | InvalidRemovalException e1) {
			controller.defend(currRow, currCol);
		}
	}


	private Stack<Coordinate> shortestPath(int fromRow, int fromCol) {
		
		Queue<Coordinate> q = new LinkedList<Coordinate>();
		HashSet<Coordinate> coordinatesVisited = new HashSet<Coordinate>();
		
		
		Coordinate[][] pathTo = new Coordinate[boardHeight][boardWidth];
		
		Coordinate start = new Coordinate(fromRow, fromCol);

		q.add(start);
		coordinatesVisited.add(start);
		
		
		while(!q.isEmpty()) {
			Coordinate c = q.remove();
			coordinatesVisited.add(c);
			
			for(int i = 0; i<8; i++) {
				
				Coordinate adjacent = getCoordinate(c.row, c.col, i);
				
				
				Tile tile = coordToTile(adjacent.row, adjacent.col);
				
				//If out of bounds or already visited 
				if(tile == null  || !(tile.isOpenTile())|| coordinatesVisited.contains(adjacent)) {
					continue;
				} 
				
				
				q.add(adjacent);
				coordinatesVisited.add(adjacent);
				pathTo[adjacent.row][adjacent.col] = c;
				
				
				Piece onAdjacent = tile.getPiece();
				
		
				

				//Human found, start executing path.
				if(onAdjacent != null && onAdjacent.getTeam().equals(Team.HUMAN)) {
					return getPath(pathTo, adjacent);
				}
								
			}
			
		}
		
		return null; //No human on board, but if that were the case, this method would not have been called.
		
	}
	
	private Stack<Coordinate> getPath(Coordinate[][] pathTo, Coordinate finalCoord){
		Stack<Coordinate> shortestPath = new Stack<Coordinate>();
		
		shortestRow = finalCoord.row;
		shortestCol = finalCoord.col;
		
		shortestPath.push(finalCoord);
		Coordinate currCoord = pathTo[finalCoord.row][finalCoord.col];
		
		while(currCoord != null) {
			shortestPath.push(currCoord);
			currCoord = pathTo[currCoord.row][currCoord.col];
		}
		
		shortestPath.pop(); //Top of the stack is the current piece--pop, otherwise pieces would always stay still
		
		return shortestPath;
	}
	
	private class Coordinate {
		public int row;
		public int col;
	

		public Coordinate(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public boolean equals(Object other) {
			if(!(other instanceof Coordinate)) {
				return false;
			}
			
			Coordinate o = (Coordinate) other;
			
			return ((this.row == o.row) && (this.col == o.col));
		}
		
		public int hashCode() {
			return (row+col)/2;
		}
		
		public String toString() {
			return "(" + row + ", " + col + ")";
		}
	}
}