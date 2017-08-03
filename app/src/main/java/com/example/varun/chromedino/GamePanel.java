package com.example.varun.chromedino;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Varun on 7/30/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 2996; // 856
    public static final int HEIGHT = 1680; // 480
    private static final int PLAYERWIDTH = 6142;
    private static final int PLAYERHEIGHT = 1060;
    private MainThread thread;
    private Background background;
    private Player player;
    private ArrayList<Obstacle> obstacles;
    private long startTime;
    private boolean firstTime = false;

    public GamePanel(Context context) {
        super(context);

        // add the callback to the surfaceholder to intercept events e.g. button presses

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        // make gamepanel focusable so it can handle events

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        background.setVector(-15);
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.chromedinofixed), PLAYERWIDTH/6, PLAYERHEIGHT, 3);
        obstacles = new ArrayList<>();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(!player.isPlaying()) {
                player.setPlaying(true);
            } else {
                player.jump();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        if(player.isPlaying()) {
            background.update();
            player.update();
            if(obstacles != null) {
                for (int i = 0; i < obstacles.size(); i++) {
                    obstacles.get(i).update();
                    if(obstacles.get(i).getX() < -(obstacles.get(i).width * obstacles.get(i).cactusNum)) {
                        obstacles.remove(i);
                    }
                }
            }

            if(obstacles.size() <= 1) {
                obstacles.add(new Obstacle());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = (float) getWidth()/WIDTH;
        final float scaleFactorY = (float) getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);
            if(obstacles != null) {
                for (Obstacle obstacle : obstacles) {
                    obstacle.draw(canvas, this.getContext());
                }
            }
        }
    }
}
