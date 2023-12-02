/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity.item.consumable;

import java.awt.Shape;

import game.entity.Entity;
import game.entity.item.Item;
import game.gamestate.GameStateType;
import game.graphics.Image2d;
import game.shape.Vector2;

public class QuestionPotion extends Item {

	public QuestionPotion(String id, String name, Image2d image) {
		super(id, name, image);
		this.setRemoveOnUse(true);
	}

	@Override
	public boolean onInteract(Entity e, Vector2 dir) {
		e.removeItem(this);
		game.getGsm().changeGameState(GameStateType.QUESTION);
		return false;
	}

	@Override
	public void onCollect(Entity e) {
		
	}

	@Override
	public void onRemove(Entity e) {
		
	}

	@Override
	public void onStaticCollision(Shape s) {
		
	}

}
