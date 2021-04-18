package model;

public class OpenTile extends Tile {

	public OpenTile() {}
	
	public OpenTile(Piece piece) {
		this.piece = piece;
	}
	
	@Override
	boolean canMoveInto() {
		return hasPlayer();
	}

	@Override
	boolean canShootThrough() {
		return true;
	}

}
