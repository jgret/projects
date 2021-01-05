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
import game.graphics.Images;

public class GameStateIntro extends GameState {
	private Image2d background = Images.HOLY_GRAIL;
	
	public GameStateIntro(Game game) {
		super(game);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update(double elapsedTime) {
		
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		
		g2.fillRect(0, 0, (int) cam.getWidth(), (int) cam.getHeight());
		background.draw(g2, cam.getWidth() / 2 - background.getWidth() / 2, cam.getHeight() / 2 - background.getHeight() / 2);
	}

}
