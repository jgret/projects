/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import game.Game;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.graphics.Images;
import game.util.StringUtil;

public class GameStateIntro extends GameState {
	private Image2d background = Images.HOLY_GRAIL;
	private double introTimer;
	
	public GameStateIntro(Game game) {
		super(game);
	}

	@Override
	public void init() {
		
	}
	
	float alpha = 0.0f;
	boolean up = true;
	
	@Override
	public void update(double elapsedTime) {
		
		if (introTimer >= 5) {
			game.getGsm().setGameState(GameStateType.HOME_MENU);
		} 
		introTimer += elapsedTime;
		
	}

	
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
//	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, (int) cam.getWidth(), (int) cam.getHeight());

		g2.setColor(Color.RED);
		g2.drawString("time: " + StringUtil.digits(introTimer, 2), 25, 25);
		
		g2.translate(cam.getWidth() / 2, cam.getHeight() / 2);
		g2.rotate(introTimer * 2);
		
		background.draw(g2, -background.getWidth() / 2, -background.getHeight() / 2);
		
	    //set the opacity

	}

}
