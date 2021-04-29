package onboard;

/**
 * 
 * This represents a tile such as a water or a fence. Pieces can
 * shoot over it, but cannot move into it.
 * 
 * @author Drake Sitaraman
 *
 */
public class BlockedSeeThroughTile extends Tile{
	
	/**
	 * No arg constructor for BlockedSeeThroughTile
	 */
	public BlockedSeeThroughTile() {
		super();
		imgPath="assets/BlockedSeeThroughTile.png";
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	/**
	 * @return false
	 */
	public boolean canMoveInto() {
		return false;
	}

	@Override
	/**
	 * @return true
	 */
	public boolean canShootThrough() {
		return true;
	}
	
}