package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import model.Team;
import onboard.InvalidMoveException;
import onboard.InvalidRemovalException;
import onboard.Knight;
import onboard.OpenTile;
import onboard.Tile;

public class TilesTests {
	/**
	 * Tests the setPiece method of Tile
	 * @throws InvalidMoveException 
	 */
	@Test
	public void testSetPiece() throws InvalidMoveException {
		Tile t = new OpenTile();
		t.setPiece(new Knight(Team.HUMAN));
		assertTrue(t.getPiece() instanceof Knight);
		assertThrows(InvalidMoveException.class, ()->{t.setPiece(null);});
	}
	
	/**
	 * Tests some more miscillaneous methods of Tile
	 * @throws InvalidMoveException 
	 * @throws InvalidRemovalException 
	 */
	@Test
	public void testMiscMethods() throws InvalidMoveException, InvalidRemovalException {
		Tile t = new OpenTile();
		t.setPiece(new Knight(Team.HUMAN));
		t.removePiece();
	}
}
