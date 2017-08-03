package com.example.varun.chromedino;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Varun on 7/30/2017.
 */

public class MainThread extends Thread {
    private int FPS = 40;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.gamePanel = gamePanel;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            if(waitTime > 0) {
                try {
                    this.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = (totalTime/frameCount)/1000000;
                frameCount = 0;
                totalTime = 0;
            }
            System.out.println(waitTime);
        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
