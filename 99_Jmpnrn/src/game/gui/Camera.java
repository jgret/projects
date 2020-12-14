/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gui;

import game.data.Rectangle;
import game.data.Vector2;
import game.entity.GameObject;

public class Camera extends Rectangle {

	private GameObject target;
	private GameScreen screen;

	public Camera(GameScreen screen, double x, double y, double width, double height) {
		super(x, y, width, height);
		this.screen = screen;
	}

	public void setTarget(GameObject target) {
		this.target = target;
	}

	public void update(double elapsedTime) {
		Vector2 screen = this.getDim();
		Rectangle world = target.getWorldIn().getBounds();
		int scale = this.screen.getScale();

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

	public double getX(double scale) {
		return this.pos.x * scale;
	}

	public double getY(double scale) {
		return this.pos.y * scale;
	}

}
