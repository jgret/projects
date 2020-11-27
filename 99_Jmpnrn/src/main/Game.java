package main;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game extends Engine {

    public Game() {
        super(1280, 720, -1);
    }

    @Override
    public void init() {
    	
    }
    
    @Override
    public void update(double elapsedTime) {
    	if (input.keyPressed(KeyEvent.VK_F11)) {
    		if (gameScreen.isFullscreen()) {
    			gameScreen.leaveFullscreen();
    		} else {
    			gameScreen.enterFullscreen();
    		}
    	}
    }

    @Override
    public void render(Graphics2D g2, Camera cam) {

    }

}