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
    static final int WIDTH = 32;
    static final int HEIGHT = 64;
    static final int MAX_SPEED = 8;
    static final float GRAVITY = 0.8f;
    static final float JUMP_SPEED = 12;
    static final int HEAD_X = 6;
    static final int HEAD_Y = 0;
    static final int CORE_X = 4;
    static final int CORE_Y = 14;
    static final int BASE_X = 2;
    static final int BASE_Y = 32;
    static final int LEFT_LEG_X = 4;
    static final int RIGHT_LEG_X = 22;
    static final int LEG_Y = 29;
    static final int LEFT_TIRE_X = 0;
    static final int RIGHT_TIRE_X = 18;
    static final int TIRE_Y = 49;
    static final int MAX_TENSION = 13;
    static final int TIRE_ACCELERATION = 1;
    static final int GROUND_FRICTION = 1;

    float x, y;
    float dx, dy;
    int tension;

    Stage stage;
    //Image sprite;
    Image head;
    Image core;
    Image leg;
    Image base;
    Image tire;

    public Robot(Stage stage) {
        this.stage = stage;
        this.x = 0;
        this.y = 0;
        this.dx = 0;
        this.dy = 0;
        this.tension = 0;

        //ImageIcon ii = new ImageIcon(
        //        this.getClass().getResource("/assets/RoboSprite.png"));
        //sprite = ii.getImage();
        ImageIcon ii = new ImageIcon(
                this.getClass().getResource("/assets/Head.png"));
        head = ii.getImage();
        ii = new ImageIcon(
                this.getClass().getResource("/assets/Core.png"));
        core = ii.getImage();
        ii = new ImageIcon(
                this.getClass().getResource("/assets/Leg.png"));
        leg = ii.getImage();
        ii = new ImageIcon(
                this.getClass().getResource("/assets/Base.png"));
        base = ii.getImage();
        ii = new ImageIcon(
                this.getClass().getResource("/assets/Tire.png"));
        tire = ii.getImage();
    }

    private void decelerate() {
        if(dx > 0) {
            if(dx - GROUND_FRICTION < 0) {
                dx=0;
            }
            else {
                dx -= GROUND_FRICTION;
            }
        }
        else if(dx < 0) {
            if(dx + GROUND_FRICTION > 0) {
                dx = 0;
            }
            else {
                dx += GROUND_FRICTION;
            }
        }
    }

    public void cycle(InputState state) {
        //If left is pressed, set dx for left movement.
        //If right is pressed, set dx for right movement.
        //If both or neither, set dx to 0.
        if(state.isPressed(InputState.RIGHT)) {
            if(state.isPressed(InputState.LEFT)) {
                if(onFloor()) {
                    decelerate();
                }
            }
            else {
                if(onFloor() && dx < MAX_SPEED) {
                    dx+= TIRE_ACCELERATION;
                }
            }
        }
        else if(state.isPressed(InputState.LEFT)){
            if(onFloor() && dx > -MAX_SPEED) {
                dx -= TIRE_ACCELERATION;
            }
        }
        else if(onFloor()) {
            decelerate();
        }

        //If on the floor and up is pressed, set dy for a jump.
        //if(state.isPressed(InputState.UP) && onFloor()) {
        //    dy = -JUMP_SPEED;
        //}
        if(state.isPressed(InputState.DOWN)) {
            if(onFloor() && tension != MAX_TENSION) {
                tension++;
            }
        }
        else {
            if(tension != 0 && onFloor()) {
                dy = -tension;
                tension = 0;
            }
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
        g2d.drawImage(head, (int)x+HEAD_X, (int)y+HEAD_Y+tension, observer);
        g2d.drawImage(core, (int)x+CORE_X, (int)y+CORE_Y+tension, observer);
        g2d.drawImage(leg, (int)x+LEFT_LEG_X, (int)y+LEG_Y, observer);
        g2d.drawImage(leg, (int)x+RIGHT_LEG_X, (int)y+LEG_Y, observer);
        g2d.drawImage(base, (int)x+BASE_X, (int)y+BASE_Y+tension, observer);
        g2d.drawImage(tire, (int)x+LEFT_TIRE_X, (int)y+TIRE_Y, observer);
        g2d.drawImage(tire, (int)x+RIGHT_TIRE_X, (int)y+TIRE_Y, observer);
        //g.setColor(Color.BLACK);
        //g.drawRect((int)x,(int)y, WIDTH, HEIGHT);
    }
}
