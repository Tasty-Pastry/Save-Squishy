/* Variable Dictionary
 * 
 * x: x coord
 * y: y coord
 * xmap: x coord of the tileMap
 * ymap: y coord of the tileMap
 * 
 * width: width of sprite
 * length: length of sprite
 * 
 * animation: Used to reference the animation class
 * sprites: array of sprites
 * remove: Boolean to hold whether or not the animation should be removed
 * 
 */

package entity;

// Imports

// Images
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Explosion {

	// Variables
	
	// Directions
	private int x;
	private int y;
	private int xmap;
	private int ymap;
	
	// Dimensions
	private int width;
	private int height;
	
	// Images & Animation
	private Animation animation;
	private BufferedImage[] sprites;
	private boolean remove;
	
	// Constructor
	public Explosion(int x, int y) {
		
		this.x = x; // Sets x to the specific instance x of Explosion when called
		this.y = y; // Sets y to the specific instance y of Explosion when called
		
		// Dimensions
		width = 30;
		height = 30;
		
		try {
			
			 // Loads images in
			 BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion.gif"));
			 
			 // Sets the length of the buffered image array to the number of frames
			 sprites = new BufferedImage[6];
			 
			 // Loads indiviudal frames into the array
			 for (int i = 0; i < sprites.length; i++) {
				 
				 sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
				 
			 }
					 
		} catch (Exception e) {
			
			e.printStackTrace(); // Prints errors
			
		}
		
		// Creates a new instance of animation
		animation = new Animation();
		animation.setFrames(sprites); // Sets the sprites
		animation.setDelay(70); // Sets the delay
		
	}
	
	// Update function
	public void update() {
		
		animation.update(); // Updates the animation
		
		// If the animation played once, set remove to true
		if (animation.hasPlayedOnce()) {
			
			remove = true;
			
		}
		
	}
	
	// Returns the remove variable
	public boolean shouldRemove() {
		
		return remove;
		
	}
	
	// Sets the map positon
	public void setMapPosition(int x, int y) {
		
		xmap = x; 
		ymap = y;
		
	}
	
	// Draws the image
	public void draw(Graphics2D g) {
		
		g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
		
	}
}
