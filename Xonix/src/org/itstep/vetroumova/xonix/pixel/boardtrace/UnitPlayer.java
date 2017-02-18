package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class UnitPlayer extends Unit implements Comparable<Unit>
{
    private int xSize = 10;

    private int ySize = xSize;

    private double x = 0;

    private double y = 0;

    private static double dx = 0; // wasn't static

    private static double dy = 0;

    private int w = 0;

    private int h = 0;

    private Color color;

    // private Rectangle2D bounds;

    private Thread thr;

    private int life = 3;

    ArrayList<Unit> sharks;

    // private boolean isDead = false; // for trace - to stop getting x,y -
    // coordinates

    private boolean isSwim = false; // to fill trace to earth

    // private boolean isGround = false;

    private int[][] board;

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
     * @return the dx
     */
    public static double getDx()
    {
        return dx;
    }

    /**
     * @param dx
     *            the dx to set
     */
    public static void setDx(double dx)
    {
        UnitPlayer.dx = dx;
    }

    /**
     * @return the dy
     */
    public static double getDy()
    {
        return dy;
    }

    /**
     * @param dy
     *            the dy to set
     */
    public static void setDy(double dy)
    {
        UnitPlayer.dy = dy;
    }

    /**
     * @return the life
     */
    public int getLife()
    {
        return life;
    }

    /**
     * @param life
     *            the life to set
     */
    public void setLife(int life)
    {
        this.life = life;
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
     * constructor of player
     * 
     * @param w
     * @param h
     */
    public UnitPlayer(int w, int h)
    {
        super();
        x = w / 2;
        y = UnitComponent.getStartSeaBounds() - ySize - 5;

        this.w = w;
        this.h = h;

        color = Color.RED;
    }

    /**
     * Overrides method that tell how to move for instance
     */
    @Override
    public void move(int[][] b)
    {
        x += dx;
        y += dy;

        board = b;

        // out of bounds check
        isOutOfBounds();

        // //////////////////////////////////////////////
        // traces!!!
        // /////////////////////////////////////////////

        /*
         * for (int i = 0; i < h; i++) { for (int j = 0; j < w; j++) { if
         * (board[j][i] == 2) // sea { isSwim = true; } else { isSwim = false; }
         * } }
         */

        // для ненаступления на свой след

        /*
         * надо проверить 4 направления вокруг игрока, 
         * если в одном из направлений (вне контура игрока) есть след - 
         * или прекратить движение в этом направлении, или чуть отодвинуться назад
         * 
         * 
         * 
         * if (board[(int) (x-1)][(int) y] == 2 || board[(int)
         * (x+xSize+1)][(int) y] == 2) // trace { dx = 0; }
         * 
         * if (board[(int)x][(int) y-1] == 2 || board[(int)x][(int) y+ySize+1]
         * == 2) // trace { dy = 0; }
         */

        // //////////////////

        sharks = UnitComponent.getAllSharks();

        // sea-check for trace
        for (int i = (int) y; i <= (int) (y + ySize); i++)
        {
            for (int j = (int) x; j <= (int) (x + xSize); j++)
            {
                if (board[j][i] == 0) // sea
                {
                    // draw trace exactly on player size
                    // System.out.println("isSea");
                    board[j][i] = 2;

                    isSwim = true;
                }
            }
        }

        // earth-check for trace
        if (board[(int) x][(int) y] == 1
                && board[(int) (x + xSize)][(int) (y + ySize)] == 1) // earth
        {
            // System.out.println("isEarth");

            // Do we had swimming?
            if (isSwim == true)
            {
                // method fillSea() - work with board [][]
                fillSea();

                isSwim = false; // filled area, disable flag.
            }
        }
    }

    /**
     * checking move of player out of bounds
     */
    private void isOutOfBounds()
    {
        // TODO check

        if (x + xSize > w)
        {
            x = w - xSize - 5;
            dx = 0;
        }

        if (x < 5)
        {
            x = 5;
            dx = 0;
        }

        if (y + ySize + 1 > h)
        {
            y = h - ySize - 1;
            dy = -dy;
        }

        if (y < 0)
        {
            y = 0;
            dy = -dy;
        }

    }

    /**
     * fill a trace of player to earth & check possibility to fill sea in trace
     * bounds
     */
    @SuppressWarnings("unused")
    private void fillSea()
    {
        // work with board [][] to change closed trace to earth & check for
        // sharks in surrounded area

        int xShark = -1;
        int yShark = -1;
        int xSizeShark = -1;
        int ySizeShark = -1;
        int centerXShark = -1;
        int centerYShark = -1;

        // put sharks from ArrayList with units to Board
        /*
         * for (Unit shark : sharks) { xShark = (int) shark.getX(); yShark =
         * (int) shark.getY(); xSizeShark = shark.getxSize(); ySizeShark =
         * shark.getySize(); centerXShark = xShark + (xSizeShark / 2);
         * centerYShark = xShark + (xSizeShark / 2);
         * 
         * // во время простановки на доску акул расширить размер акулы - во //
         * избежание промаха с движущимися объектами?
         * 
         * // или брать только центр акулы для упрощения? - пока такой вариант
         * 
         * for (int i = 0; i < h; i++) { for (int j = 0; j < w; j++) { if (j ==
         * centerXShark && i == centerYShark) // center of // // shark {
         * board[j][i] = 3; // temporary sharks - to delete after // можно здесь
         * циклом отрисовать всю акулу от центра // // пока только точкой } } }
         * } } } }
         */

        // //////////////
        // shark are ready - checking board

        int countTrace = 0; // how many traces in row we have
        int countTraceStack = 0; // how many traces go on x-axis & making stack
                                 // of traces
        int areaNum = 4; // 1-earth, 2-sea,3-sharks(in future), 4,5,6 - new
                         // areas to fill

        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++) // x-axis
            {
                // TODO если предыдущий ряд начинался с 2 - areaNum++ в начале
                // ряда
                /*
                 * if (i > 0) { if (board[i-1][j] == 2 && j == 0) { areaNum++;
                 * }}
                 */

                if (board[j][i] == 0)
                {
                    // fill a sea-pixel
                    System.out.println("fill");

                    countTraceStack = 0; // если встретили if 0(sea) after
                                         // 2(trace) - reset stack
                    board[j][i] = areaNum; // 4 (5,6) depends on countTrace
                }
                else if (board[j][i] == 2)
                {
                    // new slice
                    countTrace++;
                    countTraceStack++;
                    areaNum++;

                    if (countTraceStack > 1) // if stack bigger than 1
                    {
                        if (j < w - 1 && board[j + 1][i] == 2)
                        // if next pixel - not element of its stack of trace
                        // new area
                        {
                            // else its stack-element, not new trace, not new
                            // area
                            countTrace--;
                            areaNum--; //
                        }
                    }

                    // if have a noose (loop) - change areaNum to previous
                    if (countTrace % 2 == 0 && countTrace > 1)
                    {
                        areaNum -= 2;
                    }

                    board[j][i] = areaNum;
                }

            }

            // in the end of row-work reset all counters
            countTrace = 0;
            countTraceStack = 0;
            areaNum = 0;
        }

        UnitComponent.setBoard(board);
        // обязательно передать массив обратно компоненту - не ссылочный тип
    }

    /**
     * return a shape of instance
     */
    public Rectangle2D getRectShape()
    {
        System.out.println(" x: " + (int) x + ", y: " + (int) y + ", xSize: "
                + xSize + ", ySize: " + ySize);
        return new Rectangle2D.Double(x, y, xSize, ySize);
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
     * overrides reaction of unit for crash with another unit instance - death
     * of player
     */
    @Override
    public void crashReact() //
    {
        dx = 0;
        dy = 0;

        life -= 1;
        System.out.println("You Are Dead! - Life : " + life);
        GameFrame.setLifeCount(life); // need?
        GameFrame.setLife(life);

        if (life < 1)
        {
            System.out.println("Game Over");
            x = 0;
            dx = 0;

            y = 0;
            dy = 0;

            // TODO Game Over
            // died, but how to start a new game

            try
            {
                // thr.wait();
                interrupt();
            }
            catch (Exception e) // (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        // minus 1 life & play
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            x = w / 2;
            y = UnitComponent.getStartSeaBounds() - ySize - 5;
        }

        // to kill old traces

        System.out.println("Deleting dead traces");
        // clear all traces (2) from board
        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++) // x-axis
            {

                if (board[j][i] == 2)
                {
                    board[j][i] = 0;
                }
            }
        }

        UnitComponent.setBoard(board);
        // обязательно передать массив обратно компоненту - не ссылочный тип
    }

    /**
     * get thread
     */
    @Override
    public Thread getThr()
    {
        return thr;
    }

    /**
     * set thread
     * 
     * @param Thread
     *            thr
     */
    @Override
    public void setThr(Thread thr)
    {
        this.thr = thr;
    }

    /**
     * interrupt a thread
     */
    @Override
    public void interrupt()
    {
        this.thr.interrupt();
    }

    /**
     * @return x
     */
    @Override
    public double getX()
    {
        return x;
    }

    /**
     * @return y
     */
    @Override
    public double getY()
    {
        return y;
    }

    /**
     * @return Ellipse2D
     */
    @Override
    public Ellipse2D getShape()
    {
        return new Ellipse2D.Double(x, y, xSize, ySize);
    }

    /**
     * @return LinearGradientPaint
     */
    @Override
    public LinearGradientPaint getGradient()
    {
        return null;
    }

    /**
     * @return String (name of instance type)
     */
    @Override
    public String toString()
    {
        return "EnemyPlayer";
    }

}
