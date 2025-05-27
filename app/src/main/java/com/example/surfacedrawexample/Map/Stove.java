package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.Crafts.getCraftIng;
import static com.example.surfacedrawexample.Map.MapArray.getEl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class Stove extends MapElement{
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;

    int IMAGE_COLUMN = 6;
    int IMAGE_ROWS = 2;
    boolean isСooking = false;
    int currentFrame = 0;
    int speed = 5000;
    int maxNum = 20;
    int numIn = 0;
    int numOut = 0;
    int numfuel = 0;
    int powerFuel = 5;
    TransportBeltItem itemIn;
    TransportBeltItem itemFuel;
    TransportBeltItem itemOut;
    long lastUpdateTime = 0;
    public Stove(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  Stove.class);
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
        lastUpdateTime = System.currentTimeMillis();
        changeSize(TEXTURE_SIZE);
        tag = "crafter";
        mapScaleX = 2;
        mapScaleY = 2;
      //  numIn = 3;
       // itemIn = new TransportBeltItem(5, 0, 0, TEXTURE_SIZE);
     //   numfuel = 5;
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
    }

    //private void calculete
    @Override
    public void drawUpItem(Canvas canvas, long currentF){
        currentF %= IMAGE_COLUMN;
        int k = 0;
        if(!isСooking){
            currentF = 0;
            k = 1;
        }
        Rect src = new Rect((int)(currentF*widthFrame + 1), (int)(k *  heightFrame),
                (int)((currentF+1)*widthFrame), (int)((k + 1) * heightFrame ));
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                ( ArrayX + 2) * TEXTURE_SIZE, (ArrayY + 2)* TEXTURE_SIZE);

        canvas.drawBitmap(texture,src, dst , paint);
        if(isDestroy){
             src = new Rect(0, 0,
            crossBimap.getWidth(),  crossBimap.getHeight());
             dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                    ( ArrayX + 2) * TEXTURE_SIZE, (ArrayY + 2)* TEXTURE_SIZE);
            canvas.drawBitmap(crossBimap,src, dst , paint);
        }
    }
    @Override
    synchronized public void updateState(){
        if(System.currentTimeMillis() - lastUpdateTime >= speed){
            if(numIn > 0 && numfuel > 0 && numOut < maxNum){
                lastUpdateTime = System.currentTimeMillis();
                numIn--;
                numfuel--;
                numOut++;
                if(numOut > 0 && itemOut == null)
                    itemOut = new TransportBeltItem(getCraftIng(id, itemIn.id).id, ArrayX + 1, ArrayY + 1, TEXTURE_SIZE);
                isСooking = true;
                if(numIn == 0)
                    itemIn = null;
                if(numfuel == 0)
                    itemFuel = null;
                if(numOut == 0)
                    itemOut = null;
            }
            else {
                isСooking = false;
            }
        }
    }

    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int x, int y){
        if(itemIn != null && itemIn.id == it.id && numIn < maxNum){
            if(isChange){
                numIn++;
            }
            return true;
        }
        if(numIn == 0 && getCraftIng(id, it.id) != null){
            if(isChange){
                itemIn = it;
                numIn++;
                itemOut = new TransportBeltItem(getCraftIng(id, it.id).id, ArrayX + 1, ArrayY + 1, TEXTURE_SIZE);
            }
            return true;
        }
        if(it.id == 7 && numfuel < maxNum){
            if(isChange){
                numfuel += powerFuel;
                itemFuel = it;
            }
            return true;
        }
        return false;
    }
    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
       if(numOut > 0 ){
           if(isChange){
               numOut--;
           }
           int itId = itemOut.id;
           return new TransportBeltItem(itId , (ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1) * TEXTURE_SIZE, TEXTURE_SIZE);
       }
        return null;
    }
    @Override
    public void changeSize(int ts){
        TEXTURE_SIZE = ts;
    }
}
