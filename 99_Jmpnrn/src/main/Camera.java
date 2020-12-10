/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package main;

public class Camera extends Rectangle {
	
	private GameObject target;
	private GameScreen screen;
	
    public Camera(GameScreen screen, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.screen = screen;
    }
    
    public void setTarget(GameObject target) {
    	this.target = target;
    }
    
    public void update(double elapsedTime) {
    	Vector2 screen = this.getDim();
    	Rectangle world = target.getWorldIn().getBounds();
    	int scale = this.screen.getScale();

    	
    	
    	this.pos.x = target.getX();
    	this.pos.y = target.getY();
    	this.pos.set(0, 0);
    	
    }
    
    public double getX(double scale) {
    	return this.pos.x * scale;
    }
    
    public double getY(double scale) {
    	return this.pos.y * scale;
    }

}
