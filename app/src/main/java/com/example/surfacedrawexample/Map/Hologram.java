package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Hologram {
    Bitmap image;
    public int id;
    public int rotation;
    public int x;
    public int y;
    int widthFrame;
    int heightFrame;
    public int orderIndex;
    Paint paint;
    public Hologram(Bitmap image, int id, int rotation, int x, int y, int orderIndex){
        this.id = id;
        this.rotation = rotation;
        this.image = image;
        this.x = x;
        this.y = y;
        widthFrame = image.getWidth();
        heightFrame = image.getHeight();
        paint = new Paint();
        paint.setARGB(100, 0, 0, 0);
        this.orderIndex = orderIndex;
    }
    public void draw(Canvas canvas){
        Rect src = new Rect(0, 0, widthFrame,heightFrame );
        Rect dst = new Rect((int)x * TEXTURE_SIZE, (int)y* TEXTURE_SIZE, ( x + 1) * TEXTURE_SIZE, (y + 1)* TEXTURE_SIZE);

        canvas.drawBitmap(image ,src, dst , paint);
    }
}
