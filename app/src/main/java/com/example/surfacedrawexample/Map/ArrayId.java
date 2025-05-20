package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.MapArray.mapBackGround;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArrayId {
    static  Class<? extends MapElement> [] arrayId = new Class[10];
    static Bitmap[] image = new Bitmap[10];
    static Point[] scale = new Point[10];
    static Bitmap[] backGroundImage = new Bitmap[1];
    static Map<Integer, Bitmap[]> ore = new HashMap<>();

    static Map<Integer, Integer> MapOre = new HashMap<>();
    static boolean[] isRotate = new boolean[10];
    public static int TEXTURE_SIZE = 64;

    public ArrayId(MySurfaceView mySurfaceView, Resources resources) {
        arrayId[0] = null;
        TransportBelt transportBelt = new TransportBelt(1, 0, mySurfaceView, resources, 0, 0 );
        arrayId[1] = TransportBelt.class;
        image[1] =  transportBelt.icon;
        scale[1] = new Point(1, 1);
        isRotate[1] = true;
        TEXTURE_SIZE = transportBelt.TEXTURE_SIZE;
        Manipulator manipulator = new Manipulator( 2, 0, mySurfaceView, resources, 0, 0 );
        arrayId[2] = Manipulator.class;
        image[2] = manipulator.icon;
        scale[2] = new Point(1, 1);
        isRotate[2] = true;
        Stove stove = new Stove(3, 0, mySurfaceView, resources, 0, 0);
        arrayId[3] = Stove.class;
        image[3] = stove.icon;
        scale[3] = new Point(2, 2);
        isRotate[3] = false;
        image[4] = BitmapFactory.decodeResource(resources, R.drawable.item_ironingot);
        scale[4] = new Point(1, 1);
        isRotate[4] = false;
        image[5] = BitmapFactory.decodeResource(resources, R.drawable.background_ironore_1);
        scale[5] = new Point(1, 1);
        isRotate[5] = false;
        image[7] = BitmapFactory.decodeResource(resources, R.drawable.item_coal);
        scale[7] = new Point(1, 1);
        isRotate[7] = false;
        CrossRoad crossRoad = new CrossRoad(8, 0, mySurfaceView, resources, 0, 0);
        arrayId[8] = CrossRoad.class;
        image[8] = crossRoad.icon;
        scale[8] = new Point(1, 1);
        isRotate[8] = false;
        Boer boer = new Boer(9, 0, mySurfaceView, resources, 0, 0);
        arrayId[9] = Boer.class;
        image[9] = boer.icon;
        scale[9] = new Point(2, 2);
        isRotate[9] = true;
        //generateBackGround(resources);
        backGroundImage[0] = BitmapFactory.decodeResource(resources, R.drawable.background_desert);
        generateOre(resources);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public static Point getScaleId(int id){
        if(id < scale.length && scale[id] != null){
            return new Point(scale[id]);
        }
        return new Point(0, 0);
    }
    public static Class<? extends MapElement> getClassId(int id){
        if(id < arrayId.length){
            return arrayId[id];
        }
        return null;
    }
    public static Bitmap getBackGroundId(int id){
        if(id >= 0 && id < backGroundImage.length){
            return backGroundImage[id];
        }
        return null;
    }
    public static Bitmap getImageId(int id){
        if(id >= 0 && id < arrayId.length){
            return image[id];
        }
        return null;
    }
    public static Bitmap getStaticImage(int id, int rotate){
        if(!isRotate[id])
            rotate = 0;
        return RotateBitmap(getImageId(id), rotate * 90);
    }
    public static void generateOre(Resources resources){
        ore.put(5, new Bitmap[]{
                BitmapFactory.decodeResource(resources, R.drawable.background_ironore_0),
                BitmapFactory.decodeResource(resources, R.drawable.background_ironore_1)
        });
        ore.put(7, new Bitmap[]{
                BitmapFactory.decodeResource(resources, R.drawable.background_coalore_0),
                BitmapFactory.decodeResource(resources, R.drawable.background_coalore_1)
        });
        int random = new Random().nextInt(0, ore.get(5).length) ;
        MapOre.put(5, random);
        MapOre.put(7, random);
    }
    public static Bitmap getOreId(int id){
      //  int random = new Random().nextInt(0, ore.get(id).length) ;
        return ore.get(id)[MapOre.get(id)];
    }
}
