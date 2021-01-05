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
import java.awt.event.KeyEvent;

import game.Game;
import game.entity.platform.Platform;
import game.graphics.Camera;
import game.graphics.Screen;
import game.shape.Rectangle;

public class GameStateTest extends GameState {

	private Screen screen;

	public GameStateTest(Game game) {
		super(game);
		this.screen = game.getScreen();
	}
	
	private Rectangle rect;
	private Rectangle rect2;
	
	@Override
	public void init() {
		this.rect = new Rectangle(300, 300, 100, 100);
		this.rect2 = new Platform(null, new Rectangle(0, 0, 50, 50), null);
	}

	@Override
	public void update(double elapsedTime) {
		globalHotKeys();
		this.rect2.setPosition(input.getPoint());

		if (rect2.intersects(rect)) {
			System.out.println(rect.predictPositionOf(rect2));
		}
	}

	public void globalHotKeys() {
		if (input.keyPressed(KeyEvent.VK_F11)) {
			if (screen.isFullscreen()) {
				screen.leaveFullscreen();
			} else {
				screen.enterFullscreen();
			}
		}

		if (input.keyHeld(KeyEvent.VK_SHIFT)) {
			int scale = Screen.TILESIZE;
			scale += input.wheelRotations() * -1;
			Screen.TILESIZE = scale;
		}

	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		g2.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		g2.drawRect((int) rect2.getX(), (int) rect2.getY(), (int) rect2.getWidth(), (int) rect2.getHeight());
	}

}
