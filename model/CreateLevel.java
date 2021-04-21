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
		StrategyGameState level1 = new StrategyGameState();
		Tile[][] map = {{new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile()},
						{new OpenTile(new Knight(Team.COMPUTER)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(new Archer(Team.COMPUTER)),new OpenTile()},
						{new OpenTile(),new BlockedTile(),new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
						{new BlockedTile(),new BlockedTile(),new OpenTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile(),new BlockedTile()},
						{new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
						{new OpenTile(),new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
						{new OpenTile(),new BlockedTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()},
						{new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Archer(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(new Knight(Team.HUMAN)),new OpenTile(),new OpenTile(),new OpenTile(),new OpenTile()}};
		Team curTurn = Team.HUMAN;
		String mapIMG = "assets/level_2_background.png";
		
		level1.board = map;
		level1.currentTurn = curTurn;
		level1.backgroundImageFileName = mapIMG;
		
		File newFile = new File("levels/level_2.dat");
		try {
			newFile.createNewFile();
			FileOutputStream saveToFile = new FileOutputStream("levels/level_2.dat");
		    ObjectOutputStream outputStream = new ObjectOutputStream(saveToFile);
			outputStream.writeObject(level1);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

	}

}
