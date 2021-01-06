/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.util;

public class StringUtil {
	
	public static String digits(double v, int n) {
		return String.format("%."+ n + "g", v).replace(',', '.');
	}

}
