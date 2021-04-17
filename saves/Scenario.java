package saves;

import java.io.Serializable;

/**
 * Represents a Scenario/Level
 * @author Ember Chan
 *
 */
public class Scenario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String backgroundImageFilename;
	private Tile[][] board;

}
