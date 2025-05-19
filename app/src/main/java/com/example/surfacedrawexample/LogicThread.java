package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.MapArray.updateStats;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class LogicThread extends Thread{
    boolean isRun;
    long prevTime;

    public LogicThread(){
        isRun = false;
        prevTime = System.currentTimeMillis();
    }
    @Override
    public void run() {
        while (isRun){
            Canvas canvas = null;
            long nowTime = System.currentTimeMillis();
            long elapsedTime = nowTime - prevTime;
            if(elapsedTime > 10){
                prevTime = nowTime;
                updateStats();
            }
        }
    }

    public void setRunning(boolean b) {
        isRun = b;
    }
}
