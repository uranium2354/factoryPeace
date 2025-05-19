package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.MapArray.mySurfaceViewStatic;
import static com.example.surfacedrawexample.Map.MapArray.resourcesStatic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.surfacedrawexample.MySurfaceView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MapElement {

    int id;
    public int TEXTURE_SIZE= 128;
    int rotation = 0;
    String tag = "none";
    int hp;
    int startHp;
    public MapElement object;//TODO обект который размещён на карте например TransportBelt
    Paint paint;
    Bitmap icon;
    public MapElement(int id, int rotation,int x, int y, boolean isObjectSetState, Class<? extends MapElement> clazz){
       // this.object = object;
        this.id = id;
        this.rotation = rotation;
        if(isObjectSetState == false){
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor(int.class,int.class, MySurfaceView.class, Resources.class, int.class, int.class);
                Object ins = constructor.newInstance(id, rotation, mySurfaceViewStatic, resourcesStatic, x, y);
                object = (MapElement)ins;

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } ;
            //object.constructor(id, rotation, mySurfaceViewStatic, resourcesStatic, x, y);
        }
    }
    void rotate(){
        rotation++;
        rotation %= 4;
    }
    void damage(int a){}
    void hill(int a){}
    public void setTransparency(int a){
        paint.setARGB(a, 0, 0,0 );
    }
    public void constructor(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){}
    boolean pullItem(TransportBeltItem id, boolean isChange){return false;}
    TransportBeltItem getItem(boolean isChange){return null;}
    public void draw(Canvas canvas, long currentFrame){}
    public void drawItems(Canvas canvas){}
    public void changeSize(int ts){};
    synchronized public void updateState(){}
}
