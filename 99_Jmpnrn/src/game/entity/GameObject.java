/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.entity;

import java.awt.Graphics2D;

import game.Game;
import game.data.Rectangle;
import game.data.Vector2;
import game.graphics.Image2d;
import game.gui.Camera;
import game.io.Input;
import game.level.World;
import game.util.MathUtil;

public abstract class GameObject extends Rectangle {

	public static final double GRAVITY = 3.7;
	public static final double FRICTION = 0.56;
	
	public static final int PUSHOUT_NONE = 0; //this would be quite questionable, think about it
	public static final int PUSHOUT_UP = 1;
	public static final int PUSHOUT_DOWN = 2;
	public static final int PUSHOUT_LEFT = 3;
	public static final int PUSHOUT_RIGHT = 4;
	
	protected int priority;
	protected Input input;
	protected Game game;
	protected World worldIn;
	protected Image2d image;
	protected Vector2 vel;
	protected double friction;
	protected double gravity;
	protected boolean grounded;
	
	protected boolean remove;

	public GameObject(World worldIn, Rectangle r, Image2d image) {
		super(r);
		this.worldIn = worldIn;
		this.game = worldIn.getGame();
		this.input = game.getInput();
		this.image = image;
		this.vel = new Vector2(0, 0);
		this.gravity = GRAVITY;
		this.friction = FRICTION;
	}

	@Override
	public void draw(Graphics2D g2, Camera cam, int scale) {
		image.draw(g2, getX() * scale - cam.getX(scale), getY() * scale - cam.getY(scale), getWidth() * scale, getHeight() * scale);
	}

	public void accelerate(Vector2 acc) {
		this.vel = vel.add(acc);
	}
	
	public void move(double elapsedTime) {
		this.pos = pos.add(vel.mul(elapsedTime));
	}
	
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}
	
	public boolean isGrounded() {
		return this.grounded;
	}
	
	public void update(double elapsedTime) {
		this.move(elapsedTime);
		vel = vel.mul(friction);
		this.accelerate(new Vector2(0, gravity));
	}
	
	public int pushout(Rectangle master) {
		
		double dxl, dxr, dyt, dyb;
		dxl = Math.abs(this.getRight() - master.getLeft());
		dxr = Math.abs(master.getRight() - this.getLeft());
		dyt = Math.abs(this.getBot() - master.getTop());
		dyb = Math.abs(master.getBot() - this.getTop());

		if (MathUtil.min(dxl, dxr, dyt, dyb)) {
			// collision left
			this.getPos().x = master.getLeft() - this.getWidth();
			return PUSHOUT_LEFT;
		} else

		if (MathUtil.min(dxr, dxl, dyt, dyb)) {
			// collision right
			this.getPos().x = master.getRight();
			return PUSHOUT_RIGHT;
		} else

		if (MathUtil.min(dyt, dxr, dxl, dyb)) {
			// collision top
			this.getPos().y = master.getTop() - this.getHeight();
			return PUSHOUT_UP;
		} else

		if (MathUtil.min(dyb, dxr, dyt, dxl)) {
			// collision bot
			this.getPos().y = master.getBot();
			return PUSHOUT_DOWN;
		}
		
		return 0;
		
	}
	
	public World getWorldIn() {
		return worldIn;
	}
	
	public void setWorldIn(World worldIn) {
		this.worldIn = worldIn;
	}
	
	public int getPriority() {
		return this.priority;
	}

	public Vector2 getVel() {
		return vel;
	}

	public void setVel(Vector2 vel) {
		this.vel = vel;
	}

	public Game getGame() {
		return game;
	}

	public Image2d getImage() {
		return image;
	}

	public boolean isRemove() {
		return remove;
	}

}
