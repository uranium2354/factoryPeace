package com.example.surfacedrawexample.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class ArrayId {
    static Object [] arrayId = new Object[5];
    static Bitmap[] image = new Bitmap[5];
   public static int TEXTURE_SIZE = 64;

    public ArrayId(MySurfaceView mySurfaceView, Resources resources) {
        arrayId[0] = null;
        TransportBelt transportBelt = new TransportBelt(1, 0, mySurfaceView, resources, 0, 0 );
        arrayId[1] = transportBelt;
        image[1] =   BitmapFactory.decodeResource(resources, R.drawable.cathead);
        TEXTURE_SIZE = transportBelt.TEXTURE_SIZE;
        Manipulator manipulator = new Manipulator( 2, 0, mySurfaceView, resources, 0, 0 );
        arrayId[2] = manipulator;
        image[2] = manipulator.icon;
    }


    public static Object getClassId(int id){
        if(id < arrayId.length){
            return arrayId[id];
        }
        return null;
    }
    public static Bitmap getImageId(int id){
        if(id >= 0 && id < arrayId.length){
            return image[id];
        }
        return null;
    }
}
