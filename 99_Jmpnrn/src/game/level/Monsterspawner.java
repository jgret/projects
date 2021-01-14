/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.level;

import java.util.ArrayList;
import java.util.Random;

import game.entity.enemy.Enemies;
import game.io.FileIO;
import game.shape.Vector2;

public class Monsterspawner {

	private ArrayList<Vector2> locations;
	private ArrayList<String> monsters;
	private World world;
	private Random random;
	
	public Monsterspawner(World world, String fmlist) {
		this.locations = new ArrayList<>();
		this.monsters = new ArrayList<>();
		this.world = world;
		
		String text = FileIO.read(fmlist);
		String[] smonsters = text.split("\n");
		for (String s : smonsters) {
			if (Enemies.exists(s)) {
				monsters.add(s.trim());
			}
		}
		
		random = new Random();
	}
	
	public void addLocation(Vector2 location) {
		this.locations.add(location);
	}
	
	public void spawnMonsters(int n) {
		if (this.locations.size() < 1) {
			System.err.println("The list of locations is less than 1. Make sure to add some points...");
		} else {
			ArrayList<Vector2> prev = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				Vector2 location = locations.get(random.nextInt(locations.size()));
				boolean hasBeenUsed = false;

				while (hasBeenUsed && n < locations.size()) {
					for (Vector2 prevLocation : prev) {
						if (location.equals(prevLocation)) {
							hasBeenUsed = true;
						}
					}
				}
				String monster = randomMonster();
				world.spawnQueue(Enemies.get(monster), location);
			}
		}
	}
	
	public String randomMonster() {
		return monsters.get(random.nextInt(monsters.size()));
	}

}
