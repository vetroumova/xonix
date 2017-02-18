package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.util.ArrayList;

public class UnitComparator implements Runnable
{
    private ArrayList<Unit> units = new ArrayList<>();

    @SuppressWarnings("unused")
    private UnitPlayer player;

    public UnitComparator(ArrayList<Unit> units)
    {
        this.units = units;
    }

    /**
     * run a thread that compare a units
     */
    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                if (units.size() > 1)
                {
                    for (int i = 0; i < units.size() - 1; i++)
                    {
                        for (int j = i + 1; j < units.size(); j++)
                        {
                            // System.out.println(enemies.get(i).toString());
                            if (units.get(i).toString() == "EnemyPlayer")
                            {
                                player = (UnitPlayer) units.get(i);
                            }

                            if (units.get(i).compareTo(units.get(j)) < 0)
                            {
                                units.get(i).crashReact();
                                units.get(j).crashReact();
                            }
                        }
                    }
                }
                // minimal sleep of thread make comparator do hard work
                // calculating & do slow motion for every objects
                Thread.sleep(10);
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }

    }

}
