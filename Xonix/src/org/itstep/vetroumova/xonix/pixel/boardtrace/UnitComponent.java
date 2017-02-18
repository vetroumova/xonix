package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class UnitComponent extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -2592022174639579181L;

    // panel for keep & draw instances of Units

    private static ArrayList<Unit> units = new ArrayList<>();

    private static int w = 785;

    private static int h = 495;

    // Sea is 2-dimension array type now, for add, subtract & subdivision
    // bounds still exists
    private static int[][] board = new int[w][h];

    private static int startSeaBounds = 40;

    // private static Color seaColor = new Color(0,10,100);
    private static Color seaColor = new Color(42, 52, 111);

    private static Color earthColor = new Color(241, 200, 122);

    private static Color traceColor = new Color(255, 0, 0, 200);

    private static UnitPlayer player;

    // ImageIcon background = new ImageIcon("background.jpg");

    /**
     * @return the seaColor
     */
    public static Color getSeaColor()
    {
        return seaColor;
    }

    /**
     * @param seaColor
     *            the seaColor to set
     */
    public static void setSeaColor(Color seaColor)
    {
        UnitComponent.seaColor = seaColor;
    }

    /**
     * @return the w
     */
    public int getW()
    {
        return w;
    }

    /**
     * @param w
     *            the w to set
     */
    public void setW(int w)
    {
        UnitComponent.w = w;
    }

    /**
     * @return the h
     */
    public int getH()
    {
        return h;
    }

    /**
     * @param h
     *            the h to set
     */
    public void setH(int h)
    {
        UnitComponent.h = h;
    }

    /**
     * gets list of Unit instances
     * 
     * @return units
     */
    public ArrayList<Unit> getUnits()
    {
        return units;
    }

    /**
     * sets list of Unit instances
     * 
     * @param units
     */
    public void setUnits(ArrayList<Unit> units)
    {
        UnitComponent.units = units;
    }

    /**
     * constructor for UnitComponent (extends JPanel)
     */
    public UnitComponent()
    {
        setMaximumSize(new Dimension((int) w, (int) h));
        setDoubleBuffered(true); // added

        // Create a thread for comparing distances of objects in collection
        Runnable runComparator = new UnitComparator(units);
        Thread t = new Thread(runComparator);
        t.setPriority(Thread.MAX_PRIORITY); // for high quality of comparing
                                            // units
        t.start();

        // start board initialization

        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++)
            {
                if (i < startSeaBounds || i > h - startSeaBounds) // height -> y
                {
                    board[j][i] = 1; // earth
                }
                else if (j < startSeaBounds || j > w - startSeaBounds) // width
                                                                       // -> x
                {
                    board[j][i] = 1;
                }
                else
                {
                    board[j][i] = 0;
                }
            }
        }
    }

    /**
     * @return the board
     */
    public static int[][] getBoard()
    {
        return board;
    }

    /**
     * @param board
     *            the board to set
     */
    public static void setBoard(int[][] board)
    {
        UnitComponent.board = board;
    }

    /**
     * @return the startSeaBounds
     */
    public static int getStartSeaBounds()
    {
        return startSeaBounds;
    }

    /**
     * @param startSeaBounds
     *            the startSeaBounds to set
     */
    public static void setStartSeaBounds(int startSeaBounds)
    {
        UnitComponent.startSeaBounds = startSeaBounds;
    }

    /**
     * @return the earthColor
     */
    public static Color getEarthColor()
    {
        return earthColor;
    }

    /**
     * @param earthColor
     *            the earthColor to set
     */
    public static void setEarthColor(Color earthColor)
    {
        UnitComponent.earthColor = earthColor;
    }

    /**
     * @return the player
     */
    public static UnitPlayer getPlayer()
    {
        return player;
    }

    /**
     * @param player
     *            the player to set
     */
    public static void setPlayer(UnitPlayer player)
    {
        UnitComponent.player = player;
    }

    public void add(Unit unit)
    {
        units.add(unit);
    }

    /**
     * all draw & animation is here
     */
    @Override
    public void paintComponent(Graphics g)
    {
        // g.drawImage(background.getImage(), 0, 0, this);
        super.paintComponent(g);
        setBounds(5, 50, w, h);

        setBackground(new Color(250, 250, 250));

        Graphics2D g2 = (Graphics2D) g;

        // drawing board
        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++)
            {
                if (board[j][i] == 1) // earth
                {
                    g2.setColor(earthColor);
                }
                else if (board[j][i] == 0) // sea
                {
                    g2.setColor(seaColor);
                }
                // TODO experimental fill of different area according on
                // traectory of trace
                else if (board[j][i] == 4) // 1st slice
                {
                    g2.setColor(Color.WHITE);
                }
                else if (board[j][i] == 5) // 2nd slice
                {
                    g2.setColor(Color.CYAN);
                }
                else if (board[j][i] == 6) // 3rd slice
                {
                    g2.setColor(Color.GRAY);
                }

                else
                {
                    g2.setColor(traceColor); // trace
                }

                g2.fillRect(j, i, 1, 1); // pixel
            }
        }

        // System.out.println("Board are painted ");

        for (Unit unit : units)
        {
            // System.out.println("Reading units: ");
            if (unit instanceof UnitPlayer)
            {
                /*
                 * System.out.println("Unit is Player or Trace: " +
                 * unit.toString());
                 */
                g2.setColor(unit.getColor());
                g2.fill(unit.getRectShape());
            }
            else if (unit instanceof UnitTiger)
            {
                g2.setPaint(unit.getGradient());
                g2.fill(unit.getShape());
            }
            else
            {
                g2.setColor(unit.getColor());
                g2.fill(unit.getShape());
            }

        }
    }

    /**
     * for getAllSharks in ArrayList<Unit>
     * 
     * @return ArrayList<Unit>
     */
    public static ArrayList<Unit> getAllSharks()
    {
        ArrayList<Unit> sharks = new ArrayList<Unit>();
        for (int i = 0; i < units.size(); i++)
        {
            if (units.get(i).toString() == "EnemyShark")
            {
                // System.out.println("copy sharks to check fill of seaField");
                sharks.add(units.get(i));
            }
        }
        return sharks;
    }

}
