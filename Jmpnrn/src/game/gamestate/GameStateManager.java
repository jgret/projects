/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Iterator;

import game.graphics.Camera;
import game.graphics.Drawable;

public class GameStateManager implements Drawable {
	
	private HashMap<GameStateType, GameState> gameStates;
	private GameStateType state;
	
	public GameStateManager(GameStateType start) {
		this.state = start;
		this.gameStates = new HashMap<>();
	}
	
	public void register(GameStateType type, GameState gs) {
		this.gameStates.put(type, gs);
	}
	
	public void remove(GameStateType type) {
		this.gameStates.remove(type);
	}
	
	public void init() {
		System.out.println("Initializing Game States");
		Iterator<GameStateType> states = gameStates.keySet().iterator();
		while (states.hasNext()) {
			GameState state = gameStates.get(states.next());
			state.init();
		}
		
	}
	
	public void changeGameState(GameStateType state) {
		if (this.state != null) {
			gameStates.get(this.state).onEnd();
		}
		this.state = state;
		this.gameStates.get(this.state).onStart();
	}
	
	public GameStatePlay getPlayState() {
		return (GameStatePlay) this.gameStates.get(GameStateType.PLAY);
	}
	
	public void update(double elapsedTime) {
		gameStates.get(state).update(elapsedTime);
	}
	
	public void draw(Graphics2D g2, Camera cam) {
		gameStates.get(state).draw(g2, cam);
	}
	

}
