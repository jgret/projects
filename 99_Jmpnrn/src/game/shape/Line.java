/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.shape;

import java.awt.Graphics2D;

import game.graphics.Camera;
import game.graphics.Drawable;

public class Line implements Drawable {
	
	private Vector2 a;
	private Vector2 b;

	public Line(double x1, double y1, double x2, double y2) {
		this.a = new Vector2(x1, y1);
		this.b = new Vector2(x2, y2);
	}
	
	public Line(Vector2 a, Vector2 b) {
		this.a = a;
		this.b = b;
	}
	
	public double getDistanceBetweenPointAndLine(Vector2 p) {
		
		Vector2 ap = p.sub(a);
		Vector2 ab = b.sub(a);
		double phi = ab.angle(ap);
		
		return ap.length() * Math.sin(phi);
		
	}
	
	public double getDistanceToContactNormal(Vector2 p) {
		Vector2 ap = p.sub(a);
		Vector2 ab = b.sub(a);
		double phi = ab.angle(ap);
		
		return ap.length() * Math.cos(phi);
	}
	
	public Vector2 getNormalContactPoint(Vector2 p) {
		double x = 0;
		double y = 0;

		Vector2 axdir = getDir(); 
		double ax = getDistanceToContactNormal(p);
		double phi = axdir.angleX();

		if (ax < 0) {
			return new Vector2(Double.NaN, Double.NaN);
		} else if (ax > this.lenght()) {
			return new Vector2(Double.NaN, Double.NaN);
		}

		if (phi > 0 && phi < Math.PI * 0.5) {
			
			x = Math.abs(ax * Math.cos(phi));
			y = Math.abs(ax * Math.sin(phi));
			
		} else if (phi < Math.PI) {
			
			x = -Math.abs(ax * Math.cos(phi));
			y = Math.abs(ax * Math.sin(phi));
			
		} else if (phi < Math.PI * 1.5) {
			
			x = -Math.abs(ax * Math.cos(phi));
			y = -Math.abs(ax * Math.sin(phi));
			
		} else if (phi < Math.PI * 2) {
			
			x = Math.abs(ax * Math.cos(phi));
			y = -Math.abs(ax * Math.sin(phi));
		
		}
		
		x += a.getX();
		y += a.getY();
		
		return new Vector2(x, y);
		
	}
	
	public boolean contains(Vector2 p) {
		Vector2 line = b.sub(a);
		Vector2 check = p.sub(a);
		double angle = b.angle(p);
		return angle == 0;
	}
	
	public boolean mayContain(Vector2 p) {
		return
		(a.getX() <= p.getX() && b.getX() >= p.getX() || b.getX() <= p.getX() && a.getX() >= p.getX()) &&
		(a.getY() <= p.getY() && b.getY() >= p.getY() || b.getY() <= p.getY() && a.getY() >= p.getY());
	}
	
	public Vector2 getNormalAxis() {
		return a.sub(b).swap().negateX();
	}
	
	public Line getUpDown() {
		if (a.getY() > b.getY()) {
			return new Line(b, a);
		}
		return new Line(a, b);
	}
	
	public double lenght() {
		return b.sub(a).length();
	}
	
	public Line normalize() {
		if (a.getX() > b.getX()) {
			return new Line(b, a);
		}
		return new Line(a, b);
	}
	
	public Vector2 getDir() {
		return b.sub(a);
	}
	
	public double getGradient() {
		return (b.getY() - a.getY()) / (b.getX() - a.getX());
	}
	
	public Vector2 getLeftPoint() {
		if (a.getX() > b.getX()) {
			return b;
		}
		return a;
	}
	
	public Vector2 getRightPoint() {
		if (a.getX() > b.getX()) {
			return a;
		}
		return b;
	}

	public Vector2 getA() {
		return a;
	}

	public void setA(Vector2 a) {
		this.a = a;
	}

	public Vector2 getB() {
		return b;
	}

	public void setB(Vector2 b) {
		this.b = b;
	}
	
	@Override
	public String toString() {
		return a + " -> " + b;
	}
	
	@Override
	public void draw(Graphics2D g2, Camera cam) {
		g2.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
	}
	
}
