import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.LevelEditorModel;
import onboard.Tile;

/**
 * Class for viewing and controlling the level editor
 * 
 * @author Ember Chan
 *
 */
public class LevelEditor {

	private Scene scene;
	private LevelEditorModel model;
	private BorderPane root;
	
	/**
	 * Creates a new LevelEditor on the given scene
	 * @param scene - the scene to display the level editor on
	 */
	public LevelEditor(Scene scene) {
		this.scene = scene;
		model = new LevelEditorModel();
		root = new BorderPane();
		scene.setRoot(root);
	}
	
	public void useLevelEditor() { //should return once user is finished and backs out of menu
		
	}
	
	/**
	 * Updates the scene with the current state of the model
	 */
	private void update() {
		//Map is represented by a GridPane of StackPanes
		//Each stackpane has space for a tile image and a piece image
		
		GridPane map = new GridPane();
		for (int row = 0; row < LevelEditorModel.SIZE; row++) {
			for(int col = 0; col < LevelEditorModel.SIZE; col++) {
				StackPane sp = new StackPane();
				Tile tile = model.getTile(row, col);
				
				//Add tile image
				if(tile == null) {
					sp.getChildren().add(new ImageView("assets/NullTile.png"));
					continue;
				}
				ImageView tileImg = new ImageView(tile.imgPath);
				sp.getChildren().add(tileImg);
				
				//add piece image
				if(tile.getPiece()!= null) {
					ImageView pieceImg = new ImageView(tile.getPiece().getSpriteFileName());
					sp.getChildren().add(pieceImg);
				}
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
		returnToMainMenu.setOnAction((event)->{});//TODO
		menu.getItems().add(returnToMainMenu);
		
		root.setTop(new MenuBar(menu));
	}
	
	/**
	 * Initializes the tiles bar to the right of the screen
	 */
	private void initTilesBar() {
		//TODO Encapsulate with scrollpane if we get that many tiles
	}
}
