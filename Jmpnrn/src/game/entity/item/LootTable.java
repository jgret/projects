/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import game.Game;
import game.io.FileIO;

public class LootTable {
	
	private HashMap<String, Integer> loot;
	private Random random;
	private Items items;
	
	public LootTable(String fname) {
		this.loot = new HashMap<>();
		this.random = new Random();
		this.items = Game.instance.getItems();
		String sloot = FileIO.read(fname);
		String[] lootLines = sloot.split("\n");
		for (String s : lootLines) {
			String[] parts = s.split(":");
			if (parts.length != 2) {
				System.err.println("Syntax error");
			} else {
				loot.put(parts[0], 100 - Integer.parseInt(parts[1]));
			}
		}
	}
	
	public ArrayList<Item> getRandomLoot() {
		ArrayList<Item> aloot = new ArrayList<>();
		Iterator<String> items = loot.keySet().iterator(); //by Elim 
		while (items.hasNext()) {
			String item = items.next();
			int likelynessOfBeeingHappening = loot.get(item);
			int rnd = random.nextInt(100);
			if (likelynessOfBeeingHappening <= rnd) {
				aloot.add(this.items.get(item));
			}
		}
		return aloot;
	}

}
