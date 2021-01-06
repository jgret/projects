/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.questions;

import game.graphics.Image2d;

public class Questionair {
	
	private String name;
	private Image2d img;
	
	public Questionair(String name, Image2d img) {
		this.name = name;
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image2d getImg() {
		return img;
	}

	public void setImg(Image2d img) {
		this.img = img;
	}
	
}
