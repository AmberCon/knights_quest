package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.BadSaveException;
import model.StrategyGameModel;

public class ControllerTest {

	@Test
	public void ControllerTest1() throws BadSaveException {
		StrategyGameModel model = new StrategyGameModel("saves\\sample-save.dat");
		StrategyGameController controller = new StrategyGameController(model);
		List<int[]> validMoves = controller.getValidMoves(2, 4);
		assertEquals(validMoves.size(), 4);
		List<int[]> validAttacks = controller.getValidAttacks(2, 4);
		assertEquals(validAttacks.size(), 1);
		
		
	}
}
