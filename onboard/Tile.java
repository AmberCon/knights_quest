package onboard;

import java.io.Serializable;

/**
 * This class is the abstract implementation of a Tile. A "Tile"
 * is a piece on the 2D array stored by the model. It has several
 * characteristics, such as whether a piece can stand on it,
 * whether a piece can move through it, whether a piece can shoot through
 * it, or whether it is occupied or not. These are all handled
 * by their respective derived classes.
 * 
 * @author Drake Sitaraman
 *
 */
public abstract class Tile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Piece piece; //Piece who is standing on the tile
	
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
		if(isOpenTile()) {
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
	
	public boolean isOpenTile() {
		return this instanceof OpenTile;
	}
	
	/**
	 * Abstract method for determining if a piece can move into it.
	 * 
	 * @return false if either a Blocked tile or if a piece is occupying
	 * it. true otherwise.
	 */
	public abstract boolean canMoveInto();
	
	/**
	 * Abstract method for determining if a piece can move into it.
	 * 
	 * @return false if a BlockedSeeThroughTile. true otherwise.
	 */
	public abstract boolean canShootThrough();

}