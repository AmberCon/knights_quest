import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.StrategyGameController;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.StrategyGameModel;
import model.StrategyGameModel.Team;

@SuppressWarnings("deprecation")
public class StrategyGameLevelView implements Observer {
	
	BorderPane gameWindow;
	GridPane map;
	Text message;
	
	int mapWidthPixel;
	int mapHeightPixel;
	
	StrategyGameModel model;
	StrategyGameController controller;
	StrategyGameView mainView;
	
	boolean hasRecentSave;
	String levelFileName;
	
	public StrategyGameLevelView(StrategyGameView mainView, BorderPane gameWindow, String levelFileName) {
		
		this.mainView = mainView;
		this.gameWindow = gameWindow;
		this.levelFileName = levelFileName;
		
		hasRecentSave = true;
			
		// Set up model and controller
		model = new StrategyGameModel(levelFileName);
		model.addObserver(this);
		controller = new StrategyGameController(model);
				
		// Bottom GUI
		HBox bottomGUI = new HBox();
		
		message = new Text();
		message.setFont(Font.font("Palatino", 15));
		bottomGUI.getChildren().add(message);
		HBox.setMargin(message, new Insets(10,10,10,10));
		Button nextTurn = new Button("Next Turn");
		nextTurn.setOnAction((event) -> {
			controller.nextTurn();
			});
		bottomGUI.getChildren().add(nextTurn);
		HBox.setMargin(nextTurn, new Insets(10,10,10,10));
		
		bottomGUI.setPrefHeight(50);
		bottomGUI.setAlignment(Pos.CENTER_RIGHT);
		
		gameWindow.setBottom(bottomGUI);
				
		// Set up the map
		setBoard();
	}
	
	private void setBoard() {
		Image mapBackground = new Image(controller.getBackgroundImageFileName());
		mapWidthPixel = (int) mapBackground.getWidth();
		mapHeightPixel = (int) mapBackground.getHeight();
		this.message.setText("");
		VBox center = new VBox();
		center.setPrefSize(mapWidthPixel, mapHeightPixel);
		center.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		map = new GridPane();
		map.setAlignment(Pos.BASELINE_CENTER);
		map.setPrefSize(mapWidthPixel, mapHeightPixel);;
		map.setBackground(new Background(new BackgroundImage(mapBackground,
							BackgroundRepeat.NO_REPEAT, 
							BackgroundRepeat.NO_REPEAT, 
							BackgroundPosition.CENTER, 
							BackgroundSize.DEFAULT)));
		
		for (int row = 0; row < controller.getBoardLength(); row++) {
			for (int col = 0; col < controller.getBoardWidth(); col++) {
				GridPane tile = new GridPane();
				setTile(tile, row, col, true);
				map.add(tile, col, row);
			}
		}
		center.getChildren().add(map);
		center.setAlignment(Pos.CENTER);;
		gameWindow.setCenter(center);
	}
	
	private void setTile(GridPane tile, int row, int col, boolean withEventFilters) {
		
		int tileWidth = Math.floorDiv(mapWidthPixel, controller.getBoardWidth());
		int tileHeight = Math.floorDiv(mapHeightPixel, controller.getBoardLength());
		
		tile.setPrefSize(tileWidth, tileHeight);
		tile.setMaxSize(tileWidth, tileHeight);
		tile.setBorder(new Border(new BorderStroke(Color.BLACK,
				   BorderStrokeStyle.SOLID,
				   CornerRadii.EMPTY,
				   new BorderWidths(1,1,1,1))));
		tile.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		tile.setAlignment(Pos.BOTTOM_CENTER);
		
		if (controller.hasPlayer(row, col)) {
			
			if (controller.isDefended(row, col)) {
				tile.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
			}
			
			if (controller.getPieceTeam(row, col) == controller.getTurn() && withEventFilters) {
				// On-Click Menu
				final int pieceRow = row;
				final int pieceCol = col;
				ContextMenu onClickMenu = new ContextMenu();
				MenuItem attack = new MenuItem("Attack");
				attack.setOnAction((event) -> {
						showAttacks(pieceRow, pieceCol);
					});
				
				MenuItem defend = new MenuItem("Defend");
				defend.setOnAction((event) -> {
						controller.defend(pieceRow, pieceCol);
					});
				MenuItem move = new MenuItem("Move");
				move.setOnAction((event) -> {
						showMoves(pieceRow, pieceCol);
					});
				onClickMenu.getItems().addAll(attack, defend, move);
				
				EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			        	int row = GridPane.getRowIndex(tile);
			        	int col = GridPane.getColumnIndex(tile);
			        	if (e.isSecondaryButtonDown()) {
				    		showMoves(row, col);
				    	} else {
				    		onClickMenu.show(tile, e.getScreenX(), e.getScreenY());
				    	}
			        } 
			    };  
			    tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				
			} else if (withEventFilters) {
				
				EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
			        @Override 
			        public void handle(MouseEvent e) { 
			        	displayMessage("This is not your piece.");
			        } 
			    };  
			    
			    tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
			}
			
			
			// Display character
		    ImageView sprite = new ImageView();
		    sprite.setImage(new Image(controller.getPieceSprite(row, col)));
		    sprite.setFitWidth(tileWidth);
		    sprite.setFitHeight(tileHeight * 5/6);
		    sprite.setPreserveRatio(true);
			tile.add(sprite, 0, 0);
			GridPane.setHalignment(sprite, HPos.CENTER);
			
			// Display Health
			GridPane healthBar = new GridPane();
			Rectangle positive = new Rectangle((controller.getPieceHealth(row, col) / 100.0) * (tileWidth * 5/6), tileHeight * 1/6);
			positive.setFill(Color.LIMEGREEN);
			Rectangle negative = new Rectangle((1 - (controller.getPieceHealth(row, col) / 100.0)) * (tileWidth * 5/6), tileHeight * 1/6); 
			negative.setFill(Color.DARKRED);
			healthBar.add(positive, 0, 0);
			healthBar.add(negative, 1, 0);
			GridPane.setHalignment(positive, HPos.CENTER);
			GridPane.setHalignment(negative, HPos.CENTER);
			tile.add(healthBar, 0, 1);
			GridPane.setHalignment(healthBar, HPos.CENTER);
			
		}
	}
	
	private void showAttacks(int row, int col) {
		
		List<int[]> validAttacks = controller.getValidAttacks(row, col);
		
		if (validAttacks.size() == 0) {
			displayMessage("No valid attacks for this piece.");
			return;
		}
		
		for (int mapRow = 0; mapRow < controller.getBoardLength(); mapRow++) {
			for (int mapCol = 0; mapCol < controller.getBoardWidth(); mapCol++) {
				
				GridPane tile = new GridPane();
				setTile(tile, mapRow, mapCol, false);
				
				int[] curCoordinates = {mapRow, mapCol};
				
				boolean isAttack = false;
				for (int[] coord : validAttacks) {
					if (coord[0] == curCoordinates[0] && coord[1] == curCoordinates[1]) {
						isAttack = true;
					}
				}
				
				if (isAttack) {
					
					tile.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
					
					final int againstRow = mapRow;
					final int againstCol = mapCol;
					
					EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				        @Override 
				        public void handle(MouseEvent e) { 
				        	controller.attack(row, col, againstRow, againstCol);
				        } 
				    };  
				    
				    tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				    
				} else {
					EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				        @Override 
				        public void handle(MouseEvent e) { 
				        	setBoard();
				        }
				    };  
				    
				    tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				}
				
				map.add(tile, mapCol, mapRow);
			}
		}
	}
	
	private void showMoves(int row, int col) {

		List<int[]> validMoves = controller.getValidMoves(row, col);
		
		if (validMoves.size() == 0) {
			displayMessage("No valid moves for this piece.");
			return;
		}
		
		for (int mapRow = 0; mapRow < controller.getBoardLength(); mapRow++) {
			for (int mapCol = 0; mapCol < controller.getBoardWidth(); mapCol++) {
				
				GridPane tile = new GridPane();
				setTile(tile, mapRow, mapCol, false);
				
				int[] curCoordinates = {mapRow, mapCol};
				
				boolean isMove = false;
				for (int[] coord : validMoves) {
					if (coord[0] == curCoordinates[0] && coord[1] == curCoordinates[1]) {
						isMove = true;
					}
				}
				
				if (isMove) {
					
					tile.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5), Insets.EMPTY)));
					
					final int toRow = mapRow;
					final int toCol = mapCol;
					
					EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				        @Override 
				        public void handle(MouseEvent e) { 
				        	controller.move(row, col, toRow, toCol);
				        } 
				    };  
				    
				    tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				    
				} else {
					EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
				        @Override 
				        public void handle(MouseEvent e) { 
				        	setBoard();
				        }
				    };  
				    
				    tile.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
				}
				
				map.add(tile, mapCol, mapRow);
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setBoard();
		if (controller.isOver()) {
			if (controller.getWinner() == Team.HUMAN) {
				
				mainView.displayNextLevelMenu(levelFileName);
		        
			} else {
				
				mainView.displayRetryMenu(levelFileName);
			}
		}
		hasRecentSave = false;
	}
	
	private void displayMessage(String message) {
		this.message.setText(message);
	}
	
	public int[] getMapDimensions() {
		int[] dimensions = {mapWidthPixel, mapHeightPixel};
		return dimensions;
	}
	
	public void setHasRecentSave() {
		hasRecentSave = true;
	}
	
	public boolean hasRecentSave() {
		return hasRecentSave;
	}

}
