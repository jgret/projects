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

import game.graphics.Camera;
import game.graphics.Screen;
import game.graphics.Image2d;
import game.io.Input;

public abstract class Engine implements Runnable {

	public int fps;
	public int ups;
	public int lastFps;
	public int lastUps;
	
	protected Input input;
	protected Screen screen;
	private volatile boolean running;

	public Engine(int width, int height, int scale) {
		this.fps = 0;
		this.ups = 0;
		this.lastFps = 0;
		this.lastUps = 0;
		this.input = new Input();
		this.screen = new Screen(this, width, height, scale);
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
		double usum = 0;
		double rsum = 0;
		while (running) {
			double now = Time.getTime();
			double elapsedTime = now - last;
			last = now;
			double ustart = Time.getTime();
			this.input.poll();
			Time.setElapsedTime(elapsedTime);
			this.update(elapsedTime);
			usum += Time.getTime() - ustart;
			double rstart = Time.getTime();
			this.screen.render();
			rsum += Time.getTime() - rstart;
			ups++;
			fps++;
			if ((Time.getTime() - timer) >= 1) {
				lastFps = fps;
				lastUps = ups;
				
				String info = String.format("UPS: %9d  FPS:  %9d\n", ups, fps);
					   info+= String.format("avr: %.7f  avr:  %.7f\n", usum / ups, rsum / fps);
					   info+= String.format("sum: %.7f  sum:  %.7f\n", usum, rsum);
				System.out.println(info);
				ups = 0;
				fps = 0;
				usum = 0;
				rsum = 0;
				timer += 1;
			}
		}

	}

	public abstract void init();

	public abstract void update(double elapsedTime);

	public abstract void render(Graphics2D g2, Camera cam);

	public Input getInput () {
		return this.input;
	}

	public Screen getScreen() {
		return screen;
	}
	
}
