package gameState;

public class Organizer {

	private GameState[] gameStates;
	public int currentState; // Index of the game state and list

	// State positions in array
	public static final int NUMGAMESTATES = 5;
	public static final int MENU = 0;
	public static final int LEVEL1 = 1;
	public static final int LEVEL1BOSS = 2;
	public static final int GAMEOVER = 3;
	public static final int WIN = 4;

	public Organizer() {

		// Creates a new array of GameState with the number of gamestates as the length
		gameStates = new GameState[NUMGAMESTATES];

		// Sets the default state
		currentState = MENU;

		// Loads the state
		loadState(currentState);

	}

	private void loadState(int state) {

		// Sets the state to a new instance of the currentState
		if (state == MENU) {

			gameStates[state] = new MenuState(this);
			gameStates[MENU].init(); // Initialises Menu

		}

		if (state == LEVEL1) {

			gameStates[state] = new Level1State(this);

		}

		if (state == LEVEL1BOSS) {

			gameStates[state] = new Level1Boss(this);

		}

		if (state == GAMEOVER) {

			gameStates[state] = new GameOver(this);
			gameStates[GAMEOVER].init();

		}

		if (state == WIN) {

			gameStates[state] = new Win(this);
			gameStates[WIN].init();

		}

	}

	private void unloadState(int state) {

		// Sets the state to null to unload and free space
		gameStates[state] = null;

	}

	// Unloads the state and sets the new state
	public void setState(int state) {

		unloadState(currentState);
		currentState = state;
		loadState(currentState);

	}

	public void update() {

		// Calls the state update method
		if (gameStates[currentState] != null) {

			gameStates[currentState].update();

		}

	}

	public void draw(java.awt.Graphics2D g) {

		// Calls the state draw method
		if (gameStates[currentState] != null) {

			gameStates[currentState].draw(g);

		}

	}

	public void keyPressed(int k) {

		// Passes the keycode to the respective state method
		gameStates[currentState].keyPressed(k);

	}

	public void keyReleased(int k) {

		// Passes the keycode to the respective state method
		gameStates[currentState].keyReleased(k);

	}

}
