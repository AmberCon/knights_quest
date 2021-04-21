import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * 
 * @author Amber Converse
 * 
 * This class encapsulates the GUI view for the Strategy Game.
 */

public class StrategyGameView extends Application {

	Stage stage;
	Scene mainMenu;
	Scene game;
	
	StrategyGameLevelView curGame;
	
	static final int numLevels = 5;
	
	/**
	 * This function starts the application, opening to the main menu.
	 * 
	 * @param primaryStage (stage): the stage to display the scene on
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
				
		primaryStage.setTitle("Strategy Game");
		
		primaryStage.setOnCloseRequest(event -> {
			if (curGame != null && !curGame.controller.isOver() && !curGame.isSaveUpToDate()) {
				attemptSave();
			}
		});
		
		BorderPane window = new BorderPane();
		
		VBox centerGUI = new VBox();
		
		centerGUI.setPadding(new Insets(110,10,100,10));
		centerGUI.setBackground(new Background(new BackgroundImage(new Image("assets/menu_background.jpg"), 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundPosition.DEFAULT, 
                									new BackgroundSize(100, 100, true, true, false, true))));
		
		GridPane center = new GridPane();
		
		// Title
		Text title = new Text("Strategy Game");
		title.setFont(Font.font("Palatino", 80));
		title.setFill(Color.ANTIQUEWHITE);
		
		Button resume = new Button("Resume Game");
		resume.setPrefSize(200, 50);
		resume.setStyle("-fx-font-size:20");
		resume.setOnAction((event) -> {
					resumeGame();
			});
		
		Button selectLevel = new Button("Select Level");
		selectLevel.setPrefSize(200, 50);
		selectLevel.setStyle("-fx-font-size:20");
		selectLevel.setOnAction((event) -> {
					levelSelect();
			});
		
		ColumnConstraints elemColumn = new ColumnConstraints();
		elemColumn.setPercentWidth(100);
		elemColumn.setHalignment(HPos.CENTER);
		center.getColumnConstraints().add(elemColumn);
		
		center.add(title, 0, 0);
		center.add(resume, 0, 1);
		center.add(selectLevel, 0, 2);
		
		GridPane.setMargin(title, new Insets(0, 0, 100, 0));
		GridPane.setMargin(resume, new Insets(20, 0, 50, 0));
		
		centerGUI.getChildren().add(center);
		window.setCenter(centerGUI);
		
		mainMenu = new Scene(window, 600, 600);
		primaryStage.setScene(mainMenu);
		primaryStage.show();
	}
	
	/**
	 * Opens a level select screen.
	 */
	private void levelSelect() {
		BorderPane levelWindow = new BorderPane();
		
		VBox centerGUI = new VBox();
		
		centerGUI.setPadding(new Insets(110,10,100,10));
		centerGUI.setBackground(new Background(new BackgroundImage(new Image("assets/menu_background.jpg"), 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundPosition.DEFAULT, 
                									new BackgroundSize(100, 100, true, true, false, true))));
		
		GridPane center = new GridPane();
		
		// Title
		Text title = new Text("Level Select");
		title.setFont(Font.font("Palatino", 80));
		title.setFill(Color.ANTIQUEWHITE);
		
		// Levels
		GridPane levels = new GridPane();
		levels.setAlignment(Pos.BASELINE_CENTER);
		levels.setHgap(10);
		levels.setVgap(10);
		List<Integer> unlockedLevels = getUnlockedLevels();
		for (int i = 0; i < numLevels; i++) {
			final int levelNum = i + 1;
			Button level = new Button("Level " + Integer.toString(levelNum));
			level.setPrefSize(100, 75);
			level.setStyle("-fx-font-size:20");
			level.setOnAction((event) -> {
				startGame("levels/level_" + Integer.toString(levelNum) + ".dat"); // TODO: EDIT WITH AGREED UPON FORMAT
			});
			levels.add(level, i % 4, Math.floorDiv(i, 4));
			ColumnConstraints levelColumn = new ColumnConstraints();
			levelColumn.setPercentWidth(100 / 4);
			levelColumn.setHalignment(HPos.CENTER);
			levels.getColumnConstraints().add(levelColumn);
			if (!unlockedLevels.contains(levelNum)) {
				level.setDisable(true);
			}
		}
		
		// Back to main menu
		Button backToMain = new Button("Main Menu");
		backToMain.setPrefSize(150, 50);
		backToMain.setStyle("-fx-font-size:20");
		backToMain.setOnAction((event) -> {
			stage.setScene(mainMenu);
		});
		
		ColumnConstraints elemColumn = new ColumnConstraints();
		elemColumn.setPercentWidth(100);
		elemColumn.setHalignment(HPos.CENTER);
		center.getColumnConstraints().add(elemColumn);
		
		center.add(title, 0, 0);
		center.add(levels, 0, 1);
		center.add(backToMain, 0, 2);
		
		GridPane.setHalignment(levels, HPos.CENTER);
		
		GridPane.setMargin(title, new Insets(0, 0, 100, 0));
		
		centerGUI.getChildren().add(center);
		levelWindow.setCenter(centerGUI);
		
		Scene levelSelect = new Scene(levelWindow, 600, 600);
		stage.setScene(levelSelect);
		stage.show();
	}
	
	/**
	 * Opens a save select screen.
	 */
	private void resumeGame() {
		BorderPane saveSelectWindow = new BorderPane();
		
		VBox centerGUI = new VBox();
		
		centerGUI.setPadding(new Insets(110,10,100,10));
		centerGUI.setBackground(new Background(new BackgroundImage(new Image("assets/menu_background.jpg"), 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundPosition.DEFAULT, 
                									new BackgroundSize(100, 100, true, true, false, true))));
		
		GridPane center = new GridPane();
		
		// Title
		Text title = new Text("Save Select");
		title.setFont(Font.font("Palatino", 80));
		title.setFill(Color.ANTIQUEWHITE);
		
		// All saves
		VBox saves = new VBox();
		saves.setAlignment(Pos.BASELINE_CENTER);
		saves.setSpacing(10);
		List<String> saveNames = getSaves();
		for (String saveName : saveNames) {
			Button save = new Button(saveName);
			save.setPrefHeight(75);
			save.setStyle("-fx-font-size:20");
			save.setOnAction((event) -> {
				startGame("saves/" + saveName + ".dat"); // TODO: EDIT WITH AGREED UPON FORMAT
			});
			saves.getChildren().add(save);
		}
		
		// Delete a save
		Button deleteSave = new Button("Delete Save");
		deleteSave.setPrefSize(150, 50);
		deleteSave.setStyle("-fx-font-size:20");
		deleteSave.setOnAction((event) -> {
			TextInputDialog deleteSaveDialog = new TextInputDialog();
			deleteSaveDialog.setTitle("Delete Save");
			deleteSaveDialog.setHeaderText( "Enter the name of the save you would like to delete.");
			deleteSaveDialog.setContentText("Save Name:");
			
			Optional<String> fileName = deleteSaveDialog.showAndWait();
			
			fileName.ifPresent(file -> {
				File saveFile = new File("saves/" + file + ".dat"); // TODO: EDIT WITH AGREED UPON FORMAT
				saveFile.delete();
			});
			resumeGame();
		});
		
		// Back to main menu
		Button backToMain = new Button("Main Menu");
		backToMain.setPrefSize(150, 50);
		backToMain.setStyle("-fx-font-size:20");
		backToMain.setOnAction((event) -> {
			stage.setScene(mainMenu);
		});
		
		ColumnConstraints elemColumn = new ColumnConstraints();
		elemColumn.setPercentWidth(100);
		elemColumn.setHalignment(HPos.CENTER);
		center.getColumnConstraints().add(elemColumn);
		
		center.add(title, 0, 0);
		center.add(saves, 0, 1);
		center.add(deleteSave, 0, 2);
		center.add(backToMain, 0, 3);
		
		GridPane.setMargin(title, new Insets(0, 0, 25, 0));
		GridPane.setMargin(saves, new Insets(0, 0, 50, 0));
		GridPane.setMargin(deleteSave, new Insets(0, 0, 25, 0));
		
		centerGUI.getChildren().add(center);
		saveSelectWindow.setCenter(centerGUI);
		
		Scene levelSelect = new Scene(saveSelectWindow, 600, 600);
		stage.setScene(levelSelect);
		stage.show();
	}
	
	/**
	 * Starts a game from the given string levelFileName, which it will pass
	 * into the model to construct the level.
	 * 
	 * @param levelFileName (String): should represent the name a of levelFile or
	 * save file.
	 */
	private void startGame(String levelFileName) {
		
		BorderPane gameWindow = new BorderPane();
		
		// Top GUI
		MenuBar topGUI = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem restart = new MenuItem("Restart");
		restart.setOnAction((event) -> {
			if (!curGame.isSaveUpToDate()) {
				attemptSave();
			}
			startGame(levelFileName);
			});
		fileMenu.getItems().add(restart);
		MenuItem backToMenu = new MenuItem("Main Menu");
		backToMenu.setOnAction((event) -> {
		if (!curGame.isSaveUpToDate()) {
			attemptSave();
		}
			stage.setScene(mainMenu);
			});
		fileMenu.getItems().add(backToMenu);
		MenuItem save = new MenuItem("Save");
		save.setOnAction((event) -> {
			save();
			});
		fileMenu.getItems().add(save);
		MenuItem saveAndExit = new MenuItem("Save and Exit");
		saveAndExit.setOnAction((event) -> {
			save();
			stage.setScene(mainMenu);
			});
		fileMenu.getItems().add(saveAndExit);
		topGUI.getMenus().add(fileMenu);
		
		gameWindow.setTop(topGUI);
		
		curGame = new StrategyGameLevelView(this, gameWindow, levelFileName);
		int[] dimensions = curGame.getMapDimensions();
		
		Scene game = new Scene(gameWindow, dimensions[0]+50, dimensions[1]+50);
		stage.setScene(game);
	}
	
	/**
	 * This function opens a pop-up window that allows the user to type a name for a save
	 * and save their game.
	 */
	private void save() {
		TextInputDialog saveGame = new TextInputDialog();
		saveGame.setTitle("Saving game...");
		saveGame.setHeaderText( "Enter a name for your save to save your progress.\n" +
								"If you do not enter a name, your progress will not be saved.");
		saveGame.setContentText("Save Name:");
		
		Optional<String> fileName = saveGame.showAndWait();
		
		fileName.ifPresent(file -> curGame.controller.saveGame("saves/" + file + ".dat")); // TODO: EDIT WITH AGREED UPON FORMAT
		curGame.setSaveUpToDate();
	}
	
	/**
	 * This function shows a pop-up window warning the user they are about to exit without saving
	 * and offers them the option to save.
	 */
	private void attemptSave() {
		Dialog<String> saveGameWarning = new Dialog<String>();
		saveGameWarning.setTitle("Save game?");
		saveGameWarning.setHeaderText("Are you sure you want to exit without saving?");
		
		ButtonType saveButtonType = new ButtonType("Save");
		ButtonType exitButtonType = new ButtonType("Exit without saving", ButtonData.OK_DONE);
		
		saveGameWarning.getDialogPane().getButtonTypes().addAll(saveButtonType, exitButtonType);
		
		Button saveButton = (Button) saveGameWarning.getDialogPane().lookupButton(saveButtonType);
        
        saveButton.setOnAction((event) -> {
        	save();
        });
        
		saveGameWarning.showAndWait();
	}
	
	/**
	 * This function is displayed when a user wins a level.
	 * 
	 * @param completedLevelName (string) : name of the level file that was just compeleted
	 */
	public void displayNextLevelMenu(String completedLevelName) {
		Dialog<String> endGameAlert = new Dialog<String>();
		endGameAlert.setTitle("Game over!");
		
		endGameAlert.setHeaderText("You won!");
		
		ButtonType nextButtonType = new ButtonType("Next level");
		ButtonType exitButtonType = new ButtonType("Exit to main menu");
		
		endGameAlert.getDialogPane().getButtonTypes().addAll(nextButtonType, exitButtonType);
		
		Button nextButton = (Button) endGameAlert.getDialogPane().lookupButton(nextButtonType);
		Button exitButton = (Button) endGameAlert.getDialogPane().lookupButton(exitButtonType);
        
        nextButton.setOnAction((event) -> {
        	startGame(getNextLevel(completedLevelName));
        });
        exitButton.setOnAction((event) -> {
        	stage.setScene(mainMenu);;
        });
        
		endGameAlert.showAndWait();
	}
	
	/**
	 * This function is displayed when a user loses a level.
	 * 
	 * @param completedLevelName (string) : name of the level file that was just lost.
	 */
	public void displayRetryMenu(String completedLevelName) {
		Dialog<String> endGameAlert = new Dialog<String>();
		endGameAlert.setTitle("Game over!");
		
		endGameAlert.setHeaderText("You lost!");
		
		ButtonType nextButtonType = new ButtonType("Retry");
		ButtonType exitButtonType = new ButtonType("Exit to main menu");
		
		endGameAlert.getDialogPane().getButtonTypes().addAll(nextButtonType, exitButtonType);
		
		Button nextButton = (Button) endGameAlert.getDialogPane().lookupButton(nextButtonType);
		Button exitButton = (Button) endGameAlert.getDialogPane().lookupButton(exitButtonType);
        
        nextButton.setOnAction((event) -> {
        	startGame(completedLevelName);
        });
        exitButton.setOnAction((event) -> {
        	stage.setScene(mainMenu);;
        });
        
		endGameAlert.showAndWait();
	}
	
	
	/**
	 * PLACEHOLDER
	 * 
	 * @return the name of the next level
	 */
	
	private String getNextLevel(String currentLevelName) {
		return "level_2.dat";
	}
	
	/**
	 * PLACEHOLDER
	 * 
	 * @return List<Integer>: a list of all levels unlocked
	 */
	private List<Integer> getUnlockedLevels() {
		List<Integer> unlockedLevels = new ArrayList<Integer>();
		unlockedLevels.add(1);
		return unlockedLevels;
	}
	
	/**
	 * PLACEHOLDER
	 * 
	 * @return List<String>: a list of all save file names
	 */
	private List<String> getSaves() {
		List<String> saves = new ArrayList<String>();
		saves.add("ambers-save");
		return saves;
	}
	
}
