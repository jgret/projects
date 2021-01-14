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
import game.entity.enemy.Enemies;
import game.entity.enemy.Skeleton;
import game.entity.item.Item;
import game.entity.item.Items;
import game.entity.item.consumable.QuestionPotion;
import game.entity.item.weapon.Bow;
import game.entity.item.weapon.Sword;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.graphics.Images;
import game.graphics.Screen;
import game.io.FileIO;
import game.io.Input;
import game.level.World;
import game.shape.Rectangle;
import game.shape.Vector2;
import sound.SoundEngine;

public class GameStatePlay extends GameState {

	private Player player;
	private Screen screen;
	private Input input;
	private boolean showActors;
	private boolean showPlayerInfo;

	public GameStatePlay(Game game) {
		super(game);
		this.screen = game.getScreen();
		this.input = game.getInput();
		this.showActors = false;
		this.selectedIndex = -1;
	}

	@Override
	public void init() {
		Items items = game.getItems();
		items.register(new Bow("game_bow", "Bow", FileIO.loadImage("img/game_bow.png"), FileIO.loadImage("img/game_arrow.png"), new Rectangle(0, 0, 0.5, 0.5), 20, 10, 0.1));
		items.register(new Bow("game_bow_skelly", "Bow", FileIO.loadImage("img/game_bow_skelly.png"), FileIO.loadImage("img/game_fire_arrow.png"), new Rectangle(0, 0, 0.5, 0.5), 11, 20, 0.1));
		items.register(new Sword("game_sword", "Sword", Images.SWORD, 10, 0.1));
		items.register(new QuestionPotion("game_question_potion", "Magic Potion", FileIO.loadImage("img/game_potion.png")));
		
		World world1 = new World(game);
		world1.load("slopes");
		world1.init();
		game.getLevels().put("slopes", world1);
		
		World world2 = new World(game);
		world2.load("default");
		world2.init();
		game.getLevels().put("default", world2);
				
		player = new Player(world1, new Rectangle(3, 3, 1, 2.5), FileIO.loadImage("img/bacardi.png"));
		player.addItem(items.get("game_bow"));
		player.addItem(items.get("game_sword"));
		player.getWorldIn().spawn(player, player.getWorldIn().getSpawnPoint());

		Skeleton skelly = new Skeleton(player.getWorldIn(), new Rectangle(0, 0, 1, 2), FileIO.loadImage("img/skeleton.png"));
		skelly.addItem(items.get("game_bow_skelly"));
		player.getWorldIn().spawn(skelly, player.getWorldIn().getSpawnPoint().sub(new Vector2(1, 0)));
		
		//    	Platform platform = new Platform(world, new Rectangle(0, 0, 5, 1), Color.RED);
		//    	world.spawn(platform, new Vector2(76, 42));

		screen.getCam().setTarget(player);
	}
	
	private int selectedIndex;
	private int hoverSelectedIndex;
	
	@Override
	public void update(double elapsedTime) {
		globalHotKeys();
		if (player.isInInventory()) {
			
			
			hoverSelectedIndex = getInventoryItemIndex(input.getPoint());
			if (hoverSelectedIndex != -1) {
				Item item = player.getInventory().getInventory()[hoverSelectedIndex];
				if (input.keyPressed(KeyEvent.VK_1)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(0, item);
				} else if (input.keyPressed(KeyEvent.VK_2)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(1, item);
				} else if (input.keyPressed(KeyEvent.VK_3)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(2, item);
				} else if (input.keyPressed(KeyEvent.VK_4)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(3, item);
				} else if (input.keyPressed(KeyEvent.VK_5)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(4, item);
				} else if (input.keyPressed(KeyEvent.VK_6)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(5, item);
				} else if (input.keyPressed(KeyEvent.VK_7)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(6, item);
				} else if (input.keyPressed(KeyEvent.VK_8)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(7, item);
				} else if (input.keyPressed(KeyEvent.VK_9)) {
					player.removeFromHotbar(item);
					player.setHotbarAt(8, item);
				}
			}
			
			if (input.mousePressed(0)) {
				selectedIndex = getInventoryItemIndex(input.getPoint());
			}
			
			if (input.mouseReleased(0)) {
				if (selectedIndex != -1) {
					
					int index = getInventoryItemIndex(input.getPoint());
					if (index != -1) {
						if (selectedIndex == index) {
							Item item = player.getInventory().getInventory()[index];
							player.use(item);
						} else {
							player.getInventory().swap(selectedIndex, index);
						}
					} else
						player.drop(selectedIndex);
					}
				selectedIndex = -1;
			}
			
			int mouseCycle = input.wheelRotations();
			if (mouseCycle != 0) {

				while (mouseCycle < 0) {
					mouseCycle++;
					this.scrollOffset--;
				}

				while (mouseCycle > 0) {
					mouseCycle--;
					this.scrollOffset++;
				}

			}

			
		}
		player.getWorldIn().update(elapsedTime);
		screen.getCam().update(elapsedTime);
		float speed = 1f;
		
	}

	public void globalHotKeys() {

		if (input.keyPressed(KeyEvent.VK_ENTER)) {
			player.getWorldIn().spawn(Enemies.createSkelly(), player.getWorldIn().getSpawnPoint());
		}
		
		if (input.keyPressed(KeyEvent.VK_ESCAPE)) {
			gsm.changeGameState(GameStateType.HOME_MENU);
		}
		
		if (input.keyPressed(KeyEvent.VK_P)) {
			gsm.changeGameState(GameStateType.QUESTION);
		}
		
		if (input.keyPressed(KeyEvent.VK_F1)) {
			this.showActors = !showActors;
		}
		
		if (input.keyPressed(KeyEvent.VK_E)) {
			player.setInInventory(!player.isInInventory());
		}
		
		if (input.keyPressed(KeyEvent.VK_F4)) {
			this.showPlayerInfo = !showPlayerInfo;
		}
		
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
//		g2.setXORMode(new Color(red, green, blue));
		player.getWorldIn().draw(g2, cam);
		//    	drawGrid(g2, Screen.TILESIZE, 50, 50, cam);
		if (showPlayerInfo) {
			player.drawInfo(g2, cam);
		}
		drawPlayerInventory(g2, cam);
		
		if (showActors) {
			drawWorldInfo(g2, cam);
		}
		drawPlayerHealth(g2, cam);
		
		if (player.isInInventory()) {
			drawInventory(g2, cam);
			if (selectedIndex != -1) {
				Item item = player.getInventory().getInventory()[selectedIndex];
				if (item != null) {
					item.getImage().draw(g2, input.getX(), input.getY(), size, size);
				}
			}
			
			if (hoverSelectedIndex != -1) {
				Item item = player.getInventory().getInventory()[hoverSelectedIndex];
				if (item != null) {
					item.drawInfo(g2, input.getX(), input.getY());
				}
			}
		}
		
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

		int asize = player.getWorldIn().getActors().size();
		int dyLine = 15;
		int width = 300;
		g2.setColor(Color.BLUE);
		g2.fillRoundRect((int) cam.getWidth() - width - 25, 25, width, dyLine * asize + 50, 10, 10);
		g2.setColor(Color.WHITE);

		g2.drawString("Actors size: " + asize, (float) cam.getWidth() - width, 50);
		int i = 0; for (GameObject g : player.getWorldIn().getActors()) {
			g2.drawString("" + g, (float) cam.getWidth() - width, i * dyLine + 75);
			i++;
		}
		
	}
	
	private int size = 48;
	private int space = 5;
	private int rx = 25;
	private int ry = 100;
	private int scrollOffset = 0;
	private int itemsPerLine = 10;
	private int totalLines = 10;
	private Image2d invBuffer;
	
	public void drawInventory(Graphics2D  g2, Camera cam) {
		
		Item[] items = player.getInventory().getInventory();
		
		int rwidth = itemsPerLine * size + (itemsPerLine + 1) * space;
		int rheight = totalLines * size + (totalLines + 1) * space;
		
		g2.setColor(Color.BLUE.brighter());
		g2.fillRoundRect(rx, ry, rwidth, rheight, 20, 20);
		
		int tiles = itemsPerLine * totalLines;
		if (scrollOffset < 0) {
			scrollOffset = 0;
		}
		
		int maxScrollOffset = (items.length / itemsPerLine) - totalLines;
		if ((scrollOffset + totalLines) * itemsPerLine > items.length) {
			scrollOffset = maxScrollOffset;
		}
		
		for (int i = 0; i < tiles; i++) {
			int x = i % itemsPerLine;
			int y = i / itemsPerLine;

			g2.setColor(new Color(0, 191, 255));
			g2.fillRoundRect(x * size + (x + 1) * space + rx, y * size + (y + 1) * space + ry, size, size, 25, 25);
			if (items[i + scrollOffset * itemsPerLine] != null) {
				items[i + scrollOffset * itemsPerLine].getImage().draw(g2, x * size + (x + 1) * space + rx, y * size + (y + 1) * space + ry, size, size);
			}
		}
		
		double percent = (double) ((double) scrollOffset / (double) maxScrollOffset);
		double y = rheight * percent;
		g2.setColor(Color.YELLOW);
		g2.fillRoundRect((int) (rx + rwidth),(int) (ry + y), 10, 10, 10, 10);
	}
	
	public int getInventoryItemIndex(Vector2 pos) {
		pos = pos.sub(new Vector2(rx + space / 2, ry + space / 2));
		int rwidth = itemsPerLine * size + (itemsPerLine + 1) * space;
		int rheight = totalLines * size + (totalLines + 1) * space;
		int size = this.size + this.space;
		
		if (pos.getX() < rwidth && pos.getY() < rheight && pos.getX() > 0 && pos.getY() > 0) {
			
			int x = (int) (pos.getX() / size);
			int y = (int) (pos.getY() / size);
			return y * itemsPerLine + x + scrollOffset * itemsPerLine;
		}
		
		
		return -1;
	}
	
	@Override
	public void onStart() {
		SoundEngine.getInstance().loop("bacardi_feeling", -1);
	}

	@Override
	public void onEnd() {
		SoundEngine.getInstance().stop("bacardi_feeling");
	}
}
