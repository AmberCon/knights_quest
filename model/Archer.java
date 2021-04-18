package model;

import model.StrategyGameModel.Team;

public class Archer extends Piece {
	
	public Archer(Team team) {
		health = 100;
		attackRange = new int[2];
		attackRange[0] = 10;
		attackRange[1] = 15;
		attackDistance = 3;
		defenseRange = new int[2];
		defenseRange[0] = 5;
		defenseRange[1] = 10;
		moveDistanceRemaining = 3;
		boolean isDefended = false;
		boolean hasAttacked = false;
		boolean hasDefended = false;
		this.team = team;
		if (team == Team.HUMAN) {
			spriteFileName = "assets/archer_human.png";
		} else {
			spriteFileName = "assets/archer_computer.png";
		}
	}
	
	@Override
	public void resetTurn() {
		moveDistanceRemaining = 3;
		hasAttackedOrDefended = false;
	}
	
}