/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.stream.FileImageOutputStream;

import game.io.FileIO;

public class SoundEngine {
	
	private static SoundEngine instance;

	public static SoundEngine getInstance() {
		if (SoundEngine.instance == null) {
			SoundEngine.instance = new SoundEngine();
		}
		return instance;
	}
	
	private HashMap<String, Track> tracks;
	
	public SoundEngine() {
		this.tracks = new HashMap<>();
	}
	
	public void play(String id) {
		
		Track track;
		if ((track = tracks.get(id)) != null) {
			track.play();
		} else {
			System.err.println("Track not found");
		}
		
	}
	
	public void stop(String id) {
		
		Track track;
		if ((track = tracks.get(id)) != null) {
			track.stop();
		} else {
			System.err.println("Track not found");
		}
		
	}
	
	public void pause(String id) {
		
		Track track;
		if ((track = tracks.get(id)) != null) {
			track.pause();
		} else {
			System.err.println("Track not found");
		}
		
	}
	
	public void resume(String id) {
		
		Track track;
		if ((track = tracks.get(id)) != null) {
			track.resume();
		} else {
			System.err.println("Track not found");
		}
		
	}
	
	public void waitForEnd(String id) {
		tracks.get(id).waitForEnd();
	}
	
	public void setVolume(String id, float value) {
		tracks.get(id).setVolume(value);
	}
	
	public void setGlobalVolume(float value) {
		Iterator<String> it = tracks.keySet().iterator();
		while (it.hasNext()) {
			tracks.get(it.next()).setVolume(value);
		}
	}
	
	public void loop(String id, int n) {
		if (n > 0) {
			tracks.get(id).loop(n);
		} else {
			tracks.get(id).loop();
		}
	}
	
	public void load(String tracklistfile) {
		InputStream in = FileIO.getResourceAsStream(tracklistfile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(":");
				String id = split[0];
				String path = split[1];
				this.tracks.put(id, new Track(path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args) throws InterruptedException {
		SoundEngine sound = SoundEngine.getInstance();
		sound.load("sound/tracklist.trl");
		sound.setGlobalVolume(-40);
		
		long time = System.nanoTime();
		sound.play("main_theme");
		System.out.println(System.nanoTime() - time);
		
		while (true) {
			
		}
		
	}

}