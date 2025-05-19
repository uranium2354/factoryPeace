package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.MapArray.getEl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class Manipulator extends MapElement{
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int ArrayX, ArrayY;
    MySurfaceView mySurfaceView;
    int ICON_SIZE =  2;
    int IMAGE_COLUMN = 1;
    int IMAGE_ROWS = 1;
    Paint paint;
    int  heightScreen,  widthScreen;
    int currentFrame = 0;
    int speed = 3000;
    long lastUpdateTime;
    TransportBeltItem item;
    int[] dx = {1, -1, 0, 0, 0, 1, 0, -1, 1, 0, -1, 0};//TODO сдвиги туда куда манипулятор положет придмет
    int[] dy = {0, 0, -1, 1, -1, 0, -1, 0, 0, 1, 0, 1};
    int[] dx2 = {-1, 1, 0, 0, 1, 0, -1, 0, 0, 1, 0, -1};//TODO откуда он его берёт
    int[] dy2 = {0, 0, 1, -1, 0, -1, 0, -1, 1, 0, 1, 0};
    public Manipulator(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y) {
        super(id, direction, x, y, true, Manipulator.class);
        object = this;
        constructor(id, direction, mySurfaceView, resources, x, y);
    }
    public void constructor(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y) {
        this.mySurfaceView = mySurfaceView;
        texture =  BitmapFactory.decodeResource(resources, R.drawable.map_manipulator);
        ArrayX = x;
        ArrayY = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        this.direction = direction;
        icon = Bitmap.createBitmap(texture, 0, 0, (int) widthFrame, (int)heightFrame);
        lastUpdateTime = System.currentTimeMillis();

        item = null;
    }

    public boolean pushItem(TransportBeltItem it, boolean isChange){
        int nx = ArrayX + dx[direction];
        int ny = ArrayY + dy[direction];
        MapElement el = getEl(nx, ny);
        if(el != null){
            if(el.object.pullItem(it, isChange)){
                return true;
            }
        }
        return false;
    }
    public TransportBeltItem pullOut(boolean isChange){
        int nx = ArrayX + dx2[direction];
        int ny = ArrayY + dy2[direction];
        MapElement el = getEl(nx, ny);
        if(el != null){
            return el.object.getItem(isChange);
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas, long currentFrame) {
        Rect src = new Rect(0, 0,
                (int)heightFrame, (int)heightFrame);
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE, (int)ArrayY* TEXTURE_SIZE,
                ( ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1)* TEXTURE_SIZE);
        canvas.drawBitmap(texture, src, dst, paint);
    }
    synchronized public void updateState(){
        if( System.currentTimeMillis() - lastUpdateTime > speed){
            item = pullOut(false);
            if(item != null){
                if(pushItem(item, false)){
                    pullOut(true);
                    pushItem(item, true);
                    lastUpdateTime =  System.currentTimeMillis();
                }
            }

        }
    }

    @Override
    public void changeSize(int ts) {
        TEXTURE_SIZE = ts;
    }
}
