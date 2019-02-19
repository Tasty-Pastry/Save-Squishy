package TileMap;

import java.awt.image.BufferedImage;

public class Tile {

	// Images
	private BufferedImage image; 
	
	// Tile Type
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	private int type;
	
	// Loads the tiles in
	public Tile(BufferedImage image, int type) {
		
		this.image = image;
		this.type = type;
		
	}
	
	// Returns image variable
	public BufferedImage getImage() {
		
		return image;
		
	}
	
	// Return the type variable
	public int getType() {
		
		return type;
		
	}
	
}
