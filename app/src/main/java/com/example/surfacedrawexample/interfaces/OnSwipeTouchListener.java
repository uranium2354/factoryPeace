package com.example.surfacedrawexample.interfaces;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.Player;
import com.example.surfacedrawexample.R;

import java.util.ArrayList;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;
    MySurfaceView mySurfaceView;
    Player player;
    public OnSwipeTouchListener (Context ctx, Player player,  MySurfaceView mySurfaceView){
       this.player = player;
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        this.mySurfaceView = mySurfaceView;
    }
    float xStart = 0, yStart= 0;
    float dist = 0;
    ArrayList<Integer> fingers = new ArrayList<Integer>();
    // public boolean onTouchEvent (MotionEvent event)
    float distance(float x1, float y1, float x2, float y2){
        float d= (x1 - x2 ) * (x1 - x2 ) + (y1 - y2) * (y1 - y2);
        return (float) Math.sqrt(d);
    }
    boolean isRemove = false;

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int fingerId = event.getPointerId(pointerIndex);
        //обработка клика
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                fingers.add(fingerId);
                if(fingers.size() == 2 && event.findPointerIndex(1) != -1){
                    dist = distance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                }
                yStart = event.getY();
                xStart = event.getX();
                player.setStartSelectDestroy(new Point((int) (event.getX() - mySurfaceView.translateX), (int) (event.getY()- mySurfaceView.translateY)));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                for(int i = 0; i < fingers.size(); i++){
                    if(fingers.get(i) == fingerId){
                        fingers.remove(i);
                        if( fingers.size() == 1) {
                           isRemove = true;
                        }
                    }
                }
                if(fingers.size() == 0){
                    player.regionDestroy( (int) (event.getX()- mySurfaceView.translateX), (int) (event.getY()-mySurfaceView.translateY));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if( fingers.size() == 1) {
                    float diffY = event.getY() - yStart;
                    float diffX = event.getX() - xStart;
                    if(isRemove){
                        isRemove = false;
                        diffY = 0;
                        diffX = 0;
                    }
                    yStart = event.getY();
                    xStart = event.getX();
                    player.moveCord(-(int) diffX, -(int) diffY);
                    player.setDeltaSelectDestroy(new Point((int) diffX, (int) diffY));
                }
                else if(fingers.size() == 2){
                    float dist2 = distance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    float delta = dist - dist2;
                    dist = dist2;
                    mySurfaceView.changeDeltaSize(-(int)delta / 2);
                }
                int pointerCount = event.getPointerCount();
                break;
        }


        //textView.setText(Integer.toString(fingers.size()));
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 0;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            player.select(e.getX() - mySurfaceView.translateX, e.getY() - mySurfaceView.translateY);
              return true;
        }

        @Override
        public boolean onContextClick(@NonNull MotionEvent e) {
            //onClick(e.getX(), e.getY());

            return super.onContextClick(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {





            return true;
        }
        public void onSwipeLeft() {
        }
        public void onClick(float x, float y){

        }

    }
}