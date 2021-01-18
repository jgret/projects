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
import game.entity.projectile.Projectile;
import game.gamestate.GameStateType;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.io.Input;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;
import game.util.Direction;

public class Player extends Entity {

	private Input input;
	private Item[] hotbar;
	private int mainhand;
	private double jumpStartTime = -0xABC;
	private double maxJumpTime = 0.1;
	private Direction facing;
	private double maxVel = 20;
	private boolean inInventory;
	
	public Player(World worldIn, Rectangle rect, Image2d image) {
		super(worldIn, rect, image);
		this.input = game.getInput();
		this.hotbar = new Item[9];
		this.facing = Direction.RIGHT;
		this.inventory = new Inventory(this, 200);
	}

	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);

		if (!isDead()) {

			if (input.keyHeld(KeyEvent.VK_A)) {
				this.facing = Direction.LEFT;
				this.walkLeft(elapsedTime);
			}

			if (input.keyHeld(KeyEvent.VK_D)) {
				this.facing = Direction.RIGHT;
				this.walkRight(elapsedTime);
			}

			if (isGrounded()) {
				if (input.keyPressed(KeyEvent.VK_SPACE)) {
					jumpStartTime = Time.getTime();
				}
				
				if (jumpStartTime > 0) {
					if ((Time.getTime() - jumpStartTime) > maxJumpTime) {
						jump(1);
						jumpStartTime = -0xABC;
					}
				}
				
				if (input.keyReleased(KeyEvent.VK_SPACE)) {
					double intensity = (Time.getTime() - jumpStartTime) / maxJumpTime;

					if (intensity <= 1 && intensity >= 0) {
						jump(intensity);
					}
					jumpStartTime = -0xABC;
				}
				
			}

			if (input.keyPressed(KeyEvent.VK_SHIFT)) {
				this.setHeight(1);
				this.addPosition(0, 1.5);
			}

			if (input.keyReleased(KeyEvent.VK_SHIFT)) {
				this.setHeight(2.5);
				this.addPosition(0, -1.5);
			}
			
			if (!inInventory) {
				if (input.mousePressed(0)) {
					Item item = hotbar[mainhand];
					use(item);
				}
				
				int mouseCycle = input.wheelRotations();
				if (mouseCycle != 0) {

					while (mouseCycle < 0) {
						mouseCycle++;
						this.hotbarCycleLeft();
					}

					while (mouseCycle > 0) {
						mouseCycle--;
						this.hotbarCycleRight();
					}

				}
			}
			
			if (input.keyPressed(KeyEvent.VK_G)) {
				Item item = hotbar[mainhand];
				if (item != null) {
					drop(item);
				}
			}
			
		} else {
			game.getGsm().changeGameState(GameStateType.GAMEOVER);
		}
	}
	
	@Override
	public void removeItem(Item i) {
		if (i != null) {
			super.removeItem(i);
			if (hotbarContains(i)) {
				removeFromHotbar(i);
			}
		}
	}
	
	public void drop(Item item) {
		Vector2 pos = this.getCenter();
		System.out.println("Dropping Item " + item.getName() + ":" + item.getUID());
		if (facing == Direction.LEFT) {
			pos = pos.addX(-this.getWidth() - item.getWidth());
			item.setVelX(-5);
		} else {
			pos = pos.addX(this.getWidth() + item.getWidth());
			item.setVelX(5);
		}
		
		this.worldIn.spawnQueue(item, pos);
		this.inventory.remove(item);
		this.removeFromHotbar(item);
	}
	
	public void drop(int i) {
		Item item = inventory.getInventory()[i];
		if (item != null) {
			drop(item);
		}
	}
	
	public void removeFromHotbar(Item item) {
		if (item != null) {
			System.out.println(item.getClass().getSimpleName());
			for (int i = 0; i < hotbar.length; i++) {
				Item it = hotbar[i];
				if (it != null) {
					if (it.compare(item)) {
						hotbar[i] = null;
						return;
					}
				}
			}
		}
	}
	
	public void use(Item item) {
		if (item != null) {
			if(item.onInteract(this, game.getMouseLocationOnScreen().sub(this.getCenter()))) {
				if (item.isRemoveOnUse()) {
					this.inventory.remove(item);
					this.removeFromHotbar(item);
				}
			}
		}
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		super.draw(g2, cam);
	}

	@Override
	public void onOutOfWorld(World world) {
		if (this.getWorldIn().equals(world)) {
			this.setPosition(world.getSpawnPoint());
		}
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
				if (hotbar[i] != null) {
					if (hotbar[i].compare(item)) {
						hotbar[i] = null;
						break;
					}
				}
			}
		}
	}

	@Override
	public void onDead() {
		dropItems();
	}

	@Override
	public String toString() {
		return "Player " + this.getPosition() + " " + this.getVel();
	}

	public void drawInfo(Graphics2D g2, Camera cam) {
		g2.setColor(Color.BLUE);
		g2.fillRoundRect(25, 25, 200, 180, 10, 10);
		g2.setColor(Color.WHITE);
		g2.drawString("VEL:  " + this.getVel().toString(), 50, 50);
		g2.drawString("POS:  " + this.getPosition().toString(), 50, 70);
		g2.drawString("JST:  " + this.jumpStartTime, 50, 90);
		g2.drawString("TIME: " + Time.getTime(), 50, 110);
		g2.drawString("FRC:  " + this.friction, 50, 130);
		g2.drawString("GRV:  " + this.gravity, 50, 150);
		g2.drawString("GND:  " + this.isGrounded(), 50, 170);
		g2.drawString("DIR:  " + this.facing, 50, 190);

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

	@Override
	public void onHit(Projectile p) {
		
	}

	public boolean isInInventory() {
		return inInventory;
	}

	public void setInInventory(boolean inInventory) {
		this.inInventory = inInventory;
	}
	
	public void setHotbarAt(int i, Item item) {
		this.hotbar[i] = item;
	}
	
}
