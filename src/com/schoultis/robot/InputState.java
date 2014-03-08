package com.schoultis.robot;

/**
 * Created by onelyx on 3/8/14.
 */
public class InputState {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private int state;

    public InputState() {
        state = 0;
    }

    public boolean isPressed(int key) {
        return (state & (1 << key)) != 0;
    }

    public void setPressed(int key) {
        state = state | (1 << key);
    }

    public void setReleased(int key) {
        state = state ^ (1 << key);
    }
}
