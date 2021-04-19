package onboard;

import model.Team;

public class Archer extends Piece{
	
	public Archer(Team team) {
		super(); //Initialize Random
		
		health = 100;
		
		attackRange[0] = 10; //Minimum attack is 10
		attackRange[1] = 15; //Maximum attack is 15
		
		attackDistance = 3;
		
		defenseRange[0] = 5; //Can defend at least 5 damage
		defenseRange[1] = 10; //Can defend at most 10 damage
		
		moveDistanceRemaining = 3;
		
		isDefended = false;
		hasAttackedOrDefended = false;
		
		this.team = team;
		
		spriteFileName = "assets/archer_"+ this.team +".png";

	}

	@Override
	public void resetTurn() {
		moveDistanceRemaining = 3;
		isDefended = false;
		hasAttackedOrDefended = false;
		
	}
	
}