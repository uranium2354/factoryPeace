package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.ArrayId.RotateBitmap;
import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;

import static com.example.surfacedrawexample.Map.ArrayId.getBackGroundId;

import static com.example.surfacedrawexample.Map.ArrayId.getOreId;
import static com.example.surfacedrawexample.Map.MapArray.map;
import static com.example.surfacedrawexample.Map.MapArray.mapBackGround;
import static com.example.surfacedrawexample.Map.MapArray.mapHologram;
import static com.example.surfacedrawexample.Map.MapArray.mapOre;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.surfacedrawexample.Map.Element.TransportBelt;
import com.example.surfacedrawexample.Map.Element.TransportBeltItem;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap image;
    float x, y, touchX, touchY; //координаты рисунка и точки касания
    float dx, dy;// изменения координат при движении
    Paint paint;
    float speed;
    int kx = 0, ky = 0;
    int renderingCellX = 16;
    int renderingCellY = 8;
    Resources resources;
    SurfaceHolder holder;
    DrawThread drawThread;//поток рисования
    long currentFrame = 0;
    int minSize = 30;
    int offsetCanvas = 0;
    public float translateX = 0;
    public float translateY = 0;
    boolean isMapGenerate = false;
    Bitmap background_left, background_left_top;
    TransportBelt transport_belt1, transport_belt2;
    Context context;
 //   arrayId;
    TransportBeltItem t;
    int startSize = 64;
    Player player;
    private int dts = 0;

    public MySurfaceView(Context context) {
        super(context);
        constructor(context);
    }
    public MySurfaceView (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        constructor(context);
    }
    private void constructor(Context context){
        getHolder().addCallback(this);
        holder = getHolder();
        holder.addCallback(this);//"активируем" интерфейс SurfaceHolder.Callback
        paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        x = 400;
        y = 1100;
        resources = getResources();
        image = BitmapFactory.decodeResource(resources, R.drawable.sprites);
        background_left = BitmapFactory.decodeResource(resources, R.drawable.background_desert_edge_left);
        background_left_top = BitmapFactory.decodeResource(resources, R.drawable.background_desert_edge_left_top);
        speed = 20;//коэффициент скорости

        t = new TransportBeltItem(1, 0, 0, 128);
        t.move(0, 1500, 1500, 0, 10000);
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        this.context = context;

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //mapWorker.draw(canvas);
       // player.move(1, 1);
        translateX = canvas.getWidth() / 2  -player.x * TEXTURE_SIZE;
        translateY = canvas.getHeight() / 2 -  player.y * TEXTURE_SIZE;
       canvas.translate(translateX , translateY);

     
        int xPlayerCell = (int)player.x;
        int yPlayerCell = (int)player.y;
        renderingCellX = canvas.getWidth()/ 2 / TEXTURE_SIZE + 3;
        renderingCellY = canvas.getHeight() /2 / TEXTURE_SIZE + 3;
        int startI = xPlayerCell - renderingCellX;
        int topI = xPlayerCell + renderingCellX;
        int startJ = yPlayerCell - renderingCellY;
        int topJ = yPlayerCell + renderingCellY;
        for (int i = Math.max(-3, startI - 1); i < Math.min(map.length + 3, topI + 2); i++) {
            for (int j = Math.max(-3, startJ - 1); j < Math.min(map[0].length + 3, topJ + 2); j++) {
                if(i % 3 == 0 && j % 3 == 0)
                    drawBackGround(canvas, i, j);
            }
        }
        for(int layer = 0; layer < 5; layer++) {
            for (int i = Math.max(0, startI); i < Math.min(map.length, topI); i++) {
                for (int j = Math.max(0, startJ); j < Math.min(map[i].length, topJ); j++) {
                    switch (layer) {
                        case 1:

                            if(mapOre[i][j] != null){
                                drawOre(canvas, i, j);
                            }

                            break;
                        case 2:
                            if (map[i][j] != null && map[i][j].ArrayX == i && map[i][j].ArrayY == j) {
                                map[i][j].object.draw(canvas, currentFrame);
                            }
                            if(mapHologram[i][j] != null  && mapHologram[i][j].x == i && mapHologram[i][j].y == j){
                                mapHologram[i][j].draw(canvas);
                            }
                            break;
                        case 3:
                            if(map[i][j] != null && map[i][j].ArrayX == i && map[i][j].ArrayY == j){
                                map[i][j].object.drawItems(canvas);
                            }
                            break;
                        case 4:
                            if(map[i][j] != null && map[i][j].ArrayX == i && map[i][j].ArrayY == j)
                                map[i][j].object.drawUpItem(canvas, currentFrame);

                    }

                }
            }
        }
       // character.draw(canvas);
        currentFrame++;
        player.draw(canvas);
        TEXTURE_SIZE += dts;
        offsetCanvas += dts;
        TEXTURE_SIZE = Math.max(minSize, TEXTURE_SIZE);
        for(int i = 0; i < map.length; i++){
            for(int j = 0;  j < map[i].length; j++){
                if (map[i][j] != null) {
                    map[i][j].object.changeSize(TEXTURE_SIZE);
                }
            }
        }
        changeSizeBackGround(TEXTURE_SIZE);
        dts = 0;
    }
    public void changeDeltaSize(int dts){
        this.dts += dts;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
        }
        return true;
    }

    private void calculate() {
        double hypot = Math.sqrt((touchX - x)*(touchX - x) + (touchY - y)*(touchY - y));
        dx = speed * (touchX - x)/(float) hypot;
        dy = speed * (touchY - y)/(float) hypot;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread(holder, this);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public  void changeSizeBackGround(int ts){
        TEXTURE_SIZE = ts;
    }
    public  void drawBackGround(Canvas canvas, int posX, int posY){
        int heightFrame = getBackGroundId(0).getHeight();
            Rect src = new Rect(0, 0,
                    (int)heightFrame, (int)heightFrame);
            Rect dst = new Rect((int)posX * TEXTURE_SIZE - 1, (int)posY* TEXTURE_SIZE - 1,
                    ( posX + 3) * TEXTURE_SIZE, (posY+ 3)* TEXTURE_SIZE);
            if(posX < 0 || posY < 0 || posX >= mapBackGround.length || posY >= mapBackGround[0].length){

                if(posX > mapBackGround.length && posY < 0 )
                    canvas.drawBitmap(RotateBitmap(background_left_top, 90), src, dst, paint);
                else if(posX < 0 && posY < 0 )
                    canvas.drawBitmap(RotateBitmap(background_left_top, 0), src, dst, paint);
                else if(posX > mapBackGround.length && posY > mapBackGround[0].length )
                    canvas.drawBitmap(RotateBitmap(background_left_top, 180), src, dst, paint);
                else if(posX < 0 && posY > mapBackGround[0].length )
                    canvas.drawBitmap(RotateBitmap(background_left_top, 180+90), src, dst, paint);
                else if(posX < 0)
                    canvas.drawBitmap(RotateBitmap(background_left, 0), src, dst, paint);
                else if(posX > mapBackGround.length)
                    canvas.drawBitmap(RotateBitmap(background_left, 180), src, dst, paint);
                else if(posY < 0)
                    canvas.drawBitmap(RotateBitmap(background_left, 90), src, dst, paint);
                else if(posY > mapBackGround[0].length)
                    canvas.drawBitmap(RotateBitmap(background_left, -90 ), src, dst, paint);
            }
            else
                canvas.drawBitmap(getBackGroundId(mapBackGround[posX][posY]), src, dst, paint);
    }
    public void drawOre(Canvas canvas, int posX, int posY){
        int heightFrame = getOreId(mapOre[posX][posY].id, mapOre[posX][posY].num).getHeight();
        Rect src = new Rect(0, 0,
                (int)heightFrame, (int)heightFrame);
        Rect dst = new Rect((int)posX * TEXTURE_SIZE - 1, (int)posY* TEXTURE_SIZE - 1,
                ( posX + 1) * TEXTURE_SIZE, (posY+ 1)* TEXTURE_SIZE);
        canvas.drawBitmap(getOreId(mapOre[posX][posY].id, mapOre[posX][posY].num), src, dst, paint);
    }
}
