/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

public interface Animation {
	
	/**
	 * This Method should return the next Image of the animation
	 * @return the next Image
	 */
	public Image2d next();
	
	/**
	 * This Method should be used to tell if an animation has reached its limit
	 * @return if the animation has more images to display
	 */
	public boolean done();

	/**
	 * This Method should return the current Frame of the animation
	 * @return the current frame, if it equals length done() should be true
	 */
	public int getFrame();
	
	/**
	 * This Method should return the length of the Animation
	 * @return the amout of frames
	 */
	public int getLength();

}
