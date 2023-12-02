/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import game.shape.Polygon2D;
import game.shape.Vector2;

public class TestPolygon2D {
	
	public static void main(String[] args) {
		
		Vector2[] p = {
				new Vector2(0, 0),
				new Vector2(2, 0),
				new Vector2(2, 2),
				new Vector2(0, 2)
		};

		for (int i = 0; i < p.length; i++) {
			p[i] = p[i].add(new Vector2(1, 1));
		}
		
		Polygon2D poly = new Polygon2D(p);
		System.out.println(poly);
		
	}

}
