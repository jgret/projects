/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of Greimel jgret
 *******************************************************/
package main;

import java.awt.*;

public abstract class Engine implements Runnable {

    protected Input input;
    protected GameScreen gameScreen;
    protected double frameTime;
    private double precisionThreshold = 0.004;
    private volatile boolean running;

    public Engine(int width, int height, int fps) {
        System.out.println("FPS set to " + fps);
        this.frameTime = 1.0 / fps;
        this.input = new Input();
        this.gameScreen = new GameScreen(this, width, height);
        this.gameScreen.addKeyListener(input);
        this.gameScreen.addMouseListener(input);
        this.gameScreen.addMouseMotionListener(input);
    }

    public void start() {
        Thread game = new Thread(this);
        gameScreen.setVisible(true);
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
                    this.gameScreen.render();

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
                this.gameScreen.render();

            }

        }

    }

    public abstract void init();

    public abstract void update(double elapsedTime);

    public abstract void render(Graphics2D g2, Camera cam);

    public Input getInput () {
        return this.input;
    }

}
