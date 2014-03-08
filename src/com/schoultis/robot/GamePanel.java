package com.schoultis.robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by onelyx on 3/8/14.
 */
class GamePanel extends JPanel implements Runnable, KeyListener
{
    private final int PANEL_WIDTH = 600;
    private final int PANEL_HEIGHT = 400;
    private final int DELAY = 25;

    private Thread animator;
    private Robot robot;
    private Stage stage;
    private InputState inputState;

    public GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setDoubleBuffered(true);

        stage = new Stage(PANEL_WIDTH, PANEL_HEIGHT);
        robot = new Robot(stage);
        inputState = new InputState();
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
                System.out.println("Interrupted Exception: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    private void cycle(){
        robot.cycle(inputState);
    }

    public Dimension getPreferredSize(){
        return new Dimension(stage.getWidth(), stage.getHeight());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        robot.paint(g, this);
    }

   public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_S: inputState.setPressed(InputState.DOWN);
                                break;
            case KeyEvent.VK_W: inputState.setPressed(InputState.UP);
                                break;
            case KeyEvent.VK_D: inputState.setPressed(InputState.RIGHT);
                                break;
            case KeyEvent.VK_A: inputState.setPressed(InputState.LEFT);
                                break;
            default:            break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_S: inputState.setReleased(InputState.DOWN);
                                break;
            case KeyEvent.VK_W: inputState.setReleased(InputState.UP);
                                break;
            case KeyEvent.VK_D: inputState.setReleased(InputState.RIGHT);
                                break;
            case KeyEvent.VK_A: inputState.setReleased(InputState.LEFT);
                                break;
            default:            break;
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }

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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new GamePanel());

        frame.pack();
        frame.setVisible(true);
    }
}
