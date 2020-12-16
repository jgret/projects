/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.io;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import game.graphics.Image2d;

public class FileIO {
	
	public static String read(String fname) {
		String content = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(FileIO.class.getClassLoader().getResource(fname).getFile()));
			String line;
			while ((line = in.readLine()) != null) {
				content += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static JsonObject readJson(String fname) {
		return (JsonObject) JsonParser.parseString(read(fname));
	}
	
	public static Image2d loadImage(String name) {
		Image2d img = null;
		try {
			img = new Image2d(ImageIO.read(getURL(name)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage loadBufferedImage(String name) {
		try {
			return ImageIO.read(getURL(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static URL getURL(String fname) {
		return FileIO.class.getClassLoader().getResource(fname);
	}

	public static InputStream getInputStream(String fname) {
		return FileIO.class.getClassLoader().getResourceAsStream(fname);
	}
}
