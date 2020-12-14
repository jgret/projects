/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

	
		String content = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("res/item/items.json"));
			String line;
			while ((line = in.readLine()) != null) {
				content+= line;
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObject element = (JsonObject) JsonParser.parseString(content);
		JsonArray items = element.getAsJsonArray("items");
		
		for (JsonElement e : items) {
			JsonObject obj = (JsonObject) e;
			System.out.println(obj.getAsJsonPrimitive("id") + " " + obj.getAsJsonPrimitive("name") + " " + obj.getAsJsonPrimitive("img"));
		}
		
	}

}
