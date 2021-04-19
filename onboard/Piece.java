package onboard;

import java.io.Serializable;
import java.util.Random;

import model.StrategyGameModel;
import model.Team;

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
	
	public Piece() {
		this.rand = new Random();
	}
	
	private int randRange(int lowerBound, int upperBound) {
		
		//Random number from [0,upperBound-lowerBound]
		int tempRandom = rand.nextInt(upperBound-lowerBound+1);
		
		//Now the number is between [lowerBound, upperBound]
		int result = tempRandom + lowerBound;
		
		return result;
	}

	
	public void attack(StrategyGameModel model, int row, int col) {
		Tile tileToAttack = model.getTile(row, col);
		Piece pieceToAttack = tileToAttack.getPiece();

		int damageDealt = randRange(attackRange[0], attackRange[1]);
		
		pieceToAttack.takeDamage(model, row, col, damageDealt);
		
		
		hasAttackedOrDefended = true;
	}
	
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
			thisTile.removePiece(); //Point the tile to null
		}
	}
	
	public void defend() {
		isDefended = true;
		hasAttackedOrDefended = true;
	}
	
	public void move(int distance) {
		if((moveDistanceRemaining - distance) < 0) {
			throw new OutOfMovesException("This piece is out of moves! Make sure to handle this with an if statement.");
		} else {
			moveDistanceRemaining -= distance;
		}
		
		
	}

	public abstract void resetTurn();

	public int getMoveDistanceRemaining() {
		return moveDistanceRemaining;
	}
	
	public boolean isDefended() {
		return isDefended;
	}
	
	public boolean hasAttackedOrDefended() {
		return hasAttackedOrDefended;
	}
	
	public int getAttackDistance() {
		return attackDistance;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public String getSpriteFileName() {
		return spriteFileName;
	}


}