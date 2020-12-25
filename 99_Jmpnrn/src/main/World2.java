/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class World2 {
	
	private BufferedImage world;
	
	private int tileWidth;
	private int tileHeight;
	private int tileSize;
	
	private int[][] background;
	private int[][] foreground;
	
	public World2() {
		
	}
	
	public void load(String fname) {
		
		try {
			File file = new File("res/world/" + fname + ".tmx");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			Element map = doc.getDocumentElement();
			
			tileWidth = Integer.parseInt(map.getAttribute("width"));
			tileHeight = Integer.parseInt(map.getAttribute("height"));
			tileSize = Integer.parseInt(map.getAttribute("tilewidth"));

			NodeList layers = doc.getElementsByTagName("layer");
			for (int i = 0; i < layers.getLength(); i++) {
				
				Element layer = (Element) layers.item(i);
				Element data = (Element) layer.getElementsByTagName("data").item(0);
				
				if (layer.getAttribute("name").contentEquals("background")) {
					background = toIntArray(data.getTextContent().trim());
				} else if (layer.getAttribute("name").contentEquals("foreground")) {
					foreground = toIntArray(data.getTextContent().trim());
				}
				
			}
			
			
			for (int i = 0; i < background.length; i++) {
				for (int j = 0; j < background[i].length; j++) {
					System.out.print(background[i][j]);
				}
				System.out.println();
			}
			
			for (int i = 0; i < foreground.length; i++) {
				for (int j = 0; j < foreground[i].length; j++) {
					System.out.print(foreground[i][j]);
				}
				System.out.println();
			}
			
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private int[][] toIntArray(String sdata) {
		int[][] data;
		int width, height;

		String[] rows = sdata.split("\n");
		height = rows.length;
		width = rows[0].split(",").length;
		data = new int[height][width];

		for (int i = 0; i < rows.length; i++) {
			String[] row_values = rows[i].split(",");
			for (int j = 0; j < row_values.length; j++) {
				data[i][j] = Integer.parseInt(row_values[j]);
			}
		}

		return data;
	}
	
	public static void main(String[] args) {
		World2 world = new World2();
		world.load("jamila");
	}
	
}
