/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package main;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

public class GameScreen extends Canvas implements WindowListener, ComponentListener {

	private static final long serialVersionUID = 1L;
	private Engine game;
	private Camera cam;
	private Frame frame;

	private BufferStrategy strategy;
	private Image2d backbuffer;

	private int scale;
	private double zoom;
	
	public GameScreen(Engine game, int width, int height, int scale) {
		this.setPreferredSize(new Dimension(width, height));
		this.scale = scale;
		this.zoom = 1.0;
		this.game = game;
		this.cam = new Camera(0, 0, width, height);
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

	public void createBackbuffer() {
		backbuffer = new Image2d((int) (getWidth()), (int) (getHeight()));
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getScale() {
		return scale;
	}

	public double getZoom() {
		return this.zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		createBackbuffer();
	}

	public void render() {

		cam.dim.set(getWidth(), getHeight());

		Graphics2D g3 = (Graphics2D) backbuffer.getGraphics();
		g3.clearRect(0, 0, getWidth(), getHeight());
		game.render(g3, cam, scale);

		Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
		g2.clearRect(0, 0, getWidth(), getHeight());

		double width = getWidth() * (1 / zoom);
		double height = getHeight() * (1 / zoom);

		backbuffer.draw(g2, -(width - getWidth()) / 2, -(height - getHeight()) / 2, width, height, 0, 0, getWidth(), getHeight());
		strategy.show();

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
