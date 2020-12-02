/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of Greimel jgret
 *******************************************************/
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;

public class Game extends Engine {

    public Game() {
        super(1280, 720, 60);
    }

    @Override
    public void init() {

    }
    
    public double rotX;
    
    @Override
    public void update(double elapsedTime) {
    	if (input.keyPressed(KeyEvent.VK_F11)) {
    		if (gameScreen.isFullscreen()) {
    			gameScreen.leaveFullscreen();
    		} else {
    			gameScreen.enterFullscreen();
    		}
    	}
    	
    	rotX += 10 * elapsedTime;
    }

    @Override
    public void render(Graphics2D g2, Camera cam) {
    	int width = (int) cam.getWidth();
    	int height = (int) cam.getHeight();

    	g2.translate(width / 2, height / 2);
    	g2.rotate(Math.toRadians(rotX));
    	
    	Font font = new Font("Consolas", Font.ITALIC, 20);
    	g2.setFont(font);
    	
    	Area area = new Area();
    	Polygon p = new Polygon();
    	p.addPoint(-50, -50);
    	p.addPoint( 25, -50);
    	p.addPoint( 50, -25);
    	p.addPoint( 50,  50);
    	p.addPoint(-50,  50);
    	
    	p.translate(40, 40);
    	
    	g2.drawPolygon(p);
    	
    	g2.setColor(Color.RED);
    	g2.drawLine(0, 0, 250, 0);
    	g2.drawString("+x", 255, 0);
    	g2.setColor(Color.BLUE);
    	g2.drawLine(0, 0, 0, 250);
    	g2.drawString("+y", 0, 255);
    	
//    	g2.setColor(Color.YELLOW);
//    	g2.fillRect(0, 0, width, height);
//    	
//    	g2.setColor(Color.GREEN);
//    	g2.fillRect(0, 0, width, height / 2);
//    	
//    	g2.setColor(Color.BLUE);
//    	g2.fillRect(0, 0, width / 2, height);
//    	
//    	g2.setColor(Color.RED);
//    	g2.fillRect(0, 0, width / 2, height /2);
    	
    }

}