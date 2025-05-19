package com.example.surfacedrawexample.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class MapElement {

    int id;
    public int TEXTURE_SIZE= 128;
    int rotation = 0;
    String tag = "none";
    int hp;
    int startHp;
    public MapElement object;
    Bitmap icon;
    public MapElement(int id, int rotation){
        this.id = id;
        this.rotation = rotation;

    }
    void rotate(){
        rotation++;
        rotation %= 4;
    }
    void damage(int a){}
    void hill(int a){}
    boolean pullItem(TransportBeltItem id, boolean isChange){return false;}
    TransportBeltItem getItem(boolean isChange){return null;}
    public void draw(Canvas canvas, long currentFrame){}
    public void drawItems(Canvas canvas){}
    public void changeSize(int ts){};
    synchronized public void updateState(){}
}
