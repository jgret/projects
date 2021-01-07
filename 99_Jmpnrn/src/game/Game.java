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

import game.entity.item.Items;
import game.gamestate.GameStateManager;
import game.gamestate.GameStateHomeMenu;
import game.gamestate.GameStateIntro;
import game.gamestate.GameStatePlay;
import game.gamestate.GameStateQuestion;
import game.gamestate.GameStateTest;
import game.gamestate.GameStateType;
import game.graphics.Camera;
import game.questions.Questions;
import sound.SoundEngine;

public class Game extends Engine {
	
	public static Game instance;
    private GameStateManager gsm;
    private Questions questions;
    private Items items;
    private boolean timings = true;
    
    public Game() { 
    	super(1280, 720, 64);
    	Game.instance = this;
    	this.showTimings(timings);
    	this.gsm = new GameStateManager(GameStateType.INTRO);
    	this.items = new Items();
    	this.questions = new Questions("db/questions.db");
    }
    
    @Override
    public void init() {
    	SoundEngine.getInstance().load("sound/tracklist.trl");
    	gsm.register(GameStateType.PLAY, new GameStatePlay(this));
    	gsm.register(GameStateType.TEST, new GameStateTest(this));
    	gsm.register(GameStateType.INTRO, new GameStateIntro(this));
    	gsm.register(GameStateType.QUESTION, new GameStateQuestion(this));
    	gsm.register(GameStateType.HOME_MENU, new GameStateHomeMenu(this));
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
	
}