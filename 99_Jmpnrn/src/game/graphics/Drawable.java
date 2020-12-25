/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import java.awt.*;

public interface Drawable {

	/**
	 * This method is called when the game gets rendered
	 * 
	 * @param g2 the graphics object to draw to<br>
	 * @param cam the current position of the camera.<br> 
	 *        Use getX(scale) /getY(scale) to get the location on screen<br>
	 */
	public void draw(Graphics2D g2, Camera cam);

}
