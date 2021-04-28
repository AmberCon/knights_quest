import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Scanner;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BadSaveException;


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
		resume.setPrefSize(200, 60);
		resume.setStyle("-fx-font-size:20");
		resume.setOnAction((event) -> {
					resumeGame();
			});
		
		Button selectLevel = new Button("Select Level");
		selectLevel.setPrefSize(200, 60);
		selectLevel.setStyle("-fx-font-size:20");
		selectLevel.setOnAction((event) -> {
					levelSelect();
			});
		
		Button levelEditor = new Button("Level Editor");
		levelEditor.setPrefSize(200, 60);
		levelEditor.setStyle("-fx-font-size:20");
		levelEditor.setOnAction((event) -> {
					startLevelEditor();
			});
		
		Button displayRules = new Button("Piece Info");
		displayRules.setPrefSize(200, 60);
		displayRules.setStyle("-fx-font-size:20");
		displayRules.setOnAction((event) -> {
					displayRules();
			});
		
		ColumnConstraints elemColumn = new ColumnConstraints();
		elemColumn.setPercentWidth(100);
		elemColumn.setHalignment(HPos.CENTER);
		center.getColumnConstraints().add(elemColumn);
		
		center.add(title, 0, 0);
		center.add(resume, 0, 1);
		center.add(selectLevel, 0, 2);
		center.add(levelEditor, 0, 3);
		center.add(displayRules, 0, 4);
		
		GridPane.setMargin(title, new Insets(0, 0, 60, 0));
		GridPane.setMargin(resume, new Insets(0, 0, 40, 0));
		GridPane.setMargin(selectLevel, new Insets(0, 0, 40, 0));
		GridPane.setMargin(levelEditor, new Insets(0, 0, 40, 0));
		
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
				startGame("levels/level_" + Integer.toString(levelNum) + ".dat");
			});
			levels.add(level, i % 5, Math.floorDiv(i, 5));
			ColumnConstraints levelColumn = new ColumnConstraints();
			levelColumn.setPercentWidth(100 / 4);
			levelColumn.setHalignment(HPos.CENTER);
			levels.getColumnConstraints().add(levelColumn);
			if (!unlockedLevels.contains(levelNum)) {
				level.setDisable(true);
			}
		}
		
		// Custom Levels
		Button displayCustomLevels = new Button("Custom Levels");
		displayCustomLevels.setPrefSize(200, 75);
		displayCustomLevels.setStyle("-fx-font-size:20");
		displayCustomLevels.setOnAction((event) -> {
			displayCustomLevels();
		});
		
		// Back to main menu
		Button backToMain = new Button("Main Menu");
		backToMain.setPrefSize(200, 75);
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
		center.add(displayCustomLevels, 0, 2);
		center.add(backToMain, 0, 3);
		
		GridPane.setHalignment(levels, HPos.CENTER);
		
		GridPane.setMargin(title, new Insets(0, 0, 100, 0));
		GridPane.setMargin(levels, new Insets(0, 0, 50, 0));
		GridPane.setMargin(displayCustomLevels, new Insets(0, 0, 20, 0));
		
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
			HBox saveBox = new HBox();
			
			Button save = new Button(saveName);
			save.setPrefHeight(75);
			save.setStyle("-fx-font-size:20");
			save.setOnAction((event) -> {
				startGame("saves/" + saveName + ".dat");
			});
			
			Button deleteSave = new Button("Delete");
			deleteSave.setPrefHeight(30);
			deleteSave.setStyle("-fx-font-size:15");
			deleteSave.setOnAction((event) -> {
				Dialog<String> deleteSaveDialog = new Dialog<String>();
				deleteSaveDialog.setTitle("Delete Save");
				deleteSaveDialog.setHeaderText( "Are you sure you want to delete this save?");
				deleteSaveDialog.setContentText("It cannot be recovered later.");
				
				ButtonType deleteButtonType = new ButtonType("Delete");
				ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.OK_DONE);
				
				deleteSaveDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, cancelButtonType);
				
				Button deleteButton = (Button) deleteSaveDialog.getDialogPane().lookupButton(deleteButtonType);
		        
		        deleteButton.setOnAction((e) -> {
		        	File saveFile = new File("saves/" + saveName + ".dat");
					saveFile.delete();
					resumeGame();
		        });
				
				deleteSaveDialog.showAndWait();
			});
			
			saveBox.getChildren().add(save);
			saveBox.getChildren().add(deleteSave);
			
			saveBox.setAlignment(Pos.CENTER);
			
			HBox.setMargin(save, new Insets(0,10,0,0));
			
			saves.getChildren().add(saveBox);
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
		center.add(saves, 0, 1);
		center.add(backToMain, 0, 3);
		
		GridPane.setMargin(title, new Insets(0, 0, 25, 0));
		GridPane.setMargin(saves, new Insets(0, 0, 50, 0));
		
		centerGUI.getChildren().add(center);
		saveSelectWindow.setCenter(centerGUI);
		
		Scene levelSelect = new Scene(saveSelectWindow, 600, 600);
		stage.setScene(levelSelect);
		stage.show();
	}
	
	/**
	 * Displays a list of custom levels
	 */
	private void displayCustomLevels() {
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
		Text title = new Text("Custom Levels");
		title.setFont(Font.font("Palatino", 80));
		title.setFill(Color.ANTIQUEWHITE);
		
		// Levels
		VBox levels = new VBox();
		levels.setAlignment(Pos.BASELINE_CENTER);
		levels.setSpacing(10);
		List<String> levelNames = getCustomLevels();
		for (String levelName : levelNames) {
			HBox levelBox = new HBox();
			
			Button level = new Button(levelName);
			level.setPrefHeight(75);
			level.setStyle("-fx-font-size:20");
			level.setOnAction((event) -> {
				startGame("levels/" + levelName + ".dat");
			});
			
			Button deleteLevel = new Button("Delete");
			deleteLevel.setPrefHeight(30);
			deleteLevel.setStyle("-fx-font-size:15");
			deleteLevel.setOnAction((event) -> {
				Dialog<String> deleteLevelDialog = new Dialog<String>();
				deleteLevelDialog.setTitle("Delete Level");
				deleteLevelDialog.setHeaderText( "Are you sure you want to delete this custom level?");
				deleteLevelDialog.setContentText("It cannot be recovered later.");
				
				ButtonType deleteButtonType = new ButtonType("Delete");
				ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.OK_DONE);
				
				deleteLevelDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, cancelButtonType);
				
				Button deleteButton = (Button) deleteLevelDialog.getDialogPane().lookupButton(deleteButtonType);
		        
		        deleteButton.setOnAction((e) -> {
		        	File saveFile = new File("levels/" + levelName + ".dat");
					saveFile.delete();
					displayCustomLevels();
		        });
				
				deleteLevelDialog.showAndWait();
			});
			
			levelBox.getChildren().add(level);
			levelBox.getChildren().add(deleteLevel);
			
			levelBox.setAlignment(Pos.CENTER);
			
			HBox.setMargin(level, new Insets(0,10,0,0));
			
			levels.getChildren().add(levelBox);
		}
		
		Button levelEditor = new Button("Level Editor");
		levelEditor.setPrefSize(150, 50);
		levelEditor.setStyle("-fx-font-size:20");
		levelEditor.setOnAction((event) -> {
					startLevelEditor();
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
		center.add(levels, 0, 1);
		center.add(levelEditor, 0, 2);
		center.add(backToMain, 0, 3);
		
		GridPane.setHalignment(levels, HPos.CENTER);
		
		GridPane.setMargin(title, new Insets(0, 0, 50, 0));
		GridPane.setMargin(levels, new Insets(0, 0, 50, 0));
		GridPane.setMargin(levelEditor, new Insets(0, 0, 10, 0));
		
		centerGUI.getChildren().add(center);
		levelWindow.setCenter(centerGUI);
		
		Scene levelSelect = new Scene(levelWindow, 600, 600);
		stage.setScene(levelSelect);
		stage.show();
	}
	
	private void displayRules() {
		BorderPane rulesWindow = new BorderPane();
		
		VBox centerGUI = new VBox();
		
		centerGUI.setPadding(new Insets(30,10,100,10));
		centerGUI.setBackground(new Background(new BackgroundImage(new Image("assets/menu_background.jpg"), 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundRepeat.NO_REPEAT, 
                									BackgroundPosition.DEFAULT, 
                									new BackgroundSize(100, 100, true, true, false, true))));
		
		GridPane center = new GridPane();
		
		// Title
		Text title = new Text("Rules");
		title.setFont(Font.font("Palatino", 60));
		title.setFill(Color.ANTIQUEWHITE);
		
		// Pieces
		ScrollPane rules = new ScrollPane();
		rules.setContent(new ImageView("assets/rules.png"));
		rules.setMinHeight(500);
		
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
		center.add(rules, 0, 1);
		center.add(backToMain, 0, 2);
		
		GridPane.setHalignment(rules, HPos.CENTER);
		
		GridPane.setMargin(title, new Insets(0, 0, 20, 0));
		GridPane.setMargin(rules, new Insets(0, 0, 20, 0));
		
		centerGUI.getChildren().add(center);
		rulesWindow.setCenter(centerGUI);
		
		Scene levelSelect = new Scene(rulesWindow, 600, 700);
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
		
		try {
			curGame = new StrategyGameLevelView(this, gameWindow, levelFileName);
		} catch (BadSaveException e) {
			badSaveWarning();
			return;
		}
		int[] dimensions = curGame.getMapDimensions();
		
		Scene game = new Scene(gameWindow, dimensions[0]+50, dimensions[1]+50);
		stage.setScene(game);
	}
	
	/**
	 * Takes the user to the level editor
	 */
	private void startLevelEditor() {
		new LevelEditor(this);
	}
	
	/**
	 * Returns the user to the main menu
	 */
	public void returnToMainMenu() {
		stage.setScene(mainMenu);
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
		
		fileName.ifPresent(file -> curGame.controller.saveGame("saves/" + file + ".dat"));
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
		unlockNextLevel(completedLevelName);
		Dialog<String> endGameAlert = new Dialog<String>();
		endGameAlert.setTitle("Game over!");
		
		endGameAlert.setHeaderText("You won!");
		
		ButtonType nextButtonType = new ButtonType("Next level");
		ButtonType exitButtonType = new ButtonType("Exit to main menu");
		
		endGameAlert.getDialogPane().getButtonTypes().addAll(nextButtonType, exitButtonType);
		
		Button nextButton = (Button) endGameAlert.getDialogPane().lookupButton(nextButtonType);
		Button exitButton = (Button) endGameAlert.getDialogPane().lookupButton(exitButtonType);
        
        nextButton.setOnAction((event) -> {
        	String nextLevelName = getAndUnlockNextLevel(completedLevelName);
        	if (nextLevelName != "") {
        		startGame(nextLevelName);
        	} else {
        		stage.setScene(mainMenu);
        		Dialog<String> noMoreLevels = new Dialog<String>();
        		noMoreLevels.setTitle("No more levels!");
        		noMoreLevels.setHeaderText("You have beat the game!");
        		noMoreLevels.setContentText("There are no more levels to unlock.");
        		noMoreLevels.showAndWait();
        	}
        });
        exitButton.setOnAction((event) -> {
        	stage.setScene(mainMenu);
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
	
	public void badSaveWarning() {
		stage.setScene(mainMenu);
		Dialog<String> badSaveAlert = new Dialog<String>();
		
		badSaveAlert.setTitle("Invalid save file");
		badSaveAlert.setHeaderText("Save file is not accessible");
		badSaveAlert.setContentText("Save file does not exist, is corrupted, or is not\na Stategy Game save file.");
		
		ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
		
		badSaveAlert.getDialogPane().getButtonTypes().addAll(okButtonType);
        
		badSaveAlert.showAndWait();
	}
	
	
	/**
	 * Returns the name of the next level.
	 * 
	 * @return String : the name of the next level
	 */
	
	private String getAndUnlockNextLevel(String currentLevelName) {
		int nextLevelNum = Integer.parseInt(currentLevelName.substring(-5)) + 1;
		if (nextLevelNum > numLevels) {
			return "";
		}
		return "levels/level_" + Integer.toString(nextLevelNum) + " .dat";
	}
	
	/**
	 * Unlocks the next level
	 * 
	 * @param completedLevelName (String) : the name of the level that was just completed.
	 */
	private void unlockNextLevel(String completedLevelName) {
		int levelNum = Integer.parseInt(completedLevelName.substring(-5));
		File unlockedLevels = new File("profile/unlockedLevels.info");
		try {
			if (!unlockedLevels.exists()) {
				try {
					unlockedLevels.createNewFile();
					FileWriter levelWriter = new FileWriter("profile/completedLevels.info");
					levelWriter.write("1");
					levelWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				FileWriter levelWriter = new FileWriter("profile/completedLevels.info");
				levelWriter.write("," + Integer.toString(levelNum));
				levelWriter.close();
			}
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	/**
	 * Returns a list of all levels that are allowed to be played
	 * 
	 * @return List<Integer>: a list of all levels unlocked
	 */
	private List<Integer> getUnlockedLevels() {
		List<Integer> unlockedLevels = new ArrayList<Integer>();
		File completedLevelFile = new File("profile/completedLevels.info");
		Scanner completedLevelScanner = null;
		try {
			completedLevelScanner = new Scanner(completedLevelFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] completedLevels = completedLevelScanner.next().strip().split(",");
		int levelNum = 0;
		for (String level : completedLevels) {
			levelNum = Integer.parseInt(level);
			unlockedLevels.add(levelNum);
		}
		if (levelNum < numLevels) {
			unlockedLevels.add(levelNum + 1);
		}
		return unlockedLevels;
	}
	
	/**
	 * Returns a list of all save file names
	 * 
	 * @return List<String>: a list of all save file names
	 */
	private List<String> getSaves() {
		List<String> saves = new ArrayList<String>();
		File savesDir = new File("saves");
		for (String save : savesDir.list()) {
			saves.add(save.replaceAll("(.dat)", ""));
		}
		return saves;
	}
	
	/**
	 * Returns of list of all custom level file names
	 */
	private List<String> getCustomLevels() {
		List<String> levelNames = new ArrayList<String>();
		File levelsDir = new File("levels");
		for (String level : levelsDir.list()) {
			String levelName = level.replaceAll("(.dat)", "");
			if (!levelName.replaceAll("[0-9]", "").equals("level_")) {
				levelNames.add(level.replaceAll("(.dat)", ""));
			}
			
		}
		return levelNames;
	}
	
}
