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

import game.entity.item.Item;
import game.graphics.Image2d;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;

public abstract class Entity extends GameObject {
	
	protected Inventory inventory;
	protected double maxJumpHeight = 9;
	protected boolean sneaking;
	
	protected double health;
	protected double maxHealth;
	protected double alcohol;
	
	public Entity(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
		this.inventory = new Inventory(this, 64);
		this.sneaking = false;
		this.health = 100;
		this.maxHealth = 100;
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		if (this.health < 0) {
			onDead();
		}
	}
	
	@Override
	public void onCollision(GameObject o) {
		
	}
	
	@Override
	public void onOutOfWorld(World world) {
		this.setPosition(world.getSpawnPoint());
	}
	
	@Override
	public boolean shouldCollide(GameObject g) {
		return true;
	}
	
	@Override
	public void onStaticCollision(Shape s) {
		
	}
	
	public abstract void onItemAdd(Item item);
	public abstract void onItemRemove(Item item);
	public abstract void onDead();
	
	public void addItem(Item i) {
		this.inventory.add(i);
	}
	
	public void removeItem(Item i) {
		this.inventory.remove(i);
	}
	
	public void jump(double intensity) {
		this.accelerate(new Vector2(0, -maxJumpHeight * intensity));
	}
	
	public void sneak() {
		if (!sneaking) {
			sneaking = true;
			double newHeight = this.getHeight() / 2;
			this.setY(this.getY() + newHeight);
			this.setHeight(newHeight);
		}
	}
	
	public void standUp() {
		if (sneaking) {
			sneaking = false;
			double newHeight = this.getHeight() * 2;
			this.setY(this.getY() - getHeight());
			this.setHeight(newHeight);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public double getMaxJumpHeight() {
		return maxJumpHeight;
	}

	public void setMaxJumpHeight(double maxJumpHeight) {
		this.maxJumpHeight = maxJumpHeight;
	}

	public boolean isSneaking() {
		return sneaking;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	public void looseHealth(double health) {
		this.health -= health;
	}
	
	public void regenerateHealth(double health) {
		this.health += health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public boolean isDead() {
		return this.health <= 0;
	}

	public double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(double alcohol) {
		this.alcohol = alcohol;
	}
	
	public void drinkAlcohol(double alcohol) {
		this.alcohol += alcohol;
	}
	
}
