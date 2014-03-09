package com.schoultis.robot;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Color;


/**
 * Created by mongoose on 3/9/14.
 */
public class Block extends Robot {

    int width;
    int height;


    // Constructor
    Block(Stage stage, float x, float y, String blockType){
        super(stage);

        this.x = x;
        this.y = y;
        width = 50;
        height = 50;

        ImageIcon icon = new ImageIcon(
                this.getClass().getResource("/assets/"+blockType+".jpg"));
        sprite = icon.getImage();


    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.ORANGE);
        g2d.drawImage(sprite, (int)x, (int)y, width, height, observer);
        g.drawRect((int) x, (int) y, width, height);

    }
}
