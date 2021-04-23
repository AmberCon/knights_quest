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
import onboard.Piece;
import onboard.Tile;

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


	public Stack<Coordinate> shortestPath(int fromRow, int fromCol) {
		
		Queue<Coordinate> q = new LinkedList<Coordinate>();
		HashSet<Coordinate> coordinatesVisited = new HashSet<Coordinate>();
		
		
		Stack<Coordinate> shortestPath = new Stack<Coordinate>();
		Coordinate[][] pathTo = new Coordinate[boardHeight][boardWidth];
		
		q.add(new Coordinate(fromRow, fromCol));
		
		while(!q.isEmpty()) {
			
			Coordinate c = q.remove();
			coordinatesVisited.add(c);
			
			for(int i = 0; i<8; i++) {
				
				Coordinate adjacent = getCoordinate(fromRow, fromCol, i);
				Tile tile = coordToTile(adjacent.row, adjacent.col);
				
				//If out of bounds or already visited OR if you can't move into a tile.
				if(tile == null || !tile.canMoveInto() || coordinatesVisited.contains(adjacent)) {
					continue;
				} 
				
				q.add(adjacent);
				coordinatesVisited.add(adjacent);
				pathTo[adjacent.row][adjacent.col] = c;
				
				
				Piece onAdjacent = tile.getPiece();
				if(onAdjacent !=null) {
				System.out.println(onAdjacent.getTeam());
				} else {
					System.out.println("null");
				}
				//Human found, start executing path.
				if(onAdjacent != null && onAdjacent.getTeam().equals(Team.HUMAN)) {
					
					shortestPath = getPath(pathTo, adjacent.row, adjacent.col);
					break;
				}
								
			}
			
		}
		
		return shortestPath;
		
	}
	
	private Stack<Coordinate> getPath(Coordinate[][] pathTo, int finalRow, int finalCol){
		Stack<Coordinate> shortestPath = new Stack<Coordinate>();
		
		Coordinate currCoord = pathTo[finalRow][finalCol];
		
		while(currCoord != null) {
			shortestPath.push(currCoord);
			System.out.println("(" + currCoord.row + ", " + currCoord.col + ")");
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
	}
}