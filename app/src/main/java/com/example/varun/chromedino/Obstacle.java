package com.example.varun.chromedino;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.res.Resources;

import java.util.Random;

/**
 * Created by Varun on 8/1/2017.
 */

public class Obstacle extends GameObject {
    private static final int CACTUS_WIDTH = 1309;
    private static final int CACTUS_HEIGHT = 1711;
    private int dx = -15;
    public int cactusNum;
    public Obstacle() {
        this.width = CACTUS_WIDTH;
        this.height = CACTUS_HEIGHT;
        this.x = GamePanel.WIDTH;
        this.y = GamePanel.HEIGHT - (GamePanel.HEIGHT/3);
        cactusNum = new Random().nextInt(3) + 1;
    }

    public void update() {
        x+=dx;
    }

    public void setVector(int dx) {
        this.dx = dx;
    }

    public void draw(Canvas canvas, Context context) {
        for(int i = 0; i<cactusNum; i++) {
            canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cactus), 300, 300, false), x + (width * i), y, null);
        }
    }
}
