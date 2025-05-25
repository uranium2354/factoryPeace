package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.backGroundImage;
import static com.example.surfacedrawexample.Map.ArrayId.getBackGroundId;
import static com.example.surfacedrawexample.Map.ArrayId.getClassId;
import static com.example.surfacedrawexample.Map.ArrayId.ore;

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
    public static Item[][] mapOre = new Item[100][100];
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
        mapOre[8][8] = new Item(5);
        mapOre[8][7] =  new Item(5);
        mapOre[9][8] =  new Item(5);
        mapOre[9][9] =  new Item(5);
        mapOre[10][8] =  new Item(5);

        mapOre[10][15] =  new Item(7);
        mapOre[10][16] =  new Item(7);
        mapOre[11][15] =  new Item(7);
        mapOre[9][15] =  new Item(7);
        mapOre[10][17] =  new Item(7);
        mapOre[12][17] =  new Item(7);

        mapOre[20][30] =  new Item(14);
        mapOre[21][30] =  new Item(14);
        mapOre[22][31] =  new Item(14);
        mapOre[20][32] =  new Item(14);
        mapOre[21][31] =  new Item(14);
        mapOre[23][30] =  new Item(14);

        mapOre[40][10] =  new Item(16);
        mapOre[40][11] =  new Item(16);
        mapOre[40][15] =  new Item(16);
        mapOre[39][60] =  new Item(16);
        mapOre[39][11] =  new Item(16);
        mapOre[40][12] =  new Item(16);
        for(int i = 0; i < mapOre.length; i++){
            for(int j = 0 ; j < mapOre[i].length; j++){
                if(mapOre[i][j] != null){
                    int random = new Random().nextInt(0, ore.get(mapOre[i][j].id).length) ;
                    mapOre[i][j].num = random;
                }
            }
        }
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
