/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.data.Rectangle;
import game.data.Vector2;
import game.entity.Entity;
import game.graphics.Image2d;
import game.gui.Camera;
import game.io.Input;
import game.level.World;

public class Player extends Entity {
	
	private Input input;

	public Player(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
		this.input = game.getInput();
	}
	
	private int inAir = 0;

	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		
		if (input.keyHeld(KeyEvent.VK_A)) {
			this.accelerate(new Vector2(-1.5, 0));
		}
		
		if (input.keyHeld(KeyEvent.VK_D)) {
			this.accelerate(new Vector2(1.5, 0));
		}
		
		if (input.keyHeld(KeyEvent.VK_SPACE) && inAir < 1200) {
			this.grounded = false;
			this.accelerate(new Vector2(0, -(10 - inAir / 100)));
			inAir++;
		}
		
		if (grounded) {
			inAir = 0;
		}
		
		if (input.keyPressed(KeyEvent.VK_SHIFT)) {
			this.dim.y = 1;
			this.pos.y += 1.5;
		}
		
		if (input.keyReleased(KeyEvent.VK_SHIFT)) {
			this.dim.y = 2.5;
			this.pos.y -= 1.5;
		}
		
		this.pos = pos.add(vel.mul(elapsedTime));
		
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam, int scale) {
		super.draw(g2, cam, scale);
		
		g2.drawString("" + grounded, (int) (getX() * scale -cam.getX(scale)), (int) (getY() * scale - cam.getY(scale)));
		
	}

}
