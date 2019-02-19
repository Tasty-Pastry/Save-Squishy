package mainWindow;

// Imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import gameState.Organizer;

@SuppressWarnings("serial")
public class MainGame extends JPanel implements Runnable, KeyListener{ // Extended the JPanel to Window so that Window is a child class of JPanel, 
	
	// Dimension variables
	public static final int WIDTH = 320; 
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	// Game Running Variables
	private Thread thread; // Creates a new Thread 
	private boolean isRunning; // To check if the game is running or not
	private int FPS = 60; // Variable to hold the amount of FPS the game should run at
	private long goalTime = 1000 / FPS; // The optimal time for the game to run at
	
	// Image variables
	private BufferedImage image;
	private Graphics2D g; 
	
	// Variables from different classes
	private Organizer o; 
	
	// Constructor
	public MainGame () {
		
		super(); // Calls the superclass constructor (In this case it's the JPanel)
		setFocusable(true); // Sets the focusable state of this Component to the specified value. This value overrides the Component's default focusability.
		requestFocus(); // Requests that this Component gets the input focus. 
		
	}
	
	public void addNotify() {
		
		super.addNotify(); // Calls the parent class
		
		if (thread == null) { // Checks if the thread has not been initialized
			
			thread = new Thread(this); // Creates a new thread
			addKeyListener(this); // Adds the specified key listener to receive key events from this component
			thread.start(); // Starts the thread
			
		}
		
	}
	
	// Initializes variables
	private void varInit() { 
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // Creates a new BufferedImage
		g = image.createGraphics(); // Creates a Graphics2D, which can be used to draw into this BufferedImage.
		isRunning  = true; // Sets running to true
		o = new Organizer(); // Creates a new instance of the Organizer class
		
	}
	
	// Runs the program
	public void run() {
		
		varInit();
		
		// Creates 2 timers
		long start;
		long wait;
		
		while(isRunning) {
			
			start = System.nanoTime(); // Gets the time when started
			
			// Draws onto the screen
			update();
			draw();
			
			wait = goalTime /*Milliseconds*/ - (System.nanoTime() - start) /*Nanoseconds*/ / 1000000; // Calculates the amount of time needed to wait to keep the framerate at a steady 60 FPS
			
			// If the wait time is negative, set it to 0 (Error checking)
			if (wait < 0) {
				
				wait = 0; 
				
			}
			
			try {
				
				Thread.sleep(wait); // Causes the thread to sleep for a specific amount of time
				
			}
			catch (Exception e) {
				
				e.printStackTrace(); // Prints the error (debugging)
				
			}
			
		}
		
	}
	
	private void update() {
		
		o.update(); // Calls the Organizer update method
		
	}
	
	private void draw() {
		
		o.draw(g); // Calls the Organizer Draw method
		
		Graphics g2 = getGraphics(); // Creates a new variable and assigns it the getGraphics(); method, letting me draw on the JFrame 
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null); // Draws the image
		g2.dispose(); // Frees any system resources that are used by the graphics context
		
	}
	
	public void keyTyped(KeyEvent key) {
	
		
		
	}
	
	public void keyPressed(KeyEvent key) {
		
		o.keyPressed(key.getKeyCode()); // Calls the Organizer keyPressed Method and passes the key code of the key pressed
		
	}
	
	public void keyReleased(KeyEvent key) {
		
		o.keyReleased(key.getKeyCode()); // Calls the Organizer keyReleased Method and passes the key code of the key released
		
	}

	
}
