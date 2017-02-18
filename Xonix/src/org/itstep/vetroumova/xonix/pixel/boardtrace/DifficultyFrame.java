package org.itstep.vetroumova.xonix.pixel.boardtrace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

public class DifficultyFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JRadioButton easy, normal, hard;

    private JPanel mainPanel;
    
    private JButton back;

    private Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 14);

    /**
     * constructor of difficulty frame
     */
    public DifficultyFrame()
    {
        setTitle("Difficulty");
        setLayout(new BorderLayout());
        setLocation(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(200, 260));
        setResizable(false);

        // main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new BevelBorder(1, new Color(10, 40, 150),
                new Color(10, 100, 150)));

        easy = new JRadioButton("EASY");
        easy.setFont(font);
        normal = new JRadioButton("NORMAL");
        normal.setFont(font);
        hard = new JRadioButton("HARD");
        hard.setFont(font);
        
        ButtonGroup radGroup = new ButtonGroup();
        radGroup.add(easy);
        radGroup.add(normal);
        radGroup.add(hard);
        
        mainPanel.add(easy, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(15, 10, 15, 10), 0, 0));

        mainPanel.add(normal, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(15, 10, 15, 10), 0, 0));

        mainPanel.add(hard, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(15, 10, 15, 10), 0, 0));

        back = new JButton("BACK");
        back.setFont(font);
        back.setToolTipText("Back to game");
        
        mainPanel.add(back, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(15, 0, 15, 30), 0, 0));

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        
        
      //listeners        
        back.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                //new MainXonix();
            }
            
        });
        
        easy.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if(easy.getSelectedObjects() != null)
                {
                    GameFrame.setDifficultyLevel(easy.getText());
                    GameFrame.setDifficulty(easy.getText());
                    //System.out.println(easy.toString());
                    //System.out.println(GameFrame.getDifficultyLevel().toString());
                    //System.out.println(GameFrame.getDifficulty().getText());
                }
                
            }
            
        });
        
        normal.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if(normal.getSelectedObjects() != null)
                {
                    GameFrame.setDifficultyLevel(normal.getText());
                    GameFrame.setDifficulty(normal.getText());
                }
                
            }
            
        });
        
        hard.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                if(hard.getSelectedObjects() != null)
                {
                    GameFrame.setDifficultyLevel(hard.getText());
                    GameFrame.setDifficulty(hard.getText());
                }
                
            }
            
        });
    }

}
