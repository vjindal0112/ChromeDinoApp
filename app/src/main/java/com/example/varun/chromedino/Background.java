package com.example.varun.chromedino;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;
/**
 * Created by Varun on 7/30/2017.
 */

public class Background {
    private Bitmap image;
    private int x = 0,y = 0,dx;

    public Background(Bitmap bitmap) {
        image = bitmap;

    }

    public void update() {
        x+=dx;
        if(x < -GamePanel.WIDTH) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y,null);
        canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
    }

    public void setVector(int dx) {
        this.dx = dx;
    }

    public void spawnObstacle() {
        Random randGen = new Random();
        int cactusNum = randGen.nextInt(2) + 1;
        // TODO: determine whether new class or naw + FINISH
    }

}
