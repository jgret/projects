/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item;

import java.awt.Graphics2D;

import game.entity.Entity;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.level.World;

public class NoItem extends Item {

	public NoItem() {
		super("game_noitem", "Nothing", new Image2d(1, 1));
	}

	@Override
	public void onInteract(Entity e) {
		
	}

	@Override
	public void onCollect(Entity e) {
		
	}

	@Override
	public void onRemove(Entity e) {
		
	}
	
	@Override
	public void update(double elapsedTime) {

	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {

	}

	@Override
	public void onOutOfWorld(World world) {
		this.remove = true;
	}
	
}
