/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

public class TiledTileSet {

	private TileSet[] sets;
	
	public TiledTileSet() {
		this.sets = new TileSet[0];
	}
	
	public void addTileset(TileSet t) {
		int length = sets.length + 1;
		TileSet[] newSets = new TileSet[length];
		
		for (int i = 0; i < sets.length; i++) {
			newSets[i] = sets[i];
		}
		
		newSets[newSets.length -1] = t;
		sets = newSets;
	}
	
	public Image2d get(int n) {
		int sum = 0;
		int index = 0;
		
		while (sum < n && n > sets[index].getTileCount() + sum) {
			sum += sets[index].getTileCount();
			index++;
		}
		
		return sets[index].get(n - sum);
	}
	
}
