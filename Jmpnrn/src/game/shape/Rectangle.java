/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package game.shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import game.graphics.Camera;
import game.graphics.Drawable;
import game.graphics.Screen;
import game.util.MathUtil;

public class Rectangle extends Rectangle2D.Double implements Drawable {

	private static final long serialVersionUID = 1L;
	public static final int POS_NONE = 0;
	public static final int POS_UP = 1;
	public static final int POS_DOWN = 2;
	public static final int POS_LEFT = 3;
	public static final int POS_RIGHT = 4;

	public Rectangle() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public Rectangle(double x, double y, double width, double height) {
    	super(x, y, width, height);
    }
    
    public Rectangle(Rectangle r) {
    	super(r.x, r.y, r.width, r.height);
    }

    public Vector2 getCenter() {
        return new Vector2(x + width / 2, y + height / 2);
    }

    public void setCenter(Vector2 center) {
    	this.x = center.getX() - width / 2;
    	this.y = center.getY() - height / 2;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public void setPosition(Vector2 pos) {
    	this.x = pos.x;
    	this.y = pos.y;
    }
    
    public void setPosition(double x, double y) {
    	this.x = x;
    	this.y = y;
    }
    
    public void addPosition(Vector2 pos) {
    	this.x += pos.x;
    	this.y += pos.y;
    }
    
    public void addPosition(double x, double y) {
    	this.x += x;
    	this.y += y;
    }

    public Vector2 getDim() {
    	return new Vector2(width, height);
    }
    
    public void setDimensions(Point p) {
    	this.width = p.getX();
    	this.height = p.getY();
    }
    
    public void setDimensions(double width, double height) {
    	this.width = width;
    	this.height = height;
    }
    
    public boolean isInside(double x, double y) {
        return (x >= this.getX() && x <= this.getX() + this.getWidth() && y >= this.getY()
                && y < this.getY() + this.getHeight());
    }

    public boolean isInside(Point p) {
        return (p.x >= this.getX() && p.x <= this.getX() + this.getWidth() && p.y >= this.getY()
                && p.y < this.getY() + this.getHeight());
    }

    public boolean collRay(Vector2 origin, Vector2 dir, Vector2 cp, Vector2 cn) {
        Vector2 t_near = new Vector2((this.getX() - origin.getX()) / dir.getX(),
                (this.getY() - origin.getY()) / dir.getY());
        Vector2 t_far = new Vector2((this.getX() + this.getWidth() - origin.getX()) / dir.getX(),
                (this.getY() + this.getHeight() - origin.getY()) / dir.getY());

        if (t_near.getX() > t_far.getX()) {
            double x = t_near.getX();
            t_near.setX(t_far.getX());
            t_far.setX(x);
        }

        if (t_near.getY() > t_far.getY()) {
            double y = t_near.getY();
            t_near.setY(t_far.getY());
            t_far.setY(y);
        }

        if (t_near.getX() > t_far.getY() || t_near.getY() > t_far.getX()) {
            return false;
        }

        double t_hit_near = t_near.getX() > t_near.getY() ? t_near.getX() : t_near.getY();
        double t_hit_far = t_far.getX() < t_far.getY() ? t_far.getX() : t_far.getY();

        if (!(java.lang.Double.isFinite(t_near.getX() + 1) || java.lang.Double.isFinite(t_near.getY() + 1)
                || java.lang.Double.isFinite(t_far.getX() + 1) || java.lang.Double.isFinite(t_far.getY() + 1))) {
            return false;
        }

        if (t_hit_far < 0)
            return false;

        if (t_hit_near > 1)
            return false;

        if (java.lang.Double.isNaN(t_hit_near)) {
            System.out.println(t_near);
            System.out.println(t_far);
            return false;
        }

        cp.set(origin.getX() + t_hit_near * dir.getX(), origin.getY() + t_hit_near * dir.getY());

        if (t_near.getX() > t_near.getY()) {
            if (dir.getX() < 0) {
                cn.set(1, 0);
            } else {
                cn.set(-1, 0);
            }
        } else if (t_near.getX() < t_near.getY()) {
            if (dir.getY() < 0) {
                cn.set(0, 1);
            } else {
                cn.set(0, -1);
            }
        }

        return true;

    }

    public double getLeft() {
        return this.getX();
    }

    public double getRight() {
        return this.getX() + this.getWidth();
    }

    public double getTop() {
        return this.getY();
    }

    public double getBot() {
        return this.getY() + this.getHeight();
    }

    @Override
    public String toString() {
		return "Pos: " + getPosition() + " Dim: " + getDim();
    }

    @Override
    public void draw(Graphics2D g2, Camera cam) {
        if (cam == null) {
        	g2.draw(this);
        } else {
            g2.drawRect((int) (((getX() - cam.getX()) * Screen.TILESIZE)), (int) (((getY() - cam.getY()) * Screen.TILESIZE)), (int) (getWidth() * Screen.TILESIZE), (int) (getHeight() * Screen.TILESIZE));
        }
    }
    
    public Rectangle scale(double s) {
    	Rectangle r = new Rectangle(this);
    	r.x *= s;
    	r.y *= s;
    	r.width *= s;
    	r.height *= s;
    	return r;
    }
    
    public void setX(double x) {
    	this.x = x;
    }
    
    public void setY(double y) {
    	this.y = y;
    }
    
    public void setWidth(double widht) {
    	this.width = widht;
    }
    
    public void setHeight(double height) {
    	this.height = height;
    }
    
    public void setTop(double y) {
    	this.y = y;
    }
    
    public void setBot(double y) {
    	this.y = y - height;
    }
    
    public void setLeft(double x) {
    	this.x = x;
    }
    
    public void setRight(double x) {
    	this.x = x - width;
    }
    
    public void setCenterX(double x) {
    	this.x = x - width / 2;
    }
    
    public void setCenterY(double y) {
    	this.y = y - height / 2;
    }

	public void translate(double deltaX, double deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
	
	public int predictPositionOf(Rectangle master) {
		double dxl, dxr, dyt, dyb;
		dxl = Math.abs(this.getRight() - master.getLeft());
		dxr = Math.abs(master.getRight() - this.getLeft());
		dyt = Math.abs(this.getBot() - master.getTop());
		dyb = Math.abs(master.getBot() - this.getTop());

		if (MathUtil.min(dxl, dxr, dyt, dyb)) {
			// collision left
			return POS_RIGHT;
		} else

		if (MathUtil.min(dxr, dxl, dyt, dyb)) {
			// collision right
			return POS_LEFT;
		} else

		if (MathUtil.min(dyt, dxr, dxl, dyb)) {
			// collision top
			return POS_DOWN;
		} else

		if (MathUtil.min(dyb, dxr, dyt, dxl)) {
			// collision bot
			return POS_UP;
		}
		
		return 0;
	}


}
