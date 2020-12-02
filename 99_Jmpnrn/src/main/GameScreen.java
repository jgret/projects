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

    private Engine game;
    private Camera cam;
    private Frame frame;

    private VolatileImage vBuffer;
    private GraphicsConfiguration gc;
    private BufferStrategy strategy;

    private int width, height;
    private boolean useVBuffer;
    
    public GameScreen(Engine game, int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
        this.game = game;
        this.useVBuffer = true;
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

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createVBuffer() {
        if (vBuffer != null) {
            vBuffer.flush();
            vBuffer = null;

        }

        gc = this.getGraphicsConfiguration();
        vBuffer = gc.createCompatibleVolatileImage(getWidth(), getHeight());
    }

    private void checkVBuffer() {
        if (vBuffer == null) {
            createVBuffer();
        }

        if (vBuffer.validate(gc) != VolatileImage.IMAGE_OK) {
            createVBuffer();
        }
    }
    
    public boolean isUseVBuffer() {
		return useVBuffer;
	}

	public void setUseVBuffer(boolean useVBuffer) {
		this.useVBuffer = useVBuffer;
	}

	public void render() {
    	
    	if (useVBuffer) {
    		checkVBuffer();

            Graphics2D g2 = (Graphics2D) vBuffer.getGraphics();
            cam.dim.set(getWidth(), getHeight());
            game.render(g2, cam);
            g2.dispose();

            g2 = (Graphics2D) strategy.getDrawGraphics();
            g2.drawImage(vBuffer, 0, 0, this);
            g2.clearRect(0, 0, (int) getWidth(), (int) getHeight());
            game.render(g2, cam);
            g2.dispose();

            strategy.show();
    		
    	} else {

            Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
            g2.clearRect(0, 0, (int) getWidth(), (int) getHeight());
            game.render(g2, cam);
            g2.dispose();

            strategy.show();
    		
    	}
        
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
        gc = this.getGraphicsConfiguration();
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
