package org.itstep.vetroumova.xonix.pixel.boardtrace;

public class UnitEachRunnable implements Runnable
{
    private Unit enemy;
    private UnitComponent component;

    public static final int DELAY = 7; // was 5

    /**
     * constructor for instance of class that implements Runnable - movement of units
     * @param anEnemy
     * @param aC
     */
    public UnitEachRunnable(Unit anEnemy, UnitComponent aC)
    {
        enemy = anEnemy;
        component = aC;
    }

    /**
     * run all movements
     */
    @SuppressWarnings("static-access")
    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                enemy.move(component.getBoard());
                component.repaint();
                Thread.sleep(DELAY);
            }

        }
        catch (InterruptedException e)
        {
            ;
        }

    }
}
