package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.addNumItemId;
import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.MapArray.getEl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class Core extends MapElement{
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int IMAGE_COLUMN = 1;
    int IMAGE_ROWS = 1;

    int  heightScreen,  widthScreen;
    int currentFrame = 0;

    public Core(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

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
    }
    @Override
    public void draw(Canvas canvas, long currentF){
        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();
        Rect src = new Rect((int)(0), (int)(0),
                (int)(widthFrame ), (int)(heightFrame ));
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                ( ArrayX + 3) * TEXTURE_SIZE, (ArrayY + 3)* TEXTURE_SIZE);
        canvas.drawBitmap(texture,src, dst , paint);
        if(isDestroy){
            src = new Rect(0, 0,
                    crossBimap.getWidth(),  crossBimap.getHeight());
            dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                    ( ArrayX + 3) * TEXTURE_SIZE, (ArrayY + 3)* TEXTURE_SIZE);
            canvas.drawBitmap(crossBimap,src, dst , paint);
        }
    }


    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int PosX, int PosY){
        addNumItemId(it.id);
        it = null;
        return true;
    }
    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
        return null;
    }
    @Override
    public void changeSize(int ts){
        TEXTURE_SIZE = ts;
    }
}
