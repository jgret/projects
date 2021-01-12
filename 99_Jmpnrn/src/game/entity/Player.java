/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.Time;
import game.entity.item.Item;
import game.gamestate.GameStateType;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.io.Input;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;

public class Player extends Entity {

	private Input input;
	private Item[] hotbar;
	private int mainhand;
	private double jumpStartTime = -0xABC;
	private double maxJumpTime = 0.1;
	
	public Player(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
		this.input = game.getInput();
		this.hotbar = new Item[9];
	}

	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		
		if (!isDead()) {

			if (input.keyHeld(KeyEvent.VK_A)) {
				this.accelerate(new Vector2(-5 * elapsedTime, 0));
			}

			if (input.keyHeld(KeyEvent.VK_D)) {
				this.accelerate(new Vector2(5 * elapsedTime, 0));
			}

			if (input.keyPressed(KeyEvent.VK_SPACE) && isGrounded()) {
				jumpStartTime = Time.getTime();
			}

			if (jumpStartTime > 0) {
				if ((Time.getTime() - jumpStartTime) > maxJumpTime && isGrounded()) {
					jump(1);
					jumpStartTime = -0xABC;
				}
			}

			if (input.keyReleased(KeyEvent.VK_SPACE) && isGrounded()) {
				double intensity = (Time.getTime() - jumpStartTime) / maxJumpTime;

				if (intensity <= 1 && intensity >= 0) {
					jump(intensity);
				}
				jumpStartTime = -0xABC;
			}

			if (input.keyPressed(KeyEvent.VK_SHIFT)) {
				this.setHeight(1);
				this.addPosition(0, 1.5);
			}

			if (input.keyReleased(KeyEvent.VK_SHIFT)) {
				this.setHeight(2.5);
				this.addPosition(0, -1.5);
			}

			int mouseCycle = input.wheelRotations();
			if (mouseCycle != 0) {

				while (mouseCycle < 0) {
					mouseCycle++;
					hotbarCycleLeft();
				}

				while (mouseCycle > 0) {
					mouseCycle--;
					hotbarCycleRight();
				}

			}
			
			if (input.mousePressed(0)) {
				Item item = hotbar[mainhand];
				if (item != null) {
					if(item.onInteract(this, game.getMouseLocationOnScreen().sub(this.getCenter()))) {
						if (item.isRemoveOnUse()) {
							this.inventory.remove(item);
							this.hotbar[mainhand] = null;
							this.hotbarCycleRight();
						}
					}
				}
			}
		} else {
			game.getGsm().changeGameState(GameStateType.GAMEOVER);
		}
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		super.draw(g2, cam);
	}

	@Override
	public void onOutOfWorld(World world) {
		this.setPosition(world.getSpawnPoint());
	}

	@Override
	public void onItemAdd(Item item) {

		if (!hotbarContains(item)) {
			for (int i = 0; i < hotbar.length; i++) {
				if (hotbar[i] == null) {
					hotbar[i] = item;
					break;
				}
			}
		}
	}

	@Override
	public void onItemRemove(Item item) {
		if (item != null) {
			for (int i = 0; i < hotbar.length; i++) {
				if (hotbar[i].compare(item)) {
					hotbar[i] = null;
					break;
				}
			}
		}
	}

	@Override
	public void onDead() {

	}

	@Override
	public String toString() {
		return "Player " + this.getPosition() + " " + this.getVel();
	}

	public void drawInfo(Graphics2D g2, Camera cam) {
		g2.setColor(Color.BLUE);
		g2.fillRoundRect(25, 25, 200, 160, 10, 10);
		g2.setColor(Color.WHITE);
		g2.drawString("VEL:  " + this.getVel().toString(), 50, 50);
		g2.drawString("POS:  " + this.getPosition().toString(), 50, 70);
		g2.drawString("JST:  " + this.jumpStartTime, 50, 90);
		g2.drawString("TIME: " + Time.getTime(), 50, 110);
		g2.drawString("FRC:  " + this.friction, 50, 130);
		g2.drawString("GRV:  " + this.gravity, 50, 150);
		g2.drawString("GND:  " + this.isGrounded(), 50, 170);

	}

	public boolean hotbarContains(Item item) {

		for (Item i : hotbar) {
			if (i != null) {
				if (item.compare(i)) {
					return true;
				}
			}
		}
		return false;
	}

	public void hotbarCycleRight() {
		this.mainhand++;
		if (this.mainhand >= hotbar.length) {
			mainhand = 0;
		}
	}

	public void hotbarCycleLeft() {
		this.mainhand--;
		if (this.mainhand < 0) {
			mainhand = hotbar.length - 1;
		}
	}

	public int getMainhand() {
		return mainhand;
	}

	public void setMainhand(int mainhand) {
		this.mainhand = mainhand;
	}

	public Item[] getHotbar() {
		return hotbar;
	}

	public void setHotbar(Item[] hotbar) {
		this.hotbar = hotbar;
	}

}
