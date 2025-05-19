package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Hologram {
    Bitmap image;
    int id;
    int rotation;
    int x;
    int y;
    int widthFrame;
    int heightFrame;
    Paint paint;
    public Hologram(Bitmap image, int id, int rotation, int x, int y){
        this.id = id;
        this.rotation = rotation;
        this.image = image;
        this.x = x;
        this.y = y;
        widthFrame = image.getWidth();
        heightFrame = image.getHeight();
        paint = new Paint();
        paint.setARGB(100, 0, 0, 0);
    }
    public void draw(Canvas canvas){
        Rect src = new Rect(0, 0, widthFrame,heightFrame );
        Rect dst = new Rect((int)x * TEXTURE_SIZE, (int)y* TEXTURE_SIZE, ( x + 1) * TEXTURE_SIZE, (y + 1)* TEXTURE_SIZE);

        canvas.drawBitmap(image ,src, dst , paint);
    }
}
