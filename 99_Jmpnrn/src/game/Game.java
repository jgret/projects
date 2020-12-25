/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.data.Rectangle;
import game.data.Vector2;
import game.entity.Player;
import game.entity.item.consumable.Food;
import game.gamestate.GameStateManager;
import game.gamestate.GameStatePlay;
import game.gamestate.GameStateType;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.io.FileIO;
import game.level.World;

public class Game extends Engine {
	
	public static Game instance;
    
    private GameStateManager gsm;

    public Game() { 
    	super(1280, 720, 64);
    	Game.instance = this;
    	this.gsm = new GameStateManager();
    }
    
    @Override
    public void init() {
    	gsm.register(GameStateType.PLAY, new GameStatePlay(this));
    	gsm.init();
    	gsm.setGameState(GameStateType.PLAY);
    }
    
    @Override
    public void update(double elapsedTime) {
    	gsm.update(elapsedTime);
    }
    
    @Override
    public void render(Graphics2D g2, Camera cam) {
    	gsm.draw(g2, cam);
    }
    
	public GameStateManager getGsm() {
		return gsm;
	}

}