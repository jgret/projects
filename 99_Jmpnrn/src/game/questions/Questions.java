/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.questions;

import java.io.File;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteConnection;

import game.io.FileIO;

public class Questions {
	
	private Question[] questions;
	private Random random;
	
	public Questions(String fname) {
		this.questions = readFromDataBase(fname);
		this.random = new Random();
	}
	
	private Question[] readFromDataBase(String fname) {
		ArrayList<Question> questions = new ArrayList<Question>();
		SQLiteConnection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("jdbc:sqlite:" + fname + " : " + new File(fname));
			c = (SQLiteConnection) DriverManager.getConnection("jdbc:sqlite:" + FileIO.getURL(fname).getFile());
			c.setAutoCommit(false);
			System.out.println(fname + ": opened  successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM FRAGEN;" );

			while ( rs.next() ) {
				int id = rs.getInt("id");
				String  question = rs.getString("fragentext");
				String  solution = rs.getString("loesungstext");
				int points = rs.getInt("punkte");
				Question q = new Question(question, solution, points);
				questions.add(q);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return (Question[]) questions.toArray(new Question[1]);
	}
	
	public Question next() {
		return questions[random.nextInt(questions.length)];
	}
	
	public static void main(String[] args) {
		Questions questions = new Questions("db/questions.db");
		Question question = questions.next();
		String answer = JOptionPane.showInputDialog(null, question.getQuestion(), "Question", 3);
		
		if (question.isCorrect(answer)) {
			System.out.println("Richtig");
		} else {
			System.out.println("Falsch");
		}
		
	}
	
}
