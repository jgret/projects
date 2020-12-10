package main;

public class MathUtil {

	public static double pyth(double a, double b) {
		return Math.sqrt(a * a + b * b);
	}

	public static double pytk(double c, double a) {
		return Math.sqrt(c * c - a * a);
	}
	
	public static boolean min(double val, double... d) {
		
		for (Double v : d) {
			if (val > v) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean max(double val, double... d) {
		
		for (Double v : d) {
			if (val < v) {
				return false;
			}
		}
		
		return true;
	}

}
