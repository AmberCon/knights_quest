package onboard;

import java.io.Serializable;

public abstract class Tile implements Serializable{
	protected Piece piece;
	
	/**
	 * @return The Piece object at the tile or null if one does not exist.
	 */
	public Piece getPiece() {
		return this.piece;
	}
	
	/**
	 * A short way to determine move validity. Set the piece if the tile is free
	 * to move into. Throw an Exception if there is an existing piece on the tile
	 * or the tile is either a BlockedTile or BlockedSeeThroughTile
	 *  
	 * @param newPiece is the Piece object to set as the field.
	 * @throws InvalidMoveException if the piece is not able to move into
	 * the tile at this particular time.
	 */
	public void setPiece(Piece newPiece) throws InvalidMoveException {
		if(this.canMoveInto()) {
			this.piece = newPiece;
		} else {
			throw new InvalidMoveException("Can't move the piece there! Either an invalid tile or a piece is already there");
		}
	}
	
	/**
	 * A short way to determine if removing a piece is legal.
	 * Set the piece to null if the tile is an OpenTile. If it is
	 * any other kind of tile, there is no Piece there to remove.
	 * 
	 * @throws InvalidRemovalException
	 */
	public void removePiece() throws InvalidRemovalException {
		if(this instanceof OpenTile) {
			this.piece = null;
		} else {
			throw new InvalidRemovalException("Can't remove the piece from a Blocked tile!");
		}
		
	}
	
	/**
	 * @return true if the Tile has a player there. false if not.
	 */
	public boolean hasPlayer() {
		return piece != null;
	}
	
	public abstract boolean canMoveInto();
	
	public abstract boolean canShootThrough();

}