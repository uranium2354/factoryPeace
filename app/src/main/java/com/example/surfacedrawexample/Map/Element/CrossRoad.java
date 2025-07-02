package com.example.surfacedrawexample.Map.Element;

import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.MapArray.getEl;
import static com.example.surfacedrawexample.Map.MapArray.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;

public class CrossRoad extends MapElement {
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int ICON_SIZE =  2;
    int IMAGE_COLUMN =1;
    int IMAGE_ROWS =1;

    int  heightScreen,  widthScreen;
    int currentFrame = 0;
    int speed = 200, deltaSpeed = 10; //TODO нужна чтобы сгладить неровности при движении item
    TransportBeltItem[] itemVer, itemHor;
    int dirVer = 0, dirHor = 0;
    long lastUpdateTime = 0;
    int xS, yS, xT, yT;
    int[] dx = {1, -1, 0, 0, 0, 1, 0, -1, 1, 0, -1, 0};
    int[] dy = {0, 0, -1, 1, -1, 0, -1, 0, 0, 1, 0, 1};
    int[] dx2 = {-1, 1, 0, 0, 1, 0, -1, 0, 0, 1, 0, -1};
    int[] dy2 = {0, 0, 1, -1, 0, -1, 0, -1, 1, 0, 1, 0};
    int[] dxi = {1, -1, 0, 0, -1, 1, -1, -1, 1, -1, 1, -1};
    int[] dyi = {0, 0, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    int n ;
    public CrossRoad(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  CrossRoad.class);
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
        itemVer = new TransportBeltItem[]{null, null, null};
        itemHor = new TransportBeltItem[]{null, null, null};
        n = itemVer.length;
        changeSize(TEXTURE_SIZE);
        tag = "transportItem";
    }

    //private void calculete

    @Override
    public void drawUpItem(Canvas canvas, long currentF){
        //как определить номер кадра
        Rect src = new Rect((int)(0), (int)(0),
                (int)((1)*widthFrame ), (int)(heightFrame ));
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

    boolean isСycle = false;
    private void updatePush(MapElement el){
        if(isСycle == false){
            isСycle = true;
            if(el.object.frame != frame){
                el.object.updateState(frame);
            }
            isСycle = false;
        }
    }
    @Override
    synchronized public void updateState(long frame){
        this.frame = frame;
        if(System.currentTimeMillis() - lastUpdateTime >= speed){
            lastUpdateTime = System.currentTimeMillis();
            if(itemVer[n - 1] != null ){
                if(pushItem(itemVer[n - 1], dirVer)) {
                    itemVer[n - 1] = null;
                }
            }
            for(int i = itemVer.length - 1; i >= 1; i--){
                if(i == 1 && itemVer[i - 1] != null && !itemVer[0].isMove()){

                }
                else if(itemVer[i] == null && itemVer[i - 1] != null){
                    itemVer[i] = itemVer[i - 1];
               
                    itemVer[i - 1] = null;
                }
            }
            if(itemHor[n - 1] != null ){
                if(pushItem(itemHor[n - 1], dirHor)) {
                    itemHor[n - 1] = null;
                }
            }
            for(int i = itemHor.length - 1; i >= 1; i--){
                if(i == 1 && itemHor[i - 1] != null && !itemHor[0].isMove()){

                }
                else if(itemHor[i] == null && itemHor[i - 1] != null){
                    itemHor[i] = itemHor[i - 1];

                    itemHor[i - 1] = null;
                }
            }
        }
    }

    public boolean pushItem(TransportBeltItem it, int dir){
        int nx = ArrayX + dx[dir];
        int ny = ArrayY + dy[dir];
        MapElement el = getEl(nx, ny);
        if(el != null && el.tag == "transportItem"){
           updatePush(el);
            rotation = dir;
            if(TEXTURE_SIZE == 0){
                return false;
            }
            int lx = ArrayX*TEXTURE_SIZE + TEXTURE_SIZE / 2;
            int ly = ArrayY * TEXTURE_SIZE + TEXTURE_SIZE / 2;
            it.move(lx, ly,lx, ly, 0);
            if(el.object.pullItem(it, true, ArrayX, ArrayY)){
                return true;
            }
        }
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        return false;
    }
    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int PosX, int PosY){
        if(PosX == ArrayX){
            if(itemHor[0] == null){
                if(isChange){
                    itemHor[0] = it;
                    dirHor = map[PosX][PosY].rotation;
                }
                return true;
            }
            return false;
        }
        if(PosY == ArrayY){
            if(itemVer[0] == null){
                if(isChange){
                    itemVer[0] = it;
                    dirVer = map[PosX][PosY].rotation;
                }
                return true;
            }
            return false;
        }
        return false;

    }
    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
        if(getEl(PosX, PosY).rotation <= 1){
            for(int i = 0; i < itemVer.length; i++){
                if(itemVer[i] != null){
                    TransportBeltItem ans =itemVer[i];
                    if(isChange)
                        itemVer[i] = null;
                    return ans;
                }
            }
        }
       else
            for(int i = 0; i < itemHor.length; i++){
                if(itemHor[i] != null){
                    TransportBeltItem ans =itemHor[i];
                    if(isChange)
                        itemHor[i] = null;
                    return ans;
                }
            }
        return null;
    }
    @Override
    public void changeSize(int ts){
        float delta = (float)ts / (float)TEXTURE_SIZE;
        TEXTURE_SIZE = ts;
        xS = TEXTURE_SIZE / 2 + dx2[this.direction] * TEXTURE_SIZE / 2 - (int)(dx2[this.direction] * TEXTURE_SIZE  / 6);
        yS = TEXTURE_SIZE / 2 + dy2[this.direction] * TEXTURE_SIZE / 2- (int)(dy2[this.direction] * TEXTURE_SIZE   / 6);
        xT = TEXTURE_SIZE / 2 + dx[this.direction] * TEXTURE_SIZE / 2+ (int)(dx[this.direction] *  TEXTURE_SIZE   / 6);
        yT = TEXTURE_SIZE / 2 + dy[this.direction] * TEXTURE_SIZE / 2+ (int)(dy[this.direction] * TEXTURE_SIZE  / 6);


    }
}
