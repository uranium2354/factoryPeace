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

public class TransportBelt extends MapElement {
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;
    int ArrayX, ArrayY;
    int ICON_SIZE =  2;
    int IMAGE_COLUMN = 32;
    int IMAGE_ROWS = 40;
    Paint paint;
    int  heightScreen,  widthScreen;
    int currentFrame = 0;
    int speed = 500, deltaSpeed = 10; //TODO нужна чтобы сгладить неровности при движении item
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
   // Vector<Integer> moveItem = new Vector<>();
    public TransportBelt(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction);

        texture =  BitmapFactory.decodeResource(resources, R.drawable.map_transportbelt);
        ArrayX = x;
        ArrayY = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        this.direction = direction;
        icon = Bitmap.createBitmap(texture, (int)heightFrame/2, (int)widthFrame / 2, (int) widthFrame, (int)heightFrame);
        lastUpdateTime = System.currentTimeMillis();
        object = this;
        item = new TransportBeltItem[]{null, null, new TransportBeltItem(1, 0, 0, TEXTURE_SIZE)};
        n = item.length;
        changeSize(TEXTURE_SIZE);
        tag = "transportItem";
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
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE, (int)ArrayY* TEXTURE_SIZE,
                ( ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1)* TEXTURE_SIZE);

        canvas.drawBitmap(texture,src, dst , paint);
        paint.setARGB(255, 250, 0, 0);
    }

    @Override
    public void drawItems(Canvas canvas){
        for(TransportBeltItem el : item){
            if(el != null)
                el.draw(canvas);
        }

    }

    @Override
    synchronized public void updateState(){
        if(System.currentTimeMillis() - lastUpdateTime >= speed){
            lastUpdateTime = System.currentTimeMillis();
            if(item[n - 1] != null ){
                if(pushItem(item[n - 1])) {
                    item[n - 1] = null;
                }
                else
                    moveItem(n - 1, n - 1);
            }
                for(int i = item.length - 1; i >= 1; i--){
                    if(i == 1 && item[i - 1] != null && !item[0].isMove()){

                    }
                    else if(item[i] == null && item[i - 1] != null){
                        item[i] = item[i - 1];
                        moveItem(i - 1, i);
                        item[i - 1] = null;
                    }
                }
        }
    }

     public boolean pushItem(TransportBeltItem it){
        int nx = ArrayX + dx[direction];
        int ny = ArrayY + dy[direction];
        MapElement el = getEl(nx, ny);
        if(el != null && el.tag == "transportItem"){
            if(el.object.pullItem(it, true)){
                return true;
            }
        }
        return false;
    }
    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange){
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
    TransportBeltItem getItem(boolean isChange){
         if(item[(int)n / 2] != null){
            TransportBeltItem ans =item[(int)n / 2];
            if(isChange)
             item[(int)n / 2] = null;
             return ans;
         }
         return null;
    }
    @Override
    public void changeSize(int ts){
        int delta = TEXTURE_SIZE - ts;
        TEXTURE_SIZE = ts;
        xS = TEXTURE_SIZE / 2 + dx2[this.direction] * TEXTURE_SIZE / 2 - (int)(dx2[this.direction] * TEXTURE_SIZE  / 6);
        yS = TEXTURE_SIZE / 2 + dy2[this.direction] * TEXTURE_SIZE / 2- (int)(dy2[this.direction] * TEXTURE_SIZE   / 6);
        xT = TEXTURE_SIZE / 2 + dx[this.direction] * TEXTURE_SIZE / 2+ (int)(dx[this.direction] *  TEXTURE_SIZE   / 6);
        yT = TEXTURE_SIZE / 2 + dy[this.direction] * TEXTURE_SIZE / 2+ (int)(dy[this.direction] * TEXTURE_SIZE  / 6);
        for(TransportBeltItem el : item){
            if(el != null){
                el.TEXTURE_SIZE = ts;
                el.xs -= ArrayX * delta;
                el.ys -= ArrayY * delta;
                el.xt -= ArrayX * delta;
                el.yt -= ArrayY * delta;
            }
        }
    }
}
