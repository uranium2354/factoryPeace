package com.example.surfacedrawexample.Map.Element;

import static com.example.surfacedrawexample.MainActivity.MAIN_ACTIVITY;
import static com.example.surfacedrawexample.MainActivity.isEducationMode;
import static com.example.surfacedrawexample.Map.ArrayId.addNumItemId;
import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.MapArray.getEl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;



public class InfiniteResources extends MapElement {
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int IMAGE_COLUMN = 1;
    int IMAGE_ROWS = 1;
    boolean educationMode = false;
    int  heightScreen,  widthScreen;
    int idItem = 5;
    int[] dx = {1, 1, 1, -1, -1, -1, 0, 0};
    int[] dy = {1, 0, -1, -1, 0, 1, 1, -1};

    public InfiniteResources(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  Core.class);
        object = this;
        constructor(id, direction, mySurfaceView, resources, x, y);
    }
    public void constructor(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){
        texture =  getTextureId(id);
        ArrayX = x;
        ArrayY = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        this.direction = direction;
        icon = Bitmap.createBitmap(texture, 0, 0, (int) widthFrame, (int)heightFrame);
        changeSize(TEXTURE_SIZE);
        tag = "transportItem";
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        educationMode = isEducationMode;
    }
    @Override
    public void draw(Canvas canvas, long currentF){
        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();
        Rect src = new Rect((int)(0), (int)(0),
                (int)(widthFrame ), (int)(heightFrame ));
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                ( ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1)* TEXTURE_SIZE);
        canvas.drawBitmap(texture,src, dst , paint);
        if(isDestroy){
            src = new Rect(0, 0,
                    crossBimap.getWidth(),  crossBimap.getHeight());
            dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                    ( ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1)* TEXTURE_SIZE);
            canvas.drawBitmap(crossBimap,src, dst , paint);
        }
    }

    @Override
    public synchronized void updateState(long frame) {
        if(idItem != 0){
            pushItem(idItem);
        }

    }

    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
        return null;
    }
    @Override
    public void changeSize(int ts){
        TEXTURE_SIZE = ts;
    }
    public boolean pushItem(int id){
        for(int i = 0; i < 8; i++) {
            int nx = ArrayX + dx[i];
            int ny = ArrayY + dy[i];
            MapElement el = getEl(nx, ny);
            if (el != null && el.tag == "transportItem") {
                el.object.pullItem(new TransportBeltItem(id, ArrayX * TEXTURE_SIZE + TEXTURE_SIZE / 2, ArrayY * TEXTURE_SIZE + TEXTURE_SIZE / 2, TEXTURE_SIZE), true, ArrayX, ArrayY);
            }
        }
        return true;
    }

}

