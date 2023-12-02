/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item.weapon;

import game.Time;
import game.entity.Entity;
import game.entity.item.Item;
import game.graphics.Image2d;
import game.shape.Vector2;

public abstract class Weapon extends Item {
	
	private double damage;
	private double cooldown;
	private double lastTime;

	public Weapon(String id, String name, Image2d image, double damage, double cooldown) {
		super(id, name, image);
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	public void startCooldown() {
		lastTime = Time.getTime();
	}
	
	public boolean isReady() {
		if (Time.getTime() - lastTime > cooldown || lastTime == -1) {
			lastTime = -1;
			return true;
		}
		return false;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}
	
}
