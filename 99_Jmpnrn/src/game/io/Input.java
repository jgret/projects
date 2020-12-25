/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.io;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import game.data.Vector2;

public class Input implements KeyListener, MouseInputListener, MouseWheelListener{
	
	private int x, y;
	
	private int idxPast = 0;
	private int idxNow = 1;
	private int idxBuffer = 2;
	
	private boolean[][] keys;
	private boolean[][] mouse;
	
	private int[] mwheel;
	
	
	public Input() {
		keys = new boolean[3][KeyEvent.KEY_LAST];
		mouse = new boolean[3][5];
		mwheel = new int[2];
	}
	
	public void poll() {
		//shift to lower index
		mwheel[0] = mwheel[1];
		mwheel[1] = 0;
		shift(keys);
		shift(mouse);
 	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Vector2 getPoint() {
		return new Vector2(x, y);
	}
	
	public boolean mouseReleased(int button) {
		return !mouse[idxNow][button] && mouse[idxPast][button];
	}
	
	public boolean mousePressed(int button) {
		return mouse[idxNow][button] && !mouse[idxPast][button];
	}
	
	public boolean mouseHeld(int button) {
		return mouse[idxNow][button];
	}
	
	public boolean keyPressed(int keyCode) {
		return keys[idxNow][keyCode] && !keys[idxPast][keyCode];
	}
	
	public boolean keyReleased(int keyCode) {
		return !keys[idxNow][keyCode] && keys[idxPast][keyCode];
	}
	
	public boolean keyHeld(int keyCode) {
		return keys[idxNow][keyCode];
	}
	
	public int wheelRotations() {
		return mwheel[0];
	}
	
	public void shift(boolean[][] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				array[i-1][j] = array[i][j];
			}
		}
	}
	
	public int value(boolean b) {
		return b ? 1 : 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() > 0  && e.getKeyCode() < keys[idxBuffer].length) {
			keys[idxBuffer][e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() > 0  && e.getKeyCode() < keys[idxBuffer].length) {
			keys[idxBuffer][e.getKeyCode()] = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse[idxBuffer][e.getButton()-1] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse[idxBuffer][e.getButton()-1] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mwheel[1] += e.getWheelRotation();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		move(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		move(e.getPoint());
	}
	
	private void move(Point p) {
		x = p.x;
		y = p.y;
	}

}
