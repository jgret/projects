/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.data.controller;

import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import game.entity.item.Item;
import game.entity.item.consumable.Food;
import game.io.FileIO;

public class ItemController {

	private HashMap<String, Item> items;
	
	public ItemController() {
		this.items = new HashMap<String, Item>();
	}
	
	public void load(String fname) {
		
		JsonObject file = FileIO.readJson(fname);
		JsonArray foodlist = file.getAsJsonArray("food");
		for (JsonElement e : foodlist) {
			JsonObject foodobj = e.getAsJsonObject();
			String id = foodobj.get("id").getAsJsonPrimitive().getAsString();
			String name = foodobj.get("name").getAsJsonPrimitive().getAsString();
			String img = foodobj.get("img").getAsJsonPrimitive().getAsString();
			Food food = new Food(id, name, FileIO.loadImage(img));
			
			JsonObject bounds = (JsonObject) foodobj.get("bounds");
			double width = bounds.get("width").getAsDouble();
			double height = bounds.get("height").getAsDouble();
			food.set(0, 0, width, height);
			
			food.setAlcohol(foodobj.get("alcohol").getAsFloat());
			food.setHealth(foodobj.get("health").getAsFloat());
			food.setPoison(foodobj.get("poison").getAsFloat());
			food.setRotteness(foodobj.get("rotteness").getAsFloat());
			items.put(id, food);
		
			System.out.println(food);
		}
		
	}
	

	public static void main(String[] args) {
		
		new ItemController().load("item/items.json");
		
	}

}
