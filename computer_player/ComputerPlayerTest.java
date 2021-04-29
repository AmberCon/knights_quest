package computer_player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import controller.StrategyGameController;
import model.BadSaveException;
import model.StrategyGameModel;
import onboard.Piece;

public class ComputerPlayerTest{
	
	@Test
	/**
	 * This test shows that the shortest path from CPU position (1,4)
	 * is Human position (4,1), distance 3. It is NOT human position (1,0), distance 4.
	 */
	
	public void test_standardPath() { //i.e. with nothing blocking it.
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("./computer_player/test_levels/open.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
		cpu.moveTowardHumanPiece(m.getTile(1, 4).getPiece(), 1, 4); 
		
		assertEquals(cpu.shortestRow, 4);
		assertEquals(cpu.shortestCol, 1);
		
		assertTrue(m.getTile(1, 4).getPiece().isDefended());
	}
	
	@Test
	public void test_Empty() {
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("./computer_player/test_levels/empty.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
		cpu.moveTowardHumanPiece(m.getTile(2, 2).getPiece(), 2, 2); 
		
		assertEquals(cpu.shortestRow, 0);
		assertEquals(cpu.shortestCol, 0);
		
		assertTrue(m.getTile(2, 2).getPiece().isDefended());
		
	}
	
	@Test
	public void test_knightMoveAttack() {
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("./computer_player/test_levels/knight_ma.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
		Piece attacker = m.getTile(2, 3).getPiece();
		cpu.moveTowardHumanPiece(attacker, 2, 3); 
		
		assertEquals(cpu.shortestRow, 4);
		assertEquals(cpu.shortestCol, 1);
		
		assertFalse(attacker.isDefended());
		
		assertTrue(attacker.hasAttackedOrDefended());
		
		attacker.resetTurn();
		
		
		//Stays in place and attacks the same piece again.
		cpu.moveTowardHumanPiece(attacker, 3, 2); 
		assertEquals(cpu.shortestRow, 4);
		assertEquals(cpu.shortestCol, 1);
		
		assertFalse(attacker.isDefended());
		assertTrue(attacker.hasAttackedOrDefended());
		
		
	}
	
	
	@Test
	public void test_archerMoveAttack() {
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("./computer_player/test_levels/archer_ma.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
		Piece attacker = m.getTile(0, 4).getPiece();
		cpu.moveTowardHumanPiece(attacker, 0, 4); 
		
		
		assertFalse(attacker.isDefended());
		assertTrue(attacker.hasAttackedOrDefended());
		
	}
	
	
	@Test
	public void test_blockedPath() {
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("./computer_player/test_levels/blocked.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
		cpu.moveTowardHumanPiece(m.getTile(1, 4).getPiece(), 1, 4); 
		
		
		//Piece did not move anywhere
		assertTrue(m.getTile(1, 4).getPiece().isDefended());
	}
	
	
	
	@Test
	public void test_blockedByAllies() {
		StrategyGameModel m = null;
		try {
			m = new StrategyGameModel("./computer_player/test_levels/blocked_allies.dat");
		} catch (BadSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StrategyGameController c = new StrategyGameController(m);
		
		ComputerPlayer cpu = new ComputerPlayer(c, m);
		cpu.moveTowardHumanPiece(m.getTile(1, 4).getPiece(), 1, 4); 
		

		
		assertTrue(m.getTile(1, 4).getPiece().isDefended());
	}
	

	

}