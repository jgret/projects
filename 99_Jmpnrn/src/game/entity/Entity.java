/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity;

import game.data.Rectangle;
import game.entity.item.Item;
import game.graphics.Image2d;
import game.level.World;

public abstract class Entity extends GameObject {

	public Entity(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
	}
	
	@Override
	public void onCollision(GameObject o) {
		
	}
	
	public void add(Item i) {
		
	}
	
	public void remvoe(Item i) {
		
	}
	
}
