package com.example.surfacedrawexample.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.surfacedrawexample.R;

public class MySurfaceView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Thread renderThread;
    private volatile boolean isRunning = false;
    private Paint paint;

    private Bitmap spriteSheet;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
    private int frameWidth;
    private int frameHeight;
    private Bitmap backgroundImage;
    private int currentFrame = 0;
    private final int FRAME_COUNT = 28;
    private final int FRAME_COUNT_LAST_FARME =60;
    private long frameDelay = 50;
    private int bgOffsetX = 0;
    private int bgWidth;
    private final int MOVE_SPEED = 1;

    public MySurfaceView2(Context context) {
        super(context);
        constructor(context);
    }
    public MySurfaceView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructor(context);
    }
    private void constructor(Context context){
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
        paint.setFilterBitmap(false);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.menu_sky);

        spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.menu_background);
        bgWidth = backgroundImage.getWidth();
        frameWidth = spriteSheet.getWidth() / FRAME_COUNT;
        frameHeight = spriteSheet.getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        backgroundImage = Bitmap.createScaledBitmap(backgroundImage, width, height, false);
        bgWidth = backgroundImage.getWidth();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        try {
            renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!holder.getSurface().isValid()) continue;

            updateFrame();
            drawFrame();

            try {
                Thread.sleep(frameDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateFrame() {
        currentFrame = (currentFrame + 1) % FRAME_COUNT_LAST_FARME;
        bgOffsetX += MOVE_SPEED;
        if(bgOffsetX >= bgWidth)
            bgOffsetX = 0;
    }

    private void drawFrame() {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        try {
            int drawX = bgOffsetX;

            canvas.drawBitmap(backgroundImage, drawX, 0, paint);
            canvas.drawBitmap(backgroundImage, drawX - bgWidth , 0, paint);
            int frameLeft = Math.min(currentFrame, FRAME_COUNT - 1 )* frameWidth;
            srcRect.set(frameLeft, 0, frameLeft + frameWidth, frameHeight);
            dstRect.set(0, 0, getWidth(), getHeight());
             canvas.drawBitmap(spriteSheet, srcRect, dstRect, paint);
        } finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }
}