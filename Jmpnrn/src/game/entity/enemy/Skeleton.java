/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.enemy;

import java.awt.Shape;
import java.util.ArrayList;

import game.Time;
import game.entity.GameObject;
import game.entity.Inventory;
import game.entity.item.Item;
import game.entity.projectile.Projectile;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Rectangle;

public class Skeleton extends Enemy {

	private double lastCrouchTime;
	private double walkTime;
	private boolean walkright;
	private double cooldown;
	private double cooldownJump;
	
	public Skeleton(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image, "loot/skelly.loot");
		this.lastCrouchTime = Time.getTime();
		this.walkright = true;
		this.setFriction(FRICTION);
		this.weapon = null;
		this.cooldown = 0;
		this.cooldownJump = 0;
		this.range = 30;
		this.inventory = new Inventory(this, 5);
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		cooldown += elapsedTime;
		cooldownJump += elapsedTime;
		
		if (cooldownJump > 1) {
			if (isGrounded()) {
				if (willFallDownLeft || willFallDownRight) {
					this.jump(1);
					cooldownJump = 0;
				}
			}
		}
		
		if (target != null) {
			this.maxVel = 20;
			if (target.getCenter().distance(this.getCenter()) < range) {
				if (target.getCenterX() > this.getCenterX()) {
					walkRight(elapsedTime);					
				} else {
					walkLeft(elapsedTime);
				}
				if (weapon != null) {
					if (cooldown >= weapon.getCooldown() * 2) {
						cooldown = 0;
						weapon.onInteract(this, target.getCenter().sub(this.getCenter()));
					}
				}
			}
		} else {
			this.maxVel = 15;
			if (Time.getTime() - lastCrouchTime > 1) {

				if (sneaking) {
					standUp();
				} else {
					sneak();
				}

				lastCrouchTime = Time.getTime();

			}
			
			if (Time.getTime() - walkTime > 2) {
				walkright = !walkright;
				walkTime = Time.getTime();
			}
			
			if (walkright) {
				this.walkRight(elapsedTime);
			} else {
				this.walkLeft(elapsedTime);
			}
		}
	}
	
	@Override
	public void onCollision(GameObject o) {
		super.onCollision(o);
	}
	
	@Override
	public void onOutOfWorld(World world) {
		this.remove = true;
	}
	
	@Override
	public String toString() {
		return "Skeleton " + this.getPosition() + " " + this.getVel();
	}

	@Override
	public void onDead() {
		ArrayList<Item> loot = this.getRandomLoot();
		this.dropItems(loot);
		this.dropItems();
		this.remove = true;
	}

	@Override
	public void onStaticCollision(Shape s) {
		
	}

	@Override
	public void onHit(Projectile p) {
		super.onHit(p);
		this.cooldown = 0;
	}
	
}
