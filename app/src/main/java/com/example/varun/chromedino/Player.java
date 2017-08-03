package com.example.varun.chromedino;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Varun on 7/30/2017.
 */

public class Player extends GameObject {
    private Bitmap image[];
    private int score;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private boolean jump = false;
    private int ddy = 3;
    private int dy = -60;
    private static final int DINOYPOS = (int) (GamePanel.HEIGHT - Math.floor(GamePanel.HEIGHT/2.6));

    public Player(Bitmap res, int w, int h, int numFrames) {
        x = 200;
        y = DINOYPOS;
        score = 0;
        height = h;
        width = w;
        Bitmap spritesheet = res;
        image = new Bitmap[numFrames];

        for(int i = 0; i<image.length; i++) {
            if(i >= 1) {
                image[i] = Bitmap.createBitmap(spritesheet, (i+1) * width, 0, width, height);
            } else if(i < 1) {
                image[i] = Bitmap.createBitmap(spritesheet, i * width, 0, width, height);
            }
        }

        animation.setFrames(image);
        animation.setDelay(100);
        startTime = System.currentTimeMillis();
    }

    public void update() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        animation.update();
        if(elapsedMillis > 100) {
            score++;
            startTime = System.currentTimeMillis();
        }

        if(jump) {
            y+=dy;
            dy+=ddy;
            if(y>DINOYPOS) {
                y = DINOYPOS;
                jump = false;
                dy = -60;
                ddy = 3;
            }
        }
    }

    public void jump() {
        jump = true;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(image[animation.getCurrentFrame()], 500,500, false), x, y, null);
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
