package game.gamestate;

import java.awt.Graphics2D;

import game.Game;
import game.graphics.Camera;
import game.graphics.Drawable;
import game.io.Input;

public abstract class GameState implements Drawable {
	
	protected Input input;
	protected Game game;
	protected GameStateManager gsm;

	public GameState(Game game) {
		this.game = game;
		this.input = game.getInput();
		this.gsm = game.getGsm();
	}

	/**
	 * called once at start
	 */
	public abstract void init();

	/**
	 * called every frame for update and logic 
	 * @param elapsedTime length of the frame
	 */
	public abstract void update(double elapsedTime);

	/**
	 * called every frame to render to screen 
	 * @param g2 the graphics component to draw
	 * @param cam
	 * @param scale
	 */
	@Override
	public abstract void draw(Graphics2D g2, Camera cam);
	
}
