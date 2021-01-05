/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import game.entity.item.consumable.Food;
import game.io.FileIO;

public class Items {
	
	private HashMap<String, Item> items;
	
	public static final Item NOTHING = new NoItem();
	
	public Items() {
		this.items = new HashMap<>();
	}
	
	public void register(String id, Item item) {
		this.items.put(id, item);
	}
	
	public void loadJSON(String json) {
		
		//load food
		JsonObject file = FileIO.readJson(json);
		JsonArray foodlist = file.getAsJsonArray("food");
		for (JsonElement e : foodlist) {
			JsonObject foodobj = e.getAsJsonObject();
			
			//create food from id, name, img
			String id = foodobj.get("id").getAsJsonPrimitive().getAsString();
			String name = foodobj.get("name").getAsJsonPrimitive().getAsString();
			String img = foodobj.get("img").getAsJsonPrimitive().getAsString();
			Food food = new Food(id, name, FileIO.loadImage(img));
			
			//set bounds
			JsonObject bounds = (JsonObject) foodobj.get("bounds");
			double width = bounds.get("width").getAsDouble();
			double height = bounds.get("height").getAsDouble();
			food.setRect(0, 0, width, height);
			
			//set attributes
			food.setAlcohol(foodobj.get("alcohol").getAsFloat());
			food.setHealth(foodobj.get("health").getAsFloat());
			food.setPoison(foodobj.get("poison").getAsFloat());
			food.setRotteness(foodobj.get("rotteness").getAsFloat());
			if (items.containsKey(id)) {
				System.err.println("Dublicated Item id " + id);
			}
			items.put(id, food);
		}
		
	}
	
	public Item get(String id) {
		Item item;
		
		if ((item = items.get(id)) == null) {
			System.err.printf("Item id %s not registered\n", id);
		}
		
		return items.get(id).clone();
	}
	
	@Override
	public String toString() {
		String ret = "";
		
		Iterator<String> it = items.keySet().iterator();
		while (it.hasNext()) {
			ret += items.get(it.next()) + "\n";
		}
		
		return ret;
	}
	
}
