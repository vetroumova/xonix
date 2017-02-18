package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class UnitShark extends Unit implements Comparable<Unit>
{
    private int xSize = 10;

    private int ySize = 10;

    private double x = 0;

    private double y = 0;

    private double dx = 1;

    private double dy = 1;

    private double prevX = x;

    private double prevY = y;

    @SuppressWarnings("unused")
    private int w = 0;

    @SuppressWarnings("unused")
    private int h = 0;

    private Color color;

    private Thread thr;

    private UnitPlayer player;

    private int[][] board;

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
     * @return the xSize
     */
    public int getxSize()
    {
        return xSize;
    }

    /**
     * @param xSize
     *            the xSize to set
     */
    public void setxSize(int xSize)
    {
        this.xSize = xSize;
    }

    /**
     * @return the ySize
     */
    public int getySize()
    {
        return ySize;
    }

    /**
     * @param ySize
     *            the ySize to set
     */
    public void setySize(int ySize)
    {
        this.ySize = ySize;
    }

    /**
     * constructor for instance of shark
     * 
     * @param board
     * @param w
     * @param h
     * @param player
     */
    public UnitShark(int[][] board, int w, int h, UnitPlayer player)
    {
        super();

        this.w = w;
        this.h = h;
        this.player = player;
        this.board = board;

        boolean isEarth = true;

        outer: while (isEarth) // check for non-out-of-bounds creation
        {
            x = (int) (Math.random() * w);
            y = (int) (Math.random() * h);

            // if shark contains earth coordinates - reborn
            for (int i = (int) y; i <= (int) (y + ySize); i++)
            {
                for (int j = (int) x; j <= (int) (x + xSize); j++)
                {
                    if (board[j][i] == 1) // earth
                    {
                        isEarth = true;
                        // System.out.println(isEarth);
                        continue outer;
                    }
                }
            }
            isEarth = false;
            // System.out.println(isEarth);

        }

        dx = (0.1 + (Math.random())) - (0.1 + (Math.random())); // was 0.1
        dy = 0.1 + ((Math.random())) - (0.1 + (Math.random()));

        // color = Color.getHSBColor((float) (Math.random()), 1.0f, 1.0f);
        color = new Color(222, 222, 222, 128); // last - opaque
    }

    /**
     * Overrides method that tell how to move for instance
     */
    @Override
    public void move(int[][] b)
    {
        board = b;

        // out of bounds check
        // if shark contains earth coordinates - crash reaction
        for (int i = (int) y; i <= (int) (y + ySize); i++)
        {
            for (int j = (int) x; j <= (int) (x + xSize); j++)
            {
                if (board[j][i] == 1) // earth
                {
                    crashReact();
                    System.out.println("isEarth");
                    i = (int) (y + ySize);
                    break;
                }
                else if (board[j][i] == 2) // trace
                {
                    player.crashReact(); // kill a player & traces
                }
            }
            // System.out.println("i: " + i);
        }

        prevX = x;
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
        final int enemyRadius = xSize / 2; // TODO RECTANGLE
        ;
        return (int) ((Point2D.distance(thisCenterX, thisCenterY, enemyCenterX,
                enemyCenterY) - thisRadius - enemyRadius));
    }

    /**
     * overrides reaction of unit for crash with another unit instance - change
     * direction
     */
    @Override
    public void crashReact()
    {
        int step = 30;
        // TODO - if walls - check directions on 90 degr both sides

        if (x - prevX > 0 && y - prevY < 0) // right-up
        {
            // если - 90 градусов по вектору - земля
            if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 1)
            {
                // если + 90 градусов по вектору - земля
                if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 1)
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
                if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 1)
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
            // если - 90 градусов по вектору - земля
            if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 1)
            {
                // если + 90 градусов по вектору - земля
                if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 1)
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
                if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 1)
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
            // если - 90 градусов по вектору - земля
            if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 1)
            {
                // если + 90 градусов по вектору - земля
                if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 1)
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
                if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 1)
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
            // если - 90 градусов по вектору - земля
            if (board[(int) (x + dx * step)][(int) (y - dy * step)] == 1)
            {
                // если + 90 градусов по вектору - земля
                if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 1)
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
                if (board[(int) (x - dx * step)][(int) (y + dy * step)] == 1)
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
        else
        // внезапно
        {
            /*
             * dx = -dx; dy = -dy;
             */
        }
    }

    /**
     * @return Thread thr
     */
    public Thread getThr()
    {
        return thr;
    }

    /**
     *  set thread
     */
    public void setThr(Thread thr)
    {
        this.thr = thr;
    }

    /**
     *  interrupt a thread
     */
    public void interrupt()
    {
        this.thr.interrupt();
    }

    /**
     * get x
     */
    @Override
    public double getX()
    {
        return x;
    }

    /**
     * get y
     */
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
    public LinearGradientPaint getGradient()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "EnemyShark";
    }

}
