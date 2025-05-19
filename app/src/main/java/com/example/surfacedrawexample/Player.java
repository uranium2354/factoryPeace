package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.widget.TextView;

import com.example.surfacedrawexample.Map.MapArray;
import com.example.surfacedrawexample.Map.MapElement;
import com.example.surfacedrawexample.Map.TransportBeltItem;

public class Player {
    float x, y;
    TransportBeltItem selectedItem;
    MapElement selectedMapPlace;
    int SIZE_X = 128, SIZE_Y = 128;
    int direction;
    Bitmap texture;
    int IMAGE_COLUMN = 1;
    int IMAGE_ROWS = 1;
    float widthFrame, heightFrame;
    int selectedMapPlaceId = 0;
    Paint paint;
    String tag;
    int rotationPlace = 0;
    TextView textView;
    public Player(int direction, Resources resources, float x, float y, TextView textView){
        texture =  BitmapFactory.decodeResource(resources, R.drawable.entities_player);
        this.x = x;
        this.y = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        this.direction = direction;
        tag = "Player";
        this.textView = textView;
    }
    public void moveDelta(int dx, int dy){
        x += dx;
        y += dy;
    }
    public void moveCord(int dx, int dy){
        x += (float) dx / TEXTURE_SIZE;
        y += (float) dy / TEXTURE_SIZE;
    }
    public void setSelectedMapPlace(int id){
         selectedMapPlaceId = id;
         textView.setText(Integer.toString(id));
    }
}
