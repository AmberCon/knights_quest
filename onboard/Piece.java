package onboard;

import java.io.Serializable;

public abstract class Piece implements Serializable{
	int health;
	int[] attackRange;
	int attackDistance;
	int[] defenseRange;
	int moveDistanceRemaining;
	boolean isDefended;
	boolean hasAttackedOrDefended;
	String team;
	String spriteFileName;

}