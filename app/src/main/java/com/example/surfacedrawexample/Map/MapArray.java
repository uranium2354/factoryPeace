package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.backGroundImage;
import static com.example.surfacedrawexample.Map.ArrayId.getClassId;
import static com.example.surfacedrawexample.Map.ArrayId.ore;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;


import com.example.surfacedrawexample.Map.Element.MapElement;
import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.Player;

import java.util.Random;

public class MapArray {
    int sizeMap = 100;
    public static MapElement[][]  map ;
    public static Hologram[][] mapHologram ;
    public static int[][] mapBackGround ;
    public static Item[][] mapOre;
    public static MySurfaceView mySurfaceViewStatic;
    public static Resources resourcesStatic;
    public static int speedTransportBelt = 200;
    public static long lastUpdateTime = 0;
    static long frame = 0;
   // MapElement c = TransportBelt;
    static Player playerStatic;
    static int TEXTURE_SIZE = 128;
    static Paint paint;
    public MapArray(Resources resources, MySurfaceView mySurfaceView, Player player, int sizeMap){

        this.playerStatic = player;
        this.sizeMap = sizeMap;
        setSizeMap(sizeMap);
        mySurfaceViewStatic  = mySurfaceView;
        resourcesStatic = resources;
        paint = new Paint();
        generateBackGround(resources);
    }
    private void setSizeMap(int sizeMap){
        map = new MapElement[sizeMap][sizeMap];
        mapHologram = new Hologram[sizeMap][sizeMap];
        mapBackGround = new int[sizeMap][sizeMap];
        mapOre = new Item[sizeMap][sizeMap];

    }
    public void generateOre(){
        addOreCluster(5, 3, 10, 5, 60);
        addOreCluster(7, 0, 50, 0, 23);
        addOreCluster(14, 10, 80, 10, 99);
        addOreCluster(16, 5, 80, 5, 50);
        generateSpriteOre();
    }
    public void generateOre(int id, Point[] points){
        for(Point pos : points){
            mapOre[pos.x][pos.y] = new Item(id);
        }
    }
    public void generateSpriteOre(){
        for(int i = 0; i < mapOre.length; i++){
            for(int j = 0 ; j < mapOre[i].length; j++){
                if(mapOre[i][j] != null){
                    int random = 0;
                    if (android.os.Build.VERSION.SDK_INT >= 35) {
                        random = new Random().nextInt(0, ore.get(mapOre[i][j].id).length);
                    }
                    else {
                        random = (int)(Math.random() * (ore.get(mapOre[i][j].id).length ));
                    }
                    mapOre[i][j].num = random;
                }
            }
        }
    }
    private void addOreCluster(int oreType, int startX, int endX, int startY, int endY) {
       Random random = new Random(oreType * 4546);

        for (int x = startX; x <= endX; x++) {

            for (int y = startY; y <= endY; y++) {
                float dist = distance(x, y, endX / 2, endY / 2);
                if(random.nextFloat() / dist  > 0.1 || dist < 7){
                    mapOre[x][y] =  new Item(oreType);
                }
            }
        }
    }
    float distance(int x1, int y1, int x2, int y2){
        return (float)Math.sqrt((x1- x2) * (x1- x2) + (y1 - y2) * (y1 - y2));
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
        try {
            playerStatic.updateState();
            frame++;
            boolean isUpdateTransportBelt = false;
            if(System.currentTimeMillis() - lastUpdateTime >= speedTransportBelt){
                isUpdateTransportBelt = true;
                lastUpdateTime = System.currentTimeMillis();
            }
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[i].length; j++){
                    if(map[i][j] == null){
                        continue;
                    }
                    if(map[i][j].id == 1 && !isUpdateTransportBelt){
                        continue;
                    }
                    map[i][j].object.updateState(frame);

                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }
}
