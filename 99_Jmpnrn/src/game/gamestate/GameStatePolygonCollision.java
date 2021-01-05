/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import game.Game;
import game.graphics.Camera;
import game.io.Input;
import game.shape.Line;
import game.shape.Polygon2D;
import game.shape.Vector2;

public class GameStatePolygonCollision extends GameState {

	private Input input;
	private Polygon2D polygon;

	private Vector2 mouse;
	private Vector2 solution;

	private Line line;

	private Vector2[] vert = {
			new Vector2(1, 1),
			new Vector2(4, 2),
			new Vector2(5, 7),
			new Vector2(10, 10),
			new Vector2(2, 6),
			new Vector2(0, 2),
	};

	public GameStatePolygonCollision(Game game) {
		super(game);
		input = game.getInput();
	}

	@Override
	public void init() {
		this.line = new Line(500, 500, 800, 600);
		this.polygon = new Polygon2D(vert);
		this.mouse = new Vector2(0, 0);
		this.solution = new Vector2(0, 0);
	}

	@Override
	public void update(double elapsedTime) {
		mouse = input.getPoint();
		if (polygon.scale(64).contains(mouse)) {
			solution = polygon.scale(64).getNextPointOnBounds(mouse);
		}

		solution = line.getNormalContactPoint(mouse);

		double vel = 200;
		
		if (input.keyHeld(KeyEvent.VK_UP)) {
			line.getB().y -= vel * elapsedTime;
		} else
		if (input.keyHeld(KeyEvent.VK_DOWN)) {
			line.getB().y += vel * elapsedTime;
		} else
		if (input.keyHeld(KeyEvent.VK_LEFT)) {
			line.getB().x -= vel * elapsedTime;
		} else
		if (input.keyHeld(KeyEvent.VK_RIGHT)) {
			line.getB().x += vel * elapsedTime;
		}
		
		if (input.keyHeld(KeyEvent.VK_W)) {
			line.getA().y -= vel * elapsedTime;
		} else
		if (input.keyHeld(KeyEvent.VK_S)) {
			line.getA().y += vel * elapsedTime;
		} else
		if (input.keyHeld(KeyEvent.VK_A)) {
			line.getA().x -= vel * elapsedTime;
		} else
		if (input.keyHeld(KeyEvent.VK_D)) {
			line.getA().x += vel * elapsedTime;
		}
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, (int) cam.getWidth(), (int) cam.getHeight());
		
		g2.setColor(Color.RED);
		g2.fill(polygon.scale(64));
		g2.setColor(Color.CYAN);
		g2.fillArc((int) mouse.getX() - 5, (int) mouse.getY() - 5, 10, 10, 0, 360);
		g2.fillArc((int) solution.getX() - 5, (int) solution.getY() - 5, 10, 10, 0, 360);

		if (line.mayContain(solution)) {
			g2.setColor(Color.CYAN);
		} else {
			g2.setColor(Color.BLACK);
		}
		
		line.draw(g2, cam);
	}

}
