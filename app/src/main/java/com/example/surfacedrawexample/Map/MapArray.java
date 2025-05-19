package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.getClassId;

import android.content.res.Resources;
import android.graphics.Bitmap;


import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.Player;

public class MapArray {
    public static MapElement[][]  map = new MapElement[10][10];
    public static Hologram[][] mapHologram = new Hologram[10][10];
    static MySurfaceView mySurfaceViewStatic;
    static Resources resourcesStatic;
   // MapElement c = TransportBelt;
    static Player playerStatic;
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
    }
    public static MapElement getEl(int x, int y){//TODO возращает елемент карты
        if(x >= 0 && y >= 0 && map.length > x && map[0].length > y){
            return map[x][y];
        }
        return null;
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
