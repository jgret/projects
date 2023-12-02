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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import game.entity.Player;
import game.entity.item.Items;
import game.gamestate.GameStateGameOver;
import game.gamestate.GameStateHomeMenu;
import game.gamestate.GameStateIntro;
import game.gamestate.GameStateManager;
import game.gamestate.GameStatePlay;
import game.gamestate.GameStateQuestion;
import game.gamestate.GameStateSettings;
import game.gamestate.GameStateShop;
import game.gamestate.GameStateTest;
import game.gamestate.GameStateType;
import game.graphics.Camera;
import game.graphics.Screen;
import game.io.FileIO;
import game.level.World;
import game.questions.Questions;
import game.shape.Rectangle;
import game.shape.Vector2;
import sound.SoundEngine;

public class Game extends Engine {
	
	public static Game instance;
    private GameStateManager gsm;
    private Questions questions;
    private Items items;
    private Player player;
    private boolean timings = false;
    private HashMap<String, World> levels;
    
    public Game() { 
    	super(1280, 720, 64);
    	Game.instance = this;
    	this.showTimings(timings);
    	this.gsm = new GameStateManager(GameStateType.INTRO);
    	this.items = new Items();
    	
    	URL url = FileIO.getURL("db/questions.db");
    	File db = new File(url.getFile());
    	if (db.exists()) {
    		System.out.println("SQLite Database exists, no need to install");
    		this.questions = new Questions("db/questions.db");
    	} else {
    		try {
    			System.out.println("SQLite Database doesn't exist, installing now");
    			InputStream input = FileIO.getResourceAsStream("db/questions.db");

    			File copy = new File("questions.db");
    			if(copy.exists())
    			{
    				copy.delete();
    			}

    			copy.createNewFile();
    			DataInputStream din = new DataInputStream(input);
    			DataOutputStream dout = new DataOutputStream(new FileOutputStream(copy));
    			int c;
    			c = 1;
    			while(c!=-1)
    			{
    				c = din.read();
    				dout.write(c);
    			}
    			
    			din.close();
    			dout.flush();
    			dout.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

			this.questions = new Questions("questions.db");
    	}
    	this.levels = new HashMap<>();
    	
    }
    
    @Override
    public void init() {
    	
    	SoundEngine.getInstance().load("sound/tracklist.trl");
    	SoundEngine.getInstance().setGlobalVolume(-40.0f);
    	gsm.register(GameStateType.PLAY, new GameStatePlay(this));
    	gsm.register(GameStateType.TEST, new GameStateTest(this)); 
    	gsm.register(GameStateType.INTRO, new GameStateIntro(this));
    	gsm.register(GameStateType.QUESTION, new GameStateQuestion(this));
    	gsm.register(GameStateType.HOME_MENU, new GameStateHomeMenu(this));
    	gsm.register(GameStateType.SHOP, new GameStateShop(this));
    	gsm.register(GameStateType.GAMEOVER, new GameStateGameOver(this));
    	gsm.register(GameStateType.SETTINGS, new GameStateSettings(this));
    	gsm.init();
    	items.loadJSON("item/items.json");
    }
    
    @Override
    public void update(double elapsedTime) {
    	gsm.update(elapsedTime);
    	
		if (input.keyPressed(KeyEvent.VK_F11)) {
			if (screen.isFullscreen()) {
				screen.leaveFullscreen();
			} else {
				screen.enterFullscreen();

			}
		}
    }
    
    @Override
    public void render(Graphics2D g2, Camera cam) {
    	gsm.draw(g2, cam);
    }
    
	public GameStateManager getGsm() {
		return gsm;
	}

	public Items getItems() {
		return items;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	public void setGsm(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void setItems(Items items) {
		this.items = items;
	}
	
	public Vector2 getMouseLocationOnScreen() {
		return input.getPoint().mul(1 / (double) (Screen.TILESIZE)).add(screen.getCam().getPosition());
	}

	public HashMap<String, World> getLevels() {
		return levels;
	}

	public void setLevels(HashMap<String, World> levels) {
		this.levels = levels;
	}
	
}