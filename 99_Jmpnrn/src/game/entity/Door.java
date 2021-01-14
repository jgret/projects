/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity;

import java.awt.Shape;
import java.awt.event.KeyEvent;

import game.entity.platform.Platform;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Rectangle;

public class Door extends GameObject {
	
	private String target;

	public Door(World worldIn, Rectangle r, Image2d image, String target) {
		super(worldIn, r, image);
		this.target = target;
		this.setGravity(0);
		this.setFriction(0);
		this.setStaticCollision(false);
		this.setSlopeCollision(false);
		this.setBoxCollision(false);
	}

	@Override
	public boolean shouldCollide(GameObject g) {
		return false;
	}

	@Override
	public void onStaticCollision(Shape s) {
		
	}

	@Override
	public void onCollision(GameObject o) {
		if (o instanceof Player) {
			if (game.getInput().keyPressed(KeyEvent.VK_W)) {
				World world = game.getLevels().get(target);
				world.spawn(o, world.getSpawnPoint());
			}
		}
	}

	@Override
	public void onOutOfWorld(World world) {
		
	}
	
	

}
