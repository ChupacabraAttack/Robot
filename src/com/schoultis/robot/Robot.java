package com.schoultis.robot;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Color;

/**
 * Created by onelyx on 3/8/14.
 */
public class Robot {
    final int WIDTH = 90;
    final int HEIGHT = 90;
    final int MOVE_SPEED = 8;
    final float GRAVITY = 0.8f;
    final float JUMP_SPEED = 15;

    float x, y;
    float dx, dy;

    Stage stage;
    Image mushroom;

    public Robot(Stage stage) {
        this.stage = stage;
        this.x = 0;
        this.y = 0;
        this.dx = 0;
        this.dy = 0;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/assets/mushroom.jpeg"));
        mushroom = ii.getImage();
    }

    public void cycle(InputState state) {
        //If left is pressed, set dx for left movement.
        //If right is pressed, set dx for right movement.
        //If both or neither, set dx to 0.
        if(state.isPressed(InputState.RIGHT)) {
            if(state.isPressed(InputState.LEFT)) {
                dx = 0;
            }
            else {
                dx = MOVE_SPEED;
            }
        }
        else if(state.isPressed(InputState.LEFT)){
            dx = -MOVE_SPEED;
        }
        else {
            dx = 0;
        }

        //If on the floor and up is pressed, set dy for a jump.
        if(state.isPressed(InputState.UP) && onFloor()) {
            dy = -JUMP_SPEED;
        }

        //Move per dx,dy
        x += dx;
        y += dy;

        //If outside horizontal bounds of stage, move just within bounds and set dx to 0.
        if (x + WIDTH > stage.getWidth()) {
            x = stage.getWidth()-WIDTH;
            dx = 0;
        }
        else if(x < 0) {
            x = 0;
            dx = 0;
        }

        //If lower than bounds of stage, move within bounds and set dy to 0.
        //Otherwise, modify dy according to gravity.
        if (y + HEIGHT > stage.getHeight()) {
            y = stage.getHeight()-HEIGHT;
            dy = 0;
        }
        else if(!onFloor()) {
            dy += GRAVITY;
        }
    }

    private boolean onFloor() {
        return y == stage.getHeight()-HEIGHT;

    }

    public void paint(Graphics g, ImageObserver observer) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(mushroom, (int)x, (int)y, WIDTH, HEIGHT, observer);
        g.setColor(Color.WHITE);
        g.drawRect((int)x,(int)y, WIDTH, HEIGHT);
    }
}
