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
		int damage = (int) Math.floor(Math.random() * (attackRange[1] - attackRange[0] + 1) + attackRange[0]);
		model.getTile(row, col).getPiece().takeDamage(model, row, col, damage);
		isDefended = false;
		hasAttackedOrDefended = true;
	}
	
	public void takeDamage(StrategyGameModel model, int row, int col, int damage) {
		int negation = 0;
		if (isDefended) {
			negation = (int) Math.floor(Math.random() * (defenseRange[1] - defenseRange[0] + 1) + defenseRange[0]);
		}
		health -= damage - negation;
		if (health <= 0) {
			model.getTile(row, col).setPiece(null);
		}
	}
	
	public void defend() {
		isDefended = true;
		hasAttackedOrDefended = true;
	}
	
	public void move(int distance) {
		moveDistanceRemaining -= distance;
		isDefended = false;
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
