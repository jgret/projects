/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.Graphics2D;

import game.Game;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.io.FileIO;

public class GameStateGameOver extends GameState {

	Image2d background;
	
	public GameStateGameOver(Game game) {
		super(game);
		this.background = FileIO.loadImage("img/ui/bg_game_over.png");
	}

	@Override
	public void init() {

	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void update(double elapsedTime) {
		
	}

	@Override
	public void onEnd() {
		
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		background.draw(g2, 0, 0, cam.getWidth(), cam.getHeight());
		
	}

}
