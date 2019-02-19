package TileMap;

// Imports
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import mainWindow.MainGame;

public class Background {

	// Images
	private BufferedImage image;
	
	// Movement and Position
	private double x;
	private double y;
	private double dx;
	private double dy;
	private boolean hasPlayedOnce;
	private double moveScale;
	
	// Load image
	public Background(String s, double ms) {
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
			
		} 
		catch (Exception e) {
			
			e.printStackTrace(); // Print errors
			
		}
		
	}
	
	// Sets the instance of the background's x and y 
	public void setPosition(double x, double y) {
		
		this.x = (x * moveScale) % MainGame.WIDTH;	
		this.y = (y * moveScale) % MainGame.HEIGHT;
		
	}
	
	// Sets the instance of the backgrounds dx and dy
	public void setVector(double dx, double dy) {
		
		this.dx = dx;
		this.dy = dy;
		
	}
	
	public void update() {
	
		// Positioning
		x += dx % MainGame.WIDTH;	
		y += dy;
		
		// Starts the image from the beginning if it has moved off the screen (Wraparound)
		if (x < -320) {
			
			x = 0;
			hasPlayedOnce = true;
			
		}
		
		
	}
	
	// Returns x variable
	public double getx() {
		
		return x;
		
	}
	
	// Returns playedOnce variable
	public boolean playedOnce() {
		
		return hasPlayedOnce;
		
	}
	
	public void draw(Graphics2D g) {
		
		// Draws the image
		g.drawImage(image, (int) x, (int) y, null);
		
		
		// Draws to the right if x is less than 0, and draws to the left if x is greater than 0
		if (x < 0) {
			
			g.drawImage(image, (int) x + MainGame.WIDTH, (int) y, null);
			
		}
		
		if (x > 0) {
			
			g.drawImage(image, (int) x - MainGame.WIDTH, (int) y, null);
			
		}
		
	}
	
}
