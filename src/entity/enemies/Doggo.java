/* Variable Dictionary
 * 
 * sprites: Holds an array of sprites (dog walking)
 * 
 */

package entity.enemies;

// Imports

// Images
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// Other Class Imports
import TileMap.TileMap;
import entity.*;

public class Doggo extends Enemy{

	// Variables
	
	// Animation
	private BufferedImage[] sprites;
	
	// Constructor
	public Doggo(TileMap tm) {
		
		// Calls the tileMap constructor
		super(tm);
		
		// Physics
		moveSpeed = 0.5;
		maxSpeed = 0.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		// Dimensions
		width = 64;
		height = 64;
		cwidth = 19;
		cheight = 30;
		
		// Health & Damage
		health = maxHealth = 2;
		damage = 1;
		
		// Load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/DogSheetAddon1 (2).PNG"));
			
			sprites = new BufferedImage[8];
			
			for (int i = 0; i < sprites.length; i++) {
				
				sprites[i] = spritesheet.getSubimage(i * width, height, width, height);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace(); // Print errors
			
		}
		
		// Create a new instance of the animation class and set it to the sprites
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
		
		// Sets directions
		right = true;
		facingRight = true;
		
	}
	
	// Gets the position of the dog
	private void getNextPosition() {
		
		// If left, move left
		if (left) {
			
			dx = -moveSpeed;
			
		// If right, move right
		} else if (right) {
			
			dx = moveSpeed;
				
		} 
		
		// If the dog is falling, accellerate downwards
		if (falling) {
			
			dy += fallSpeed;
			
		}
		
	}
		
	// Update
	public void update() {
		
		// If flinching, and the timer is greater than 400, stop flinching
		if (flinching) {
			
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			
			if (elapsed > 400) {
				
				flinching = false;
				
			}
			
		}
		
		// Gets the next position and calculates collision
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		
		// If the dog is next to a ledge, turn around
		if(!bottomLeft) {
			
			left = false;
			right = true;
			facingRight = true;
			
		}
		
		if(!bottomRight) {
			
			left = true;
			right = false;
			facingRight = false;
			
		}
		
		// Set position
		setPosition(xtemp, ytemp);
		
		// If the dog collides with a wall, turn around
		if(dx == 0) {
			
			left = !left;
			right = !right;
			facingRight = !facingRight;
			
		}
		
		// Update animation
		animation.update();
		
	}
	
	// Draw 
	public void draw(Graphics2D g) {
		
		/*if (offScreen()) {
			
			return;
			
		}*/
		
		// Set position relative to the map
		setMapPosition();
		
		// Calls the enemy draw method
		super.draw(g);
		
		
	}
	
}
