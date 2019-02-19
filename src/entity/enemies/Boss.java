package entity.enemies;

// Imports

// Images
import java.awt.Graphics2D;

// Other Class Imports
import TileMap.TileMap;
import entity.*;

public class Boss extends Enemy{

	public Boss(TileMap tm) {
		
		// Calls the tileMap constructor
		super(tm);

		// Dimensions
		width = 64;
		height = 64;
		
		// Collision dimensions
		cwidth = 148;
		cheight = 326;
		
		// Health of the enemy
		health = maxHealth = 1;
		damage = 1;
				
	}
	
	private void getNextPosition() {
		
		
	}
		
	
	public void update() {
		
		// Gets position
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		
		// Sets the position
		setPosition(xtemp, ytemp);
		
	}
	
	// Draw method
	public void draw(Graphics2D g) {
		
		/*if (offScreen()) {
			
			return;
			
		}*/
		
		// Sets the position relative to the map
		setMapPosition();
		
		// Calls the Enemy draw method
		super.draw(g);
		
		
	}
	
}
