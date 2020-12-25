/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;
import game.data.Rectangle;
import game.data.Vector2;
import game.entity.Player;
import game.entity.item.consumable.Food;
import game.graphics.Camera;
import game.graphics.Screen;
import game.graphics.Image2d;
import game.io.FileIO;
import game.io.Input;
import game.level.World;

public class GameStatePlay extends GameState {

    private World world;
    private Game game;
    private Player player;
    private Screen screen;
    private Input input;
	
	public GameStatePlay(Game game) {
		super(game);
		this.screen = game.getScreen();
		this.input = game.getInput();
	}

	@Override
	public void init() {
		world = new World(game);
    	world.load("default");
    	world.init();
    	
    	Image2d player_img = null;
		try {
			player_img = new Image2d(ImageIO.read(new File("res/img/bacardi.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	player = new Player(world, new Rectangle(3, 3, 1, 2.5), player_img);
    	world.spawn(player, new Vector2(3, 3));
    	screen.getCam().setTarget(player);
	}

	@Override
	public void update(double elapsedTime) {
		globalHotKeys();
    	world.update(elapsedTime);
    	screen.getCam().update(elapsedTime);
    }
    
    public void globalHotKeys() {
    	if (input.keyPressed(KeyEvent.VK_F11)) {
    		if (screen.isFullscreen()) {
    			screen.leaveFullscreen();
    		} else {
    			screen.enterFullscreen();
    		}
    	}
    	
    	if (input.keyPressed(KeyEvent.VK_H)) {
    		world.setShowHitboxes(!world.isShowHitboxes());
    	}
    	
    	if (input.keyHeld(KeyEvent.VK_SHIFT)) {
    		int scale = Screen.TILESIZE;
    		scale += input.wheelRotations() * -1;
    		Screen.TILESIZE = scale;
    	}
    	
    	if (input.keyHeld(KeyEvent.VK_ENTER)) {
    		Food food = new Food("Test", "game_test", FileIO.loadImage("img/game_apple.png"));
    		food.set(0, 0, 0.5, 0.5);
    		world.spawn(food, new Vector2(4, 4));
    	}
    	
    }

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		world.draw(g2, cam);
	}
	
}
