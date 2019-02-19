/* Variable Dictionary
 * 
 * tileMap: Used to reference tileMap
 * tileSize: Holds how large the tile ix
 * xmap: x coord of tileMap
 * ymap: y coord of tileMap
 * 
 * x: x coord
 * y: y coord
 * dx: acceleration in the x plane
 * dy: acceleration in the y plane
 * 
 * width: width of the object
 * height: height of the object
 * 
 * cwidth: collision width of the object
 * cheight: collision height of the object
 * 
 * currRow: the current row the object is in
 * currCol: the current column the object is in
 * 
 * xdest: where the x coord of the object will be
 * ydest: where the y coord of the object will be
 * xtemp: temperary storage of the x coord
 * ytemp: temperary storage of the y cord
 * 
 * topLeft: boolean for collision
 * topRight: boolean for collision
 * bottomLeft: boolean for collision
 * bottomRight: boolean for collision
 * 
 * animation: Reference for the animation class
 * currentAction: Holds the curretn animation
 * previousAction: Holds the animation previously played
 * facingRight: Holds if the animation is facing left or right
 * 
 * left: Holds if the animation is doing left
 * right: Holds if the animation is going right
 * up: Holds if the animation is going up
 * down: Holds if the animation is going down
 * falling: Holds if the animation is falling 
 * 
 * moveSpeed: How fast the animation accelerates
 * maxSpeed: The max speed the animation can move
 * fallSpeed: How fast the animation accelerates downward
 * maxFallSpeed: The max speed the animation can fall
 * jumpStart: How fast the animation accelerates upward when jumping
 * stopJumpSpeed: How fast the animation deaccelerates when reaching the max jump height
 * 
 */

package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import TileMap.Tile;
import TileMap.TileMap;
import mainWindow.MainGame; 

public abstract class MapObject {

	// Variables
	
	// Map
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// Directions
	public double x;
	public double y;
	protected double dx;
	protected double dy;
	
	// Dimensions
	protected int width;
	protected int height;
	
	// Collision Dimensions
	protected int cwidth;
	protected int cheight;
	
	// Collision Variables
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// Animation 
	public Animation animation;
	public int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// Animation Move Direction
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// Physics Variables
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	// Constructor
	public MapObject(TileMap tm) {
		
		tileMap = tm; // Sets the tileMap to the size passed to the constructor
		tileSize = tm.getTileSize(); // Gets the tile size of the tiles
		
	}
	
	// Checks if two objects intersect
	public boolean intersect(MapObject o) {
		
		// Creates two new Rectangles
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle(); // Creates a new rectangle with the collision dimensions of the passed object
		return r1.intersects(r2); // Checks if they intersect
		
	}
	
	// Creates a new rectangle with the collision dimensions
	public Rectangle getRectangle() {
		
		return new Rectangle((int) x - cwidth, (int) y - cheight, cwidth, cheight);
		
	}
	
	public void calculateCorners(double x, double y) {
		
		// Calculates the tiles next to the passed tile 
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;
		
		// If any of the tiles are less than 0, or bottom/top tiles are greater than the window size, set the collisions to false 
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
			
		}
		
		// Get the type (Collideable, Non-collideable) of the tile, using the collisions 
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		// If topLeft, topRight, bottomLeft, or bottomRight are blocked, set them to true, false if not
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
		
	}
	
	// Checks if an object and tile collide
	public void checkTileMapCollision() {
		
		currCol = (int) x / tileSize; // Calculates the current column the object is in
		currRow = (int) y / tileSize; // Calculates the current row the object is in
		
		// Gets where the final position of the object will be
		xdest = x + dx; 
		ydest = y + dy;
		
		// Sets temporary variables
		xtemp = x;
		ytemp = y;
		
		// Calls the calculate corner method
		calculateCorners(x, ydest);
		
		// If the dy causes the object to collide with a top tile, set the dy to 0 and move them one pixel below the tile
		if (dy < 0) {
			
			if (topLeft || topRight) {
				
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
				
			} else {
				
				ytemp += dy;
				
			}
			
		}
		
		// If the dy causes the object to collide with a bottom tile, set the dy to 0 and move the object 1 pixel above the tile
		if (dy > 0) {
			
			if (bottomLeft || bottomRight) {
				
				dy = 0;
				falling = false; // Stops the object from falling
				ytemp = (currRow + 1) * tileSize - cheight / 2;
				
			} else {
				
				ytemp += dy;
				
			}
			
		}
		
		// Calculate corners for the x plane
		calculateCorners(xdest, y);
		
		// If the dx causes the object to collide with a tile on the left, set the dx to 0 and move the object 1 pixel to the right of the tile
		if (dx < 0) {
			
			if (topLeft || bottomLeft) {
				
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
				
			} else {
				
				xtemp += dx;
				
			}
			
		}
		
		// If the dx causes the object to collide with a tile on the right, set the dx to 0 and move the object 1 pixel to the left of the tile
		if (dx > 0) {
			
			if (topRight || bottomRight) {
				
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
				
			} else {
				
				xtemp += dx;
				
			}
		}
		
		// If the object is not falling, calculate the corners of the y plane and check if there is a tile 1 pixel below where the object is going to be
		if (!falling) {
			
			calculateCorners(x, ydest + 1);
			
			// Set falling to true if there is no tiles below the object
			if (!bottomLeft && !bottomRight) {
				
				falling = true;
				
			}
			
		}
		
	}

	// Returns x coord
	public int getx() {
		
		return (int) x;
		
	}
	
	// Returns y coord
	public int gety() {
		
		return (int) y;
		
	}
	
	// Returns width of the object
	public int getWidth() {
		
		return width;
		
	}
	
	// Returns height of the object
	public int getHeight() {
		
		return height;
		
	}
	
	// Returns the collision width of the object
	public int getCWidth() {
		
		return cwidth;
		
	}
	
	// Returns the collision height of the object
	public int getCHeight() {
		
		return cheight;
		
	}
	
	// Sets the position of the specific instance of the object on the map
	public void setPosition(double x, double y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	// Sets the acceleration of the specific instance of the object
	public void setVector(double dx, double dy) {
		
		this.dx = dx;
		this.dy = dy;
		
	}
	
	// Sets the xmap and ymap to the x and y coords
	public void setMapPosition() {
		
		xmap = tileMap.getx();
		ymap = tileMap.gety();
		
	}
	
	// Sets the left variable depending on the value passed
	public void setLeft(boolean b) {
		
		left = b;
		
	}
	
	// Sets the right variable depending on the value passed
	public void setRight(boolean b) {
		
		right = b;
		
	}
	
	// Sets the lup variable depending on the value passed
	public void setUp(boolean b) {
	
		up = b;
	
	}
	
	// Sets the down variable depending on the value passed
	public void setDown(boolean b) {
		
		down = b;
	
	}
	
	// Sets the jumping variable depending on the value passed
	public void setJumping(boolean b) {
		
		jumping = b;
		
	}
	
	// Returns if the object is offscreen
	public boolean offScreen() {
		
		return (x + xmap + width < 0 || x + xmap - width > MainGame.WIDTH || y + ymap + height < 0 || y + ymap - height > MainGame.HEIGHT);
		
	}
	
	// Draw 
	public void draw(Graphics2D g) {
		
		// If the object is facing right, draw the object in the original direction of the sprite
		if (facingRight) {
			
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
			
		} else {
			
			// If the object is facing to the left, flip the sprites over the horizontal axis and then draw
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2), -width, height, null);
			
		}
		
	}
	
}
