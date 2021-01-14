/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.graphics;

import game.io.FileIO;

public class Images {

	public static final Image2d HEART_FULL = FileIO.loadImage("img/ui/game_heart_full.png");
	public static final Image2d HEART_HALF = FileIO.loadImage("img/ui/game_heart_half.png");
	public static final Image2d HEART_DEAD = FileIO.loadImage("img/ui/game_heart_dead.png");
	public static final Image2d HOLY_GRAIL = FileIO.loadImage("img/ui/bg_holy_grail.png");
	public static final Image2d TITLE_FONT = FileIO.loadImage("img/ui/game_title.png");
	public static final Image2d BACARDI = FileIO.loadImage("img/bacardi.png");
	public static final Image2d MENU_BUTTON = FileIO.loadImage("img/ui/button.png");
	public static final Image2d MENU_BACKGROUND = FileIO.loadImage("img/ui/bg_menu.png");
	
	//Questions
	public static final Image2d QUESTIONS_BG = FileIO.loadImage("img/ui/bg_black_hole.png");
	public static final Image2d ARROW = FileIO.loadImage("img/game_arrow.png");
	public static final Image2d SWORD = FileIO.loadImage("img/game_sword.png");
	public static final Image2d SWORD_HIT = FileIO.loadImage("img/game_sword_hit.png");
	
	public static final Image2d SKELLY = FileIO.loadImage("img/skeleton.png");
	
}
