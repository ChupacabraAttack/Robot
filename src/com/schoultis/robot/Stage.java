package com.schoultis.robot;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Created by onelyx on 3/8/14.
 */
public class Stage {
    int width, height, resolution;
    boolean[][] layout;

    public Stage(int width, int height, int resolution) {
        this.width = width;
        this.height = height;
        this.resolution = resolution;

        layout = new boolean[width/resolution][height/resolution];

        fillRow(0);
        fillRow(getRows()-1);
        fillColumn(0);
        fillColumn(getColumns()-1);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public int getColumns() {
        return width/resolution;
    }

    public int getRows() {
        return height/resolution;
    }

    public void fillRow(int row) {
        for(int i = 0; i < getColumns(); i++) {
            layout[i][row] = true;
        }
    }

    public void fillColumn(int column) {
        for(int i = 0; i < getRows(); i++) {
            layout[column][i] = true;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for(int j = 0; j < height/resolution; j++) {
            for(int i = 0; i < width/resolution; i++) {
                if(layout[i][j]) {
                    g.drawRect(i*resolution,j*resolution,resolution,resolution);
                }
            }
        }
    }
}
