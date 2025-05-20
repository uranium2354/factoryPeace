package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.backGroundImage;
import static com.example.surfacedrawexample.Map.ArrayId.getBackGroundId;
import static com.example.surfacedrawexample.Map.ArrayId.getClassId;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.Player;

import java.util.Random;

public class MapArray {
    public static MapElement[][]  map = new MapElement[100][100];
    public static Hologram[][] mapHologram = new Hologram[100][100];
    public static int[][] mapBackGround = new int[100][100];
    public static int[][] mapOre = new int[100][100];
    static MySurfaceView mySurfaceViewStatic;
    static Resources resourcesStatic;
   // MapElement c = TransportBelt;
    static Player playerStatic;
    static int TEXTURE_SIZE = 128;
    static Paint paint;
    public MapArray(Resources resources, MySurfaceView mySurfaceView, Player player){
        this.playerStatic = player;
        mySurfaceViewStatic  = mySurfaceView;
        resourcesStatic = resources;
        map[5][5] = new MapElement(1, 0, 5, 5, false, getClassId(1)).object;
        map[6][5] = new MapElement(1, 0, 6, 5, false, getClassId(1)).object;
        map[4][5] = new MapElement(1, 0, 4, 5, false, getClassId(1)).object;
        map[3][5] = new MapElement(1, 0, 3, 5, false, getClassId(1)).object;
        map[7][5] = new MapElement(1, 3, 7, 5, false, getClassId(1)).object;
        map[7][6] = new MapElement(1, 3, 7, 6, false, getClassId(1)).object;
        map[7][7] = new MapElement(1, 7, 7, 7, false, getClassId(1)).object;
        map[6][7] = new MapElement(1, 1, 6, 7, false, getClassId(1)).object;
        map[5][7] = new MapElement(1, 1, 5, 7, false, getClassId(1)).object;
        map[4][7] = new MapElement(1, 1, 4, 7, false, getClassId(1)).object;
        map[3][7] = new MapElement(1, 4, 3, 7, false, getClassId(1)).object;
        map[3][6] = new MapElement(1, 2, 3, 6, false, getClassId(1)).object;
        map[5][6] = new MapElement(1, 2, 5, 6, false, getClassId(1)).object;
        map[5][4] = new MapElement(2, 2, 5, 4, false, getClassId(2)).object;
        map[5][3] = new MapElement(1, 0, 5, 3, false, getClassId(1)).object;
        map[6][3] = new MapElement(1, 0, 6, 3, false, getClassId(1)).object;
        map[7][3] = new MapElement(1, 11, 7, 3, false, getClassId(1)).object;
        map[7][4] = new MapElement(1, 3, 7, 4, false, getClassId(1)).object;
        mapOre[8][8] = 5;
        mapOre[10][15] = 7;
        paint = new Paint();
        generateBackGround(resources);
    }
    public static MapElement getEl(int x, int y){//TODO возращает елемент карты
        if(x >= 0 && y >= 0 && map.length > x && map[0].length > y){
            return map[x][y];
        }
        return null;
    }
    private void generateBackGround(Resources resources){
        for(int i = 0; i < mapBackGround.length; i++){
            for(int j = 0; j < mapBackGround[0].length; j++){
                mapBackGround[i][j] = new Random().nextInt(backGroundImage.length);
            }
        }
    }
    public static  void updateStats(){//TODO обнавление объектов
        playerStatic.updateState();
       for(int i = 0; i < map.length; i++){
           for(int j = 0; j < map[i].length; j++){
               if(map[i][j] != null){
                   map[i][j].object.updateState();
               }
           }
       }
    }
}
