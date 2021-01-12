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
import game.graphics.Image2d;
import game.shape.Vector2;

public class Sword extends Weapon {

	public Sword(String id, String name, Image2d image, double damage, double cooldown) {
		super(id, name, image, damage, cooldown);
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		return false;
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
