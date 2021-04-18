package model;

public abstract class Tile {
	Piece piece;
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece newPiece) {
		piece = newPiece;
	}
	
	public boolean hasPlayer() {
		return piece != null;
	}
	
	abstract boolean canMoveInto();
	
	abstract boolean canShootThrough();
	
}
