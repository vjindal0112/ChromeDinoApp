package com.example.varun.chromedino;

import android.graphics.Bitmap;

/**
 * Created by Varun on 7/31/2017.
 */

public class Animation {
    private Bitmap[] frames;
    private int currentFrame = 0;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        startTime = System.currentTimeMillis();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void update() {
        long elapsed = System.currentTimeMillis() - startTime;
        if(elapsed>delay) {
            currentFrame++;
            startTime = System.currentTimeMillis();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public Bitmap getImage() {
        return frames[currentFrame];
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public boolean isPlayedOnce() {
        return playedOnce;
    }
}
