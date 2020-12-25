/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.questions;

public class Question {

	private String question;
	private String solution;
	private int points;

	public Question(String question, String solution, int points) {
		this.question = question;
		this.solution = solution;
		this.points = points;
	}
	
	public boolean isCorrect(String answer) {
		if (solution.equals(answer)) {
			return true;
		}
		return false;
	}
	
	public String getQuestion() {
		return question;
	}

	public String getSolution() {
		return solution;
	}

	public int getPoints() {
		return points;
	}

	@Override
	public String toString() {
		return question + "\nSolution: " + solution + "\nPoints: " + points;
	}
	
}
