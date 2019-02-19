package gameState;

//Imports
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import entity.enemies.Boss;
import entity.enemies.Doggo;
import TileMap.*;
import mainWindow.MainGame;
import audio.AudioPlayer;

import entity.*;

public class Level1Boss extends GameState {

	// Variables
	
	// Class Variables (Used as Refereces)
	private TileMap tileMap;
	private Background backTrees;
	private Background lights;
	private Background middleTrees;
	private Background frontTrees;
	private BubbleCenter BubbleCenter;
	private BubbleCenterToLeft BubbleCenterToLeft;
	private BubbleCenterToRight BubbleCenterToRight;
	private BubbleLeft BubbleLeft;
	private BubbleLeftToCenter BubbleLeftToCenter;
	private BubbleLeftToRight BubbleLeftToRight;
	private BubbleRight BubbleRight;
	private BubbleRightToCenter BubbleRightToCenter;
	private BubbleRightToLeft BubbleRightToLeft;
	private DogFoot DogFoot;
	private DogFootHurt DogFootHurt;
	private DeathTransitionStart DT;
	private DeathTransitionEnd DTE;
	private BufferedImage BossBattleText;
	private BufferedImage Fighto;
	private Title title;
	private Title subtitle;
	private Player player;
	private Boss b;
	
	// Player
	private boolean isHurt;
	private int health;

	// Boss
	private boolean reset;
	private boolean numberGen;
	private int num = 2;
	private int prevNum;
	private int y = 205;
	private int center;
	private int left;
	private int right;
	
	private ArrayList<Enemy> enemies;
	
	// HUD
	private HUD hud;
	
	// Music
	private AudioPlayer bgMusic;
	private boolean bgIsStopped;
	private HashMap<String, AudioPlayer> SFX;
	
	// Constructor
	public Level1Boss(Organizer state) {
		
		this.o = state;
		init();
		
	}
	
	public void init() {
		
		// Set Dog Leg positions
		center = MainGame.WIDTH - 77;
		left = MainGame.WIDTH - 165;
		right = MainGame.WIDTH + 21;
		
		// Create new TileMap
		tileMap = new TileMap(32);
		tileMap.loadTiles("/Tilesets/Level1TileSet.gif");
		tileMap.loadMap("/Maps/Level1MapBossRoom.map"); // Change
		tileMap.setPosition(MainGame.WIDTH / 2 - 242, MainGame.HEIGHT / 2 - 177);
		
		// Load Background
		backTrees = new Background("/Backgrounds/parallax-forest-back-trees.png", 0.1);
		lights = new Background("/Backgrounds/parallax-forest-lights.png", 0);
		middleTrees = new Background("/Backgrounds/parallax-forest-middle-trees.png", 0.2);
		frontTrees = new Background ("/Backgrounds/parallax-forest-front-trees.png", 0.4);
		
		// Set instances and positions
		
		player = new Player(tileMap);
		player.setPosition(120, 177);
		tileMap.setSmoothScroll(0.07);
		
		DT = new DeathTransitionStart(tileMap);
		DTE = new DeathTransitionEnd(tileMap);
		DTE.setPosition(center, MainGame.HEIGHT / 2);
		DT.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleCenter = new BubbleCenter(tileMap);
		BubbleCenter.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleCenterToLeft = new BubbleCenterToLeft(tileMap);
		BubbleCenterToLeft.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleCenterToRight = new BubbleCenterToRight(tileMap);
		BubbleCenterToRight.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleLeft = new BubbleLeft(tileMap);
		BubbleLeft.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleLeftToCenter = new BubbleLeftToCenter(tileMap);
		BubbleLeftToCenter.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleLeftToRight = new BubbleLeftToRight(tileMap);
		BubbleLeftToRight.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleRight = new BubbleRight(tileMap);
		BubbleRight.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleRightToCenter = new BubbleRightToCenter(tileMap);
		BubbleRightToCenter.setPosition(center, MainGame.HEIGHT / 2);
		
		BubbleRightToLeft = new BubbleRightToLeft(tileMap);
		BubbleRightToLeft.setPosition(center, MainGame.HEIGHT / 2);
		
		// Enemy 
		
		enemies = new ArrayList<Enemy>();
		
		DogFoot = new DogFoot(tileMap);
		
		DogFootHurt = new DogFootHurt(tileMap);
		
		health = 20;
		
		// Load Text
		try {
			
			BossBattleText = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/Bossbattle Start.png"));
			title = new Title(BossBattleText);
			title.sety(40);
			
			Fighto = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/Fight.png"));
			subtitle = new Title(Fighto);
			subtitle.sety(60);
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		// HUD
		hud = new HUD(player);
		
		// Music
		bgMusic = new AudioPlayer("/Music/8BitBoss.mp3");
		bgMusic.play();
		
		// Misc
		numberGen = true;
		reset = false;

	}
	
	
	public void update() {
		
		// Resets if player dies
		if (player.getReset()) { 

			reset = player.getReset();
			DT.update();
			
			if (DT.shouldRemove()) {
				
				bgMusic.stop();
				o.setState(Organizer.GAMEOVER);
				
			}

		} else {
		
			// Play Music
			if (!bgMusic.clip.isRunning() && !bgIsStopped) {
			
				bgMusic.play();
		
			}
		
			// Update Player
			if (DTE.shouldRemove()) {
			
				player.update();
			
			} else {
				
				// Update DeathScreen
				DTE.update();
				
			}
			
			// Animate Titles
		   if (DTE.shouldRemove() && !title.shouldRemove() && title != null) {
			   
			   title.begin();
			   
		   } if (title.shouldRemove() && !subtitle.shouldRemove() && subtitle != null) {
			   
			   subtitle.begin();
			   
		   }
			
		   // Updates titles
			if (title != null) {
				
				title.update();
				
			}
			
			if (subtitle != null) {
				
				subtitle.update();
				
			}
			
			// Generates random number
			if (numberGen && title.shouldRemove() && subtitle.shouldRemove()) {
				
				prevNum = num;
				num = (int) (3 * Math.random()) + 1;
				numberGen = false;
				
			}
			
			// Animations 
			if (num == 1 && prevNum == 1) {
				
				BubbleLeft.update();
				
				if (BubbleLeft.shouldRemove()) {
					
					DogFoot.setPosition(left, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleLeft = new BubbleLeft(tileMap);
						BubbleLeft.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 1 && prevNum == 2) {
				
				BubbleCenterToLeft.update();
				
				if (BubbleCenterToLeft.shouldRemove()) {
					
					DogFoot.setPosition(left, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleCenterToLeft = new BubbleCenterToLeft(tileMap);
						BubbleCenterToLeft.setPosition(center, MainGame.HEIGHT / 2);
						
					}					
				}
				
			} else if (num == 1 && prevNum == 3) {
				
				BubbleRightToLeft.update();
				
				if (BubbleRightToLeft.shouldRemove()) {
					
					DogFoot.setPosition(left, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleRightToLeft = new BubbleRightToLeft(tileMap);
						BubbleRightToLeft.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 2 && prevNum == 1) {
				
				BubbleLeftToCenter.update();
				
				if (BubbleLeftToCenter.shouldRemove()) {
					
					DogFoot.setPosition(center, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleLeftToCenter = new BubbleLeftToCenter(tileMap);
						BubbleLeftToCenter.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 2 && prevNum == 2) {
				
				BubbleCenter.update();
				
				if (BubbleCenter.shouldRemove()) {
					
					DogFoot.setPosition(center, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleCenter = new BubbleCenter(tileMap);
						BubbleCenter.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 2 && prevNum == 3) {
				
				BubbleRightToCenter.update();
				
				if (BubbleRightToCenter.shouldRemove()) {
					
					DogFoot.setPosition(center, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleRightToCenter = new BubbleRightToCenter(tileMap);
						BubbleRightToCenter.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 3 && prevNum == 1) {
				
				BubbleLeftToRight.update();
				
				if (BubbleLeftToRight.shouldRemove()) {
					
					DogFoot.setPosition(right, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleLeftToRight = new BubbleLeftToRight(tileMap);
						BubbleLeftToRight.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 3 && prevNum == 2) {
				
				BubbleCenterToRight.update();
				
				if (BubbleCenterToRight.shouldRemove()) {
					
					DogFoot.setPosition(right, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleCenterToRight = new BubbleCenterToRight(tileMap);
						BubbleCenterToRight.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			} else if (num == 3 && prevNum == 3) {
				
				BubbleRight.update();
				
				if (BubbleRight.shouldRemove()) {
					
					DogFoot.setPosition(right, MainGame.HEIGHT - y);
					DogFoot.update();
					
					if (DogFoot.shouldRemove()) {
						
						numberGen = true;
						DogFoot = new DogFoot(tileMap);
						
						BubbleRight = new BubbleRight(tileMap);
						BubbleRight.setPosition(center, MainGame.HEIGHT / 2);
						
					}
					
				}
				
			}
			
			// Creating new enemy
			if (BubbleRight.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(413, 177);
				enemies.add(b);
				
			}
			
			if (BubbleLeftToRight.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(413, 177);
				enemies.add(b);
				
			}
			
			if (BubbleCenterToRight.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(413, 177);
				enemies.add(b);
				
			}
			
			if (BubbleLeft.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(220, 177);
				enemies.add(b);
				
			}
			
			if (BubbleRightToLeft.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(220, 177);
				enemies.add(b);
				
			}
			
			if (BubbleCenterToLeft.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(220, 177);
				enemies.add(b);
				
			}

			if (BubbleCenter.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
	
				b = new Boss(tileMap);
				b.setPosition(310, 177);
				enemies.add(b);
	
			}
			
			if (BubbleLeftToCenter.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(310, 177);
				enemies.add(b);
				
			}
			
			if (BubbleRightToCenter.shouldRemove() && DogFoot.animation.getFrame() >= 9 && DogFoot.animation.getFrame() <= 64) {
				
				b = new Boss(tileMap);
				b.setPosition(310, 177);
				enemies.add(b);
	
			}
			
			// Remove enemy if dog foot is rising back up
			if (BubbleRight.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			if (BubbleLeftToRight.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			if (BubbleCenterToRight.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			if (BubbleLeft.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			if (BubbleRightToLeft.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			if (BubbleCenterToLeft.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}

			if (BubbleCenter.shouldRemove() && DogFoot.animation.getFrame() > 64) {
	
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
	
			}
			
			if (BubbleLeftToCenter.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			if (BubbleRightToCenter.shouldRemove() && DogFoot.animation.getFrame() > 64) {
				
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy e = enemies.get(i);
				
					e.update();
				
					enemies.remove(i);
					i--;
				
				}
				
			}
			
			// Check player attack
			player.checkAttack(enemies);
			
			// Remove enemy if hit
			for (int i = 0; i < enemies.size(); i++) {
				
				Enemy e = enemies.get(i);
			
				e.update();
			
				if (e.isDead()) {
				
					enemies.remove(i);
					i--;
					isHurt = true;
				
				}
			
			}
			
			// Play hurt animation if boss takes damage
			if (isHurt) {
				
				if (BubbleLeft.shouldRemove() || BubbleRightToLeft.shouldRemove() || BubbleCenterToLeft.shouldRemove()) {
					
					DogFootHurt.setPosition(left, MainGame.HEIGHT - y);
					DogFootHurt.update();
					
					if (DogFootHurt.shouldRemove()) {
						
						health--;
						isHurt = false;
						DogFootHurt = new DogFootHurt(tileMap);
						
					}
					
				} 
				
				if (BubbleCenter.shouldRemove() || BubbleLeftToCenter.shouldRemove() || BubbleRightToCenter.shouldRemove()) {
					
					DogFootHurt.setPosition(center, MainGame.HEIGHT - y);
					DogFootHurt.update();
					
					if (DogFootHurt.shouldRemove()) {
						
						health--;
						isHurt = false;
						DogFootHurt = new DogFootHurt(tileMap);
						
					}
					
				}
				
				if (BubbleRight.shouldRemove() || BubbleLeftToRight.shouldRemove() || BubbleCenterToRight.shouldRemove()) {
					
					DogFootHurt.setPosition(right, MainGame.HEIGHT - y);
					DogFootHurt.update();
					
					if (DogFootHurt.shouldRemove()) {
						
						health--;
						isHurt = false;
						DogFootHurt = new DogFootHurt(tileMap);
						
					}
					
				}
				
			}
			
			// Win if boss health is reduced to 0
			if (health <= 0) {
				
				DT.update();
				
				if (DT.shouldRemove()) {
					
					bgMusic.stop();
					o.setState(Organizer.WIN);
					
				}
				
			}
			
			// Sets tile map pos
			tileMap.setPosition(MainGame.WIDTH / 2 - 242, MainGame.HEIGHT / 2 - 177);
		
			// Sets background pos
			backTrees.setPosition(tileMap.getx(), tileMap.gety());
			middleTrees.setPosition(tileMap.getx(), tileMap.gety());
			frontTrees.setPosition(tileMap.getx(), tileMap.gety());
		
		}
		
	}
	
	// Draw
	public void draw(Graphics2D g) {
		
		if (reset || health <= 0) {
			
			DT.draw(g); // Draw Death Screen
			
		} else {
		
			// Draw bg
			backTrees.draw(g);
			lights.draw(g);
			middleTrees.draw(g);
			frontTrees.draw(g);
				
			// Draw tilemap
			tileMap.draw(g);
			
			// draw player
			player.draw(g);
		
			// draw hud
			hud.draw(g);
			
			// draw boss health
			g.drawString("Boss Health: " + health, 200, 215);
			
			// draw animations
			if (num == 1 && prevNum == 1 && !BubbleLeft.shouldRemove()) {
				
				BubbleLeft.draw(g);
				
			} else if (num == 1 && prevNum == 2 && !BubbleCenterToLeft.shouldRemove()) {
				
				BubbleCenterToLeft.draw(g);
				
			} else if (num == 1 && prevNum == 3 && !BubbleRightToLeft.shouldRemove()) {
				
				BubbleRightToLeft.draw(g);
				
			} else if (num == 2 && prevNum == 1 && !BubbleLeftToCenter.shouldRemove()) {
				
				BubbleLeftToCenter.draw(g);
				
			} else if (num == 2 && prevNum == 2 && !BubbleCenter.shouldRemove()) {
				
				BubbleCenter.draw(g);
				
			} else if (num == 2 && prevNum == 3 && !BubbleRightToCenter.shouldRemove()) {
				
				BubbleRightToCenter.draw(g);
				
			} else if (num == 3 && prevNum == 1 && !BubbleLeftToRight.shouldRemove()) {
				
				BubbleLeftToRight.draw(g);
				
			} else if (num == 3 && prevNum == 2 && !BubbleCenterToRight.shouldRemove()) {
				
				BubbleCenterToRight.draw(g);
				
			} else if (num == 3 && prevNum == 3 && !BubbleRight.shouldRemove()) {
				
				BubbleRight.draw(g);
				
			}
			
			if (BubbleLeft.shouldRemove() || BubbleCenter.shouldRemove() || BubbleCenterToLeft.shouldRemove() || BubbleCenterToRight.shouldRemove() || BubbleLeft.shouldRemove() || BubbleLeftToCenter.shouldRemove() || BubbleLeftToRight.shouldRemove() || BubbleRight.shouldRemove() || BubbleRightToCenter.shouldRemove() || BubbleRightToLeft.shouldRemove())  {
				
				DogFoot.draw(g);
				
				if (isHurt && DogFoot.animation.getFrame() <= 64) {
					
					DogFootHurt.draw(g);
					
				}
				
			}
			
			// draw titles
			if (title != null) {
				
				title.draw(g);
				
			}
			
			if (subtitle != null) {
				
				subtitle.draw(g);
				
			}
		
		}
		
	}
	
	// Movement
	
	public void keyPressed(int k) {
		
		if (k == KeyEvent.VK_LEFT) {
			
			player.setLeft(true);
			
		}
		
		if (k == KeyEvent.VK_RIGHT) {
			
			player.setRight(true);
			
		}
		
		if (k == KeyEvent.VK_UP) {
			
			player.setUp(true);
			
		}
		
		if (k == KeyEvent.VK_DOWN) {
			
			player.setDown(true);
			
		}
		
		if (k == KeyEvent.VK_Z) {
			
			player.setJumping(true);
			
		}
		
		if (k == KeyEvent.VK_X && !player.firing) {
			
			player.setMelee();
			
		}
		
		if (k == KeyEvent.VK_C && !player.melee) {
			
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
