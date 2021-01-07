/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.enemy;

import game.Time;
import game.entity.Entity;
import game.entity.GameObject;
import game.entity.item.Item;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;
public class Skeleton extends Entity {

	private double lastCrouchTime;
	private double walkTime;
	private boolean walkright;
	
	public Skeleton(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
		this.lastCrouchTime = Time.getTime();
		this.walkright = true;
		this.setFriction(0);
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		
		if (Time.getTime() - lastCrouchTime > 1) {
			
			if (sneaking) {
				standUp();
			} else {
				sneak();
			}
			
			lastCrouchTime = Time.getTime();
			
		}
		
		if (Time.getTime() - walkTime > 2) {
			
			if (walkright) {
				this.getVel().setX(1);
			} else {
				this.getVel().setX(-1);
			}
			
			walkright = !walkright;
			walkTime = Time.getTime();
			
//			this.jump(0.5);
			
		}
		
	}
	
	@Override
	public void onCollision(GameObject o) {
		super.onCollision(o);
	}
	
	
	@Override
	public void onOutOfWorld(World world) {
		super.onOutOfWorld(world);
	}
	
	@Override
	public String toString() {
		return "Skeleton " + this.getPosition() + " " + this.getVel();
	}

	@Override
	public void onItemAdd(Item i) {
		
	}

	@Override
	public void onItemRemove(Item i) {
		
	}

	@Override
	public void onDead() {
		
	}
	
}
