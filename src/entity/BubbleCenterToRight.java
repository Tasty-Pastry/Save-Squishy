/*Vairiable Dictionary
 * 
 * sprites: Holds an array of sprites
 * remove: Stores whether the animation has played once or not
 * 
 */

package entity;

// Imports

// Images & Graphics
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// Other Classes
import TileMap.TileMap;

public class BubbleCenterToRight extends MapObject{

// Variables
	
private BufferedImage[] sprites;
private boolean remove;
	
	// Constructor
	public BubbleCenterToRight(TileMap tm) {
		
		super(tm); // Calls the tileMap constructor
		
		// Dimensions of the sprite
		width = 320;
		height = 240;
		
		// Sets the direction of the sprite and if it needs to be removed
		facingRight = true;
		remove = false;
	
		try {
			
			// Loads the sprites in
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Bubble Center To Right.png"));
			
			// Sets the length of the array (Amount of frames)
			sprites = new BufferedImage[66];
			
			for (int i = 0; i < sprites.length; i++) {
				
				// Loads the individual sprites into the sprites array
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace(); // Prints errors
			
		}
		
		animation = new Animation(); // Creates a new instance of the animation
		animation.setFrames(sprites); // Sets the sprites
		animation.setDelay(60); // Sets the delay in between sprites
		
	}
	
	// Updates the animation
	public void update() {
		
		animation.update(); // Updates the animation
		
		// If the animation has played once, remove the sprites
		if (animation.hasPlayedOnce()) {
			
			remove = true;
			
		}
		
	}
	
	// Returns the remove variable
	public boolean shouldRemove() {
		
		return remove;
		
	}
		
	// Draws the animation
	public void draw(Graphics2D g) {
		
		setMapPosition(); // Sets the map position relative to the tile map
		
		super.draw(g); // Calls the MapObject draw function
		
	}
	
}
