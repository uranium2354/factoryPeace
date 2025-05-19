package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;
import static com.example.surfacedrawexample.Map.MapArray.map;
import static com.example.surfacedrawexample.Map.MapArray.mapHologram;


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

import com.example.surfacedrawexample.Map.ArrayId;
import com.example.surfacedrawexample.Map.MapElement;
import com.example.surfacedrawexample.Map.TransportBelt;
import com.example.surfacedrawexample.Map.TransportBeltItem;

import java.util.ArrayList;

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
    ArrayList<Sprite> sprites = new ArrayList<>();
    Sprite character;
    int minSize = 20;
    int offsetCanvas = 0;
    public float translateX = 0;
    public float translateY = 0;
    boolean isMapGenerate = false;
    MapWorker mapWorker;
    TransportBelt transport_belt1, transport_belt2;
 //   arrayId;
    TransportBeltItem t;
    int startSize = 64;
    Player player;
    private int dts = 0;

    public MySurfaceView(Context context) {
        super(context);
       // this.player = player;
        holder = getHolder();
        holder.addCallback(this);//"активируем" интерфейс SurfaceHolder.Callback
        paint = new Paint();
        paint.setARGB(100, 0, 0, 0);
        x = 400;
        y = 1100;
        resources = getResources();
        image = BitmapFactory.decodeResource(resources, R.drawable.sprites);
        new ArrayId(this, resources);

        speed = 20;//коэффициент скорости
        character = new Sprite(image, this, x, y);
        t = new TransportBeltItem(1, 0, 0, 128);
        t.move(0, 1500, 1500, 0, 10000);


    }
    public MySurfaceView (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        holder = getHolder();
        holder.addCallback(this);//"активируем" интерфейс SurfaceHolder.Callback
        paint = new Paint();
        paint.setARGB(100, 0, 0, 0);
        x = 400;
        y = 1100;
        resources = getResources();
        image = BitmapFactory.decodeResource(resources, R.drawable.sprites);
        new ArrayId(this, resources);

        speed = 20;//коэффициент скорости
        character = new Sprite(image, this, x, y);
        t = new TransportBeltItem(1, 0, 0, 128);
        t.move(0, 1500, 1500, 0, 10000);
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        if(!isMapGenerate){
            mapWorker = new MapWorker(canvas.getWidth(), canvas.getHeight(), resources);
            isMapGenerate = true;
        }
        //mapWorker.draw(canvas);
       // player.move(1, 1);
        translateX = canvas.getWidth() / 2 +  -player.x * TEXTURE_SIZE;
        translateY = canvas.getHeight() / 2 -  player.y * TEXTURE_SIZE;
       canvas.translate(translateX , translateY);
        character.x = player.x * TEXTURE_SIZE;
        character.y = player.y * TEXTURE_SIZE;
        character.draw(canvas);

        int xPlayerCell = (int)player.x;
        int yPlayerCell = (int)player.y;
        renderingCellX = canvas.getWidth()/ 2 / TEXTURE_SIZE + 1;
        renderingCellY = canvas.getHeight() /2 / TEXTURE_SIZE + 1;
        int startI = xPlayerCell - renderingCellX;
        int topI = xPlayerCell + renderingCellX;
        int startJ = yPlayerCell - renderingCellY;
        int topJ = yPlayerCell + renderingCellY;
        for(int layer = 0; layer < 5; layer++) {
            for (int i = Math.max(0, startI); i < Math.min(map.length, topI); i++) {
                for (int j = Math.max(0, startJ); j < Math.min(map[i].length, topJ); j++) {
                    switch (layer) {
                        case 2:
                            if (map[i][j] != null) {
                                map[i][j].object.draw(canvas, currentFrame);
                            }
                            if(mapHologram[i][j] != null){
                                mapHologram[i][j].draw(canvas);
                            }
                            break;
                        case 3:
                            if(map[i][j] != null){
                                map[i][j].object.drawItems(canvas);
                            }
                            break;
                    }

                }
            }
        }
        currentFrame++;
       // if(currentFrame > 100){
          //  changeDeltaSize(-1);
       // }
       // mapWorker.changeMap();
      //  t.draw(canvas);
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
}
