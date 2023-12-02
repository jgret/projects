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
import java.awt.Shape;

import game.entity.Entity;
import game.entity.GameObject;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Vector2;

public class NoItem extends Item {

	public NoItem() {
		super("game_noitem", "Nothing", new Image2d(1, 1));
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		return true;
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

	@Override
	public void onStaticCollision(Shape s) {
		
	}

}
