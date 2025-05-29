package com.example.surfacedrawexample.Map.Element;

import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.MapArray.getEl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.MySurfaceView;

public class Divider extends MapElement {
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int IMAGE_COLUMN = 4;
    int IMAGE_ROWS = 1;

    int  heightScreen,  widthScreen;
    int currentFrame = 0;
    int speed = 50, deltaSpeed = 10; //TODO нужна чтобы сгладить неровности при движении item
    TransportBeltItem item;
    boolean isRight = true;
    long lastUpdateTime = 0;
    int[] dl = {2, 3, 1, 0};
    int[] dr = {3, 2, 0, 1};
    int[] dx = {1, -1, 0, 0, 0, 1, 0, -1, 1, 0, -1, 0};
    int[] dy = {0, 0, -1, 1, -1, 0, -1, 0, 0, 1, 0, 1};
    int[] dx2 = {-1, 1, 0, 0, 1, 0, -1, 0, 0, 1, 0, -1};
    int[] dy2 = {0, 0, 1, -1, 0, -1, 0, -1, 1, 0, 1, 0};
    int n ;
    public Divider(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  Divider.class);
        this.texture = texture;
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
        icon = Bitmap.createBitmap(texture, (int)0, (int)0, (int) widthFrame, (int)heightFrame);
        lastUpdateTime = System.currentTimeMillis();
        changeSize(TEXTURE_SIZE);
        tag = "transportItem";
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
    }
    @Override
    public void drawUpItem(Canvas canvas, long currentF){
        Rect src = new Rect((int)( widthFrame * direction), 0,
                (int)(widthFrame * (direction + 1)), (int)heightFrame);
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE -1, (int)ArrayY* TEXTURE_SIZE - 1,
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
    @Override
    synchronized public void updateState(long frame){
        this.frame = frame;
        if(System.currentTimeMillis() - lastUpdateTime >= speed){
            lastUpdateTime = System.currentTimeMillis();
            if(item == null){
                return;
            }
            if(pushItem(item)){
                item = null;
            }
            rotation = direction;
            isRight = !isRight;
        }
    }

    public boolean pushItem(TransportBeltItem it){
        rotation = dl[direction];
        int nx = ArrayX + dx[dl[direction]];
        int ny = ArrayY + dy[dl[direction]];
        if(isRight){
             nx = ArrayX + dx[dr[direction]];
             ny = ArrayY + dy[dr[direction]];
            rotation = dr[direction];
        }
        MapElement el = getEl(nx, ny);
        if(el != null && el.tag == "transportItem"){
            updatePush(el);
            if(el.object.pullItem(it, true, ArrayX, ArrayY)){
                return true;
            }
        }
        return false;
    }
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
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int PosX, int PosY){
         if(item == null && ArrayX + dx2[direction] == PosX && ArrayY + dy2[direction] == PosY){
             if(isChange){
                 lastUpdateTime = System.currentTimeMillis();
                 item = it;
             }
             return true;
         }
         return false;
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
