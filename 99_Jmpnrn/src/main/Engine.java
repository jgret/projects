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

public abstract class Engine implements Runnable {

    protected Input input;
    protected GameScreen screen;
    protected double frameTime;
    private double precisionThreshold = 0.004;
    private volatile boolean running;

    public Engine(int width, int height, int scale, int fps) {
        System.out.println("FPS set to " + (fps < 0 ? "Unlimited" : fps));
        this.frameTime = 1.0 / fps;
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
        double last = Time.getTime();
        double delta = 0;
        double lastDelta = Time.getTime();

        this.init();

        if (frameTime > 0) {

            while (running) {

                double now = Time.getTime();
                double elapsedTime = now - last;
                last = now;

                while (delta <= frameTime) {
                    double nowDelta = Time.getTime();
                    delta += nowDelta - lastDelta;
                    lastDelta = nowDelta;
                }

                while (delta >= frameTime) {

                    this.input.poll();
                    this.update(elapsedTime);
                    this.screen.render();

                    delta -= frameTime;
                }

            }
        } else {

            while (running) {

                double now = Time.getTime();
                double elapsedTime = now - last;
                last = now;

                this.input.poll();
                this.update(elapsedTime);
                this.screen.render();

            }

        }

    }

    public abstract void init();

    public abstract void update(double elapsedTime);

    public abstract void render(Graphics2D g2, Camera cam, int scale);

    public Input getInput () {
        return this.input;
    }

}
