import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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
	
	/**
	 * Creates a new LevelEditor on the given scene
	 * @param scene - the scene to display the level editor on
	 */
	public LevelEditor(Scene scene) {
		this.scene = scene;
		model = new LevelEditorModel();
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
		//TODO what to do with the grid pane
	}
}
