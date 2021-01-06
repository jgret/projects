/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import game.shape.Rectangle;

public class Button extends Rectangle {
	
	private ArrayList<ActionListener> listeners;
	private String name;
	private Image2d img;
	private boolean hover;
	
	public Button(String name, Image2d img) {
		super(0, 0, 10, 10);
		this.name = name;
		this.img = img;
		this.listeners = new ArrayList<>();
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		g2.setFont(Screen.PIXEL_FONT_LARGE);
		FontMetrics fm = g2.getFontMetrics();
		int swidth = fm.stringWidth(name);
		int sheight = fm.getAscent();
		
		if (hover) {
			int shrink = 4;
			img.draw(g2, x + shrink, y + shrink, width - 2 * shrink, height - 2 * shrink);
			g2.drawString(name, (int) getCenterX() - swidth / 2 - shrink, (int) getCenterY() + sheight / 2 - shrink);
		} else {
			img.draw(g2, x, y, width, height);
			g2.drawString(name, (int) getCenterX() - swidth / 2, (int) getCenterY() + sheight / 2);
			
		}
	}
	
	public void addActionListener(ActionListener listener) {
		this.listeners.add(listener);
	}
	
	public void click() {
		for (ActionListener l : listeners) {
			l.actionPerformed(new ActionEvent(this, 0, "click"));
		}
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}
	
}
