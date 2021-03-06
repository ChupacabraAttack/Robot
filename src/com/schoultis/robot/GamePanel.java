package com.schoultis.robot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;

/**
 * Created by onelyx on 3/8/14.
 */
class GamePanel extends JPanel implements Runnable, KeyListener
{
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 400;
    private static final int DELAY = 25;
    private static final Color BACKGROUND_COLOR = Color.BLUE;

    private Thread animator;
    private Robot robot;
    private Stage stage;
    private InputState inputState;
    private BufferedImage buffer;
    private Image background;

    public GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setDoubleBuffered(true);

        stage = new Stage(PANEL_WIDTH, PANEL_HEIGHT, 20);
        robot = new Robot(stage);
        inputState = new InputState();

        buffer = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT,
                                   BufferedImage.TYPE_INT_RGB);
        ImageIcon ii = new ImageIcon(
                this.getClass().getResource("/assets/ominous.png"));
        background = ii.getImage();
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
        Graphics bg = buffer.getGraphics();

        //bg.setColor(BACKGROUND_COLOR);
        //bg.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        bg.drawImage(background,0,0,600,400,this);
        stage.draw(bg);
        //bg.setColor(Color.WHITE);
        //bg.drawLine(300, 400, 320, 380);
        robot.paint(bg, this);

        g.drawImage(buffer, 0,0,this);
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
        JFrame frame = new JFrame("Robot");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new GamePanel());

        frame.pack();
        frame.setVisible(true);
    }
}
