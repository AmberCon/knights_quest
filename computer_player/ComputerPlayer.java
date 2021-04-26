package computer_player;

import java.util.ArrayList;
import java.util.List;

import controller.StrategyGameController;
import model.StrategyGameModel;
import model.Team;
import onboard.InvalidMoveException;
import onboard.InvalidRemovalException;
import onboard.OutOfMovesException;
import onboard.Piece;

public class ComputerPlayer {
	private StrategyGameController controller;
	private StrategyGameModel model;
	private ArrayList<Piece> pieces;
	private final int boardHeight;
	private final int boardWidth;
	
	public ComputerPlayer(StrategyGameController controller, StrategyGameModel model) {
		this.controller = controller;
		this.model = model;
		this.boardHeight = model.getBoardHeight();
		this.boardWidth = model.getBoardWidth();
		this.getPieces();
	}
	
	private void getPieces() {
		
	}
	
	private void moveAwayFromHumanPiece(int compPieceRow, int compPieceCol) throws InvalidRemovalException, InvalidMoveException, OutOfMovesException {
		List<int[]> humanPieces = getPlayerPieces();
		List<int[]> possibleMoves = controller.getValidMoves(compPieceRow, compPieceCol);
		int avgRow = 0;
		int avgCol = 0;
		for(int[] coord : humanPieces) {
			avgRow += coord[0];
			avgCol += coord[1];
		}
		avgRow = avgRow / humanPieces.size();
		avgCol = avgCol / humanPieces.size();
		int[] farthest = possibleMoves.get(0);
		double greatestDistance = 0;
		for(int[] coord : possibleMoves) {
			double curDistance = Math.sqrt(Math.pow((coord[0] - avgRow), 2) + Math.pow((coord[1] - avgCol), 2));
			if(curDistance > greatestDistance) {
				greatestDistance = curDistance;
				farthest = coord;
			}
		}
		controller.move(compPieceRow, compPieceCol, farthest[0], farthest[1]);
		controller.defend(farthest[0], farthest[1]);
	}
	
	private List<int[]> getPlayerPieces(){
		List<int[]> playerPieces = new ArrayList<int[]>();
		for(int row = 0; row < boardHeight; row++) {
			for(int col = 0; col < boardWidth; col++) {
				if(controller.hasPlayer(row, col)) {
					if(controller.getPieceTeam(row, col).equals(Team.HUMAN)) {
						int[] coords = {row, col};
						playerPieces.add(coords);
					}
				}
			}
		}
		return playerPieces;
	}
}
