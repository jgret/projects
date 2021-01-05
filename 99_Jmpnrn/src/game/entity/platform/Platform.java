/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.platform;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Time;
import game.entity.GameObject;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.graphics.Screen;
import game.level.World;
import game.shape.Rectangle;

public class Platform extends GameObject {
	
	private Color color;
	private double timer;
	
	public Platform(World worldIn, Rectangle r, Color c) {
		super(worldIn, r, null);
		this.color = c;
		this.setFriction(0);
		this.setGravity(0);
		this.getVel().setY(1);
	}

	@Override
	public void update(double elapsedTime) {
		
		if (Time.getTime() - timer > 1) {
			
			if (this.getVel().getY() > 0) {
				this.getVel().setY(-1);
				this.getVel().setX(-1);
			} else {
				this.getVel().setY(1);
				this.getVel().setX(1);
			}
			
			timer = Time.getTime();
		}
		
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		int tilesize = Screen.TILESIZE;
		g2.setColor(color);
		g2.fillRect((int) (this.getX() * tilesize - cam.getPixelOffsetX()), (int) (this.getY() * tilesize - cam.getPixelOffsetY()), (int) (getWidth() * tilesize), (int) (getHeight() * tilesize));
	}
	
	@Override
	public void onCollision(GameObject o) {
		
	}

	@Override
	public void onOutOfWorld(World world) {
		
	}
	
	@Override
	public String toString() {
		return "Platform " + getPosition() + " " + getVel();
	}

}
