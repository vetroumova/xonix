package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public abstract class Unit
{
    public abstract double getX();
    public abstract double getY();
    
    public abstract int getxSize();
    public abstract void setxSize(int xSize);
    public abstract int getySize();
    public abstract void setySize(int ySize);
    
    public abstract void setColor(Color color);
    public abstract Color getColor();
    public abstract void move(int[][] board);  // board of sea-earth
    public abstract void crashReact();

    public abstract Thread getThr();
    public abstract void setThr(Thread thr);
    public abstract void interrupt();
    public abstract int compareTo(Unit enemy);
    
    public abstract Ellipse2D getShape();
    public abstract Rectangle2D getRectShape();
    
    public abstract LinearGradientPaint getGradient();
}
