package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class UnitTiger extends Unit implements Comparable<Unit>
{
    private int xSize = 10;

    private int ySize = 10;

    private double x = 0;

    private double y = 0;

    private double dx = 1;

    private double dy = 1;

    private double prevX = x;

    private double prevY = y;

    private int[][] board;

    private int w = 0;

    private int h = 0;

    private Color color;

    private LinearGradientPaint gradient;

    @SuppressWarnings("unused")
    private Boolean isSea = true;

    private Thread thr;

    @SuppressWarnings("unused")
    private UnitPlayer player;

    /**
     * @return the gradient
     */
    public LinearGradientPaint getGradient()
    {
        return gradient;
    }

    /**
     * @param gradient
     *            the gradient to set
     */
    public void setGradient(LinearGradientPaint gradient)
    {
        this.gradient = gradient;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * constructor for instance of tiger
     * @param board
     * @param w
     * @param h
     * @param player
     */
    public UnitTiger(int[][] board, int w, int h, UnitPlayer player)
    {
        super();

        this.w = w;
        this.h = h;
        this.player = player;
        this.board = board;

        boolean isEarth = false;

        outer: while (!isEarth) // check for non-out-of-bounds creation
        {
            x = (int) (Math.random() * w);
            y = (int) (Math.random() * h);

            // if tiger contains sea coordinates - reborn
            for (int i = (int) y; i <= (int) (y + ySize); i++)
            {
                for (int j = (int) x; j <= (int) (x + xSize); j++)
                {
                    if (board[j][i] == 0) // sea
                    {
                        isEarth = false;
                        // System.out.println("isSea");
                        continue outer;

                    }

                }
            }

            isEarth = true;
            // System.out.println(isEarth);
        }

        dx = (0.1 + (Math.random())) - (0.1 + (Math.random())); // was 0.1
        dy = 0.1 + ((Math.random())) - (0.1 + (Math.random()));

        // color = Color.getHSBColor((float) (Math.random()), 1.0f, 1.0f);
        // color = Color.BLACK;
        gradient = new LinearGradientPaint((int) x, (int) y, xSize, ySize,
                new float[] { 0f, 0.25f, 0.5f, 0.75f, 1f }, new Color[] {
                        Color.BLACK, Color.RED, Color.BLACK, Color.RED,
                        Color.BLACK });
    }

    /**
     * Overrides method that tell how to move for instance
     */
    @Override
    public void move(int[][] b)
    {
        this.board = b;
        
        // out of bounds check
        // if tiger contains sea or bounds coordinates - crash reaction
        for (int i = (int) y; i <= (int) (y + ySize); i++)
        {
            for (int j = (int) x; j <= (int) (x + xSize); j++)
            {

                if (board[j][i] == 0) // sea
                {
                    crashReact();
                    // System.out.println("is Sea");
                    i = (int) (y + ySize);
                    break;
                }
                else if (x + xSize >= w - 1 || y + ySize >= h - 1 || x <= 1
                        || y <= 1) // or bounds
                {
                    crashReact();
                    // System.out.println("is Bounds");
                    i = (int) (y + ySize);
                    break;
                }
            }

        }

        prevX = x; // vector for change direction
        prevY = y;

        x += dx;
        y += dy;

    }

    /**
     * return a shape of instance
     */
    public Ellipse2D getShape()
    {
        return new Ellipse2D.Double(x, y, xSize, ySize);
    }

    /**
     * compare all units of game in ArrayList
     * @param int
     */
    @Override
    public int compareTo(Unit enemy)
    {
        final int thisCenterX = (int) (this.x + (xSize / 2));
        final int thisCenterY = (int) (this.y + (ySize / 2));
        final int enemyCenterX = (int) (enemy.getX() + (xSize / 2));
        final int enemyCenterY = (int) (enemy.getY() + (ySize / 2));

        final int thisRadius = xSize / 2;
        final int enemyRadius = xSize / 2;
        ;
        return (int) ((Point2D.distance(thisCenterX, thisCenterY, enemyCenterX,
                enemyCenterY) - thisRadius - enemyRadius));
    }

    /**
     * overrides reaction of unit for crash with another unit instance - change direction
     */
    @Override
    public void crashReact() // change direction
    {
        int step = 2;
        
        try
        {
            if (x - prevX > 0 && y - prevY < 0) // right-up
            {
                // если - 90 градусов по вектору - вода, или выходит за границы поля
                if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 0
                        || (x - dx * step) < 0 || (y + dy * step) < 0)
                {
                    // если + 90 градусов по вектору - вода, или выходит за границы
                    // поля
                    if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 0
                            || (x + dx * step) > w || (y - dy * step) > h)
                    {
                        // попали во внутренний угол- отскок
                        dx = -dx;
                        dy = -dy;
                    }
                    else
                    {
                        // + 90
                        dy = -dy;
                    }
                }
                else
                {
                    // - 90 или если оба работают - обратно

                    // проверка +90 еше раз вдруг оба направления доступны
                    if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 0
                            || (x + dx * step) > w || (y - dy * step) > h)
                    {
                        // -90
                        dx = -dx;
                    }
                    else
                    {

                        // попали во внешний угол - отскок
                        dx = -dx;
                        dy = -dy;

                    }

                }
            }
            else if (x - prevX > 0 && y - prevY > 0) // right-down
            {
                // если - 90 градусов по вектору - вода, или выходит за границы поля
                if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 0
                        || (x + dx * step) > w || (y - dy * step) < 0)
                {
                    // если + 90 градусов по вектору - вода, или выходит за границы
                    // поля
                    if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 0
                            || (x - dx * step) < 0 || (y + dy * step) > h)
                    {
                        // попали во внутренний угол- отскок
                        dx = -dx;
                        dy = -dy;
                    }
                    else
                    {
                        // + 90
                        dx = -dx;
                    }
                }
                else
                {
                    // - 90 или если оба работают - обратно

                    // проверка +90 еше раз вдруг оба направления доступны
                    if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 0
                            || (x - dx * step) < 0 || (y + dy * step) > h)
                    {
                        // -90
                        dy = -dy;
                    }
                    else
                    {

                        // попали во внешний угол - отскок
                        dx = -dx;
                        dy = -dy;

                    }

                }
            }
            
            else if (x - prevX < 0 && y - prevY > 0) // left-down
            {
                // если - 90 градусов по вектору - вода, или выходит за границы поля
                if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 0
                        || (x - dx * step) > w || (y + dy * step) > h)
                {
                    // если + 90 градусов по вектору - вода, или выходит за границы
                    // поля
                    if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 0
                            || (x + dx * step) < 0 || (y - dy * step) < 0)
                    {
                        // попали во внутренний угол- отскок
                        dx = -dx;
                        dy = -dy;
                    }
                    else
                    {
                        // + 90
                        dy = -dy;
                    }
                }
                else
                {
                    // - 90 или если оба работают - обратно

                    // проверка +90 еше раз вдруг оба направления доступны
                    if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 0
                            || (x + dx * step) < 0 || (y - dy * step) < 0)
                    {
                        // -90
                        dx = -dx;
                    }
                    else
                    {

                        // попали во внешний угол - отскок
                        dx = -dx;
                        dy = -dy;

                    }

                }
            }
            
            else if (x - prevX < 0 && y - prevY < 0) // left-up
            {
                // если - 90 градусов по вектору - вода, или выходит за границы поля
                if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 0
                        || (x + dx * step) < 0 || (y - dy * step) > h)
                {
                    // если + 90 градусов по вектору - вода, или выходит за границы
                    // поля
                    if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 0
                            || (x - dx * step) > w || (y + dy * step) < 0)
                    {
                        // попали во внутренний угол- отскок
                        dx = -dx;
                        dy = -dy;
                    }
                    else
                    {
                        // + 90
                        dx = -dx;
                    }
                }
                else
                {
                    // - 90 или если оба работают - обратно

                    // проверка +90 еше раз вдруг оба направления доступны
                    if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 0
                            || (x - dx * step) > w || (y + dy * step) < 0)
                    {
                        // -90
                        dy = -dy;
                    }
                    else
                    {

                        // попали во внешний угол - отскок
                        dx = -dx;
                        dy = -dy;

                    }

                }
            }
            else    //внезапно
            {
                /*dx = -dx;
                dy = -dy;*/
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            
        }

    }

    @Override
    public Thread getThr()
    {
        return thr;
    }

    /**
     * set thread
     */
    @Override
    public void setThr(Thread thr)
    {
        this.thr = thr;
    }

    @Override
    public void interrupt()
    {
        this.thr.interrupt();
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }

    @Override
    public Rectangle2D getRectShape()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "EnemyTiger";
    }

    @Override
    public int getxSize()
    {
        return xSize;
    }

    @Override
    public void setxSize(int xSize)
    {
        this.xSize = xSize;

    }

    @Override
    public int getySize()
    {
        return ySize;
    }

    @Override
    public void setySize(int ySize)
    {
        this.ySize = ySize;
    }

}
