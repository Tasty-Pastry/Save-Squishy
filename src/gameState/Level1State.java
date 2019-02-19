package gameState;

// Imports
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import entity.Title;
import TileMap.*;
import entity.BigBossBark;
import entity.BigBossEntrance;
import entity.BigBossJump;
import entity.BigBossTransformation;
import entity.DeathTransitionEnd;
import entity.DeathTransitionStart;
import entity.Enemy;
import entity.Entrance;
import entity.Explosion;
import entity.HUD;
import entity.Player;
import entity.enemies.*;
import mainWindow.MainGame;
import audio.AudioPlayer;

public class Level1State extends GameState{

	// Variables
	
	// Other Class Variables
	private TileMap tileMap;
	private Background backTrees;
	private Background lights;
	private Background middleTrees;
	private Background frontTrees;
	private Entrance entrance;
	private DeathTransitionStart DT;
	private DeathTransitionEnd DTE;
	private BigBossEntrance BBE;
	private BigBossBark BBB;
	private BigBossTransformation BBT;
	private BigBossJump BBJ;
	private BufferedImage Level1Text;
	private Title title;
	public Player player;
	
	// Boss battle
	private boolean bossBattle;
	private boolean reset;
	private boolean switchRoom;
	
	// Animation
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	// HUD
	private HUD hud;
	
	// Music
	private AudioPlayer bgMusic;
	private boolean bgIsStopped;
	private boolean smolBarkoIsStopped;
	private boolean bigBarkoIsStopped;
	
	private HashMap<String, AudioPlayer> SFX;
	
	// Constructor
	public Level1State(Organizer state) {
		
		this.o = state;
		init();
		
	}
	
	public void init() {
		
		// Load tilemap
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/Level1TileSet.gif");
		tileMap.loadMap("/Maps/Level1Map.map");
		tileMap.setPosition(0, 0);
		
		// Load bg
		backTrees = new Background("/Backgrounds/parallax-forest-back-trees.png", 0.1);
		lights = new Background("/Backgrounds/parallax-forest-lights.png", 0);
		middleTrees = new Background("/Backgrounds/parallax-forest-middle-trees.png", 0.2);
		frontTrees = new Background ("/Backgrounds/parallax-forest-front-trees.png", 0.4);
		
		// Set player position
		player = new Player(tileMap);
		player.setPosition(73, 177);
		tileMap.setSmoothScroll(0.07);
		
		// Player health
		player.health = player.getMaxHealth();
		player.shot = player.getMaxShot();
		
		// Create new instances and set positions
		entrance = new Entrance(tileMap);
		entrance.setPosition(95, 154);
		
		DT = new DeathTransitionStart(tileMap);
		DTE = new DeathTransitionEnd(tileMap);
		DTE.setPosition(MainGame.WIDTH / 2, MainGame.HEIGHT / 2);
		
		BBE = new BigBossEntrance(tileMap); 
		BBE.setPosition(3543, 185);
		
		BBB = new BigBossBark(tileMap);
		BBB.setPosition(3530, 185);
		
		BBT = new BigBossTransformation(tileMap);
		BBT.setPosition(3405, 92);
		
		BBJ = new BigBossJump(tileMap);
		BBJ.setPosition(3405, 92);
		
		// Load text
		try {
			
			Level1Text = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/Level1Name.png"));
			title = new Title(Level1Text);
			title.sety(60);
			
		} catch(Exception e) {
			
			e.printStackTrace(); // Print errors
			
		}
		
		// Enemies
		populateEnemies();
		
		// Fireballs
		explosions = new ArrayList<Explosion>(); 
		
		// HUD
		hud = new HUD(player);
		
		// Only called once
		if (!reset) {
		
			bgMusic = new AudioPlayer("/Music/Level1Music.wav");
			SFX = new HashMap<String, AudioPlayer>();
			SFX.put("Smol Barko", new AudioPlayer("/SFX/Smol Barko.mp3"));
			SFX.put("Big Barko", new AudioPlayer("/SFX/Big Barko.mp3"));
			bgMusic.play();
		
		}
		
		// Misc
		bossBattle = false;
		bgIsStopped = false;
		smolBarkoIsStopped = false;
		bigBarkoIsStopped = false;
		switchRoom = false;
		reset = false;
		
	}
	
	private void populateEnemies() {
		
		// Create new arraylist
		enemies = new ArrayList<Enemy>();
		
		Doggo d;
		
		// Array of positions 
		Point[] points = new Point[] {
				
			new Point (305,145),
			new Point (407, 113), new Point (464, 113),
			new Point (533, 81),
			new Point (658, 17),
			new Point (628, 113),
			new Point (714, 177), new Point (747, 177), new Point (783, 177), new Point (817, 177), new Point  (858, 177),
			new Point (927, 113), new Point (1000, 113),
			new Point (1074, 177),
			new Point (12224, 177), new Point (1283, 177),
			new Point (1488, 177), new Point (1533, 177), new Point (1606, 177), new Point (1671, 177), new Point (1715, 177), new Point (1749, 177), new Point (1780, 177), new Point (1822, 177), new Point (1872, 177), new Point (1945, 177), 
			new Point (2009, 177), new Point (2056, 177), new Point (2110, 177), 
			new Point (2454, 81), 
			new Point (2678, 113), new Point (2740, 113), new Point (2785, 113),

		};
		 
		// Load in doggos at positions
		for (int i = 0; i < points.length; i++) {
			
			d = new Doggo(tileMap);
			d.setPosition(points[i].x, points[i].y);
			enemies.add(d);
			
		}
		
	}
	
	
	public void update() {
		
		// Reset level if player dies
		if (player.getReset()) { 

			reset = player.getReset();
			DT.setPosition(player.getx(), MainGame.HEIGHT / 2);
			DT.update();
				
			if (DT.shouldRemove()) {
				
				// Goto game over screen
				bgMusic.stop();
				o.setState(Organizer.GAMEOVER);
				
			}

		} else {
		
			// Play bg
			if (!bgMusic.clip.isRunning() && !bgIsStopped) {
			
				bgMusic.play();
		
			}
		
			// Update player
			if (entrance.shouldRemove() && DTE.shouldRemove()) {
			
				player.update();
			
			} else if (DTE.shouldRemove()) {
			
				// Update entrace
				entrance.update();
			
			} else {
				
				// Update DeathScreen
				DTE.update();
				
			}
			
		   // Animate titles
		   if (DTE.shouldRemove() && !title.shouldRemove() && title != null) {
			   
			   title.begin();
			   
		   }
			
			if (title != null) {
				
				title.update();
				
			}
		
			// Load Boss Map when player gets to boos roo,
			if (player.getx() >= 3311 && player.gety() >= 177) {
			
				tileMap.loadMap("/Maps/Level1MapBoss.map");
				bossBattle = true;
				player.x = 3311;
				player.y = 177;
			
			}
		
			// Plays animation when boss battle initiates
			if (bossBattle) {
			
				if (!bgIsStopped) {
					
					bgMusic.stop();
					bgIsStopped = true;
					
				}
				
				tileMap.setPosition(MainGame.WIDTH / 2 - 3410, MainGame.HEIGHT / 2 - 177);
				
				if (!BBE.shouldRemove()) {
					
					BBE.update();
					
				} else if (!BBB.shouldRemove()) {
					
					if (!smolBarkoIsStopped) {
						
						// Play SFX
						SFX.get("Smol Barko").play();
						smolBarkoIsStopped = true;
						
					}
					
					BBB.update();
					
				} else if (!BBT.shouldRemove()) {
					
					if (!bigBarkoIsStopped) {
						
						SFX.get("Big Barko").play();
						bigBarkoIsStopped = true;
						
					}
					
					BBT.update();
					
				} else if (!BBJ.shouldRemove()) {
					
					BBJ.update();
					
				} else if (!switchRoom){
					
					// Goto boss state
					o.setState(Organizer.LEVEL1BOSS);
					
				}
				
			} else {
		
				// Update camera postition
				tileMap.setPosition(MainGame.WIDTH / 2 - player.getx(), MainGame.HEIGHT / 2 - player.gety());
			
			}
		
			// Update bg
			backTrees.setPosition(tileMap.getx(), tileMap.gety());
			middleTrees.setPosition(tileMap.getx(), tileMap.gety());
			frontTrees.setPosition(tileMap.getx(), tileMap.gety());
		
			// Check is player attacked an enemy
			player.checkAttack(enemies);
		
			// Update enemies and remove if they died
			for (int i = 0; i < enemies.size(); i++) {
			
				Enemy e = enemies.get(i);
			
				e.update();
			
				if (e.isDead()) {
				
					enemies.remove(i);
					i--;
					explosions.add(new Explosion(e.getx(), e.gety()));
				
				}
			
			}
		
			// Update explosions
			for (int i = 0; i < explosions.size(); i++) {
			
				explosions.get(i).update();
			
				if (explosions.get(i).shouldRemove()) {
				
					explosions.remove(i);
					i--;
				
				}
			
			}
		
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		if (reset) {
			
			// Draw Transition
			DT.draw(g);
			
		} else {
		
			// Draw bg
			backTrees.draw(g);
			lights.draw(g);
			middleTrees.draw(g);
			frontTrees.draw(g);
				
			// Draw tilemap
			tileMap.draw(g);
		
			// Draw player
			if (entrance.shouldRemove() && DTE.shouldRemove()) {
				
				player.draw(g);
			
			} else if (DTE.shouldRemove()) {
			
				// Draw entrance
				entrance.draw(g);
			
			} else {
				
				// draw Transition
				DTE.draw(g);
				
			}
		
			// Draw enemies
			for (int i = 0; i < enemies.size(); i++) {
			
				enemies.get(i).draw(g);
			
			}
			
			// Draw Boss if battle has bene initated
			if (bossBattle) {
				
				if (!BBE.shouldRemove()) {
					
					BBE.draw(g);
					
				} else if (!BBB.shouldRemove()) {
					
					BBB.draw(g);
					
				} else if (!BBT.shouldRemove()) {
					
					BBT.draw(g);
					
				} else if (!BBJ.shouldRemove()){
					
					BBJ.draw(g);
					
				}
				
			}
		
			// Draw explosions frames
			for (int i = 0; i < explosions.size(); i++) {
			
				explosions.get(i).setMapPosition((int) tileMap.getx(), (int) tileMap.gety());
				explosions.get(i).draw(g);
			
			}
		
			// Draw HUD
			hud.draw(g);
			
			// Draw Transitions
			if (!entrance.shouldRemove() && !DTE.shouldRemove()) {
			
				DTE.draw(g);
				
			}
			
			// Draw title
			if (title != null) {
				
				title.draw(g);
				
			}
		
		}
		
	}
	
	// Movement
	
	public void keyPressed(int k) {
		
		if (k == KeyEvent.VK_LEFT && !bossBattle) {
			
			player.setLeft(true);
			
		}
		
		if (k == KeyEvent.VK_RIGHT && !bossBattle) {
			
			player.setRight(true);
			
		}
		
		if (k == KeyEvent.VK_UP && !bossBattle) {
			
			player.setUp(true);
			
		}
		
		if (k == KeyEvent.VK_DOWN && !bossBattle) {
			
			player.setDown(true);
			
		}
		
		if (k == KeyEvent.VK_Z && !bossBattle) {
			
			player.setJumping(true);
			
		}
		
		if (k == KeyEvent.VK_X && !player.firing && !bossBattle) {
			
			player.setMelee();
			
		}
		
		if (k == KeyEvent.VK_C && !player.melee & !bossBattle) {
			
			player.setFiring();
			
		}
		
	}
	
	public void keyReleased(int k) {
		
		if (k == KeyEvent.VK_LEFT) {
			
			player.setLeft(false);
			
		}
		
		if (k == KeyEvent.VK_RIGHT) {
			
			player.setRight(false);
			
		}
		
		if (k == KeyEvent.VK_UP) {
			
			player.setUp(false);
			
		}
		
		if (k == KeyEvent.VK_DOWN) {
			
			player.setDown(false);
			
		}
		
		if (k == KeyEvent.VK_Z) {
			
			player.setJumping(false);
			
		}
		
	}
	
}
