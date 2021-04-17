import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.StrategyGameModel;
import model.Team;

/**
 * Test cases for StrategyGameModel
 * 
 * @author Ember Chan
 *
 */
public class TestModel {
	
	/**
	 * Tests the model's nextTurn method
	 */
	@Test
	public void testNextTurn() {
		StrategyGameModel m = new StrategyGameModel();
		assertEquals(Team.HUMAN, m.getTurn());
		m.nextTurn();
		assertEquals(Team.COMPUTER, m.getTurn());
		m.nextTurn();
		assertEquals(Team.HUMAN, m.getTurn());
		m.nextTurn();
		assertEquals(Team.COMPUTER, m.getTurn());
	}

}
