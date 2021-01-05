/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test;

import game.shape.Vector2;

public class TestVectorAngle {
	
	public static void main(String[] args) {
		
		Vector2 x = new Vector2(1, 0);
		Vector2 a = new Vector2(-1, -1);
		
		Vector2 v1 = new Vector2(1, 1);
		Vector2 v2 = new Vector2(-1, 1);
		Vector2 v3 = new Vector2(-1, -1);
		Vector2 v4 = new Vector2(1, -1);
		
		System.out.println(a.angle(x));
		System.out.println(a.angle(x) / (2 * Math.PI));

		System.out.println(v1 + " " + v1.angleX());
		System.out.println(v2 + " " + v2.angleX());
		System.out.println(v3 + " " + v3.angleX());
		System.out.println(v4 + " " + v4.angleX());

	}

}
