/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item.weapon;

import game.entity.item.Item;
import game.graphics.Image2d;

public abstract class Weapon extends Item {

	public Weapon(String id, String name, Image2d image) {
		super(id, name, image);
	}

}
