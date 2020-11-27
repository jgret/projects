package main;

/**
 * The Vector Class: used for movement, force
 */

public class Vector2 {

	public static final Vector2 UP = new Vector2(0, -1);
	public static final Vector2 DOWN = new Vector2(0, 1);
	public static final Vector2 LEFT = new Vector2(-1, 0);
	public static final Vector2 RIGHT = new Vector2(1, 0);

	public double x;
	public double y;

	public Vector2() {
		this.x = 0.0;
		this.y = 0.0;
	}

	public Vector2(Vector2 v1) {
		this.x = v1.x;
		this.y = v1.y;
	}

	/**
	 * Creates new Vector
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 */

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates new Vector from <b>v1</B> to <b>v2</b> <br>
	 * <br>
	 * AB = B - A
	 * 
	 * @param v1
	 * @param v2
	 */

	public Vector2(Vector2 v1, Vector2 v2) {
		this.x = v2.x - v1.x;
		this.y = v2.y - v1.y;
	}

	/**
	 * length of the Vector
	 * 
	 * @return the length of the Vector
	 */

	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	
	/**
	 * distance to a point
	 * 
	 * @param point
	 * @return the distance to the point
	 */
	
	public double distance(Vector2 point) {
		double dx = this.x - point.x;
		double dy = this.y - point.y;
		
		return Math.sqrt(dx * dx + dy * dy);
	}
	

	/**
	 * unit Vector
	 * 
	 * @return unit Vector
	 */

	public Vector2 unitvect() {

		Vector2 vector = new Vector2(this.x, this.y);
		double length = vector.length();
		if (length > 0) {
			vector.x /= length;
			vector.y /= length;
		}

		return vector;

	}

	/**
	 * Creates a unit Vector with the lenght given
	 * 
	 * @param length of the new Vector
	 * @return the unit Vector with the length
	 */

	public Vector2 unitvectl(double length) {

		Vector2 vector = new Vector2(this.x, this.y);
		vector = vector.unitvect();
		vector.x *= length;
		vector.y *= length;

		return vector;
	}

	/**
	 * Rotates the Vector by 180�
	 * 
	 * @return the new Vector
	 */

	public Vector2 negate() {

		return new Vector2(-this.x, -this.y);
	}

	/**
	 * negates the x value
	 * 
	 * @return the new Vector
	 */

	public Vector2 negateX() {

		return new Vector2(-this.x, this.y);
	}

	/**
	 * negates the y value
	 * 
	 * @return the new Vector
	 */

	public Vector2 negateY() {

		return new Vector2(this.x, -this.y);

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Sets the Vector to x and y
	 * 
	 * @param x
	 * @param y
	 */

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setV(Vector2 vec) {
		this.x = vec.x;
		this.y = vec.y;
	}

	/**
	 * Multiplies x and y by n
	 * 
	 * @param n
	 * @return the new Vector
	 */

	public Vector2 mul(double n) {
		return new Vector2(this.x * n, this.y * n);
	}

	/**
	 * Multiplies <b>this.x * v.x</b> and <b>this.y * v.y</b>
	 * 
	 * @param v
	 * @return the new Vector
	 */

	public Vector2 mul(Vector2 v) {
		return new Vector2(this.x * v.x, this.y * v.y);
	}

	/**
	 * Subtracts n from x and y
	 * 
	 * @param n
	 * @return the new Vector
	 */

	public Vector2 sub(double n) {
		return new Vector2(this.x - n, this.y - n);
	}

	/**
	 * Subtracts <b>this.x - v.x</b> and <b>this.y - v.y</b>
	 * 
	 * @param v
	 * @return the new Vector
	 */

	public Vector2 sub(Vector2 v) {
		return new Vector2(this.x - v.x, this.y - v.y);
	}

	/**
	 * Divides x and y by n
	 * 
	 * @param n
	 * @return the new Vector
	 */

	public Vector2 div(double n) {
		return new Vector2(this.x / n, this.y / n);
	}

	/**
	 * Adds x to this.x
	 * 
	 * @param x
	 * @return the new Vector
	 */

	public Vector2 addX(double x) {
		return new Vector2(this.x + x, this.y);
	}

	/**
	 * Adds y to this.y
	 * 
	 * @param y
	 * @return the new Vector
	 */

	public Vector2 addY(double y) {
		return new Vector2(this.x, this.y + y);
	}

	/**
	 * Adds the Vector to the Vector
	 * 
	 * @param v
	 * @return the new Vector 
	 */

	public Vector2 add(Vector2 v) {
		return new Vector2(this.x + v.x, this.y + v.y);
	}

	/**
	 * Tests if the Vectors are the same
	 * 
	 * @param a
	 * @return this.x == a.x && this.y == a.y;
	 */

	public boolean equals(Vector2 v) {
		return this.x == v.x && this.y == v.y;
	}

	public String toString() {

		return String.format("%.4g | %.4g", x, y);
		
	}

	public Vector2 clone() {
		return new Vector2(this);
	}

}
