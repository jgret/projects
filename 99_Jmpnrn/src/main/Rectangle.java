/*******************************************************
 * Copyright (C) 2020-2021 jgret <thomgreimel@gmail.com>
 * 
 * This file is part of Jmpnrn.
 * 
 * Jmpnrn can not be copied and/or distributed without the express
 * permission of jgret
 *******************************************************/
package main;

import java.awt.*;

public class Rectangle implements Drawable {

    protected Vector2 pos;
    protected Vector2 dim;

    public Rectangle(double x, double y, double width, double height) {
        this.pos = new Vector2(x, y);
        this.dim = new Vector2(width, height);

    }

    public double getX() {
        return this.pos.getX();
    }

    public void set(double x, double y, double width, double height) {
        this.pos.set(x, y);
        this.dim.set(width, height);
    }

    public void setX(double x) {
        this.pos.setX(x);
    }

    public double getY() {
        return this.pos.getY();
    }

    public void setY(double y) {
        this.pos.setY(y);
    }

    public double getWidth() {
        return dim.getX();
    }

    public void setWidth(double width) {
        this.dim.setX(width);
    }

    public double getHeight() {
        return dim.getY();
    }

    public void setHeight(double height) {
        this.dim.setY(height);
    }

    public Vector2 getCenter() {
        return new Vector2(this.pos.getX() + this.dim.getX() / 2, this.pos.getY() + dim.getY() / 2);
    }

    public void setCenter(Vector2 center) {
        this.pos.setX(center.getX() - dim.getX() / 2);
        this.pos.setY(center.getY() - dim.getY() / 2);
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getDim() {
        return dim;
    }

    public void setDim(Vector2 dim) {
        this.dim = dim;
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

        if (!(Double.isFinite(t_near.getX() + 1) || Double.isFinite(t_near.getY() + 1)
                || Double.isFinite(t_far.getX() + 1) || Double.isFinite(t_far.getY() + 1))) {
            return false;
        }

        if (t_hit_far < 0)
            return false;

        if (t_hit_near > 1)
            return false;

        if (Double.isNaN(t_hit_near)) {
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

    public boolean intersects(Rectangle rect) {
        return this.getX() < rect.getX() + rect.getWidth() &&
                this.getX() + this.getWidth() > rect.getX() &&
                this.getY() < rect.getY() + rect.getHeight() &&
                this.getY() + this.getHeight() > rect.getY();
    }


    private void draw(Graphics2D g2) {
        g2.drawRect((int) pos.getX(), (int) pos.getY(), (int) dim.getX(), (int) dim.getY());
    }

    @Override
    public String toString() {
        return "Pos: " + pos.toString() + " Dim: " + dim.toString();
    }

    @Override
    public void draw(Graphics2D g2, Camera cam, int scale) {
        if (cam == null) {
            this.draw(g2);
        } else {
            g2.drawRect((int) ((getX() - cam.getX()) * scale), (int) ((getY() - cam.getY()) * scale), (int) (getWidth() * scale), (int) (getHeight() * scale));
        }
    }


}
