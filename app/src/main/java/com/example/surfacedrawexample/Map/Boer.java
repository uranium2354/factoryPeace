package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.MapArray.getEl;
import static com.example.surfacedrawexample.Map.MapArray.mapOre;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class Boer extends MapElement{
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int IMAGE_COLUMN = 10;
    int IMAGE_ROWS = 4;

    int  heightScreen,  widthScreen;
    int currentFrame = 0;
    int speed = 4000;
    TransportBeltItem[] item;
    long lastUpdateTime = 0;
    int xS, yS, xT, yT;
    int[] dx = {2, -1, 0, 0, 0, 1, 0, -1, 1, 0, -1, 0};
    int[] dy = {0, 0, -1, 2, -1, 0, -1, 0, 0, 1, 0, 1};
    int[] dx2 = {-1, 1, 0, 0, 1, 0, -1, 0, 0, 1, 0, -1};
    int[] dy2 = {0, 0, 1, -1, 0, -1, 0, -1, 1, 0, 1, 0};
    int[] dxOre = {0, 1, 0, 1};
    int[] dyOre = {0, 0, 1, 1};
    int oreNum = 0;
    boolean isRight = false;
    public Boer(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  Boer.class);
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
        tag = "Boer";
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
    }

    //private void calculete

    @Override
    public void drawUpItem(Canvas canvas, long currentF){
        currentFrame = (int) (currentF);
        currentFrame = currentFrame %  IMAGE_COLUMN ;
        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();
        //как определить номер кадра
        Rect src = new Rect((int)(currentFrame*widthFrame), (int)(direction*heightFrame ),
                (int)((currentFrame+1)*widthFrame ), (int)((direction+1)*heightFrame ));
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE -1, (int)ArrayY* TEXTURE_SIZE - 1,
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
    boolean isСycle = false;
    @Override
    synchronized public void updateState(){
        if(System.currentTimeMillis() - lastUpdateTime >= speed){
            lastUpdateTime = System.currentTimeMillis();
            int nx = ArrayX + dxOre[oreNum];
            int ny = ArrayY+ dyOre[oreNum];
            oreNum++;
            oreNum %= 4;
            if(mapOre[nx][ny] != null){
                pushItem(new TransportBeltItem(mapOre[nx][ny].id, (ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1) * TEXTURE_SIZE, TEXTURE_SIZE));
            }
        }
    }

    public boolean pushItem(TransportBeltItem it){
        int nx = ArrayX+ dx[direction];
        int ny = ArrayY + dy[direction];
        if(isRight){
            if(dx[direction] == 0){
                nx++;
            }
            else {
                ny++;
            }
        }
        isRight = !isRight;
        MapElement el = getEl(nx, ny);
        if(el != null && el.tag == "transportItem"){
            if(isСycle == false){
                isСycle = true;
                el.object.updateState();
                isСycle = false;
            }

            if(el.object.pullItem(it, true, nx - dx[direction], ny - dy[direction])){
                return true;
            }
        }
        return false;
    }
    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int PosX, int PosY){
        return false;
    }
    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
        return null;
    }
    @Override
    public void changeSize(int ts){
        TEXTURE_SIZE = ts;
        xS = TEXTURE_SIZE / 2 + dx2[this.direction] * TEXTURE_SIZE / 2 - (int)(dx2[this.direction] * TEXTURE_SIZE  / 6);
        yS = TEXTURE_SIZE / 2 + dy2[this.direction] * TEXTURE_SIZE / 2- (int)(dy2[this.direction] * TEXTURE_SIZE   / 6);
        xT = TEXTURE_SIZE / 2 + dx[this.direction] * TEXTURE_SIZE / 2+ (int)(dx[this.direction] *  TEXTURE_SIZE   / 6);
        yT = TEXTURE_SIZE / 2 + dy[this.direction] * TEXTURE_SIZE / 2+ (int)(dy[this.direction] * TEXTURE_SIZE  / 6);
    }
}
