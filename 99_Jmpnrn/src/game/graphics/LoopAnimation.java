/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

public class LoopAnimation implements Animation {

	private Image2d[] frames;
	private int frame;
	
	public LoopAnimation(Image2d[] frames) {
		this.frames = frames;
	}
	
	@Override
	public Image2d next() {
		frame++;
		frame %= frames.length;
		return frames[frame];
	}

	@Override
	public boolean done() {
		return false;
	}

	@Override
	public int getFrame() {
		return frame;
	}

	@Override
	public int getLength() {
		return frames.length;
	}

}
