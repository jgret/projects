/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item.weapon;

import java.awt.Shape;

import game.entity.Entity;
import game.entity.projectile.Arrow;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;

public class Bow extends Weapon {
	
	private Image2d arrowImage;
	private Rectangle arrowBounds;
	private double vel;

	public Bow(String id, String name, Image2d image, Image2d arrow, Rectangle bounds, double vel, double damage, double duaration) {
		super(id, name, image, damage, duaration);
		this.arrowImage = arrow;
		this.arrowBounds = bounds;
		this.vel = vel;
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		if (isReady()) {
			this.startCooldown();
			World world = e.getWorldIn();
			Arrow arrow = new Arrow(e, vel, dir, getDamage());
			arrow.setRect(arrowBounds);
			arrow.setImage(arrowImage);
			Vector2 pos = e.getCenter();
			pos.setX(pos.getX() -arrow.getWidth() / 2);
			pos.setY(pos.getY() -arrow.getHeight() / 2);
			world.spawnQueue(arrow, pos);
			return true;
		}
		return false;
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
	}

	@Override
	public void onCollect(Entity e) {
		
	}

	@Override
	public void onRemove(Entity e) {
		
	}

	@Override
	public void onStaticCollision(Shape s) {
		
	}

}
