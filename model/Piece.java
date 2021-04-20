package model;

import model.StrategyGameModel.Team;

public abstract class Piece {
	int health;
	int[] attackRange;
	int attackDistance;
	int[] defenseRange;
	int moveDistanceRemaining;
	boolean isDefended;
	boolean hasAttackedOrDefended;
	Team team;
	String spriteFileName;
	
	public void attack(StrategyGameModel model, int row, int col) {
		
	}
	
	public void takeDamage(StrategyGameModel model, int row, int col, int damage) {
		
	}
	
	public void defend() {
		
	}
	
	public void move(int distance) {
		
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
