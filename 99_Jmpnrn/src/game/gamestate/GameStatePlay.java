/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.Game;
import game.entity.GameObject;
import game.entity.Player;
import game.entity.enemy.Skeleton;
import game.entity.item.Item;
import game.entity.item.weapon.Bow;
import game.graphics.Camera;
import game.graphics.Images;
import game.graphics.Screen;
import game.io.FileIO;
import game.io.Input;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;

public class GameStatePlay extends GameState {

	private World world;
	private Player player;
	private Screen screen;
	private Input input;
	
	private boolean showActors;

	public GameStatePlay(Game game) {
		super(game);
		this.screen = game.getScreen();
		this.input = game.getInput();
		this.showActors = false;
	}

	@Override
	public void init() {
		world = new World(game);
		world.load("slopes");
		world.init();

		Bow bow = new Bow("game_bow", "Bow", FileIO.loadImage("img/game_bow.png"));
		player = new Player(world, new Rectangle(3, 3, 1, 2.5), FileIO.loadImage("img/bacardi.png"));
		player.addItem(bow);
		world.spawn(player, world.getSpawnPoint());

		Skeleton skelly = new Skeleton(world, new Rectangle(0, 0, 1, 2), FileIO.loadImage("img/skeleton.png"));
		world.spawn(skelly, world.getSpawnPoint().sub(new Vector2(1, 0)));
		
		//    	Platform platform = new Platform(world, new Rectangle(0, 0, 5, 1), Color.RED);
		//    	world.spawn(platform, new Vector2(76, 42));

		screen.getCam().setTarget(player);
	}
	
	@Override
	public void update(double elapsedTime) {
		globalHotKeys();
		world.update(elapsedTime);
		screen.getCam().update(elapsedTime);
		float speed = 1f;
		
	}

	public void globalHotKeys() {

		if (input.keyPressed(KeyEvent.VK_ENTER)) {
			world.spawn(new Skeleton(world, new Rectangle(0, 0, 4, 2), FileIO.loadImage("img/skeleton.png")), world.getSpawnPoint());
		}
		
		if (input.keyPressed(KeyEvent.VK_ESCAPE)) {
			gsm.changeGameState(GameStateType.HOME_MENU);
		}
		
		if (input.keyPressed(KeyEvent.VK_I)) {
			gsm.changeGameState(GameStateType.QUESTION);
		}
		
		if (input.keyPressed(KeyEvent.VK_F1)) {
			this.showActors = !showActors;
		}

	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		
//		g2.setXORMode(new Color(red, green, blue));
		world.draw(g2, cam);
		//    	drawGrid(g2, Screen.TILESIZE, 50, 50, cam);
		player.drawInfo(g2, cam);
		drawPlayerInventory(g2, cam);
		if (showActors) {
			drawWorldInfo(g2, cam);
		}
		drawPlayerHealth(g2, cam);

	}

	public void drawGrid(Graphics2D g2, int tilesize, int rows, int colls, Camera cam)  {

		Graphics2D g3 = (Graphics2D) g2.create();
		g3.setColor(Color.BLACK);
		g3.translate(-cam.getPixelOffsetX(), -cam.getPixelOffsetY());
		for (int i = 0; i < rows; i++) {
			g3.drawRect(0, i * tilesize, colls * tilesize, tilesize);
		}

		for (int i = 0; i < colls; i++) {
			g3.drawRect(i * tilesize, 0, tilesize, rows * tilesize);
		}

	}

	public void drawPlayerInventory(Graphics2D g2, Camera cam) {

		int size = 64;
		int space = 5;

		Item[] hotbar = player.getHotbar();

		int hotbarlength = hotbar.length * size + (hotbar.length - 1) * space;
		int offsetX = (int) (cam.getWidth() / 2 - hotbarlength / 2);
		int offsetY = 25;

		for (int i = 0; i < hotbar.length; i++) {

			g2.setColor(Color.BLUE);

			if (player.getMainhand() == i) {
				g2.setColor(Color.YELLOW);
			}

			g2.fillRoundRect(offsetX + i * (size + space), offsetY, size, size, 10, 10);

			Item item = hotbar[i];
			if (item != null) {
				item.getImage().draw(g2, offsetX + i * (size + space), offsetY, size, size);
			}

		}
	}

	public void drawPlayerHealth(Graphics2D g2, Camera cam) {

		double health = player.getHealth();
		double maxHealth = player.getMaxHealth();

		int size = 48;
		int space = 4;
		int hearts = 10;

		double percent = health / maxHealth;
		double heartsToDraw = percent * hearts;

		int totallength = hearts * size + (hearts - 1) * space;
		int offsetX = (int) (cam.getWidth() / 2.0 - totallength / 2.0);
		int offsetY = 100;

		for (int i = 0; i < hearts; i++) {

			if (i < (int) heartsToDraw) {
				Images.HEART_FULL.draw(g2, offsetX + i * (size + space), offsetY, size, size);
			} else if (i < heartsToDraw && i + 1 > heartsToDraw) {
				if (heartsToDraw - (int)(heartsToDraw) >= 0.5) {
					Images.HEART_HALF.draw(g2, offsetX + i * (size + space), offsetY, size, size);
				} else {
					Images.HEART_DEAD.draw(g2, offsetX + i * (size + space), offsetY, size, size);
				}
			} else {
				Images.HEART_DEAD.draw(g2, offsetX + i * (size + space), offsetY, size, size);
			}
			
		}
	}

	public void drawWorldInfo(Graphics2D  g2, Camera cam) {

		int asize = world.getActors().size();
		int dyLine = 15;
		int width = 300;
		g2.setColor(Color.BLUE);
		g2.fillRoundRect((int) cam.getWidth() - width - 25, 25, width, dyLine * asize + 50, 10, 10);
		g2.setColor(Color.WHITE);

		g2.drawString("Actors size: " + asize, (float) cam.getWidth() - width, 50);
		int i = 0; for (GameObject g : world.getActors()) {
			g2.drawString("" + g, (float) cam.getWidth() - width, i * dyLine + 75);
			i++;
		}


	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onEnd() {
		
	}
}
