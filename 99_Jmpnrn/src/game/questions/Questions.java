/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.questions;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.sqlite.SQLiteConnection;

import game.io.FileIO;

public class Questions {
	
	private Question[] questions;
	private Random random;
	
	public Questions(String fname) {
		this.questions = readFromDB(fname);
		this.random = new Random();
	}
	
	private Question[] readFromDB(String fname) {
		ArrayList<Question> questions = new ArrayList<Question>();
		SQLiteConnection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = (SQLiteConnection) DriverManager.getConnection("jdbc:sqlite:" + FileIO.getURL(fname).getFile());
			c.setAutoCommit(false);
			System.out.println(fname + ".db: opened  successfully");

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
	
}
