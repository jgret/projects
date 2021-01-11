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
import game.shape.Vector2;

public class Bow extends Weapon {

	public Bow(String id, String name, Image2d image) {
		super(id, name, image);
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		World world = e.getWorldIn();
		Arrow arrow = new Arrow(e, 10, dir);
		world.spawnQueue(arrow, e.getCenter());
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
