package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import onboard.Archer;
import onboard.BlockedSeeThroughTile;
import onboard.BlockedTile;
import onboard.Knight;
import onboard.OpenTile;
import onboard.Tile;

public class CreateLevel {

	public static void main(String[] args) {
		Tile[][][] maps = getMaps();
		
		for (int i = 0; i < 5; i++) {
			StrategyGameState curLevel = new StrategyGameState();
			
			
			Team curTurn = Team.HUMAN;
			String mapIMG = "assets/level_" + Integer.toString(i + 1) + "_background.png";
			
			curLevel.board = maps[i];
			curLevel.currentTurn = curTurn;
			curLevel.backgroundImageFileName = mapIMG;
			
			File newFile = new File("levels/level_" + Integer.toString(i + 1) + ".dat");
			try {
				newFile.createNewFile();
				FileOutputStream saveToFile = new FileOutputStream("levels/level_" + Integer.toString(i + 1) + ".dat");
			    ObjectOutputStream outputStream = new ObjectOutputStream(saveToFile);
				outputStream.writeObject(curLevel);
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static Tile[][][] getMaps() {
		Tile[][][] maps = new Tile[5][][];
		
		Tile[][] map1 = {{new OpenTile(),new OpenTile(),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(),new OpenTile(),new OpenTile()},
				 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				 {new BlockedSeeThroughTile(),new OpenTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new OpenTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile()},
				 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				 {new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				 {new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()}};
		maps[0] = map1;
		
		Tile[][] map2 = {{new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile()},
				{new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile()},
				{new OpenTile(),new BlockedTile(),new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				{new BlockedTile(),new BlockedTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile()},
				{new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				{new OpenTile(),new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				{new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				{new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()}};
		maps[1] = map2;
		
		Tile[][] map3 = {{new BlockedTile(),new BlockedTile(),new BlockedTile(),new OpenTile(new Knight(Team.COMPUTER)),new BlockedTile(),new BlockedTile(),new OpenTile(new Knight(Team.COMPUTER)),new BlockedTile()},
						 {new BlockedTile(),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(),new BlockedTile(),new BlockedTile(),new OpenTile(),new OpenTile()},
						 {new OpenTile(),new OpenTile(),new BlockedTile(),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
						 {new BlockedTile(),new BlockedTile(),new BlockedTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new OpenTile(),new BlockedTile()},
						 {new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new OpenTile(),new OpenTile()},
						 {new BlockedTile(),new OpenTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new OpenTile()},
						 {new BlockedTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
						 {new BlockedTile(),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()}};
		maps[2] = map3;
		
		Tile[][] map4 = {{new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedTile(),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER))},
						 {new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new BlockedTile()},
						 {new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new OpenTile(),new OpenTile(),new OpenTile()},
				 		 {new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new BlockedSeeThroughTile()},
				 		 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile()},
				 		 {new OpenTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile()},
				 		 {new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile()},
				 		 {new OpenTile(new Archer(Team.HUMAN)),new OpenTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile(),new BlockedSeeThroughTile()}};
		maps[3] = map4;
		
		Tile[][] map5 = {{new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(), new OpenTile()},
						 {new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile()}};
		maps[4] = map5;
		
		return maps;
	}

}
