package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class RatesFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 8699163867659448382L;

    @SuppressWarnings("unused")
    private JLabel RatesLabel;

    @SuppressWarnings("unused")
    private JLabel points;

    @SuppressWarnings("unused")
    private float[] pointsCount;

    private JPanel mainPanel;

    private JButton back;
    
    private JList<String> ratesList;
    
    private String [] list = {"First", "Second", "Third"};

    @SuppressWarnings("unused")
    private Font fontRate = new Font("Arial", Font.ITALIC, 14);

    private Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 14);

    /**
     *  constructor of RatesFrame
     */
    public RatesFrame()
    {
        setTitle("Rate: ");
        setLayout(new BorderLayout());
        setLocation(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(200, 260));
        setResizable(false);

        // main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new BevelBorder(1, new Color(10, 40, 150),
                new Color(10, 100, 150)));

        back = new JButton("Back");
        back.setFont(font);
        back.setToolTipText("To main menu");
        
        ratesList = new JList<String>(list);
        ratesList.setFont(font);

        mainPanel.add(back, BorderLayout.SOUTH);
        mainPanel.add(ratesList, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);

        ////////////////////////////////////////////////////////
        // LISTENER
        
        back.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }

        });
    }

}
