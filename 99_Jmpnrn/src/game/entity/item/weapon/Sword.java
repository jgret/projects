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
import game.entity.projectile.SwordHit;
import game.graphics.Image2d;
import game.graphics.Images;
import game.level.World;
import game.shape.Vector2;
import game.util.Direction;

public class Sword extends Weapon {

	public Sword(String id, String name, Image2d image, double damage, double cooldown) {
		super(id, name, image, damage, cooldown);
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		if (isReady()) {
			startCooldown();
			World world = e.getWorldIn();
			
			Vector2 u = dir.unitvect();
			double s = Math.sqrt(2) * 0.3;
			double x = u.getX();
			double y = u.getY();
			Direction direction;
			
			if (x > -s && x < s) {
				if (y > 0) {
					direction = Direction.DOWN;
				} else {
					direction = Direction.UP;
				}
			} else {
				if (x > 0) {
					direction = Direction.RIGHT;
				} else {
					direction = Direction.LEFT;
				}
			}
			
			world.spawnQueue(new SwordHit(e, 10, this.getCooldown(), 0.1, direction), dir);
		}
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
