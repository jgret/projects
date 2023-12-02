/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game;

public class Time {

	private static double elapsedTime;
	
	public static double elapsedTime() {
		return elapsedTime;
	}
	
	public static void setElapsedTime(double et) {
		elapsedTime = et;
	}
	
    public static double getTime() {
        return System.nanoTime() / 1e9;
    }

}
