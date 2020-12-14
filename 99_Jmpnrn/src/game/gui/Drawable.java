/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gui;

import java.awt.*;

public interface Drawable {

	/**
	 * This method is called when the game gets rendered
	 * 
	 * @param g2 the graphics object to draw to<br>
	 * @param cam the current position of the camera.<br> 
	 *        Use getX(scale) /getY(scale) to get the location on screen<br>
	 * @param scale the scale of the game set by the screen object. <br>
	 *        Position should be in unit space and then multiplied by scale
	 */
	public void draw(Graphics2D g2, Camera cam, int scale);

}
