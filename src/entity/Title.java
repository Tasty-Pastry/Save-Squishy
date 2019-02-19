/* Variable Dictionary
 * 
 * count: Keeps track of how long the animation has played for
 * done: Holds if the animation has finished
 * 
 * x: x coord of animation
 * y: y coord of animation
 * dx: acelleration in the x plane
 * 
 * width: width of animation
 * 
 */

package entity;

// Imports

//Images
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// Other Class Imports
import mainWindow.MainGame;

public class Title {
	
	public BufferedImage image;
	
	// Animation
	public int count;
	private boolean done;
	private boolean remove;
	private double dx;
	
	// Dimension
	private double x;
	private double y;
	private int width;
	
	// Constructor
	public Title(String s) {
		
		// Loads image in
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(s));
			width = image.getWidth();
			x = -width;
			done = false;
			
		} catch(Exception e) {
			
			e.printStackTrace(); // Prints errors
			
		}
		
	}
	
	
	public Title(BufferedImage image) {
		
		this.image = image; // Sets the specific instance of the Title to the image
		width = image.getWidth(); // Gets the wdith
		x = -width; 
		done = false;
		
	}
	
	// Sets the y coord
	public void sety (double y) {
		
		this.y = y; 
		
	}
	
	// When called, begins playing the animation
	public void begin() {
		
		dx = 10;
		
	}
	
	// Returns the remove variable
	public boolean shouldRemove() { 
		
		return remove; 
		
	}
	
	// Update
	public void update() {
		
		// If the animation isn't done playing, move the title
		if (!done) {
			
			if (x >= (MainGame.WIDTH - width) / 2) {
				
				x = (MainGame.WIDTH - width) / 2;
				
				count++;
				
				// Animation is done if count is greater than 120
				if(count >= 120) {
					
					done = true;
					
				}
				
			} else {
				
				x += dx;
				
			}
			
		} else {
			
			x += dx;
			
			// If the image is past the width of MainGame, remove the image
			if (x > MainGame.WIDTH)  {
				
				remove = true;
				
			}
			
		}
	}
	
	// Draw the image
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int) x, (int) y, null);
		
	}
	
}
