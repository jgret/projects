/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.enemy;

import game.Game;
import game.entity.GameObject;
import game.graphics.Images;
import game.shape.Rectangle;

public class Enemies {
	
	public static Skeleton createSkelly() {
		Skeleton skelly = new Skeleton(null, new Rectangle(0, 0, 1, 2), Images.SKELLY);
		skelly.addItem(Game.instance.getItems().get("game_bow_skelly"));
		return skelly;
	}

	public static GameObject get(String monster) {
		switch (monster) {
			case "skelly": {
				return createSkelly();
			}
			default:
				return createSkelly();
		}
	}

	public static boolean exists(String s) {
		switch (s) {
			case "skelly": {
				return true;
			}
			default:
				return false;
		}
		
	}

}
