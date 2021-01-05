/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity;

import java.util.ArrayList;

import game.entity.item.Item;

public class Inventory {

	private Item[] inventory;
	private Entity entity;

	public Inventory(Entity e, int size) {
		this.inventory = new Item[size];
		this.entity = e;
	}
	
	public boolean add(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				inventory[i] = item;
				entity.onItemAdd(item);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean remove(Item item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].equals(item)) {
				inventory[i] = null;
				entity.onItemRemove(inventory[i]);
				return true;
			}
		}
		return false;
	}
	
	public Item remove(String id) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i].getId().equals(id)) {
				Item ret = inventory[i];
				inventory[i] = null;
				entity.onItemRemove(inventory[i]);
				return ret;
			}
		}
		return null;
	}
	
	public Item[] getInventory() {
		return inventory;
	}

	public void setInventory(Item[] inventory) {
		this.inventory = inventory;
	}
	
}
