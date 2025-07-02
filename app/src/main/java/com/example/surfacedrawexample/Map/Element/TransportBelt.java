package com.example.surfacedrawexample.Map.Element;

import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.MapArray.getEl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.Map.MapArray;
import com.example.surfacedrawexample.MySurfaceView;

public class TransportBelt extends MapElement {
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int ICON_SIZE =  2;
    int IMAGE_COLUMN = 32;
    int IMAGE_ROWS = 40;
    int  heightScreen,  widthScreen;
    int currentFrame = 0;
    int speed = 200, deltaSpeed = 10; //TODO нужна чтобы сгладить неровности при движении item
    TransportBeltItem[] item;
    long lastUpdateTime = 0;
    int xS, yS, xT, yT;

    int[] dx = {1, -1, 0, 0, 0, 1, 0, -1, 1, 0, -1, 0};
    int[] dy = {0, 0, -1, 1, -1, 0, -1, 0, 0, 1, 0, 1};
    int[] dx2 = {-1, 1, 0, 0, 1, 0, -1, 0, 0, 1, 0, -1};
    int[] dy2 = {0, 0, 1, -1, 0, -1, 0, -1, 1, 0, 1, 0};
    int[] dxi = {1, -1, 0, 0, -1, 1, -1, -1, 1, -1, 1, -1};
    int[] dyi = {0, 0, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    int n ;
    public TransportBelt(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  TransportBelt.class);
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
        icon = Bitmap.createBitmap(texture, (int)heightFrame/2, (int)widthFrame / 2, (int) widthFrame, (int)heightFrame);
        lastUpdateTime = System.currentTimeMillis();
        item = new TransportBeltItem[]{null, null, null};
        n = item.length;
        changeSize(TEXTURE_SIZE);
        tag = "transportItem";
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        frame = 0;
    }

    //private void calculete

    private void moveItem(int i, int iT){
        float lS = (float)i / (float)(n - i);
        float lT = (float)(iT)/ (float)(n - iT);
        item[iT].move(
                TEXTURE_SIZE * ArrayX +(int)((float)(xS + lS * xT) / (float)(1 + lS)),
                TEXTURE_SIZE * ArrayY +(int)((float)(yS + lS * yT) / (float)(1 + lS)),
                TEXTURE_SIZE * ArrayX +(int)((float)(xS + lT * xT) / (float)(1 +  lT)),
                TEXTURE_SIZE * ArrayY +(int)((float)(yS + lT * yT) / (float)(1 +  lT)), speed + deltaSpeed);
    }
    @Override
    public void draw(Canvas canvas, long currentF){
        currentFrame = (int) (currentF * 2);
        currentFrame = currentFrame %  IMAGE_COLUMN ;
        heightScreen = canvas.getHeight();
        widthScreen = canvas.getWidth();
        //как определить номер кадра
        Rect src = new Rect((int)(currentFrame*widthFrame + widthFrame / 2), (int)(direction* 2*heightFrame + heightFrame/2),
                (int)((currentFrame+1)*widthFrame + widthFrame / 2), (int)((direction * 2+1)*heightFrame + heightFrame/2));
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE -1, (int)ArrayY* TEXTURE_SIZE - 1,
                ( ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1)* TEXTURE_SIZE);

        canvas.drawBitmap(texture,src, dst , paint);
        int quantityin = 0, newdir = -1;
        for(int i = 0; i < 4; i++){
            int nx = ArrayX + dx[i];
            int ny = ArrayY + dy[i];
            MapElement el = getEl(nx, ny);
            if(el != null && el.tag == "transportItem"){
                if(nx + dx[el.rotation] == ArrayX && ny + dy[el.rotation] == ArrayY){
                    quantityin++;
                    for(int j = 0; j < 12; j++){
                        int nx2 = ArrayX + dx2[j];
                        int ny2 = ArrayY + dy2[j];

                        if(nx2 == nx && ny2 == ny && dx[j] == dx[direction] && dy[j] == dy[direction]){
                            newdir = j;
                        }
                    }
                }
            }
        }
        if(quantityin == 1 && newdir != -1){
            direction = newdir;
            changeSize(TEXTURE_SIZE);
        }
        if(isDestroy){
            src = new Rect(0, 0,
                    crossBimap.getWidth(),  crossBimap.getHeight());
            dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                    ( ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1)* TEXTURE_SIZE);
            canvas.drawBitmap(crossBimap,src, dst , paint);
        }
    }

    @Override
    public void drawItems(Canvas canvas){
        for(TransportBeltItem el : item){
            if(el != null)
                el.draw(canvas);
        }

    }
    boolean isСycle = false;
    @Override
    synchronized public void updateState(long frame){
        if( frame == this.frame){
            return;
        }
        this.frame = frame;
    //    if(System.currentTimeMillis() - lastUpdateTime >= speed){
    //    if(frame % speed == 0){
            lastUpdateTime = System.currentTimeMillis();
            if(item[n - 1] != null ){
                if(pushItem(item[n - 1])) {
                    item[n - 1] = null;
                }
                else
                    moveItem(n - 1, n - 1);
            }
                for(int i = item.length - 1; i >= 1; i--){

                   if(item[i] == null && item[i - 1] != null){
                        item[i] = item[i - 1];
                        moveItem(i - 1, i);
                        item[i - 1] = null;
                    }
                }
       // }
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
     public boolean pushItem(TransportBeltItem it){
        int nx = ArrayX + dx[direction];
        int ny = ArrayY + dy[direction];
        MapElement el = getEl(nx, ny);
        if(el != null && el.tag == "transportItem"){
            updatePush(el);

            if(el.object.pullItem(it, true, ArrayX, ArrayY)){
                return true;
            }
        }
        return false;
    }
    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int PosX, int PosY){
        if(item[0] == null){
            if(isChange){
                item[0] = it;
                item[0].move(item[0].xt, item[0].yt, TEXTURE_SIZE * ArrayX +xS, TEXTURE_SIZE * ArrayY +yS, speed);
            }
            return true;
        }
        return false;
    }
    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
        for(int i = 0; i < item.length; i++){
            if(item[i] != null){
                TransportBeltItem ans =item[i];
                if(isChange)
                    item[i] = null;
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
        for(TransportBeltItem el : item){
            if(el != null){
                el.TEXTURE_SIZE = ts;
               // el.x -= ArrayX * delta;
              //  el.y -= ArrayY * delta;

                el.xs =  Math.round(delta * el.xs );
                el.ys =  Math.round(delta * el.ys ) ;
                el.xt =  Math.round(delta * el.xt ) ;
                el.yt =  Math.round(delta * el.yt ) ;

            }
        }
    }

    @Override
    public String saveString() {
        String ans = "";
        for(int i = 0 ; i < item.length; i++){
            if(item[i] == null){
                ans += "0 ";
            }
            else {
                ans += Integer.toString(item[i].id) + " ";
            }
        }
        return ans;
    }

    @Override
    public void readString(String s) {
        if(s == null){
            return;
        }
        String[] parts = s.split(" ");
        if(parts.length == item.length){
            for(int i = 0 ; i < item.length; i++){
                if(parts[i] != "0" && Integer.parseInt(parts[i]) != 0){
                    item[i] = new TransportBeltItem(Integer.parseInt(parts[i]), 0, 0, TEXTURE_SIZE);
                    moveItem(i, i);

                }
            }
        }
    }
}
