package com.example.surfacedrawexample.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class ArrayId {
    static  Class<? extends MapElement> [] arrayId = new Class[5];
    static Bitmap[] image = new Bitmap[5];
   public static int TEXTURE_SIZE = 64;

    public ArrayId(MySurfaceView mySurfaceView, Resources resources) {
        arrayId[0] = null;
        TransportBelt transportBelt = new TransportBelt(1, 0, mySurfaceView, resources, 0, 0 );
        arrayId[1] = TransportBelt.class;
        image[1] =  transportBelt.icon;
        TEXTURE_SIZE = transportBelt.TEXTURE_SIZE;
        Manipulator manipulator = new Manipulator( 2, 0, mySurfaceView, resources, 0, 0 );
        arrayId[2] = Manipulator.class;
        image[2] = manipulator.icon;
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Class<? extends MapElement> getClassId(int id){
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
    public static Bitmap getStaticImage(int id, int rotate){
        return RotateBitmap(getImageId(id), rotate * 90);
    }
}
