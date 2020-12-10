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

public class Player extends Entity {

	public Player(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
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
		
		this.pos = pos.add(vel.mul(elapsedTime));
		
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam, int scale) {
		super.draw(g2, cam, scale);
		
		g2.drawString("" + grounded, (int) (getX() * scale), (int) (getY() * scale));
		
	}

}
