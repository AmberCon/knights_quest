import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import model.LevelEditorModel;
import onboard.Archer;
import onboard.BlockedSeeThroughTile;
import onboard.BlockedTile;
import onboard.Knight;
import onboard.OpenTile;
import onboard.Piece;
import onboard.Tile;

/**
 * Class for viewing and controlling the level editor
 * 
 * @author Ember Chan
 *
 */
public class LevelEditor {

	private StrategyGameView mainView;
	private Scene scene;
	private LevelEditorModel model;
	private BorderPane root;
	
	private static final Tile[] TILE_SAMPLES = new Tile[] {
		new OpenTile(), new BlockedTile(), new BlockedSeeThroughTile()
	};
	@SuppressWarnings("unchecked") //TODO fi this
	private static final Class<? extends Piece>[] PIECE_CLASSES = new Class[] {
		Knight.class, Archer.class
	};
	private static final String NULL_TILE_IMG_PATH = "assets/NullTile.png";
	
	/**
	 * Creates a new LevelEditor on the given scene
	 * @param scene - the scene to display the level editor on
	 */
	public LevelEditor(StrategyGameView mainView) {
		this.mainView = mainView;
		model = new LevelEditorModel();
		root = new BorderPane();
		this.scene = new Scene(root);
		initMenu();
		update();
		initTilesBar();
		mainView.stage.setScene(scene);
	}
	
	/**
	 * Updates the scene with the current state of the model
	 */
	private void update() {
		//Map is represented by a GridPane of StackPanes
		//Each stackpane has space for a tile image and a piece image
		
		//TODO Seperators between tiles?
		GridPane map = new GridPane();
		for (int row = 0; row < LevelEditorModel.SIZE; row++) {
			for(int col = 0; col < LevelEditorModel.SIZE; col++) {
				StackPane sp = new StackPane();
				Tile tile = model.getTile(row, col);
				
				//Add tile image
				if(tile == null) {
					sp.getChildren().add(new ImageView(NULL_TILE_IMG_PATH));
					map.add(sp, col, row);
					continue;
				}
				ImageView tileImg = new ImageView(tile.getImgPath());
				sp.getChildren().add(tileImg);
				
				//add piece image
				if(tile.getPiece()!= null) {
					ImageView pieceImg = new ImageView(tile.getPiece().getSpriteFileName());
					sp.getChildren().add(pieceImg);
				}
				
				map.add(sp, col, row);
			}
		}
		root.setCenter(map);
	}
	
	/**
	 * Initializes the menu to the top of root
	 */
	private void initMenu() {
		Menu menu = new Menu("Menu");
		
		//Save level button
		MenuItem saveLevel = new MenuItem("Save Level");
		saveLevel.setOnAction((event)->{}); //TODO
		menu.getItems().add(saveLevel);
		
		//Return to main menu button
		MenuItem returnToMainMenu = new MenuItem("Return to Main Menu");
		returnToMainMenu.setOnAction((event)->{mainView.returnToMainMenu();});
		menu.getItems().add(returnToMainMenu);
		
		root.setTop(new MenuBar(menu));
	}
	
	/**
	 * Initializes the tiles bar to the right of the screen
	 */
	private void initTilesBar() {
		//TODO Encapsulate with scrollpane if we get that many tiles
		HBox tilesBar = new HBox();
		
		tilesBar.getChildren().add(new ImageView(NULL_TILE_IMG_PATH));
		for(Tile t:TILE_SAMPLES) {
			tilesBar.getChildren().add(new ImageView(t.getImgPath()));
		}
		
		root.setBottom(tilesBar);
	}
}
