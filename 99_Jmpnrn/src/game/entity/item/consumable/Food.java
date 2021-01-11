/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item.consumable;

import java.awt.Shape;

import game.entity.Entity;
import game.entity.item.Item;
import game.graphics.Image2d;
import game.shape.Vector2;

public class Food extends Item {
	private float health;
	private float poison;
	private float alcohol;
	private float rotteness;

	public Food(String name, String id, Image2d image) {
		super(name, id, image);
		this.health = 0.0f;
		this.poison = 0.0f;
		this.alcohol = 0.0f;
		this.rotteness = 0.0f;
	}
	
	public Food(String name, String id, Image2d image, float health, float poison, float alcohol, float rotteness) {
		super(name, id, image);
		this.health = health;
		this.poison = poison;
		this.alcohol = alcohol;
		this.rotteness = rotteness;
	}
	
	public Food(Food food) {
		super(food.getId(), food.getName(), food.getImage());
		this.health = food.health;
		this.poison = food.poison;
		this.alcohol = food.alcohol;
		this.rotteness = food.rotteness;
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getPoison() {
		return poison;
	}

	public void setPoison(float poison) {
		this.poison = poison;
	}

	public float getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(float alcohol) {
		this.alcohol = alcohol;
	}

	public float getRotteness() {
		return rotteness;
	}

	public void setRotteness(float rotteness) {
		this.rotteness = rotteness;
	}

	@Override
	public String toString() {
		return getId() + ":" + getName() + " :: " + getPosition();
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		e.regenerateHealth(health * rotteness);
		e.looseHealth(poison);
		e.drinkAlcohol(alcohol);
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
		super.update(elapsedTime);
	}

	@Override
	public void onStaticCollision(Shape s) {
		
	}
	
}
