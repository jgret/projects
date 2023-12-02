/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package test.questions;

import java.util.Scanner;

import game.questions.Question;
import game.questions.Questions;

public class TestQuestions {

	public static void main(String[] args) {
		Questions questions = new Questions("db/questions.db");
		
		Scanner in = new Scanner(System.in);
		while (true) {
			
			Question q = questions.next();
			System.out.println(q.getQuestion());
			String answer = in.nextLine();
			if (q.isCorrect(answer)) {
				System.out.println("Richtig: " + q.getPoints());
			} else {
				System.err.println("Falsche antwort, richtig wäre " + q.getSolution());
			}
			
		}
	}

}
