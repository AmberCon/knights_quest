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