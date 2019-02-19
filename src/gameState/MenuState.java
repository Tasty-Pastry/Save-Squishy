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
 * catEntrance + Stay: References the class
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

public class MenuState extends GameState {
	
	// Class Variables
	
	// Map
	private Background bg;
	private TileMap tileMap;
	
	// Text
	private Font titleFont;
	private Font font;
	private Color titleColor;
	
	// Music 
	private AudioPlayer bgMusic;
	
	// Animation
	private CatEntrance catEntrance;
	private CatEntranceStay catEntranceStay;
	
	// Transition Animation
	
	private DeathTransitionStart DTS;
	
	// Menu Screen
	
	// Text
	private String[] options = {"Start", "Help", "Quit"};
	private int currentChoice = 0;
	
	// Transparency
	private Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f);
	private double i;
	
	// Transition
	
	private boolean Transition;
	
	// Constructor
	public MenuState(Organizer o) {
		
		this.o = o;
		bgMusic = new AudioPlayer("/Music/8BitCalm.mp3"); // Load Menu Music
		
		try {
			
			bg = new Background ("/Backgrounds/Untitled.png", 1); // Load Background image
			bg.setVector(-0.895, 0); // Set moving vectors
			
			// Sets color and fonts
			titleColor = new Color(128, 0, 0); 
			titleFont = new Font("Kitty Face", Font.PLAIN, 35); 
			
		    font = new Font("04b", Font.PLAIN, 15); 
		    
		} catch (Exception e) {
		
			e.printStackTrace(); // Prints erroes
		
		}
		
	}
	
	public void init() {
		
		// Creates a new tileMap
		tileMap = new TileMap(1);
		tileMap.setPosition(0, 0);
		
		// Creates new instance and sets the position of the instance
		catEntrance = new CatEntrance(tileMap);
		catEntrance.setPosition(65, 185);
		
		// Creates new instance and sets the position of the instance
		catEntranceStay = new CatEntranceStay(tileMap);
		catEntranceStay.setPosition(65, 185);
		
		// Creates new instance and sets the position of the instance
		DTS = new DeathTransitionStart(tileMap);
		DTS.setPosition(MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
		
	}
	
	public void update() {
		
		// Plays Music
		if (!bgMusic.clip.isRunning()) {
			
			bgMusic.play();
			
		}
		
		// Updates the background
		bg.update();
		
		// Updates animations
		
		if (catEntrance.shouldRemove()) {
			
			catEntranceStay.update();
		
		} else {
			
			catEntrance.update();
			
		}
		
		if (catEntranceStay.shouldRemove()) {
			
			// Transparency 
			
			i+= 0.05;
			
			if (i >= 1) {
				
				i = 1;
				
			}
			
			c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (i));
			
			if (Transition) {
				
				// Update transition
				
				DTS.update();
				
			}
			
			if (DTS.shouldRemove()) {
				
				// Calls select when transition is done playing
				
				select();
				
			}
			
		}
		
	}
	
	// Draw
	public void draw (Graphics2D g) {
		
		// Draw background
		bg.draw(g);
		
		// Draw Text
		if (catEntranceStay.shouldRemove()) {
		
			g.setColor(titleColor);
			g.setFont(titleFont);
			g.setComposite(c);
			g.drawString("Save Squishy!", 5, 80);
			
			g.setFont(font);
		
			for (int i = 0; i < options.length; i++) {
			
				if (i == currentChoice) {
				
					g.setColor(Color.BLACK);
				
				} else {
				
					g.setColor(Color.RED);
				
				}
			
				g.drawString(options[i], 132, 145 + (i * 25));
			
			}
		
		}
		
		// Draw Animations
		if (catEntrance.shouldRemove()) {
		
			catEntranceStay.draw(g);
		
		} else {
			
			catEntrance.draw(g);
			
		}
		
		// Draw Transition
		if (Transition) {
			
			DTS.draw(g);
			
		}
		
	}
	
	private void select() {
		
		// Goes to the appropriate state when called
		if (catEntranceStay.shouldRemove()) {
		
			if (currentChoice == 0) {
			
					Transition = true;
					
					if (DTS.shouldRemove()) {
						
						bgMusic.stop();
						o.setState(Organizer.LEVEL1);
						
					}
			
			}
		
			if (currentChoice == 1) {
			
				// help
			
			}
		
			if (currentChoice == 2) {
			
				System.exit(0);
			
			}
		
		}
		
	}
	
	public void keyPressed(int k) {
		
		// Moves the selection when the up or down key is pressed, and calls the select method when the enter key is pressed
		if (catEntranceStay.shouldRemove()) {
		
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
		
	}
	
	public void keyReleased(int k) {}

}
