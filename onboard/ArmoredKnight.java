package onboard;

import model.Team;

/**
 * This class represents the Armored Knight piece.
 * 
 * The Armored Knight is a piece with an exceptional defense but low damage.
 * 
 * @author Ember Chan
 *
 */
public class ArmoredKnight extends Piece {

	/**
	 * Constructor for a new Armored Knight
	 * @param team - the team the new horseman is on
	 */
	public ArmoredKnight(Team team) {
		super();
		
		health = 100;
		
		attackRange[0] = 10; //Lower bound for attack damage
		attackRange[1] = 15; //Upper bound for attack damage
		
		attackDistance = 1; //Meelee unit
		
		defenseRange[0] = 25; //Lower bound for defense
		defenseRange[1] = 35; //Upper bound for defense
		
		moveDistanceRemaining = 1; //How many moves per turn the character has
		
		isDefended = false;
		hasAttackedOrDefended = false;
		
		this.team = team;
		
		spriteFileName = "assets/armored_knight_"+ this.team +".png";

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
