/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.projectile;

import java.awt.Shape;

import game.entity.Entity;
import game.entity.GameObject;
import game.graphics.Image2d;
import game.graphics.Images;
import game.io.FileIO;
import game.shape.Rectangle;
import game.shape.Vector2;

public class Arrow extends Projectile {
	
	public Arrow(Entity owner, double vel, Vector2 dir, double damage) {
		super(owner, new Rectangle(vel, vel, 0.5, 0.5), Images.ARROW, vel, dir, damage);
		this.setGravity(9.81);
		this.setFriction(0.10);
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		if (this.vel.isZero()) {
			this.remove = true;
		}
	}

	@Override
	public void hitEntity(Entity e) {
		if (!e.equals(owner)) {
			e.loseHealth(this.getDamage());
			this.remove = true;
		}
	}

	@Override
	public boolean shouldCollide(GameObject g) {
		return false;
	}

	@Override
	public void onStaticCollision(Shape s) {
		this.setVel(0, 0);
	}

}
