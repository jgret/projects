/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import game.shape.Rectangle;

public class Button extends Rectangle {
	
	private ArrayList<ActionListener> listeners;
	private String name;
	private Image2d img;
	
	public Button(String name, Image2d img) {
		super(0, 0, 10, 10);
		this.name = name;
		this.img = img;
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		img.draw(g2, x, y, width, height);
	}
	
	public void addActionListener(ActionListener listener) {
		this.listeners.add(listener);
	}
	
	public void click() {
		for (ActionListener l : listeners) {
			//just ignore the event
			l.actionPerformed(new ActionEvent(this, 0, "click"));
		}
	}

}
