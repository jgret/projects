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
import game.entity.GameObject;
import game.io.Input;
import game.shape.Rectangle;
import game.shape.Vector2;

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
				this.setY(this.getY() - speed * elapsedTime);
			}
			if (input.keyHeld(KeyEvent.VK_DOWN)) {
				this.setY(this.getY() + speed * elapsedTime);
			}
			if (input.keyHeld(KeyEvent.VK_LEFT)) {
				this.setX(this.getX() - speed * elapsedTime);
			}
			if (input.keyHeld(KeyEvent.VK_RIGHT)) {
				this.setX(this.getX() + speed * elapsedTime);;
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

			this.setX(world.getWidth() / 2 - tWidth / 2);
			this.setY(world.getHeight() / 2 - tHeight / 2);


		} else {

			if (xScroll) {
				if ((target.getCenter().x - tWidth / 2) < 0) {
					this.setX(0);
				} else {
						this.setX(target.getCenter().x - tWidth / 2);
			
					
				}
				
				if (target.getCenter().x  + tWidth / 2 > world.getWidth()) {
					this.setX(world.getWidth() - tWidth);
				}
				
			} else {
				this.setX(world.getWidth() / 2 - tWidth / 2);
			}

			if (yScroll) {
				if ((target.getCenter().y - tHeight / 2) < 0) {
					this.setY(0);
				} else {
					this.setY((target.getCenter().y - tHeight / 2));

					
					
				}
				
				if (target.getCenter().y > world.getHeight() - tHeight / 2) {
					this.setY((world.getHeight() - tHeight));
				}
				
			} else {
				this.setY(world.getHeight() / 2 - tHeight / 2);
			}

		}

		//    	if (target.getCenter().getX() > world.getRight() - tWidth / 2) {
		//    		pos.x = world.getRight() / scale - tWidth / 2;
		//    	}
		//    	
		//    	if (target.getCenter().getX() > world.getBot() - tHeight / 2) {
		//    		pos.y = world.getLeft() / scale - tHeight / 2;
		//    	}
		
//		double dx = target.getCenterX() - this.getCenterX();
//		this.setX(getX() - dx);
//
//		double dy = target.getCenterY() - this.getCenterY();
//		this.setY(getY() - dy);
		
		
	}
	
	public double getPixelOffsetX() {
		return this.getX() * Screen.TILESIZE;
	}
	
	public double getPixelOffsetY() {
		return this.getY() * Screen.TILESIZE;
	}
	
}
