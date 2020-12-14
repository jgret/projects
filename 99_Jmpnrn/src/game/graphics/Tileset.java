/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset {

	private Image2d[][] tiles;
	private int tileset_rows;
	private int tileset_colls;
	
	public Tileset(String filename, int tilesize) {
		
		try {
			BufferedImage img = ImageIO.read(new File("res/img/" + filename));
			this.tileset_rows = img.getHeight() / tilesize;
			this.tileset_colls = img.getWidth() / tilesize;
			this.tiles = new Image2d[tileset_rows][tileset_colls];
			
			for (int row = 0; row < tileset_rows; row++) {
				for (int coll = 0; coll < tileset_colls; coll++) {
					BufferedImage subImage = img.getSubimage(coll * tilesize, row * tilesize, tilesize, tilesize);
					tiles[row][coll] = new Image2d(subImage);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Image2d get(int n) {
		
		int row = n / tileset_colls;
		int coll = n % tileset_colls;
		return tiles[row][coll];
	}
	
	public Image2d get(int coll, int row) {
		return tiles[row][coll];
	}
	
}
