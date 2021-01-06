/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.gamestate;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import game.Game;
import game.graphics.Camera;
import game.graphics.Image2d;
import game.graphics.Images;
import game.graphics.Screen;
import game.io.FileIO;
import game.questions.Question;
import game.questions.Questionair;
import game.questions.Questions;
import sound.SoundEngine;

public class GameStateQuestion extends GameState implements Runnable {
	
	private Image2d background;
	private ArrayList<Questionair> questionairs;
	private Questionair questionair;
	private Questions questions;
	private Question question;
	private volatile boolean done; 
	private Thread input;

	public GameStateQuestion(Game game) {
		super(game);
		this.questions = game.getQuestions();
		this.questionairs = new ArrayList<>();
	}

	@Override
	public void init() {
		this.background = FileIO.loadImage("img/ui/bg_hell.png");
		questionairs.add(new Questionair("The Big Yellow Brain", FileIO.loadImage("img/questionairs/brain_yellow.png")));
		questionairs.add(new Questionair("The Big Green Brain", FileIO.loadImage("img/questionairs/brain_green.png")));
		questionairs.add(new Questionair("The Big Red Brain", FileIO.loadImage("img/questionairs/brain_red.png")));
	}

	@Override
	public void onStart() {
		Random random = new Random();
		questionair = questionairs.get(random.nextInt(questionairs.size()));
		SoundEngine.getInstance().loop("darkside", -1);
		this.question = questions.next();
		this.input = new Thread(this);
		this.input.start();
	}

	@Override
	public void update(double elapsedTime) {
		
		if (done) {
			gsm.changeGameState(GameStateType.PLAY);
		}
		
	}

	@Override
	public void onEnd() {
		SoundEngine.getInstance().stop("darkside");
	}

	@Override
	public void draw(Graphics2D g2, Camera cam) {
		
		background.draw(g2, 0, 0, cam.getWidth(), cam.getHeight());
		
		g2.setFont(Screen.PIXEL_FONT_LARGE);
		g2.drawString(questionair.getName() + " asks,", 30, 100);
		
		Image2d ques = questionair.getImg();
		ques.draw(g2, 0, cam.getHeight() - ques.getHeight(), ques.getWidth(), ques.getHeight());
		
		g2.setFont(Screen.PIXEL_FONT_SMALL);
		
		String text = question.getQuestion();
		int charsPerLine = 40;
		int lines = text.length() / charsPerLine + 1;
		
		for (int i = 0; i < lines; i++) {

			int from = i * charsPerLine;
			int to = (i + 1) * charsPerLine;
			
			if (to >= text.length()) {
				to = text.length() - 1;
			}
			
			String line = text.substring(from, to);
			
			FontMetrics fm = g2.getFontMetrics();
			int swidth = fm.stringWidth(line);
			int sheight = fm.getAscent();
			int offset = (int) cam.getHeight() / 2 - (sheight - lines) / 2 ;
			
			g2.drawString(line, (int) cam.getWidth() / 2 - swidth / 2, i * (sheight + 10) + offset);
		}

	}

	@Override
	public void run() {
		
		done = false;
		
		while (!done) {
			
			String aswer = JOptionPane.showInputDialog(game.getScreen(), "Enter your Answer");
			if (question.isCorrect(aswer)) {
				done = true;
			} else {
				System.exit(-1);
			}
			
		}
		
	}

}
