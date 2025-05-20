package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.getImageId;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class TransportBeltItem {
    int id;
    int x, y;
   int xs; int ys; int xt; int yt; long timeMoving;
    long lastDrawTime ;
    Bitmap icon = null;
    int ICON_SIZE = 3;
    int TEXTURE_SIZE;
    int heightFrame;
    Paint paint;
    public TransportBeltItem(int id, int x, int y, int TEXTURE_SIZE){
        this.x = x;
        this.y = y;
        this.xt = x;
        this.yt = y;
        this.id = id;
        lastDrawTime = System.currentTimeMillis();
        if(getImageId(id) != null){
            icon = getImageId(id);
            heightFrame = this.icon.getHeight();
        }

        this.TEXTURE_SIZE = TEXTURE_SIZE;
        paint = new Paint();
    }
    public boolean isMove(){
            return (System.currentTimeMillis() - lastDrawTime > timeMoving - 20);
    }
    public void move(int xs, int ys, int xt, int yt, long timeMoving){
        this.xs = xs;
        this.ys = ys;
        this.yt = yt;
        this.xt = xt;
        this.timeMoving = timeMoving;
        lastDrawTime = System.currentTimeMillis();
    }
    public void draw(Canvas canvas){
        if(timeMoving > 0){
            long AM = System.currentTimeMillis() - lastDrawTime;
            long BM = timeMoving - AM;
            float d = (float)AM / (float)(BM);
            x = Math.round((float)(xs + d * (float)xt) / (float)(1.0 + d));
            y = Math.round((float)(ys + d * (float)yt) / (float)(1.0 + d));
            paint.setARGB(255, 0, 0, 255);
          //  canvas.drawLine(xs, ys, xt, yt, paint);
            paint.setARGB(255, 255, 0, 0);
            ///   canvas.drawPoint(x, y, paint);
            move(x, y, xt, yt, timeMoving - AM );
            Rect srcIcon = new Rect(0,0,
                    (int)(heightFrame),  (int)(heightFrame));
            Rect dstIcon = new Rect(x - TEXTURE_SIZE / ICON_SIZE / 2, y - TEXTURE_SIZE / ICON_SIZE / 2,
                    x + TEXTURE_SIZE / ICON_SIZE / 2, y + TEXTURE_SIZE / ICON_SIZE / 2);
           canvas.drawBitmap(icon, srcIcon, dstIcon, paint);
        }
        else{
            x= xt;
            y = yt;
            Rect srcIcon = new Rect(0,0,
                    (int)(heightFrame),  (int)(heightFrame));
            Rect dstIcon = new Rect(x - TEXTURE_SIZE / ICON_SIZE / 2, y - TEXTURE_SIZE / ICON_SIZE / 2,
                    x + TEXTURE_SIZE / ICON_SIZE / 2, y + TEXTURE_SIZE / ICON_SIZE / 2);
            canvas.drawBitmap(icon, srcIcon, dstIcon, paint);
        }
    }
}
