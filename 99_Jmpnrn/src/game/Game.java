/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.data.Rectangle;
import game.data.Vector2;
import game.graphics.Image2d;
import game.gui.Camera;
import game.level.World;
import main.Player;

public class Game extends Engine {
	
	public static Game instance;

    public Game() { 
    	super(1280, 720, 64);
    	Game.instance = this;
    }
    
    private World world;
    private Player player;

    @Override
    public void init() {
    	
    	world = new World(this);
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
    		int scale = screen.getScale();
    		scale += input.wheelRotations() * -1;
    		screen.setScale(scale);
    	}
    	
    }
    

    @Override
    public void render(Graphics2D g2, Camera cam, int scale) {
    	
//    	int width = (int) cam.getWidth();
//    	int height = (int) cam.getHeight();
    	
    	world.draw(g2, cam, scale);
    	player.draw(g2, cam, scale);
    	
    	g2.setColor(Color.BLACK);
    	g2.drawString("Cam x " + cam.getX(), 10, 10);
    	g2.drawString("Cam y " + cam.getY(), 10, 20);
    	g2.drawString("Player x " + player.getX(), 10, 30);
    	g2.drawString("Player y " + player.getY(), 10, 40);
    	
    	
    	
    }

}