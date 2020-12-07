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

public class Game extends Engine {

    public Game() {
        super(1280, 720, 32, 60);
    }
    
    private World world;

    @Override
    public void init() {
    	world = new World();
    	world.load("default");
    }
    
    public double rotX;
    
    @Override
    public void update(double elapsedTime) {
    	
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
    		
    		double zoom = screen.getZoom();
    		zoom += input.wheelRotations() * -0.1;
    		screen.setZoom(zoom);
    		
    	}
    	
    	screen.setTitle("Game z: " + String.format("%.1g", screen.getZoom()).replace(',', '.') + " s: " + screen.getScale());
    	
    	
    	rotX += 10 * elapsedTime;
    }

    @Override
    public void render(Graphics2D g2, Camera cam, int scale) {
    	
    	g2.setBackground(Color.CYAN);
    	
    	int width = (int) cam.getWidth();
    	int height = (int) cam.getHeight();
    	
    	world.draw(g2, cam, scale);
    	
//    	g2.translate(width / 2, height / 2);
//    	g2.rotate(Math.toRadians(rotX));
//    	
//    	Font font = new Font("Consolas", Font.ITALIC, 20);
//    	g2.setFont(font);
//    	
//    	Area area = new Area();
//    	Polygon p = new Polygon();
//    	p.addPoint(-50, -50);
//    	p.addPoint( 25, -50);
//    	p.addPoint( 50, -25);
//    	p.addPoint( 50,  50);
//    	p.addPoint(-50,  50);
//    	p.translate(40, 40);
//    	
//    	g2.drawPolygon(p);
//    	
//    	g2.setColor(Color.RED);
//    	g2.drawLine(0, 0, 250, 0);
//    	g2.drawString("+x", 255, 0);
//    	g2.setColor(Color.BLUE);
//    	g2.drawLine(0, 0, 0, 250);
//    	g2.drawString("+y", 0, 255);
    	
    	
    	
    }

}