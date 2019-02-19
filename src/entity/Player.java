/* Variable Dictionary
 * 
 * health: health of player
 * maxHealth: The max amount of health the player can have
 * lives: The amount of lives the user has
 * shot: The amount of shots the player has
 * maxShot: The max amount of shots the player can have
 * dead: Holds if the player is dead or not
 * flinch: Holds if the player is flinching or not
 * flinchTimer: Holds how long the player has flinched for
 * knockBack: Holds if the player is getting knocked back
 * 
 * reset: Holds if the player class should be reset
 * 
 *  firing: Holds if the user is firing a shot
 *  shotCost: holds how much a shot costs
 *  shotDmg: Holds how much damage a shot can deal
 *  shots: Holds the sprites of the fireballs in an arraylist
 *  
 *  melee: Holds if the user is attacking with their melee attack or not
 *  meleeDmg: Holds how much damage the attack can deal
 *  meleeRng: Holds how far the melee attack can reach
 *  
 *  sprites: Holds an array of sprites
 *  numFrames: Holds how many frames there are for each animation in an array
 *  
 *  IDLE - DEAD: The variables the hold where the animation is located in the spritesheet and in numFrames
 * 
 * SFX: Holds audio clips
 * 
 */

package entity;

// Imports

// Other Class Imports
import TileMap.*;
import audio.AudioPlayer;
import gameState.Level1State;
import gameState.Organizer;
import mainWindow.MainGame;

// Utility Imports
import java.util.ArrayList;
import java.util.HashMap;

// Image Imports
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;

public class Player extends MapObject{

	// Player
	public int health;
	private int maxHealth;
	private int lives;
	public int shot;
	private int maxShot;
	public boolean dead;
	private boolean flinch;
	private long flinchTimer;
	private boolean knockback;
	
	// Class Reset Variable
	public boolean reset;
	
	// Fireball Variable
	public boolean firing;
	private int shotCost;
	private int shotDmg;
	private ArrayList<Shot> shots;
	
	// Melee Attack Variable
	public boolean melee;
	private int meleeDmg;
	private int meleeRng;
	
	// Animation Variables
	private ArrayList<BufferedImage[]> sprites;	
	private final int[] numFrames = {4, 8, 8, 2, 6, 16, 6, 0, 6, 0, 0, 10, 0, 0, 8};
	
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int SHOT = 8;
	private static final int MELEEROUNDKICK = 14;
	private static final int KNOCKBACK = 6;
	public static final int DEAD = 5;
	
	// Sound Variables
	private HashMap<String, AudioPlayer> SFX;
	
	// Constructor
	public Player(TileMap tm) {
		
		// Calls the tileMap constructor
		super(tm); 
		
		// Dimensions
		width = 64;
		height = 64;
		cwidth = 18;
		cheight = 30;
		
		// Physics 
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		// Animation
		facingRight = true;
		
		// Player
		health = maxHealth = 6;
		lives = 5;
		shot = 1500;
		maxShot = 1500;
		
		shotCost = 200;
		shotDmg = 5;
		shots = new ArrayList<Shot>();
		
		meleeDmg = 8;
		meleeRng = 20;
		
		// Load in sprites
		try {
			
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Bubbles.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			
			for (int i = 0; i < 15; i++) {
				
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				
				for (int j = 0; j < numFrames[i]; j++) {
					
					bi[j] = spriteSheet.getSubimage(j * width, i * height, width, height);
					
				}
				
				sprites.add(bi);
				
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace(); // Prints out errors
			
		}
		
			// Creates a new animation and sets the default as IDLE
			animation = new Animation();
			currentAction = IDLE;
			animation.setFrames(sprites.get(IDLE));
			animation.setDelay(100); 
			
			// Loads in SFX
			SFX = new HashMap<String, AudioPlayer>();
			SFX.put("FireballHit", new AudioPlayer("/SFX/Fireball.wav"));
			SFX.put("Hit", new AudioPlayer("/SFX/Hit 1.wav"));
			SFX.put("Jump", new AudioPlayer("/SFX/Jump2.wav"));
			SFX.put("Shot", new AudioPlayer("/SFX/Shoot.wav"));
			SFX.put("Dead", new AudioPlayer("/SFX/Death.wav"));
		
	}
	
	// Returns player health
	public int getHealth() {
		
		return health;
		
	}
	
	// Return if the player class needs to be reset
	public boolean getReset() {
		
		return reset;
		
	}
	
	// Returns player maxHealth
	public int getMaxHealth() {
		
		return maxHealth;
		
	}
	
	// Returns the shot variable
	public int getShot() {
		
		return shot; 
		
	}
	
	// Returns the maxShot variable
	public int getMaxShot() {
		
		return maxShot;
		
	}
	
	// Sets the firing boolean
	public void setFiring() {
		
		// If the player is getting knocked back, the player can't fire
		if (knockback) {
			
			return;
			
		}
		
		firing = true;
		
	}

	// Stop the player from firing
	public void stopFiring() {
		
		firing = false;
		
	}
	
	// Sets melee boolean
	public void setMelee() {
		
		// If the player is getting knocked back, the player can't attack
		if (knockback) {
			
			return;
			
		}
		
		melee = true;
		
	}
	
	// Stops the player from attacking
	public void stopMelee() {
		
		melee = false;
		
	}
	
	// Sets the maxHealth
	public void setHealth(int h) {
		
		maxHealth = h;
		
	}
	
	// Returns the lives variable
	public int getLives() {
		
		return lives;
		
	}
	
	// Check if the player has hit an enemy
	public void checkAttack(ArrayList<Enemy> enemies) {
		
		for (int i = 0; i < enemies.size(); i++) {
			
			// Gets the enemy in the array list
			Enemy e = enemies.get(i);
			
			// If attacking, and if the player is facing right or left, if the attack collides with the enemy, hit the enemy
			if (melee) {
				
				if (facingRight) {
					
					if (e.getx() > x && e.getx() < x + meleeRng && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
						
						e.hit(meleeDmg);
						
					}
					
				} else {
					
					if (e.getx() < x && e.getx() > x - meleeRng && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
						
						e.hit(meleeDmg);
						
					}
					
				}
				
			}
			
			// If a fireball intersects with an enemy, hit the enemy and play the Fireball hit animation
			for (int j = 0; j < shots.size(); j++) {
				
				if (shots.get(j).intersect(e)) {
					
					e.hit(shotDmg);
					shots.get(j).setHit();
					break;
					
				}
				
			}
			
			if (intersect(e)) {
				
				hit(e.getDamage()); // If the player intersects with the enemy itself, deal damage to the player
				
			}
			
		}
		
	}
	
	// Hit method
	public void hit(int damage) {
		
		// If the player is hit, but is already flinching, don't deal damage to the player
		if (flinch) {
			
			return;
		
		}
		
		// Plays the hit animation
		SFX.get("Hit").play();
		
		// Deals damage
		health -= damage;
		
		// If the health is less than 0, set it to 0 (Error checking)
		if (health < 0) {
			
			health = 0;
			
		}
		
		// If health is 0 and the animation played is not dead, subtract a life and set dead to true
		if (health == 0 && currentAction != DEAD) {
			
			if (lives > 0) lives--;
			dead = true;
			
		}
		
		// If the player is facing right, set the dx to -1, else if the player is facing to the left, set the dx to 1 (knockback)
		if(facingRight)  {
			
			dx = -1;
			
		} else {
			
			dx = 1;
			
		}
		
		//Knock the player upwards
		dy = -3;
		
		knockback = true;
		falling = true;
		jumping = false;
		flinch = true;
		flinchTimer = System.nanoTime(); // Starts the timer
		
	}
	
	// Calculates the next position of the player
	private void getNextPosition() {
		
		// Sets fallspeed to true if the user is getting knockedback
		if(knockback && currentAction != DEAD) {
			
			dy += fallSpeed * 2;
			
			if (!falling) {
				
				knockback = false;
				
			}
			
			return;
		}
		
		// Moves left
		if (left) {
			
			dx -= moveSpeed;
			
			// Caps the acceleration
			if (dx < -maxSpeed) {
				
				dx = -maxSpeed;
				
			}
			
		// Moves right
		} else if (right) {
			
			dx += moveSpeed;
			
			// Caps the acceleration
			if (dx > maxSpeed) {
				
				dx = maxSpeed;
				
			}
			
		} else {  // If the user is neither moving right or left, deaccelerate
			
			if (dx > 0) {
				
				dx -= stopSpeed;
				
				if (dx < 0) {
					
					dx = 0;
					
				}
				
		} else if (dx < 0) {
			
			dx += stopSpeed;
			
			if (dx > 0) {
				
				dx = 0;
				
			}
			
		}
		
	}
		
		// The user can't move while attacking on the ground
		if ((currentAction == MELEEROUNDKICK || currentAction == SHOT) && !(jumping || falling)) {
			
			dx = 0;
			
		}
		
		// Plays the Jump SFX and begins jumping
		if (jumping && !falling) {
			
			SFX.get("Jump").play();
			
			dy = jumpStart;
			
			falling = true;
			
		}
		
		// Makes the user fall
		if (falling) {
			
			dy += fallSpeed;
			
			// Sets jumping to false when the player lets go of the jump key
			if (dy > 0) {
				
				jumping = false;
				
			}
			
			// Deaccelerates in the air
			if (dy < 0 && !jumping) {
				
				dy += stopJumpSpeed;
				
			}
			
			// Caps the fall speed acceleration
			if (dy > maxFallSpeed) {
				
				dy = maxFallSpeed;
				
			}
			
		}
		
	}
	
	// Update function
	public void update() {
		
		// Calculates position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// Check if the player falls of the map
		if (currentAction != DEAD && y >= MainGame.HEIGHT + 200) {
			
			health = 0;
			dead = true;
			lives--;
			
		}
		
		// If the animation has already played once, don't play it anymore
		if (currentAction == DEAD && animation.hasPlayedOnce()) {
			
			dead = false;
			knockback = false; 
			reset = true; // Resets the player 
			
		}
		
		// If the animation has already played once, don't play it anymore
		if ((currentAction == MELEEROUNDKICK) && animation.hasPlayedOnce()) {
			
			melee = false;
			
		}
		
		// If the animation has already played once, don't play it anymore
		if ((currentAction == SHOT) && animation.hasPlayedOnce()) {
			
			firing = false;
			
		}
		
		// Adds 1 to the shot amount
		shot++;
		
		// Caps the shot amount
		if (shot > maxShot) {
			
			shot = maxShot;
			
		}
		
		// If firing, add a new shot animation to the array list
		if (firing && currentAction != SHOT && currentAction != KNOCKBACK && currentAction != DEAD) {
			
			if (shot > shotCost) {
				
				shot -= shotCost; // Subtract the shotCost
				Shot sh = new Shot(tileMap, facingRight); // Creates a new instance of the fireball
				sh.setPosition(x, y); // Sets position
				shots.add(sh); // Adds to array list
				
			} 
			
		}
		
		// Player stops flinching after hit when 1 second has passed
		if (flinch && !knockback) {
			
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			
			if (elapsed > 1000) {
				
				flinch = false;
				
			}
			
		}
		
		// Updates the shot animation and removes from the array list when the shot needs to be removed
		for (int i = 0; i < shots.size(); i++) {
			
			shots.get(i).update();
			
			if (shots.get(i).shouldRemove()) {
				
				shots.remove(i);
				i--;
				
			}
			
		}
		
		// Sets and plays animation
		
		 if (dead) {
			 
			 if (currentAction != DEAD) {
				 
				 SFX.get("Dead").play();
				 currentAction = DEAD;
				 animation.setFrames(sprites.get(DEAD));
				 animation.setDelay(50);
				 width = 64;
				 
			 }
			 
		 } else if (knockback) {
			
			if (currentAction != KNOCKBACK) {
				
				currentAction = KNOCKBACK;
				animation.setFrames(sprites.get(KNOCKBACK));
				animation.setDelay(30);
				width = 64;
				
			}
			
			
		} else if (melee) {
			
			if (currentAction != MELEEROUNDKICK) {
				
				currentAction = MELEEROUNDKICK;
				animation.setFrames(sprites.get(MELEEROUNDKICK));
				animation.setDelay(50);
				width = 64;
				
			}
				
			} else if (firing) {
				
				if (currentAction != SHOT) {
					
					SFX.get("Shot").play();
					currentAction = SHOT;
					animation.setFrames(sprites.get(SHOT));
					animation.setDelay(50);
					width = 64;
					
				}
				
			} else if (dy > 0) {
				
				if (currentAction != FALLING) {
					
					currentAction = FALLING;
					animation.setFrames(sprites.get(FALLING));
					animation.setDelay(100);
					width = 64;
					
				}
				
			} else if (dy < 0) {
				
				if (currentAction != JUMPING) {

					currentAction = JUMPING;
					animation.setFrames(sprites.get(JUMPING));
					animation.setDelay(50);
					width = 64;

				}
				
			} else if (left || right) {
				
			  if (currentAction != WALKING) {
				  
				  currentAction = WALKING;
				  animation.setFrames(sprites.get(WALKING));
				  animation.setDelay(50);
				  width = 64;
				  
			  }
				
			} else if (currentAction != IDLE) {
					
					currentAction = IDLE;
					animation.setFrames(sprites.get(IDLE));
					animation.setDelay(100);
					width = 64;
				
			} 
			
		 	// Updates the animations
			animation.update();
			
			// Can't turn while firing or attacking
			if (currentAction != MELEEROUNDKICK && currentAction != SHOT) {
				
				if (right) {
					
					facingRight = true;
					
				}
				
				if (left) {
					
					facingRight = false;
					
				}
				
			}
			
		}
	
	// Draw
	public void draw(Graphics2D g) {
		
		// Sets the map position
		setMapPosition();
		
		// Draws the fireballs
		for (int i = 0; i < shots.size(); i++) {
			
			shots.get(i).draw(g);
			
		}
		
		// Draw the player periodically if flinching
		if (flinch && !knockback && currentAction != DEAD) {
			
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			
			if (elapsed / 100 % 2 == 0) {
				
				return;
				
			}
			
		}
		
		// Call the MapObject draw method
		super.draw(g);
		
	}
	
}
