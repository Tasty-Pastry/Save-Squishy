package gameState;

// Imports
import java.awt.*;
import java.awt.event.*;
import entity.DeathTransitionStart;
import mainWindow.MainGame;
// Class Imports
import TileMap.*;
import audio.AudioPlayer;
import entity.*;

public class Win extends GameState {
	
	// Class Variables
	
	// Map
	private TileMap tileMap;
	
	// Text
	private Font titleFont;
	private Font font;
	private Color titleColor;
	
	// Music 
	private AudioPlayer bgMusic;
	
	// Transition Animation
	
	private DeathTransitionStart DTS;
	
	// Game Over Screen
	
	// Text
	private String[] options = {"Play Again?", "Menu", "Quit"};
	private int currentChoice = 0;
	
	// Transition
	
	private boolean Transition;
	
	// Constructor
	public Win(Organizer o) {
		
		this.o = o;
		bgMusic = new AudioPlayer("/Music/8BitWin.mp3"); // Load Menu Music
		
		// Load font
		try {
			
			titleColor = new Color(255, 20, 147);
			titleFont = new Font("Kitty Face", Font.PLAIN, 35); 
			
		    font = new Font("04b", Font.PLAIN, 15); 
		    
		} catch (Exception e) {
		
			e.printStackTrace(); // Print errors
		
		}
		
	}
	
	public void init() {
		
		// Creates new tileMap
		tileMap = new TileMap(1);
		tileMap.setPosition(0, 0);

		// Creates new instance and sets the position
		DTS = new DeathTransitionStart(tileMap);
		DTS.setPosition(MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
		
	}
	
	public void update() {
		
		// Plays bg music
		if (!bgMusic.clip.isRunning()) {
			
			bgMusic.play();
			
		}
			
			if (Transition) {
				
				// Updates Transition
				DTS.update();
				
				if (DTS.shouldRemove()) {
					
					// Stops music and calls select function
					bgMusic.stop();
					select();
					
				}
				
			}
			
		}
	
	public void draw (Graphics2D g) {
		
			// Draw text
			g.setColor(titleColor);
			g.setBackground(Color.BLACK);
			g.setFont(titleFont);
			g.drawString("You Win!!!!", 70, 80);
			
			g.setFont(font);
		
			for (int i = 0; i < options.length; i++) {
			
				if (i == currentChoice) {
				
					g.setColor(Color.WHITE);
				
				} else {
				
					g.setColor(Color.RED);
				
				}
			
				g.drawString(options[i], 120, 145 + (i * 25));
			
			}
		
		if (Transition) {
			
			// Draw Transition
			DTS.draw(g);
			
		}
		
	}
	
	// Goes to the appropriate state based the current choice
	private void select() {
		
			if (currentChoice == 0) {
			
					Transition = true;
					
					if (DTS.shouldRemove()) {
						
						// Stops music and goes to level 1
						bgMusic.stop();
						o.setState(Organizer.LEVEL1);
						
					}
			
			}
		
			if (currentChoice == 1) {
			
				Transition = true;
				
				if (DTS.shouldRemove()) {
					
					// Stops music and goes to the menu
					bgMusic.stop();
					o.setState(Organizer.MENU);
					
				}
			
			}
		
			if (currentChoice == 2) {
			
				// Exits
				System.exit(0);
			
			}
		
		}
	
	// If up or down arrow key is pressed, changs the current choice and selects teh choice if you press enter
	public void keyPressed(int k) {
		
			if (k == KeyEvent.VK_ENTER) {
			
				select();
			
			}
		
			if (k == KeyEvent.VK_UP) {
			
				currentChoice--;
				if (currentChoice == -1) {
				
					currentChoice = options.length - 1;
				
				}
			
			}
		
			if (k == KeyEvent.VK_DOWN) {
			
				currentChoice++;
				if (currentChoice == options.length) {
				
					currentChoice = 0;
				
				}
			
			}
		
		}
	
	public void keyReleased(int k) {}

}
