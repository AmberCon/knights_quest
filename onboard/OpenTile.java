package onboard;

/**
 * This Tile represents any tile that a player can have the ability to move into.
 * The tile may not be "open" if a player moves into it, but if that player vacates
 * the tile, then another player can move into it.
 * 
 * 
 * @author Drake Sitaraman
 *
 */
public class OpenTile extends Tile{
	
	public OpenTile() {
		super.piece = null;
	}
	
	public OpenTile(Piece piece) {
		super.piece = piece;
	}
	
	@Override
	/**
	 * @return false if there is a player on the piece. true
	 * if not.
	 */
	public boolean canMoveInto() {
		if(super.hasPlayer()) {
			return false;
		}
		
		return true;
	}

	@Override
	/**
	 * @return true because either there is nothing blocking a piece
	 * from doing so, or because you can shoot over the player.
	 */
	public boolean canShootThrough() {
		return true;
	}
	
}