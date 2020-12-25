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

	private ArrayList<Item> inventory;

	public Inventory() {
		this.inventory = new ArrayList<Item>();
	}
	
	public void add(Item i) {
		this.inventory.add(i);
	}
	
	public void remove(Item i) {
		this.inventory.remove(i);
	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}
	
}
