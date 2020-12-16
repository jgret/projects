/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game;

import java.awt.*;

import game.graphics.Image2d;
import game.gui.Camera;
import game.gui.GameScreen;
import game.io.Input;

public abstract class Engine implements Runnable {

	protected int ups;
	protected int fps;
	protected int ups_last;
	protected int fps_last;
	
	protected Input input;
	protected GameScreen screen;
	private volatile boolean running;
	
	public Engine(int width, int height, int scale) {
		this.input = new Input();
		this.screen = new GameScreen(this, width, height, scale);
		Image2d.makeContext(screen);
		this.screen.addKeyListener(input);
		this.screen.addMouseListener(input);
		this.screen.addMouseMotionListener(input);
		this.screen.addMouseWheelListener(input);
	}

	public void start() {
		Thread game = new Thread(this);
		screen.setVisible(true);
		game.start();
	}

	@Override
	public void run() {

		running = true;

		this.init();

		double last = Time.getTime();
		double timer = Time.getTime();
		while (running) {
			double now = Time.getTime();
			double elapsedTime = now - last;
			last = now;
			this.input.poll();
			this.update(elapsedTime);
			this.screen.render();
			fps++;
			ups++;
			
			if ((Time.getTime() - timer) > 1.0) {
				timer += 1;
				ups_last = ups;
				fps_last = fps;
				fps = 0;
				ups = 0;
				
				System.out.println(ups_last);
				System.out.println(fps_last);
			}
			
		}

	}

	public abstract void init();

	public abstract void update(double elapsedTime);

	public abstract void render(Graphics2D g2, Camera cam, int scale);

	public Input getInput () {
		return this.input;
	}

	public GameScreen getScreen() {
		return screen;
	}
	
}
