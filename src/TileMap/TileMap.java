package TileMap;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import mainWindow.MainGame;

import java.io.*;

public class TileMap {

	//Position
	private double x;
	private double y;
	
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double smoothScroll;
	
	// TileMap Dimensions
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	private int numTilesAcross;
	
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	// Images
	private BufferedImage tileset;
	private Tile[][] tiles; 
	
	// Calculates the number of cols and rows (Visible on screen)
	public TileMap(int tileSize) {
		
		this.tileSize = tileSize;
		numRowsToDraw = MainGame.HEIGHT / tileSize + 2;
		numColsToDraw = MainGame.WIDTH / tileSize + 2;
		smoothScroll = 0.07;
		
	}
	
	// Returns the numRows variable
	public int getNumRows() { 
		
		return numRows; 
		
	}
	
	// Returns the numCols variable
	public int getNumCols() { 
		
		return numCols; 
		
	}
	
	// Load in tiles
	public void loadTiles(String s) {
		
		try {
			
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross]; // Representation of the tileSet
			
			BufferedImage subimage;
			
			for (int col = 0; col < numTilesAcross; col++) {
				
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL); // Normal tiles
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED); // Collision tiles
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace(); // Print errors
			
		}
		
	}
	
	public void loadMap(String s) {
		
		// Load in map
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			// Calculate the actuals number of cols and rows (Whole map)
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = MainGame.WIDTH - width;
			xmax = 0;
			ymin = MainGame.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+"; // White space
			
			// Loads in seperate tiles into the map
			for (int row = 0; row < numRows; row++) {
				
				String line = br.readLine();
				String[] tokens = line.split(delims);
				
				for (int col = 0; col < numCols; col++) {
					
					map[row][col] = Integer.parseInt(tokens[col]); 
					
				}
				
			}
			
		} catch (Exception e){
			
			e.printStackTrace(); // Print errors
			
		}
		
	}
	
	// Return tileSize variable
	public int getTileSize() {
		
		return tileSize;
		
	}
	
	// Return x variable
	public double getx() {
	
		return x;
		
	}
	
	// Return y variable
	public double gety() {
		
		return y;
		
	}
	
	// Return width variable
	public int getWidth() {
	
		return width;
		
	}
	
	// Return height varaible
	public int getHeight() {
		
		return height;
		
	}
	
	public int getType(int row, int col) {
		
		// Calculates whether the tile is blocked or not
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
		
	}
	
	public void setPosition(double x, double y) {
		
		// Sets the instance position
		this.x += (x - this.x) * smoothScroll;
		this.y += (y - this.y) * smoothScroll;
		
		fixBounds();
		
		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;
		
	}
	
	private void fixBounds() {
		
		// Prevents the drawing from going offscreen
		
		if (x < xmin) {
			
			x = xmin;
			
		}
		
		if (y < ymin) {
			
			y = ymin;
			
		}
		
		if (x > xmax) {
	
			x = xmax;
	
		}
		
		if (y > ymax) {
			
			y = ymax;
			
		}

		
	}
	
	// Sets the smoothScroll variable
	public void setSmoothScroll(double d) { 
		
		smoothScroll = d; 
		
	}
	
	public void draw(Graphics2D g) {
		
		// Draws cols and then rows
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if (row >= numRows) {
				
				break;
				
			}
			
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if (col >= numCols) {
					
					break;
					
				}
				
				if (map[row][col] == 0) {
					
					continue;
					
				}
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				// Draw rows and cols
				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
				
			}
			
		}
		
	}
	
}
