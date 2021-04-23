package onboard;

/**
 * 
 * A Blocked Tile is a tile that a player cannot move through, nor
 * can an archer shoot through. This would be something like
 * a wall or a mountain.
 * 
 * @author Drake Sitaraman
 *
 */
public class BlockedTile extends Tile{
	
	/**
	 * No arg constructor for Blocked Tile
	 */
	public BlockedTile() {
		super();
		imgPath = "assets/BlockedTile.png";
	}
	
	@Override
	/**
	 * @return false
	 */
	public boolean canMoveInto() {
		return false;
	}

	@Override
	/**
	 * @return false
	 */
	public boolean canShootThrough() {
		return false;
	}
	
}