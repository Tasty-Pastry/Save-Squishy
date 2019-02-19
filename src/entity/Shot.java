/* Variable Dictionary:
 * 
 * hit: Holds whether or not the fireball hit something
 * 
 * sprites: Holds an array of images (fireball sprites)
 * hitSprites: Holds an array of images (fireball hit sprites)
 * 
 * start: Start timer
 * end: end timer
 * 
 * SFX: Holds SFX audio clips
 * 
 */

package entity;

// Imports

// Different Class Imports
import TileMap.TileMap;
import audio.AudioPlayer;

// Images
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;

// Utilities
import java.util.HashMap;

public class Shot extends MapObject{

	private boolean hit;
	private boolean remove; 
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private long start;
	private long end;
	private HashMap<String, AudioPlayer> SFX;
	
	public Shot(TileMap tm, boolean right) {
		
		// Calls the tileMap constructor
		super(tm);
		
		// Sets the fireball facing right
		facingRight = right;
		
		// How fast the fireball moves
		moveSpeed = 3.8;
		
		// Accelerates the fireball 
		if(right) {
			
			dx += moveSpeed;
			
		} else {
				
			dx -= moveSpeed;
		}
		
		// Dimensions
		width = 32;
		height = 32;
		cwidth = 27;
		cheight = 11;
		
		// Loads sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/shots.gif"));
			
			sprites  = new BufferedImage[4];
			hitSprites = new BufferedImage[3];
			
			for (int i = 0; i < sprites.length; i++) {
				
				if (i < sprites.length) {
					
					sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
					
				} 
				
			}
			
			for (int i = 0; i < hitSprites.length; i++) {
					
				if (i < hitSprites.length) {
				
					hitSprites[i] = spritesheet.getSubimage(i * width, height, width, height);
					
				}
				
			}
			
			// Starts timer
			start = System.currentTimeMillis();
		
			// Creates a new instance of the animation class
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
			// Sets audio
			SFX = new HashMap<String, AudioPlayer>();
			SFX.put("Fireball Hit", new AudioPlayer("/SFX/Fireball.wav"));
			
		} catch (Exception e) {
			
			e.printStackTrace(); // Prints errors
			
		}
		
	}
	
	public void setHit() {
		
		// If the fireball already hit, return
		if (hit) {
			
			return;
			
		}
		
		// Play SFX
		SFX.get("Fireball Hit").play();
		
		// Set hit and play animation
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		
		// Remove any accleration
		dx = 0;
		
	}
	
	// Return the remove variable
	public boolean shouldRemove() {
		
		return remove;
		
	}
	
	// Update
	public void update() {
		
		// Updates map position
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// Sets hit if fireball collides or the timer is greater than 250 milliseconds
		if ((dx == 0 && !hit) || (end - start) > 250) {
			
			setHit();
			
		}
		
		// Updates animation
		animation.update();
		
		// Ends timer
		end = System.currentTimeMillis();
		
		// Removes sprites if hit and animation has already played
		if (hit && animation.hasPlayedOnce()) {
			
			remove = true;
			
		}
		
	}
	
	// Draw
	public void draw(Graphics2D g) {
		
		// Sets the map position
		setMapPosition();
		
		// Draw the sprite facing to the right if its facing right and flips the sprite over the horizontal axis if facing to the left
		if (facingRight) {
			
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2) + 10, (int) (y + ymap - (height - 15) / 2), null);
			
		} else {
			
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width) - 10, (int) (y + ymap - (height - 15) / 2), -width, height, null);
			
		}
		
	}
	
}
