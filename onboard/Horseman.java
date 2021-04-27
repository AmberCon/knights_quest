package onboard;

import model.Team;

/**
 * This class represents the horseman piece.
 * 
 * The horseman is exceptionally mobile piece that has low damage.
 * 
 * @author Ember Chan
 *
 */
public class Horseman extends Piece{

	/**
	 * Constructor for a new Horseman
	 * @param team - the team the new horseman is on
	 */
	public Horseman(Team team) {
		super();
		
		health = 100;
		
		attackRange[0] = 10; //Lower bound for attack damage
		attackRange[1] = 15; //Upper bound for attack damage
		
		attackDistance = 1; //Meelee unit
		
		defenseRange[0] = 10; //Can defend as little as 10 damage
		defenseRange[1] = 20; //Can defend as high as 20 damage
		
		moveDistanceRemaining = 5; //Can moves 5 tiles per turn - very mobile character
		
		isDefended = false;
		hasAttackedOrDefended = false;
		
		this.team = team;
		
		spriteFileName = "assets/horseman_"+ this.team +".png";

	}

	/**
	 * Restores expended movement and action
	 * resources when the piece is reset after
	 * a turn. 
	 */
	@Override
	public void resetTurn() {
		moveDistanceRemaining = 5;
		hasAttackedOrDefended = false;
	}
}
