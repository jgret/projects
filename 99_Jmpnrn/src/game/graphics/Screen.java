/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

import game.Engine;

public class Screen extends Canvas implements WindowListener, ComponentListener {

	public static int TILESIZE = 64;
	private static final long serialVersionUID = 1L;
	private Engine game;
	private Camera cam;
	private Frame frame;

	private BufferStrategy strategy;
	private VolatileImage vBuffer;

	private int scale;
	
	public Screen(Engine game, int width, int height, int scale) {
		this.setPreferredSize(new Dimension(width, height));
		this.scale = scale;
		this.game = game;
		this.cam = new Camera(this, 0, 0, width, height);
		this.setFocusable(true);
		this.addKeyListener(game.getInput());
		frame = new Frame("GameScreen");
		frame.addWindowListener(this);
		frame.addComponentListener(this);
		frame.setIgnoreRepaint(true);
		frame.add(this);
		frame.pack();
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		createBackbuffer();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public Engine getGame() {
		return game;
	}

	public void createBackbuffer() {
		this.vBuffer = this.createVolatileImage(getWidth(), getHeight());
	}

	public void checkVBuffer() {
		if (vBuffer.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
			createBackbuffer();
		}
	}

	public void render() {
		checkVBuffer();
		
		Graphics2D g3 = (Graphics2D) vBuffer.getGraphics();
		g3.clearRect(0, 0, getWidth(), getHeight());
		game.render(g3, cam);
		g3.dispose();
		
		Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
		g2.clearRect(0, 0, getWidth(), getHeight());
		g2.drawImage(vBuffer, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
		strategy.show();
	}
	
	public Camera getCam() {
		return cam;
	}
	
	public void enterFullscreen() {
		frame.setVisible(false);
		frame.dispose();
		frame.setUndecorated(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	public void leaveFullscreen() {
		frame.setVisible(false);
		frame.dispose();
		frame.setUndecorated(false);
		this.setPreferredSize(new Dimension(1280, 720));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public boolean isFullscreen() {
		return frame.isUndecorated();
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		createBackbuffer();
	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

}
