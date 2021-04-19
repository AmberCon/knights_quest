package onboard;

import java.io.Serializable;
import java.util.Random;

import model.StrategyGameModel;
import model.Team;

/**
 * This class is the main way that playing the game is done.
 * This gives the ability to each piece to move, attack,
 * defemd, and take damage.
 * 
 * 
 * @author Drake Sitaraman
 *
 */
public abstract class Piece implements Serializable{
	protected int health;
	
	protected int[] attackRange = new int[2];
	protected int attackDistance;
	
	protected int[] defenseRange = new int[2];
	
	protected int moveDistanceRemaining;
	
	protected boolean isDefended;
	protected boolean hasAttackedOrDefended;
	
	protected Team team;
	
	protected String spriteFileName;
	
	protected Random rand;
	
	/**
	 * Simple constructor to initialize randomness.
	 */
	public Piece() {
		this.rand = new Random();
	}
	
	/**
	 * Generates a random number between two numbers inclusive.
	 * 
	 * @param lowerBound is the lower bound of the number as an int.
	 * @param upperBound is the upper bound of the number of an int.
	 * @return A number in the range [lowerBound, upperBound]
	 */
	private int randRange(int lowerBound, int upperBound) {
		
		//Random number from [0,upperBound-lowerBound]
		int tempRandom = rand.nextInt(upperBound-lowerBound+1);
		
		//Now the number is between [lowerBound, upperBound]
		int result = tempRandom + lowerBound;
		
		return result;
	}

	/**
	 * This method gets the piece to attack at a particular row and column,
	 * then generates a random attack value within the attack range.
	 * 
	 * @param model is a StrategyGameModel, used to fetch the Tile and Piece.
	 * @param row is the row to attack as an int.
	 * @param col is the col to attack as an int.
	 */
	public void attack(StrategyGameModel model, int row, int col) {
		Tile tileToAttack = model.getTile(row, col);
		Piece pieceToAttack = tileToAttack.getPiece();
		
		if(pieceToAttack.getTeam().equals(this.team)) {
			throw new InvalidMoveException("Can't attack members of the same team!");
		}

		int damageDealt = randRange(attackRange[0], attackRange[1]);
		
		pieceToAttack.takeDamage(model, row, col, damageDealt);
		
		
		hasAttackedOrDefended = true;
	}
	
	/**
	 * This method deals damage to the Piece. This function is called "take"Damage
	 * because health is subtracted from the current class. Damage is subtracted
	 * if the player defended.
	 * 
	 * @param model is the StrategyGameModel, used to fetch this piece's tile if
	 * health goes below 0.
	 * @param row is the row as an int of this piece's tile
	 * @param col is the col as an int of this piece's tile
	 * @param damage is the damage done (not counting if the piece defended).
	 */
	public void takeDamage(StrategyGameModel model, int row, int col, int damage) {
		int damageDefended = 0;
		if(isDefended) {
			damageDefended = randRange(defenseRange[0], defenseRange[1]);
		}
		
		//Prevents from taking negative damage (and thus healing)
		int damageTaken = Math.max(0, damage-damageDefended);
		
		health -= damageTaken;
		
		if(health < 0) { //Kill this piece if health < 0
			Tile thisTile = model.getTile(row, col);
			thisTile.removePiece(); //Point the tile to null, will get garbage collected
		}
	}
	
	/**
	 * Indicates the piece has defended.
	 */
	public void defend() {
		isDefended = true;
		hasAttackedOrDefended = true;
	}
	
	
	/**
	 * Subtracts moveDistanceRemaining if the number of moves is valid.
	 * 
	 * 
	 * @param distance
	 * @throws OutOfMovesException, an unchecked exception if the programmer
	 * did not check if the move distance was valid.
	 */
	public void move(int distance) {
		if((moveDistanceRemaining - distance) < 0) {
			throw new OutOfMovesException("This piece is out of moves! Make sure to handle this with an if statement.");
		} else {
			moveDistanceRemaining -= distance;
		}
		
		
	}
	
	/**
	 * Conditions to reset turn, varying from piece to piece.
	 */
	public abstract void resetTurn();

	/**
	 * @return the number of move tiles remaining.
	 */
	public int getMoveDistanceRemaining() {
		return moveDistanceRemaining;
	}
	
	/**
	 * @return true if the piece is in defense state. false if not.
	 */
	public boolean isDefended() {
		return isDefended;
	}
	
	/**
	 * @return true if the piece has either attacked or defended this turn.
	 * false if neither.
	 */
	public boolean hasAttackedOrDefended() {
		return hasAttackedOrDefended;
	}
	
	/**
	 * @return The range, indicating how many Tiles each Piece can attack by.
	 */
	public int getAttackDistance() {
		return attackDistance;
	}
	
	/**
	 * @return The remaining health left.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * 
	 * @return The Team enum which this piece is on.
	 */
	public Team getTeam() {
		return team;
	}
	
	/**
	 * @return The file path of the piece's sprite (for the view).
	 */
	public String getSpriteFileName() {
		return spriteFileName;
	}


}