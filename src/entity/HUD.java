/* Variable Dictionary:
 * 
 * powerMeter - powerMeter15: Variables used to hold images
 * healthBar - healthBar6: Variables used to hold images
 * 
 * player: Used to reference player class
 * font: Used to reference font class
 * 
 * Meterx: power meter x coord
 * Metery: power meter y coord
 *
 * healthMeterx: health bar x coord
 * healthMetery: health bar y coord
 * 
 */

package entity;

// Imports
import java.awt.*;

// Images
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {

	// Variables
	
	// Other Class
	private Player player;
	
	// Power Meter images
	private BufferedImage powerMeter;
	private BufferedImage powerMeter1;
	private BufferedImage powerMeter2;
	private BufferedImage powerMeter3;
	private BufferedImage powerMeter4;
	private BufferedImage powerMeter5;
	private BufferedImage powerMeter6;
	private BufferedImage powerMeter7;
	private BufferedImage powerMeter8;
	private BufferedImage powerMeter9;
	private BufferedImage powerMeter10;
	private BufferedImage powerMeter11;
	private BufferedImage powerMeter12;
	private BufferedImage powerMeter13;
	private BufferedImage powerMeter14;
	private BufferedImage powerMeter15;
	
	// Health Bar images
	private BufferedImage healthBar;
	private BufferedImage healthBar1;
	private BufferedImage healthBar2;
	private BufferedImage healthBar3;
	private BufferedImage healthBar4;
	private BufferedImage healthBar5;
	private BufferedImage healthBar6;
	
	// Font
	private Font font;
	
	// Dimentions
	private int Meterx;
	private int Metery = 203;
	
	private int healthMeterx;
	private int healthMetery = 5;
	
	// Constructor
	public HUD(Player p) {
		
		player = p;
		
		try {
			
			// Loads images
			powerMeter = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeterEmpty.png"));
			powerMeter1 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter1.png"));
			powerMeter2 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter2.png"));
			powerMeter3 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter3.png"));
			powerMeter4 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter4.png"));
			powerMeter5 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter5.png"));
			powerMeter6 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter6.png"));
			powerMeter7 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter7.png"));
			powerMeter8 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter8.png"));
			powerMeter9 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter9.png"));
			powerMeter10 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter10.png"));
			powerMeter11 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter11.png"));
			powerMeter12 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter12.png"));
			powerMeter13 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter13.png"));
			powerMeter14 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeter14.png"));
			powerMeter15 = ImageIO.read(getClass().getResourceAsStream("/HUD/SuperMeterFull.png"));
			
			healthBar = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeterEmpty.png"));
			healthBar1 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeter05.png"));
			healthBar2 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeter1.png"));
			healthBar3 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeter15.png"));
			healthBar4 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeter2.png"));
			healthBar5 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeter25.png"));
			healthBar6 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthMeterFull.png"));
			
			// Sets font
			font = new Font("Arial", font.PLAIN, 14);
			
		} catch (Exception e) {
			
			e.printStackTrace(); // Prints errors
			
		}
		
	}
	
	
	// Draw class
	public void draw (Graphics2D g) {
		
		// Draws power meter image based on amount of shots
		if (player.getShot() / 100 == 0) {
			
			g.drawImage(powerMeter, Meterx, Metery, null);
		
		}
		
		if (player.getShot() / 100 == 1) {
			
			g.drawImage(powerMeter1, Meterx, Metery, null);
		
		}
		
		if (player.getShot() / 100 == 2) {
			
			g.drawImage(powerMeter2, Meterx, Metery, null);
		
		}

		if (player.getShot() / 100 == 3) {
	
			g.drawImage(powerMeter3, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 4) {
	
			g.drawImage(powerMeter4, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 5) {
	
			g.drawImage(powerMeter5, Meterx, Metery, null);

		}
		
		if (player.getShot() / 100 == 6) {
	
			g.drawImage(powerMeter6, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 7) {
	
			g.drawImage(powerMeter7, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 8) {
	
			g.drawImage(powerMeter8, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 9) {
	
			g.drawImage(powerMeter9, Meterx, Metery, null);
		
		}

		if (player.getShot() / 100 == 10) {
	
			g.drawImage(powerMeter10 ,Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 11) {
	
			g.drawImage(powerMeter11, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 12) {
	
			g.drawImage(powerMeter12, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 13) {
	
			g.drawImage(powerMeter13, Meterx, Metery, null);

		}

		if (player.getShot() / 100 == 14) {
	
			g.drawImage(powerMeter14, Meterx, Metery, null);

		}
		
		if (player.getShot() / 100 == 15) {
			
			g.drawImage(powerMeter15, Meterx, Metery, null);

		}
		
		// Draws health bar based on health
		
		if (player.getHealth() == 6) {
			
			g.drawImage(healthBar6, healthMeterx, healthMetery, null);
			
		}
		
		if (player.getHealth() == 5) {
			
			g.drawImage(healthBar5, healthMeterx, healthMetery, null);
			
		}
		
		if (player.getHealth() == 4) {
			
			g.drawImage(healthBar4, healthMeterx, healthMetery, null);
			
		}
		
		if (player.getHealth() == 3) {
			
			g.drawImage(healthBar3, healthMeterx, healthMetery, null);
			
		}
		
		if (player.getHealth() == 2) {
			
			g.drawImage(healthBar2, healthMeterx, healthMetery, null);
			
		}
		
		if (player.getHealth() == 1) {
			
			g.drawImage(healthBar1, healthMeterx, healthMetery, null);
			
		}
		
		if (player.getHealth() == 0) {
			
			g.drawImage(healthBar, healthMeterx, healthMetery, null);
			
		}
		
		// Sets font
		g.setFont(font);
		g.setColor(Color.RED);
		
	}
	
}
