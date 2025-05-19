package com.example.surfacedrawexample.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.surfacedrawexample.MySurfaceView;

public class MapArray {
    public static MapElement[][]  map = new MapElement[10][10];
    public static Hologram[][] mapHologram = new Hologram[10][10];
    TransportBeltItem t;
    public MapArray(Resources resources, MySurfaceView mySurfaceView){
        map[5][5] = new TransportBelt(1, 0, mySurfaceView, resources, 5 , 5);
        map[6][5] = new TransportBelt(1, 0, mySurfaceView, resources, 6 , 5);
        map[4][5] = new TransportBelt(1, 0, mySurfaceView, resources, 4 , 5);
        map[3][5] = new TransportBelt(1, 0, mySurfaceView, resources, 3 , 5);
        map[7][5] = new TransportBelt(1, 3, mySurfaceView, resources, 7 , 5);
        map[7][6] = new TransportBelt(1, 3, mySurfaceView, resources, 7, 6);
        map[7][7] = new TransportBelt(1, 7, mySurfaceView, resources, 7, 7);
        map[6][7] = new TransportBelt(1, 1, mySurfaceView, resources, 6, 7);
        map[5][7] = new TransportBelt(1, 1, mySurfaceView, resources, 5, 7);
        map[4][7] = new TransportBelt(1, 1, mySurfaceView, resources, 4, 7);
        map[3][7] = new TransportBelt(1, 4, mySurfaceView, resources, 3, 7);
        map[3][6] = new TransportBelt(1, 2, mySurfaceView, resources, 3, 6);
        map[5][6] = new TransportBelt(1, 2, mySurfaceView, resources, 5 , 6);
        map[5][4] = new Manipulator(2, 2, mySurfaceView, resources, 5 , 4);
        map[5][3] = new TransportBelt(1, 0, mySurfaceView, resources, 5 , 3);
        map[6][3] = new TransportBelt(1, 0, mySurfaceView, resources, 6 , 3);
        map[7][3] = new TransportBelt(1, 11, mySurfaceView, resources, 7 , 3);
        map[7][4] = new TransportBelt(1, 3, mySurfaceView, resources, 7 , 4);
    }
    public static MapElement getEl(int x, int y){
        if(x >= 0 && y >= 0 && map.length > x && map[0].length > y){
            return map[x][y];
        }
        return null;
    }
    public static  void updateStats(){
       for(int i = 0; i < map.length; i++){
           for(int j = 0; j < map[i].length; j++){
               if(map[i][j] != null){
                   map[i][j].object.updateState();
               }
           }
       }
    }
}
