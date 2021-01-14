/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.projectile;

import java.awt.Shape;

import game.entity.Entity;
import game.entity.GameObject;
import game.graphics.Images;
import game.shape.Rectangle;
import game.shape.Vector2;
import game.util.Direction;

public class SwordHit extends Projectile {
	
	private Direction direction;
	private double duaration;
	private double timer;
	private boolean hasHit;
	private double knockback;
	
	public SwordHit(Entity owner, double damage, double duaration, double knockback, Direction dir) {
		super(owner, new Rectangle(0, 0, 1.5, 1.5), Images.SWORD_HIT, 0, new Vector2(0, 0), damage);
		this.direction = dir;
		this.duaration = duaration;
		this.timer = 0;
		this.staticCollision = false;
		this.knockback = knockback;
	}

	@Override
	public void hitEntity(Entity e) {
		if (!e.equals(owner) && !hasHit) {
			e.loseHealth(this.getDamage());
			
			Vector2 kb;
			switch (direction) {
				case DOWN:
					kb = new Vector2(0, -1).mul(knockback);
					break;
				case LEFT:
					kb = new Vector2(-1, 0).mul(knockback);
					break;
				case RIGHT:
					kb = new Vector2(1, 0).mul(knockback);
					break;
				case UP:
					kb = new Vector2(0, 1).mul(knockback);
					break;
				default:
					kb = new Vector2(0, 0);
					break;
				
			}
			e.setVel(e.getVel().add(kb));
			
			hasHit = true;
		}
	}
	
	@Override
	public void update(double elapsedTime) {
		timer += elapsedTime;
		if (timer < duaration) {
			switch (direction) {
				case DOWN: {
					this.setCenterX(owner.getCenterX());
					this.setTop(owner.getBot());
					break;
				}
				case LEFT: {
					this.setCenterY(owner.getCenterY());
					this.setRight(owner.getLeft());
					break;
				}
				case RIGHT: {
					this.setCenterY(owner.getCenterY());
					this.setLeft(owner.getRight());
					break;
				}
				case UP: {
					this.setCenterX(owner.getCenterX());
					this.setBot(owner.getTop());
					break;
				}
				default: {
					break;
				}
			}
		} else {
			this.remove = true;
		}
	}

	@Override
	public boolean shouldCollide(GameObject g) {
		return false;
	}

	@Override
	public void onStaticCollision(Shape s) {
		
	}

}
