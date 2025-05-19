package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.ArrayId.TEXTURE_SIZE;
import static com.example.surfacedrawexample.Map.ArrayId.getClassId;
import static com.example.surfacedrawexample.Map.ArrayId.getStaticImage;
import static com.example.surfacedrawexample.Map.MapArray.map;
import static com.example.surfacedrawexample.Map.MapArray.mapHologram;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.TextView;

import com.example.surfacedrawexample.Map.Hologram;
import com.example.surfacedrawexample.Map.MapArray;
import com.example.surfacedrawexample.Map.MapElement;
import com.example.surfacedrawexample.Map.TransportBelt;
import com.example.surfacedrawexample.Map.TransportBeltItem;

import java.util.ArrayList;
import java.util.List;

public class Player {
    float x, y;
    TransportBeltItem selectedItem;
    MapElement selectedMapPlace;
    int SIZE_X = 128, SIZE_Y = 128;
    int direction;
    Bitmap texture;
    int IMAGE_COLUMN = 1;
    int IMAGE_ROWS = 1;
    float widthFrame, heightFrame;
    int selectedMapPlaceId = 0;//TODO выбраный объект для сроительства
    Paint paint;
    String tag;
    public int rotationPlace = 0;
    TextView textView;
    boolean isPlace = false; //TODO включён ли режим строительства
    boolean isDestroy = false;
    List<Hologram> orderHologram;
    List<Point> orderDestroy;
    MySurfaceView mySurfaceView;
    Resources resources;
    long lastUpdateTime;
    int widthSelectDestroy;
    int heightSelectDestroy;
    long timePlace = 500; //TODO время на размещение одного объекта
    Bitmap selectDestroy;
    Point startSelect;
    Point stopSelect;
    public Player(int direction, Resources resources, float x, float y, TextView textView, MySurfaceView mySurfaceView){
        texture =  BitmapFactory.decodeResource(resources, R.drawable.entities_player);
        this.resources = resources;
        this.x = x;
        this.y = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        paint.setARGB(100, 0, 0, 0);
        this.direction = direction;
        tag = "Player";
        this.textView = textView;
        this.mySurfaceView = mySurfaceView;
        orderHologram = new ArrayList<>();
        orderDestroy = new ArrayList<>();
        lastUpdateTime =  System.currentTimeMillis();
        selectDestroy =  BitmapFactory.decodeResource(resources, R.drawable.guimap_select);
        widthSelectDestroy = selectDestroy.getWidth();
        heightSelectDestroy = selectDestroy.getHeight();
        startSelect = new Point(0, 0);
        stopSelect = new Point(0, 0);
    }
    public void moveDelta(int dx, int dy){
        x += dx;
        y += dy;
    }
    public void moveCord(int dx, int dy){
        if(!isDestroy){
            x += (float) dx / TEXTURE_SIZE;
            y += (float) dy / TEXTURE_SIZE;
        }
    }
    public void setSelectedMapPlace(int id){
         selectedMapPlaceId = id;

    }
    public void rotationPlace(){
        rotationPlace++;
        rotationPlace %= 4;
    }
    public void setIsPlace(){
        isPlace = !isPlace;
        if(isPlace){
            isDestroy = false;
        }
    }
    public void setIsDestroy(){
        isDestroy = !isDestroy;
        if(isDestroy){
            isPlace = false;
        }
    }

    public void setStartSelectDestroy(Point start) {
        if(isDestroy){
            startSelect.x = start.x;
            startSelect.y = start.y;
            stopSelect = start;
            //textView.setText("xaxaxaaxaxa");
        }
    }
    public void setDeltaSelectDestroy(Point delta){
        if(isDestroy){
            stopSelect.x += delta.x;
            stopSelect.y += delta.y;
            //stopSelect.offset(delta.x, delta.y);

        }
    }

    public void draw(Canvas canvas){
        Rect src = new Rect(0, 0, widthSelectDestroy,heightSelectDestroy);
        Rect dst = new Rect(Math.min(startSelect.x, stopSelect.x),
                Math.min(startSelect.y, stopSelect.y),
                Math.max(startSelect.x, stopSelect.x),
                Math.max(startSelect.y, stopSelect.y));
        canvas.drawBitmap(selectDestroy ,src, dst , paint);
       // textView.setText(Integer.toString( startSelect.x));
    }

    int[] dR = new int[]{0, 3, 1, 2};
    public void updateState(){
        if(isPlace){
            long delta = System.currentTimeMillis() - lastUpdateTime;
            if(!orderHologram.isEmpty() && delta > timePlace){
                lastUpdateTime = System.currentTimeMillis();
                Hologram hologramPlace = orderHologram.get(0);
                int posMapX = hologramPlace.x;
                int posMapY = hologramPlace.y;
                if(map[posMapX][posMapY] == null){
                    int rotationMap = hologramPlace.rotation;
                    int idMap = hologramPlace.id;
                    map[posMapX][posMapY] = new MapElement(idMap, dR[rotationMap], posMapX, posMapY,false, getClassId(idMap)).object;
                    map[posMapX][posMapY].changeSize(TEXTURE_SIZE);
                    mapHologram[posMapX][posMapY] = null;
                }
                orderHologram.remove(0);
            }
        }
        if(isDestroy){
            long delta = System.currentTimeMillis() - lastUpdateTime;
            if(!orderDestroy.isEmpty() && delta > timePlace){

                lastUpdateTime = System.currentTimeMillis();
                int posMapX = orderDestroy.get(0).x;
                int posMapY = orderDestroy.get(0).y;
              //  textView.setText(Integer.toString(posMapX));
                if(map[posMapX][posMapY] != null) {
                    map[posMapX][posMapY] = null;
                }
                orderDestroy.remove(0);
            }
        }
    }
    public void select(float posX, float posY){
        textView.setText(Boolean.toString(isDestroy));
        if(!isDestroy){

            int posMapX = (int)posX / TEXTURE_SIZE;
            int posMapY = (int)posY / TEXTURE_SIZE;

            if(posMapY < 0 || posMapY >= mapHologram.length)
                return;
            if(posMapX < 0 || posMapX >= mapHologram.length)
                return;
            if(mapHologram[posMapX][posMapY] != null){
                orderHologram.remove(mapHologram[posMapX][posMapY]);
                mapHologram[posMapX][posMapY] = null;
                return;
            }
            if(map[posMapX][posMapY] != null)
                return;

            if(selectedMapPlaceId != 0){
                mapHologram[posMapX][posMapY] = new Hologram(getStaticImage(selectedMapPlaceId, rotationPlace), selectedMapPlaceId, rotationPlace, posMapX, posMapY, orderHologram.size());
                orderHologram.add( mapHologram[posMapX][posMapY]) ;
                 textView.setText(Integer.toString(posMapX));
            }
        }
        if(isDestroy){
            int posMapX = (int)posX / TEXTURE_SIZE;
            int posMapY = (int)posY / TEXTURE_SIZE;
         //   textView.setText(Integer.toString(posMapX));
            if(posMapY < 0 || posMapY >= map.length)
                return;
            if(posMapX < 0 || posMapX >= map.length)
                return;
            if(map[posMapX][posMapY] == null){
                return;
            }

            orderDestroy.add(new Point(posMapX, posMapY));
        }
    }
    private Point includedMap(int x, int y){
        x = Math.max(x, 0);
        x = Math.min(x, map.length - 1);
        y = Math.max(y, 0);
        y = Math.min(y, map[0].length - 1);
        return new Point(x, y);
    }
    public void regionDestroy( int x2, int y2){
       int  x1 = startSelect.x;
       int  y1 =  startSelect.y;
        startSelect.x = stopSelect.x;
        startSelect.y = stopSelect.y;
        x1 /= TEXTURE_SIZE;
        y1 /= TEXTURE_SIZE;
        x2 /= TEXTURE_SIZE;
        y2 /= TEXTURE_SIZE;
        //textView.setText(Integer.toString(x1));

        if(isDestroy){
            x1 = includedMap(x1, y1).x;
            y1 = includedMap(x1, y1).y;
            x2 = includedMap(x2, y2).x;
            y2 = includedMap(x2, y2).y;
            for(int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++){
                for(int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++){
                    select(i  *TEXTURE_SIZE, j*TEXTURE_SIZE);
                }
            }
        }
    }
}
