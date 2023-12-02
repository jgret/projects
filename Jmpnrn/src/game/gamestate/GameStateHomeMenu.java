/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import game.Game;
import game.graphics.Button;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.graphics.Images;
import sound.SoundEngine;

public class GameStateHomeMenu extends GameState {

	private Image2d background = Images.MENU_BACKGROUND;
	private Image2d font = Images.TITLE_FONT;
	private Image2d bacardi = Images.BACARDI;
	private Image2d button = Images.MENU_BUTTON;

	private ArrayList<Button> buttons;

	private float red = 0;
	private float green = 0.333f;
	private float blue = 0.666f;

	public GameStateHomeMenu(Game game) {
		super(game);
		this.buttons = new ArrayList<>();
	}

	@Override
	public void init() {
		Button start = new Button("Start", button);
		start.setRect(100, 100, 300, 100);
		start.addActionListener((e) -> {
			gsm.changeGameState(GameStateType.PLAY);
		});
		buttons.add(start);

		Button quit = new Button("Quit", button);
		quit.setRect(100, 250, 300, 100);
		quit.addActionListener((e) -> {
			System.exit(0);
		});
		buttons.add(quit);

	}
	
	@Override
	public void onStart() {
//		SoundEngine.getInstance().play("main_theme");
	}

	@Override
	public void update(double elapsedTime) {

		if (input.keyPressed(KeyEvent.VK_ENTER)) {
			SoundEngine.getInstance().play("darkside");
		}
		
		if (input.keyReleased(KeyEvent.VK_ENTER)) {
			SoundEngine.getInstance().stop("darkside");
		}
		
		for (Button b : buttons) {
			
			b.setHover(false);
			
			if (b.contains(input.getPoint())) {
				b.setHover(true);
			}
			
			if (input.mousePressed(0)) {
				if (b.contains(input.getPoint())) {
					b.click();
				}
			}
		}

		float speed = 1f;

		red += elapsedTime * speed;
		blue += elapsedTime * speed;
		green += elapsedTime * speed;

		red %= 1;
		blue %= 1;
		green %= 1;

	}

	@Override
	public void onEnd() {
//		SoundEngine.getInstance().stop("main_theme");
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {

		background.draw(g2, 0, 0, cam.getWidth(), cam.getHeight());
//		bacardi.drawCenter(g2, cam.getWidth() / 2, cam.getHeight() / 2);

		for (Button b : buttons) {
			b.draw(g2, cam);
		}


	}

}
