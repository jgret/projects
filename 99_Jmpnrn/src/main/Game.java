/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game extends Engine {

    public Game() {
        super(1280, 720, 32, -1);
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
			
			player_img = new Image2d(ImageIO.read(new File("res/img/world_32x32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	player = new Player(world, new Rectangle(3, 3, 1, 1), player_img);
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
    	
    	if (input.keyHeld(KeyEvent.VK_SHIFT)) {
    		int scale = screen.getScale();
    		scale += input.wheelRotations() * -1;
    		screen.setScale(scale);
    	} else {
    		
//    		double zoom = screen.getZoom();
//    		zoom += input.wheelRotations() * -0.1;
//    		screen.setZoom(zoom);
    		
    	}
    }
    

    @Override
    public void render(Graphics2D g2, Camera cam, int scale) {
    	
//    	int width = (int) cam.getWidth();
//    	int height = (int) cam.getHeight();
    	
    	world.draw(g2, cam, scale);
    	player.draw(g2, cam, scale);
    	
    	g2.setColor(Color.BLUE);
    	g2.fillRect((int) (-cam.getX(scale)), (int) (-cam.getY(scale)), (int) scale * 2, (int) scale * 2);
    	
    	g2.setColor(Color.BLACK);
    	g2.drawString("Cam x " + cam.getX(), 10, 10);
    	g2.drawString("Cam y " + cam.getY(), 10, 20);
    	g2.drawString("Player x " + player.getX(), 10, 30);
    	g2.drawString("Player y " + player.getY(), 10, 40);
    	
    	
    	
    }

}