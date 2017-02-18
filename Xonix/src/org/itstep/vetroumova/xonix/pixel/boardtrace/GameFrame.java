package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 5769514585612946376L;

    private UnitComponent comp; // extends JPanel - all paint & animation here

    private JMenuBar menuBar;

    private JMenu mainMenu, helpMenu;

    private JMenuItem itemDifficulty, itemRate, itemHelp, itemAbout;

    private static JLabel lifeLabel, pointsLabel, capturedLabel, persentLabel,
            difficultyLabel;

    private static JLabel life, points, captured, difficulty;

    private JTextArea helpText, aboutText;

    private static int lifeCount = 3, pointsCount, capturedCount;

    private static String difficultyLevel = "NORMAL";

    private int level = 1; // game level

    private Unit player, trace;

    private double speed = 0.6; // speed of player cube

    private String currentKey = "";

    private boolean inGame = true; // to check

    private JPanel infoPanel, mainPanel;

    private Font font = new Font("Arial", Font.ITALIC, 14);

    // trying to get all threads & pause them by space-key
    private ArrayList<Thread> threadList = new ArrayList<Thread>();

    /**
     * @param life
     *            the life to set
     */
    public static void setLife(int life) // changed by me
    {
        GameFrame.life.setText(String.valueOf(life));
    }

    /**
     * @param difficulty
     *            the difficulty to set
     */
    public static void setDifficulty(String diff)
    {
        difficulty.setText(diff);
    }

    /**
     * constructor of starting game frame
     */
    // public GameFrame(MainXonix xonix) throws Throwable
    public GameFrame()
    {
        setTitle("Уровень 1");
        setLayout(new BorderLayout());
        setLocation(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setResizable(false);

        // menuBar above game panel

        menuBar = new JMenuBar();

        mainMenu = new JMenu("Options");
        mainMenu.setFont(font);
        helpMenu = new JMenu("Help");
        helpMenu.setFont(font);

        itemDifficulty = new JMenuItem(" Difficulty ");
        itemRate = new JMenuItem(" Rating ");
        itemHelp = new JMenuItem(" Help ");
        itemAbout = new JMenuItem(" About ");

        itemDifficulty.setFont(font);
        itemRate.setFont(font);
        itemHelp.setFont(font);
        itemAbout.setFont(font);

        mainMenu.add(itemDifficulty);
        mainMenu.add(itemRate);

        helpMenu.add(itemHelp);
        helpMenu.add(itemAbout);

        menuBar.add(mainMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // information panel for lifes, points & captured area
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        // infoPanel.setSize(500, 200);

        lifeLabel = new JLabel("Lifes: ");
        lifeLabel.setFont(font);
        life = new JLabel(String.valueOf(lifeCount)); // todo more correct
                                                      // expression
        life.setFont(font);

        pointsLabel = new JLabel("Points: ");
        pointsLabel.setFont(font);
        points = new JLabel("0");
        points.setFont(font);

        capturedLabel = new JLabel("Captured: ");
        capturedLabel.setFont(font);
        captured = new JLabel("0");
        captured.setFont(font);
        persentLabel = new JLabel(" %");
        persentLabel.setFont(font);

        difficultyLabel = new JLabel("Difficulty level: ");
        difficultyLabel.setFont(font);
        difficulty = new JLabel(difficultyLevel);
        difficulty.setFont(font);

        infoPanel.add(Box.createVerticalStrut(50));
        infoPanel.add(lifeLabel);
        // infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(life);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(pointsLabel);
        // infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(points);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(capturedLabel);
        // infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(captured);
        // infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(persentLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(difficultyLabel);
        // infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(difficulty);
        infoPanel.add(Box.createVerticalStrut(50));

        comp = new UnitComponent();

        comp.setBorder(new CompoundBorder(new EmptyBorder(1, 1, 1, 1),
                new BevelBorder(1, new Color(10, 40, 150), new Color(10, 100,
                        150))));
        

        JButton shark = new JButton(" Add Shark");
        infoPanel.add(shark);

        add(infoPanel, BorderLayout.NORTH);
        add(comp, BorderLayout.CENTER);

        setVisible(true);

        // /////////////////////////////////////////////////////////////
        // LISTENERS

        // itemDifficulty, itemRate, itemHelp, itemAbout;
        itemDifficulty.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                @SuppressWarnings("unused")
                DifficultyFrame difficulty = new DifficultyFrame();

            }

        });
        itemRate.addActionListener(new ActionListener()
        {

            @SuppressWarnings("unused")
            @Override
            public void actionPerformed(ActionEvent e)
            {
                RatesFrame records = new RatesFrame();

            }

        });

        itemHelp.addActionListener(new ActionListener()
        {
            String s = "Игровое поле разделено на сушу и море. Игрок стартует на суше \n"
                    + "и может свободно по ней перемещаться по горизонтали и вертикали.\n "
                    + "Входя в воду игрок оставляет за собой след. Как только он вновь \n"
                    + "достигает берега, от моря отрезается кусок ограниченный следом игрока. \n"
                    + "Вырезается меньший участок.\n\n Оппоненты перемещаются по диагонали. "
                    + "Есть морские оппоненты, \n количество которых фиксировано, и сухопутные, "
                    + "\n количество которых увеличивается по мере игры, но ограничено \n"
                    + "фиксированным верхним порогом зависящим от уровня. \n\n "
                    + "Игроку дается несколько жизней на прохождение одного уровня. \n"
                    + "Жизнь отнимается за столкновение с оппонентом,"
                    + "столкновением водного \n оппонента со следом игрока,"
                    + "попыткой сменить направление \n на обратное при нахождении в воде.";

            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(helpText, s);
            }

        });

        itemAbout.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String s = "Классическая игра \"Ксоникс\" \n Цель игры "
                        + "- захватить необходимое количество территории. "
                        + "Игроку в этом мешают оппоненты управляемые игрой."
                        + "\n\n Разработчик: Ветроумова Ольга";

                JOptionPane.showMessageDialog(aboutText, s);
            }

        });

        shark.addActionListener(new ActionListener()
        {
            // @Override
            public void actionPerformed(ActionEvent arg0)
            {
                addShark();
                comp.requestFocus();
            }

        });

        
        comp.requestFocus();
        // TODO, focusListener, on lost focus - game pause, then turn on back
        comp.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                // System.out.println(e);

                // don't need this check?
                if (inGame)
                {
                    if (e.getKeyCode() == KeyEvent.VK_W
                            || e.getKeyCode() == KeyEvent.VK_UP)
                    {
                        UnitPlayer.setDx(0);
                        UnitPlayer.setDy(-speed);

                        if (currentKey != "UP")
                        {
                            currentKey = "UP";
                        }

                        System.out.println(currentKey);
                    }

                    if (e.getKeyCode() == KeyEvent.VK_S
                            || e.getKeyCode() == KeyEvent.VK_DOWN)
                    {
                        UnitPlayer.setDx(0);
                        UnitPlayer.setDy(speed);

                        if (currentKey != "DOWN")
                        {
                            currentKey = "DOWN";
                        }

                        System.out.println(currentKey);
                    }

                    if (e.getKeyCode() == KeyEvent.VK_A
                            || e.getKeyCode() == KeyEvent.VK_LEFT)
                    {
                        UnitPlayer.setDx(-speed);
                        UnitPlayer.setDy(0);

                        if (currentKey != "LEFT")
                        {
                            currentKey = "LEFT";
                        }

                        System.out.println(currentKey);
                    }

                    if (e.getKeyCode() == KeyEvent.VK_D
                            || e.getKeyCode() == KeyEvent.VK_RIGHT)
                    {
                        UnitPlayer.setDx(speed);
                        UnitPlayer.setDy(0);

                        if (currentKey != "RIGHT")
                        {
                            currentKey = "RIGHT";
                        }

                        System.out.println(currentKey);

                    }
                    
                    //TODO toCheck
                    else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    {
                        inGame = false;
                        System.exit(0);
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    {

                        //TODO pause in game
                        // all threads sleep of wait
                    }

                }

                //TODO not inGame
                else
                {
                    /*
                     * JOptionPane.showMessageDialog(new JTextArea(),
                     * "Press S for start");
                     * 
                     * if (e.getKeyCode() == 's' || e.getKeyCode() == 'S') {
                     * inGame=true; //GameInit(); }
                     */
                }

            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                UnitPlayer.setDx(0);
                UnitPlayer.setDy(0);
                System.out.println("stop");

            }

        });

        setVisible(true);
    }

    /**
     * @return the component
     */
    public UnitComponent getComp()
    {
        return comp;
    }

    /**
     * @param comp
     *            the component to set
     */
    public void setComp(UnitComponent comp)
    {
        this.comp = comp;
    }

    /**
     * @param menuBar
     *            the menuBar to set
     */
    public void setMenuBar(JMenuBar menuBar)
    {
        this.menuBar = menuBar;
    }

    /**
     * @return the mainMenu
     */
    public JMenu getMainMenu()
    {
        return mainMenu;
    }

    /**
     * @param mainMenu
     *            the mainMenu to set
     */
    public void setMainMenu(JMenu mainMenu)
    {
        this.mainMenu = mainMenu;
    }

    /**
     * @return the helpMenu
     */
    public JMenu getHelpMenu()
    {
        return helpMenu;
    }

    /**
     * @param helpMenu
     *            the helpMenu to set
     */
    public void setHelpMenu(JMenu helpMenu)
    {
        this.helpMenu = helpMenu;
    }

    /**
     * @return the itemDifficulty
     */
    public JMenuItem getItemDifficulty()
    {
        return itemDifficulty;
    }

    /**
     * @param itemDifficulty
     *            the itemDifficulty to set
     */
    public void setItemDifficulty(JMenuItem itemDifficulty)
    {
        this.itemDifficulty = itemDifficulty;
    }

    /**
     * @return the itemRate
     */
    public JMenuItem getItemRate()
    {
        return itemRate;
    }

    /**
     * @param itemRate
     *            the itemRate to set
     */
    public void setItemRate(JMenuItem itemRate)
    {
        this.itemRate = itemRate;
    }

    /**
     * @return the itemHelp
     */
    public JMenuItem getItemHelp()
    {
        return itemHelp;
    }

    /**
     * @param itemHelp
     *            the itemHelp to set
     */
    public void setItemHelp(JMenuItem itemHelp)
    {
        this.itemHelp = itemHelp;
    }

    /**
     * @return the itemAbout
     */
    public JMenuItem getItemAbout()
    {
        return itemAbout;
    }

    /**
     * @param itemAbout
     *            the itemAbout to set
     */
    public void setItemAbout(JMenuItem itemAbout)
    {
        this.itemAbout = itemAbout;
    }

    /**
     * @return the lifeLabel
     */
    public static JLabel getLifeLabel()
    {
        return lifeLabel;
    }

    /**
     * @param lifeLabel
     *            the lifeLabel to set
     */
    public static void setLifeLabel(JLabel lifeLabel)
    {
        GameFrame.lifeLabel = lifeLabel;
    }

    /**
     * @return the pointsLabel
     */
    public static JLabel getPointsLabel()
    {
        return pointsLabel;
    }

    /**
     * @param pointsLabel
     *            the pointsLabel to set
     */
    public static void setPointsLabel(JLabel pointsLabel)
    {
        GameFrame.pointsLabel = pointsLabel;
    }

    /**
     * @return the capturedLabel
     */
    public static JLabel getCapturedLabel()
    {
        return capturedLabel;
    }

    /**
     * @param capturedLabel
     *            the capturedLabel to set
     */
    public static void setCapturedLabel(JLabel capturedLabel)
    {
        GameFrame.capturedLabel = capturedLabel;
    }

    /**
     * @return the persentLabel
     */
    public static JLabel getPersentLabel()
    {
        return persentLabel;
    }

    /**
     * @param persentLabel
     *            the persentLabel to set
     */
    public static void setPersentLabel(JLabel persentLabel)
    {
        GameFrame.persentLabel = persentLabel;
    }

    /**
     * @return the difficultyLabel
     */
    public static JLabel getDifficultyLabel()
    {
        return difficultyLabel;
    }

    /**
     * @param difficultyLabel
     *            the difficultyLabel to set
     */
    public static void setDifficultyLabel(JLabel difficultyLabel)
    {
        GameFrame.difficultyLabel = difficultyLabel;
    }

    /**
     * @return the life
     */
    public static JLabel getLife()
    {
        return life;
    }

    /**
     * @param life
     *            the life to set
     */
    public static void setLife(JLabel life)
    {
        GameFrame.life = life;
    }

    /**
     * @return the points
     */
    public static JLabel getPoints()
    {
        return points;
    }

    /**
     * @param points
     *            the points to set
     */
    public static void setPoints(JLabel points)
    {
        GameFrame.points = points;
    }

    /**
     * @return the captured
     */
    public static JLabel getCaptured()
    {
        return captured;
    }

    /**
     * @param captured
     *            the captured to set
     */
    public static void setCaptured(JLabel captured)
    {
        GameFrame.captured = captured;
    }

    /**
     * @return the difficulty
     */
    public static JLabel getDifficulty()
    {
        return difficulty;
    }

    /**
     * @param difficulty
     *            the difficulty to set
     */
    public static void setDifficulty(JLabel difficulty)
    {
        GameFrame.difficulty = difficulty;
    }

    /**
     * @return the helpText
     */
    public JTextArea getHelpText()
    {
        return helpText;
    }

    /**
     * @param helpText
     *            the helpText to set
     */
    public void setHelpText(JTextArea helpText)
    {
        this.helpText = helpText;
    }

    /**
     * @return the aboutText
     */
    public JTextArea getAboutText()
    {
        return aboutText;
    }

    /**
     * @param aboutText
     *            the aboutText to set
     */
    public void setAboutText(JTextArea aboutText)
    {
        this.aboutText = aboutText;
    }

    /**
     * @return the lifeCount
     */
    public static int getLifeCount()
    {
        return lifeCount;
    }

    /**
     * @param lifeCount
     *            the lifeCount to set
     */
    public static void setLifeCount(int lifeCount)
    {
        GameFrame.lifeCount = lifeCount;
    }

    /**
     * @return the pointsCount
     */
    public static int getPointsCount()
    {
        return pointsCount;
    }

    /**
     * @param pointsCount
     *            the pointsCount to set
     */
    public static void setPointsCount(int pointsCount)
    {
        GameFrame.pointsCount = pointsCount;
    }

    /**
     * @return the capturedCount
     */
    public static int getCapturedCount()
    {
        return capturedCount;
    }

    /**
     * @param capturedCount
     *            the capturedCount to set
     */
    public static void setCapturedCount(int capturedCount)
    {
        GameFrame.capturedCount = capturedCount;
    }

    /**
     * @return the difficultyLevel
     */
    public static String getDifficultyLevel()
    {
        return difficultyLevel;
    }

    /**
     * @param difficultyLevel
     *            the difficultyLevel to set
     */
    public static void setDifficultyLevel(String difficultyLevel)
    {
        GameFrame.difficultyLevel = difficultyLevel;
    }

    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the player
     */
    public Unit getPlayer()
    {
        return player;
    }

    /**
     * @param player
     *            the player to set
     */
    public void setPlayer(Unit player)
    {
        this.player = player;
    }

    /**
     * @return the trace
     */
    public Unit getTrace()
    {
        return trace;
    }

    /**
     * @param trace
     *            the trace to set
     */
    public void setTrace(Unit trace)
    {
        this.trace = trace;
    }

    /**
     * @return the speed
     */
    public double getSpeed()
    {
        return speed;
    }

    /**
     * @param speed
     *            the speed to set
     */
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * @return the currentKey
     */
    public String getCurrentKey()
    {
        return currentKey;
    }

    /**
     * @param currentKey
     *            the currentKey to set
     */
    public void setCurrentKey(String currentKey)
    {
        this.currentKey = currentKey;
    }

    /**
     * @return the inGame
     */
    public boolean isInGame()
    {
        return inGame;
    }

    /**
     * @param inGame
     *            the inGame to set
     */
    public void setInGame(boolean inGame)
    {
        this.inGame = inGame;
    }

    /**
     * @return the infoPanel
     */
    public JPanel getInfoPanel()
    {
        return infoPanel;
    }

    /**
     * @param infoPanel
     *            the infoPanel to set
     */
    public void setInfoPanel(JPanel infoPanel)
    {
        this.infoPanel = infoPanel;
    }

    /**
     * @return the mainPanel
     */
    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    /**
     * @param mainPanel
     *            the mainPanel to set
     */
    public void setMainPanel(JPanel mainPanel)
    {
        this.mainPanel = mainPanel;
    }

    /**
     * @return the font
     */
    public Font getFont()
    {
        return font;
    }

    /**
     * @param font
     *            the font to set
     */
    public void setFont(Font font)
    {
        this.font = font;
    }

    /**
     * add a new Sharks in count depends on level
     */
    @SuppressWarnings("static-access")
    public void addShark()
    {
        // according to game level - bigger qty of sharks & faster speed
        // i to level - field of this Class instance

        // TODO - set acceleration

        for (int i = 0; i < level + 1; i++)
        {
            // set different start points according to bounds
            // Enemy enemy = new EnemyShark(comp.getBounds());

            Unit enemy = new UnitShark(comp.getBoard(), comp.getW(),
                    comp.getH(), (UnitPlayer) player);
            comp.add(enemy);

            Runnable r = new UnitEachRunnable(enemy, comp);
            Thread t = new Thread(r); // new thread
            enemy.setThr(t);
            // added
            threadList.add(t);
            t.start();
        }

    }

    /**
     * add a new tiger or tigers - quantity depends on game level
     */
    @SuppressWarnings("static-access")
    public void addEnemyTiger()
    {
        // according to game level - bigger qty of tigers & faster speed
        // i to level - field of this Class instance

        // TODO - set acceleration
        // TODO - set rising quantity from level to level + 2 - step2

        for (int i = 0; i < level; i++)
        {
            // set different start points according to bounds
            Unit enemy = new UnitTiger(comp.getBoard(), comp.getW(),
                    comp.getH(), (UnitPlayer) player);
            comp.add(enemy);

            Runnable r = new UnitEachRunnable(enemy, comp);
            Thread t = new Thread(r); // new thread
            enemy.setThr(t);
            // added
            threadList.add(t);
            t.start();
        }

    }

    /**
     * add a player to panel
     */
    public void addEnemyPlayer()
    {
        player = new UnitPlayer(comp.getW(), comp.getH());
        comp.add(player);

        Runnable r = new UnitEachRunnable(player, comp);
        Thread t = new Thread(r); // new thread
        player.setThr(t);
        // added
        threadList.add(t);
        t.start();
    }

    /**
     * set pause ON
     * @param thr
     */
    public void pauseGame(Thread thr)
    {
        //TODO
    }

    /**
     * set pause OFF
     * @param thr
     */
    public void unpauseGame(Thread thr)
    {

    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args)
    {
        // start of all paint & animation
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                GameFrame frame = new GameFrame();
                frame.setVisible(true);
                frame.addEnemyPlayer();
                frame.addShark();
                frame.addEnemyTiger();

            }

        });

    }
}
