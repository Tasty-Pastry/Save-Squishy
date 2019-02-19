/* Save Squishy!
 * Daanish (Tasty Pastry) Khan
 * Mrs. Karasinska
 * ICS 3U1
 * 01/19/2018 */

/* Program description: Save Squishy from the evil clutches of the Doggos! Use your arrow keys to move, z to jump, x to kick, and c to shoot your
 * fireball!	
 */

package mainWindow;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	public static void main(String[] args) {

		JFrame window = new JFrame("Save Squishy!"); // Creates a new JFrame window

		// Prevents the user from clicking on the edges of the window and resizing it
		window.setResizable(false);

		// Window to be sized to fit the preferred size and layouts of its subcomponents
		window.pack();

		window.setContentPane(new MainGame()); // Adds the class 'GamePanel' to the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Sets the preffered size of the window
		window.setPreferredSize(new Dimension(MainGame.WIDTH * MainGame.SCALE, MainGame.HEIGHT * MainGame.SCALE));

		// Sets the minimum window size
		window.setMinimumSize(new Dimension(MainGame.WIDTH * MainGame.SCALE, MainGame.HEIGHT * MainGame.SCALE));

		// Sets the max window size
		window.setMaximumSize(new Dimension(MainGame.WIDTH * MainGame.SCALE, MainGame.HEIGHT * MainGame.SCALE));

		window.setVisible(true); // Lets the user actually see the window

	}

}
