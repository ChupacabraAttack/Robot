package com.schoultis.robot;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HelloSwing {

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });

    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame("Window name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MyPanel());

        frame.pack();
        frame.setVisible(true);
    }

}

class MyPanel extends JPanel implements Runnable, KeyListener
{
    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 400;
    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 0;
    private final int DELAY = 25;
    private final int BOX_WIDTH = 90;
    private final int BOX_HEIGHT = 90;


    int boxX = INITIAL_X;
    int boxY = INITIAL_Y;
    private Thread animator;
    private Image mushroom;


    public MyPanel(){
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setDoubleBuffered(true);

        boxX = 0;
        boxY = 0;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/assets/mushroom.jpeg"));
        mushroom = ii.getImage();
    }

    public void addNotify(){
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    public void run(){
        long beforeTime;
        long timeDif;
        long sleep;

        beforeTime = System.currentTimeMillis();

        while (true){
            cycle();
            repaint();
            timeDif = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDif;

            if (sleep < 0)
                sleep = 2;

            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                System.out.println("String interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    private void cycle(){
        boxX++;

        if (boxX + BOX_WIDTH > PANEL_WIDTH)
            boxX = 0;
    }

    public Dimension getPreferredSize(){
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(boxX,boxY,BOX_WIDTH,BOX_HEIGHT);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(mushroom, boxX, boxY, this);
    }

   public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_S)
            boxY++;
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void actionPerformed(ActionEvent e)
    {

    }



}


