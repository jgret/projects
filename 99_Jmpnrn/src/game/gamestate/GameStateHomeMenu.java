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
import java.util.ArrayList;

import game.Game;
import game.graphics.Button;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.graphics.Images;

public class GameStateHomeMenu extends GameState {

	private Image2d background = Images.RGB_BACARDI;
	private Image2d font = Images.TITLE_FONT;
	private Image2d bacardi = Images.BACARDI;
	private Image2d button = Images.HOLY_GRAIL;
	
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
		start.addActionListener((e) -> {
			gsm.setGameState(GameStateType.PLAY);
		});
		
		Button quit = new Button("", bacardi);
		quit.addActionListener((e) -> {
			
		});
		
	}
	
	

	@Override
	public void update(double elapsedTime) {

		for (Button b : buttons) {
			if (input.mousePressed(1)) {
				System.out.println(1);
			}
		}
		
		float speed = 0.1f;
		
		red += elapsedTime * speed;
		blue += elapsedTime * speed;
		green += elapsedTime * speed;
		
		red %= 1;
		blue %= 1;
		green %= 1;
		
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {

		g2.setXORMode(new Color(red, green, blue));
		background.draw(g2, 0, 0, cam.getWidth(), cam.getHeight());
		bacardi.drawCenter(g2, cam.getWidth() / 2, cam.getHeight() / 2);
		
		for (Button b : buttons) {
			b.draw(g2, cam);
		}
		
		
	}

}
