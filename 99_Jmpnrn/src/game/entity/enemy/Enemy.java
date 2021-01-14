/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.enemy;

import java.util.ArrayList;

import game.entity.Entity;
import game.entity.item.Item;
import game.entity.item.LootTable;
import game.entity.item.weapon.Weapon;
import game.entity.projectile.Projectile;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;

public class Enemy extends Entity {
	
	protected Entity target;
	protected Weapon weapon;
	private LootTable lt;
	
	protected double range;
	protected boolean willFallDownLeft;
	protected boolean willFallDownRight;

	public Enemy(World worldIn, Rectangle rect, Image2d image, String ltFile) {
		super(worldIn, rect, image);
		this.range = 10;
		this.lt = new LootTable(ltFile);
	}

	@Override
	public void onItemAdd(Item item) {
		if (weapon == null) {
			if (item instanceof Weapon) {
				this.weapon = (Weapon) item;
			}
		}
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		this.willFallDownLeft = !worldIn.checkCollision(this.checkPointLeft());
		this.willFallDownRight = !worldIn.checkCollision(this.checkPointRight()); 
			
	}
	
	public ArrayList<Item> getRandomLoot() {
		return lt.getRandomLoot();
	}

	@Override
	public void onItemRemove(Item item) {
		
	}

	@Override
	public void onDead() {
		this.remove = true;
	}

	@Override
	public void onHit(Projectile p) {
		this.setTarget(p.getOwner());
	}
	
	public Vector2 checkPointLeft() {
		return new Vector2(this.getLeft() - 0.5, this.getBot() + 0.1);
	}
	
	public Vector2 checkPointRight() {
		return new Vector2(this.getRight() + 0.5, this.getBot() + 0.1);
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
}
