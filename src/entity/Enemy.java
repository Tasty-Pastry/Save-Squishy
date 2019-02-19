/* Variable Dictionary
 * 
 * health: Stores enemy health
 * maxHealth: Stores the max health the enemy can have
 * dead: Stores whether the enemy is dead or isn't
 * damage: Stores the amount of damage the enemy can deal
 * 
 * flinching: Stores whether the enemy is flinching or not
 * flinchTimer: Stores the time when it's called
 * 
 */

package entity;

// Imports
import TileMap.TileMap;

public class Enemy extends MapObject{

	// Variables
	
	// Health
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	// Timers
	protected boolean flinching;
	protected long flinchTimer;
	
	// Constructor
	public Enemy(TileMap tm) {
		
		super(tm); // Calls the tileMap constructor
		
	}
	
	// Returns the value of the dead variable
	public boolean isDead() {
		
		return dead;
		
	}
	
	// Returns the damage value
	public int getDamage() {
		
		return damage;
		
	}
	
	// Calculates damage
	public void hit(int damage) {
		
		// If the enemy is dead or is flinching, dont calculate damage
		if (dead || flinching) {
			
			return;
			
		}
		
		// Subtract damage from health
		health -= damage;
		
		// Sets health to 0 if its less than 0, error checking
		if (health < 0) {
			
			health = 0;
			
		}
		
		// If health is zero, enemy is dead
		if (health == 0) {
			
			dead = true;
			
		}
		
		// Once hit, enemy starts flinching and a timer begins
		flinching = true;
		flinchTimer = System.nanoTime();
		
	}
	
	public void update() {
		
		
	}
	
}
