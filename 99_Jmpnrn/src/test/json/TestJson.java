/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test.json;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestJson {
	
	public static class Item {
		
		private String id;
		private String name;
		
		public Item(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return id + " " + name;
		}
		
	}
	
	public static class ItemManager {
		
		public ArrayList<Item> items;
		
		public void load(String data) {

		}
		
		public void print() {
			for (Item item : items) System.out.println(item);
		}
		
		
	}
	
	public static void main(String[] args) {
		
		Item i = new Item("game_sword", "sword");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(i));
		
		
	}

}
