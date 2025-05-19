package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.TextView;

import com.example.surfacedrawexample.Map.TransportBeltItem;

public class Player {
    float x, y;
    TransportBeltItem selectedItem;
    int SIZE_X = 128, SIZE_Y = 128;
    int direction;
    Bitmap texture;
    int IMAGE_COLUMN = 1;
    int IMAGE_ROWS = 1;
    float widthFrame, heightFrame;
    Paint paint;
    String tag;
    public Player(int direction, Resources resources, float x, float y){
        texture =  BitmapFactory.decodeResource(resources, R.drawable.player_image);
        this.x = x;
        this.y = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        this.direction = direction;
        tag = "Player";
    }
    public void moveDelta(int dx, int dy){
        x += dx;
        y += dy;
    }
    public void moveCord(int dx, int dy){
        x += (float) dx / TEXTURE_SIZE;
        y += (float) dy / TEXTURE_SIZE;
    }
}
