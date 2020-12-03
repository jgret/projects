/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package sound;

public class Sound {
	
	public Sound() {
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Track track = new Track("sound/darkside.wav");
		track.play();

		while (true) {
			
		}
		
	}
	
	

}
