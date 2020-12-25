/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import java.awt.event.KeyEvent;

import game.Game;
import game.data.Rectangle;
import game.data.Vector2;
import game.entity.GameObject;
import game.io.Input;

public class Camera extends Rectangle {

	private Game game;
	private GameObject target;
	private Screen screen;

	public Camera(Screen screen, double x, double y, double width, double height) {
		super(x, y, width, height);
		this.screen = screen;
		this.game = (Game) screen.getGame();
	}

	public void setTarget(GameObject target) {
		this.target = target;
	}

	public void update(double elapsedTime) {
		Vector2 screen = this.getDim();
		double speed = 10;
		if (target == null) {
			Input input = game.getInput();
			if (input.keyHeld(KeyEvent.VK_UP)) {
				this.pos.y -= speed * elapsedTime;
			}
			if (input.keyHeld(KeyEvent.VK_DOWN)) {
				this.pos.y += speed * elapsedTime;
			}
			if (input.keyHeld(KeyEvent.VK_LEFT)) {
				this.pos.x -= speed * elapsedTime;
			}
			if (input.keyHeld(KeyEvent.VK_RIGHT)) {
				this.pos.x += speed * elapsedTime;
			}
			return;
		}
		Rectangle world = target.getWorldIn().getBounds();
		int scale = Screen.TILESIZE;

		double tWidth, tHeight;
		tWidth = screen.getX() / scale;
		tHeight = screen.getY() / scale;

		boolean xScroll, yScroll;

		xScroll = tWidth < world.getWidth();
		yScroll =  tHeight < world.getHeight();

		if (!xScroll && !yScroll) {

			this.pos.x = world.getWidth() / 2 - tWidth / 2;
			this.pos.y = world.getHeight() / 2 - tHeight / 2;


		} else {

			if (xScroll) {
				if ((target.getCenter().x - tWidth / 2) < 0) {
					pos.x = 0;
				} else {
					pos.x = (target.getCenter().x - tWidth / 2);
				}
				
				if (target.getCenter().x  + tWidth / 2 > world.getWidth()) {
					pos.x = world.getWidth() - tWidth;
				}
				
			} else {
				this.pos.x = world.getWidth() / 2 - tWidth / 2;
			}

			if (yScroll) {
				if ((target.getCenter().y - tHeight / 2) < 0) {
					pos.y = 0;
				} else {
					pos.y = (target.getCenter().y - tHeight / 2);
				}
				
				if (target.getCenter().y > world.getHeight() - tHeight / 2) {
					pos.y = world.getHeight() - tHeight;
				}
				
			} else {
				this.pos.y = world.getHeight() / 2 - tHeight / 2;
			}

		}

		//    	if (target.getCenter().getX() > world.getRight() - tWidth / 2) {
		//    		pos.x = world.getRight() / scale - tWidth / 2;
		//    	}
		//    	
		//    	if (target.getCenter().getX() > world.getBot() - tHeight / 2) {
		//    		pos.y = world.getLeft() / scale - tHeight / 2;
		//    	}

	}
	
	public double getPixelOffsetX() {
		return this.getX() * Screen.TILESIZE;
	}
	
	public double getPixelOffsetY() {
		return this.getY() * Screen.TILESIZE;
	}
	
	public double getPixelWidth() {
		return this.getWidth() * Screen.TILESIZE;
	}
	
	public double getPixelHeight() {
		return this.getHeight() * Screen.TILESIZE;
	}
}
