package com.carbajal.danniel.ioapp.models.programacionlineal.grafico;

/**
 * Created by daniel on 11/26/16.
 */

public class Point2D {

    private double x;
    private double y;
    private boolean bound;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
