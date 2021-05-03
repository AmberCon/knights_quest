package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import controller.StrategyGameController;
import model.BadSaveException;
import model.StrategyGameModel;

public class StrategyGameControllerTest {

	@Test
	public void ControllerTest1() throws BadSaveException {
		StrategyGameModel model = new StrategyGameModel("levels/level_1.dat");
		StrategyGameController controller = new StrategyGameController(model);
		List<int[]> validMoves = controller.getValidMoves(7, 0);
		assertEquals(validMoves.size(), 1);
		List<int[]> validAttacks = controller.getValidAttacks(7, 0);
		assertEquals(validAttacks.size(), 0);
		assertTrue(controller.hasPlayer(7, 1));
		assertFalse(controller.hasAttackedOrDefended(0, 2));
		
		
	}
}
