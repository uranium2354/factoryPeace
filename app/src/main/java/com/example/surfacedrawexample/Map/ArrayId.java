package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.MapArray.mapBackGround;
import static com.example.surfacedrawexample.interfaces.Storage.setTextButtonId;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArrayId {
    private static final int sizeId = 47;
    static  Class<? extends MapElement> [] arrayId = new Class[sizeId];
    static Bitmap[] image = new Bitmap[sizeId];
    static Bitmap[] texture = new Bitmap[sizeId];
    static Point[] scale = new Point[sizeId];
    static Bitmap[] backGroundImage = new Bitmap[3];
    public static Map<Integer, Bitmap[]> ore = new HashMap<>();
    static Drawable[] storageIcon = new Drawable[sizeId];
    static int[] numItem = new int[sizeId];

    static Map<Integer, Integer> MapOre = new HashMap<>();
    static boolean[] isRotate = new boolean[sizeId];
    public static int TEXTURE_SIZE = 64;

    public ArrayId(MySurfaceView mySurfaceView, Resources resources) {
        arrayId[0] = null;
        texture[1] = BitmapFactory.decodeResource(resources, R.drawable.map_transportbelt);
        TransportBelt transportBelt = new TransportBelt(1, 0, mySurfaceView, resources, 0, 0);
        arrayId[1] = TransportBelt.class;
        image[1] =  transportBelt.icon;
        scale[1] = new Point(1, 1);
        isRotate[1] = true;
        storageIcon[1] = resources.getDrawable(R.drawable.storage_transportbelt);


        TEXTURE_SIZE = transportBelt.TEXTURE_SIZE;

        texture[2] = BitmapFactory.decodeResource(resources, R.drawable.map_manipulator);
        Manipulator manipulator = new Manipulator( 2, 0, mySurfaceView, resources, 0, 0 );
        arrayId[2] = Manipulator.class;
        image[2] = manipulator.icon;
        scale[2] = new Point(1, 1);
        isRotate[2] = true;
        storageIcon[2] = resources.getDrawable(R.drawable.storage_manipulator);

        texture[3] = BitmapFactory.decodeResource(resources, R.drawable.map_stove);
        Stove stove = new Stove(3, 0, mySurfaceView, resources, 0, 0);
        arrayId[3] = Stove.class;
        image[3] = stove.icon;
        scale[3] = new Point(2, 2);
        isRotate[3] = false;
        storageIcon[3] = resources.getDrawable(R.drawable.storage_stove);


        image[4] = BitmapFactory.decodeResource(resources, R.drawable.item_ironingot);
        scale[4] = new Point(1, 1);
        isRotate[4] = false;
        storageIcon[4] = resources.getDrawable(R.drawable.item_ironingot);

        image[5] = BitmapFactory.decodeResource(resources, R.drawable.item_ironore);
        scale[5] = new Point(1, 1);
        isRotate[5] = false;
        storageIcon[5] = resources.getDrawable(R.drawable.item_ironore);

        image[7] = BitmapFactory.decodeResource(resources, R.drawable.item_coal);
        scale[7] = new Point(1, 1);
        isRotate[7] = false;
        storageIcon[7] = resources.getDrawable(R.drawable.item_coal);

        texture[8] = BitmapFactory.decodeResource(resources, R.drawable.map_crossroad);
        CrossRoad crossRoad = new CrossRoad(8, 0, mySurfaceView, resources, 0, 0);
        arrayId[8] = CrossRoad.class;
        image[8] = crossRoad.icon;
        scale[8] = new Point(1, 1);
        isRotate[8] = false;
        storageIcon[8] = resources.getDrawable(R.drawable.storage_crossroad);

        texture[9] = BitmapFactory.decodeResource(resources, R.drawable.map_boer);
        Boer boer = new Boer(9, 0, mySurfaceView, resources, 0, 0);
        arrayId[9] = Boer.class;
        image[9] = boer.icon;
        scale[9] = new Point(2, 2);
        isRotate[9] = true;
        storageIcon[9] = resources.getDrawable(R.drawable.storage_boer);

        texture[10] = BitmapFactory.decodeResource(resources, R.drawable.map_core);
        Core core = new Core(10, 0, mySurfaceView, resources, 0, 0);
        arrayId[10] = Core.class;
        image[10] = core.icon;
        scale[10] = new Point(3, 3);
        isRotate[10] = false;
        storageIcon[10] = resources.getDrawable(R.drawable.map_core);

        texture[11] = BitmapFactory.decodeResource(resources, R.drawable.map_collector);
        Collector collector = new Collector(11, 0, mySurfaceView, resources, 0, 0);
        arrayId[11] = Collector.class;
        image[11] = collector.icon;
        scale[11] = new Point(3, 3);
        isRotate[11] = false;
        storageIcon[11] = resources.getDrawable(R.drawable.storage_collector);


        storageIcon[12] =  resources.getDrawable(R.drawable.item_ironrod);
        image[12] =  BitmapFactory.decodeResource(resources, R.drawable.item_ironrod);

        storageIcon[13] =  resources.getDrawable(R.drawable.item_gear);
        image[13] =  BitmapFactory.decodeResource(resources, R.drawable.item_gear);

        storageIcon[14] =  resources.getDrawable(R.drawable.background_woodore_0);
        image[14] =  BitmapFactory.decodeResource(resources, R.drawable.background_woodore_0);

        storageIcon[15] =  resources.getDrawable(R.drawable.item_rubber);
        image[15] =  BitmapFactory.decodeResource(resources, R.drawable.item_rubber);

        storageIcon[16] =  resources.getDrawable(R.drawable.item_copperore);
        image[16] =  BitmapFactory.decodeResource(resources, R.drawable.item_copperore);

        storageIcon[17] =  resources.getDrawable(R.drawable.item_copperingot);
        image[17] =  BitmapFactory.decodeResource(resources, R.drawable.item_copperingot);

        storageIcon[18] =  resources.getDrawable(R.drawable.item_wire);
        image[18] =  BitmapFactory.decodeResource(resources, R.drawable.item_wire);

        storageIcon[19] =  resources.getDrawable(R.drawable.item_chip);
        image[19] =  BitmapFactory.decodeResource(resources, R.drawable.item_chip);

        storageIcon[20] =  resources.getDrawable(R.drawable.item_magnet);
        image[20] =  BitmapFactory.decodeResource(resources, R.drawable.item_magnet);

        storageIcon[21] =  resources.getDrawable(R.drawable.item_engine);
        image[21] =  BitmapFactory.decodeResource(resources, R.drawable.item_engine);

        storageIcon[22] =  resources.getDrawable(R.drawable.item_board);
        image[22] =  BitmapFactory.decodeResource(resources, R.drawable.item_board);

        //generateBackGround(resources);
        backGroundImage[0] = BitmapFactory.decodeResource(resources, R.drawable.background_desert);
        backGroundImage[1] = BitmapFactory.decodeResource(resources, R.drawable.background_desert_1);
        backGroundImage[2] = BitmapFactory.decodeResource(resources, R.drawable.background_desert_2);
        generateOre(resources);
    }
    public static Bitmap getTextureId(int id){
        if(texture[id] != null){
            return texture[id];
        }
        return null;
    }
    public static void updateButtons(){
        for(int  i = 0; i < sizeId; i++){
            numItem[i] = 200;
            String s = "";
            if(numItem[i] > 999){
                s = Integer.toString((int)(numItem[i] / 1000));
                s +='k';
            }
            else
                s = Integer.toString((int)(numItem[i]));
            setTextButtonId(i, s);
        }
    }
    public static int getNumItemId(int id){
        if(id < arrayId.length){
            return numItem[id];
        }
        return 0;
    }
    public static void addNumItemId(int id){
        if(id < arrayId.length){
           numItem[id]++;
            String s = "";
            if(numItem[id] > 999){
                s = Integer.toString((int)(numItem[id] / 1000));
                s +='k';
            }
            else
                s =Integer.toString((int)(numItem[id]));

            setTextButtonId(id, s);
        }
    }
    public static void takeNumItemId(int id){
        if(id < arrayId.length){
            numItem[id]--;
            String s = "";
            if(numItem[id] > 999){
                s = Integer.toString((int)(numItem[id] / 1000));
                s +='k';
            }
            else
                s =Integer.toString((int)(numItem[id]));
            setTextButtonId(id, s);
        }
    }
    public static Drawable getDrawableId(int id){
        if(id < arrayId.length){
            return storageIcon[id];
        }
        return null;
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
        ore.put(14, new Bitmap[]{
                BitmapFactory.decodeResource(resources, R.drawable.background_woodore_0)
        });
        ore.put(16, new Bitmap[]{
                BitmapFactory.decodeResource(resources, R.drawable.background_copperore_0),
                BitmapFactory.decodeResource(resources, R.drawable.background_copperore_1)
        });
        int random = new Random().nextInt(0, ore.get(5).length) ;
        MapOre.put(5, random);
        MapOre.put(7, random);
        MapOre.put(14, 0);
        MapOre.put(16, random);
    }
    public static Bitmap getOreId(int id, int num){
      //  int random = new Random().nextInt(0, ore.get(id).length) ;
        return ore.get(id)[num];
    }
}
