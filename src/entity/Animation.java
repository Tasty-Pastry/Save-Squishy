/* Variable Dictionary
 * 
 * frames: Holds an array of images for the animation
 * currentFrame: Stores where the animation is at in its cycle (the current frame it's drawing/updating)
 * playedOnce: Used to check if the animation has played once already
 * 
 * startTime: When called, stores the time at that moment
 * delay: Holds the amount of delay in between frames
 * elapsed: Stores the amount of time elapsed when called
 * 
 */

package entity;

// Imports
import java.awt.image.BufferedImage;

public class Animation {
	
	// Variables

	// Image Animation
	private BufferedImage[] frames;
	private int currentFrame;
	public boolean playedOnce;
	
	// Timer
	private long startTime;
	private long delay;
	
	// Constructor
	public Animation() {
		
		playedOnce = false;
		
	}
	
	// Sets the amount of frames
	public void setFrames(BufferedImage[] frames) {
		
		// Sets the frames
		this.frames = frames; 
		currentFrame = 0; 
		
		// Starts the timer
		startTime = System.nanoTime();
		playedOnce = false;
		
	}
	
	// Gets the animation delay
	public void setDelay(long d) {
		
		delay = d;
		
	}
	
	// Sets the currentFrame
	public void setFrame(int i) {
		
		currentFrame = i;
		
	}
	
	// Updates the animation
	public void update() {
		
		// Checks if the delay is less than 0 and returns if true, error checking
		if (delay == -1) {
			
			return;
			
		}
		
		// Ends the timer and calculate time elapsed
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		
		// If the time elapsed is more than the delay, advance the currentFrame and start the timer again
		if (elapsed > delay) {
			
			currentFrame++;
			startTime = System.nanoTime();
					
		}
		
		// If the currentFrame is equal to the last frame, reset the currentFrames and set playedOnce to true
		if (currentFrame == frames.length) {
			
			currentFrame = 0;
			playedOnce = true;
			
		}
		
	}
	
	// Gets the currentFrame
	public int getFrame() {
		
		return currentFrame;
		
	}
	
	// Gets the currentFrame image
	public BufferedImage getImage() {
		
		return frames[currentFrame];
		
	}
	
	// Returns if the animation has played once already
	public boolean hasPlayedOnce() {
		
		return playedOnce;
		
	}
	
}
