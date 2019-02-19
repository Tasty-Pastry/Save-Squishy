/* Variable Dictionary 
 * 
 * tileMap: Used to reference the tileMap class
 * 
 * titleFont: Holds the font of the title
 * font: Holds the font of other text
 * titleColor: holds the color of the title
 * 
 * bgMusic: Holds the music
 * 
 * DTS: References the DeathTransitionStart Class
 * 
 * options: Holds an array of text
 * 
 * currentChoice: Holds the current choice
 * 
 * Transition: Holds whether or not the transition animation is playing
 * 
 */

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

public class GameOver extends GameState {
	
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
	private String[] options = {"Continue?", "Menu", "Quit"};
	private int currentChoice = 0;
	
	// Transition
	
	private boolean Transition;
	
	// Constructor
	public GameOver(Organizer o) {
		
		this.o = o;
		bgMusic = new AudioPlayer("/Music/LoseMusic.mp3"); // Load Menu Music
		
		// Set font and color
		try {
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Kitty Face", Font.PLAIN, 35); 
			
		    font = new Font("04b", Font.PLAIN, 15); 
		    
		} catch (Exception e) {
		
			e.printStackTrace(); // Print errors
		
		}
		
	}
	
	public void init() {
		
		// Creates a new tileMap
		tileMap = new TileMap(1);
		tileMap.setPosition(0, 0);

		// Creates a new instance of DTS and sets the position
		DTS = new DeathTransitionStart(tileMap);
		DTS.setPosition(MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
		
	}
	
	
	public void update() {
	
		// Plays music
		if (!bgMusic.clip.isRunning()) {
			
			bgMusic.play();
			
		}
			// Updates DTS if transition is true
			if (Transition) {
				
				DTS.update();
				
				if (DTS.shouldRemove()) {
					
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
			g.drawString("You Lose...", 70, 80);
			
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
			
			// Draw transition
			DTS.draw(g);
			
		}
		
	}
	
	private void select() {
		// Goes to the correct state when selected
			if (currentChoice == 0) {
			
					Transition = true;
					
					if (DTS.shouldRemove()) {
						
						bgMusic.stop();
						o.setState(Organizer.LEVEL1);
						
					}
			
			}
		
			if (currentChoice == 1) {
			
				Transition = true;
				
				if (DTS.shouldRemove()) {
					
					bgMusic.stop();
					o.setState(Organizer.MENU);
					
				}
			
			}
		
			if (currentChoice == 2) {
			
				System.exit(0);
			
			}
		
		}
	
	// Moves the selection when the up or down key is pressed and calles the select function when enter is pressed
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
