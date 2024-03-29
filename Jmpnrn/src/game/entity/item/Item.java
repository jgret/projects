/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.entity.GameObject;
import game.graphics.Image2d;
import game.graphics.Screen;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;

public abstract class Item extends GameObject {
		
	private static long nextUid = 0;

	private String id;
	private String name;
	private float value;
	private long uid;
	
	private boolean removeOnUse;
	private boolean consumable;
	private boolean dropable;
	
	public Item(String id, String name, Image2d image) {
		super(null, new Rectangle(0, 0, 1, 1), image);
		this.uid = nextUid++;
		this.priority = 0;
		this.id = id;
		this.name = name;
		this.value = (float) 0.0;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public abstract boolean onInteract(Entity e, Vector2 dir);
	public abstract void onCollect(Entity e);
	public abstract void onRemove(Entity e);
	
	@Override
	public void onCollision(GameObject o) {
		if (o instanceof Entity) {
			Entity entity = (Entity) o;
			World world = entity.getWorldIn();
			if (entity.addItem(this)) {
				world.remove(this);
			}
		}
	}
	
	@Override
	public void onOutOfWorld(World world) {
		this.setPosition(world.getSpawnPoint());
	}
	
	@Override
	public boolean shouldCollide(GameObject g) {
		return false;
	}
	
	@Override
	public Item clone() {
		Item clone = (Item) super.clone();
		clone.uid = nextUid++;
		return clone;
	}
	
	public boolean compare(Item i) {
		return this.uid == i.uid;
	}

	public long getUID() {
		return uid;
	}

	public boolean isConsumable() {
		return consumable;
	}

	public void setConsumable(boolean consumable) {
		this.consumable = consumable;
	}

	public boolean isDropable() {
		return dropable;
	}

	public void setDropable(boolean dropable) {
		this.dropable = dropable;
	}

	public boolean isRemoveOnUse() {
		return removeOnUse;
	}

	public void setRemoveOnUse(boolean removeOnUse) {
		this.removeOnUse = removeOnUse;
	}

	public void drawInfo(Graphics2D g2, int x, int y) {
		g2.setColor(Color.WHITE);
		g2.setFont(Screen.FONT_SMALL.deriveFont(13f).deriveFont(Font.BOLD));
		g2.drawString(name, x + 10, y);
		g2.drawString("Value: " + value, x + 10, y + 13);
		g2.drawString("UID: " + uid, x + 10, y + 26);
	}
	
}
