/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item;

import game.data.Rectangle;
import game.entity.Entity;
import game.entity.GameObject;
import game.graphics.Image2d;
import game.level.World;

public abstract class Item extends GameObject {
	
	private String id;
	private String name;
	private float value;
	
	public Item(String id, String name, Image2d image) {
		super(null, new Rectangle(0, 0, 1, 1), image);
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

	public abstract void onInteract(Entity e);
	
	@Override
	public void onCollision(GameObject o) {
		if (o instanceof Entity) {
			Entity entity = (Entity) o;
			World world = entity.getWorldIn();
			world.remove(this);

			entity.add(this);
		}
	}

}
