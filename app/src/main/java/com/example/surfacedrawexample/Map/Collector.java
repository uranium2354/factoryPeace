package com.example.surfacedrawexample.Map;

import static com.example.surfacedrawexample.Map.ArrayId.getImageId;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.Crafts.craftsItem;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.surfacedrawexample.Map.Crafts;
import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;

public class Collector extends MapElement{
    Resources resources;
    Bitmap texture ;
    int direction;
    float widthFrame, heightFrame;

    int IMAGE_COLUMN = 15;
    int IMAGE_ROWS = 1;
    boolean isCrafting = false;
    int currentFrame = 0;
    int speed = 1;
    int maxNum = 20;
    int powerFuel = 5;
    Craft craft;
    long lastUpdateTime = 0;
    Item[] ingredients;
    Item product;
    Paint paintIcon;
    int craftId = -1;
    public Collector(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){

        super(id, direction,x, y, true,  Collector.class);
        object = this;
        constructor(id, direction, mySurfaceView, resources, x, y);
    }
    public void constructor(int id, int direction, MySurfaceView mySurfaceView, Resources resources, int x, int y){
        texture =  getTextureId(id);
        ArrayX = x;
        ArrayY = y;
        widthFrame = this.texture.getWidth()/(float)IMAGE_COLUMN;
        heightFrame = this.texture.getHeight()/(float)IMAGE_ROWS;
        paint = new Paint();
        this.direction = direction;
        icon = Bitmap.createBitmap(texture, 0, 0, (int) widthFrame, (int)heightFrame);
        lastUpdateTime = System.currentTimeMillis();
        changeSize(TEXTURE_SIZE);
        tag = "crafter";
        mapScaleX = 2;
        mapScaleY = 2;
        paintIcon = new Paint();
        paintIcon.setARGB(150, 0, 0, 0);
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
      // setCraftId(1);
    }
    public void setCraft(int craftId){
        this.craftId = craftId;
        craft = craftsItem[craftId];
        ingredients = new Item[craft.ingredients.length];
        for(int i = 0; i < craft.ingredients.length; i++){
            ingredients[i] = new Item(craft.ingredients[i].id, 0);
        }
        product = new Item(craft.product.id, 0);
        lastUpdateTime = System.currentTimeMillis();
    }

    //private void calculete
    @Override
    public void drawUpItem(Canvas canvas, long currentF){
        currentF %= IMAGE_COLUMN;
        int k = 0;
        if(!isCrafting){
            currentF = 0;
            k = 0;
        }
        Rect src = new Rect((int)(currentF*widthFrame + 1), (int)(k *  heightFrame),
                (int)((currentF+1)*widthFrame), (int)((k + 1) * heightFrame ));
        Rect dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                ( ArrayX + 3) * TEXTURE_SIZE, (ArrayY + 3)* TEXTURE_SIZE);
        canvas.drawBitmap(texture,src, dst , paint);
        if(craft != null){
            Bitmap image = getImageId(craft.product.id);
             src = new Rect((int)(0), (int)(0),
                    image.getWidth(), image.getHeight());
             dst = new Rect((int)( ArrayX + 1) * TEXTURE_SIZE - 1, (int)( ArrayY + 2)* TEXTURE_SIZE - 1,
                    ( ArrayX + 2) * TEXTURE_SIZE, (ArrayY + 3)* TEXTURE_SIZE);
            canvas.drawBitmap(image,src, dst , paintIcon);
        }
    }
    @Override
    synchronized public void updateState(){
        if(craft != null && System.currentTimeMillis() - lastUpdateTime >= speed * craft.time ){
            boolean f = true;
            for(int i = 0; i < craft.ingredients.length; i++){
                if(ingredients[i].num < craft.ingredients[i].num)
                    f = false;
            }
            if(product.num + craft.product.num >= maxNum)
                f = false;

            if(f){
                lastUpdateTime = System.currentTimeMillis();
                for(int i = 0; i < craft.ingredients.length; i++){
                    ingredients[i].num -= craft.ingredients[i].num;
                }
                product.num += craft.product.num;
                isCrafting = true;
            }
            else{
                isCrafting = false;
            }
        }
    }

    @Override
    public  boolean pullItem(TransportBeltItem it,boolean isChange, int x, int y){
        if(craft == null){
            return false;
        }
        for(Item el : ingredients){
            if(el.id == it.id && el.num < maxNum){
                if(isChange)
                    el.num++;
                return true;
            }
        }
        //isCrafting = true;
        return false;
    }
    @Override
    TransportBeltItem getItem(boolean isChange, int PosX, int PosY){
        if(craft == null){
            return null;
        }
        if(product.num > 0){
            if(isChange){
                product.num--;
            }
            return new TransportBeltItem(product.id, (ArrayX + 1) * TEXTURE_SIZE, (ArrayY + 1) * TEXTURE_SIZE, TEXTURE_SIZE);
        }
        return null;
    }
    @Override
    public void changeSize(int ts){
        TEXTURE_SIZE = ts;
    }

    @Override
    public String saveString() {
        String ans = Integer.toString(craftId);
        return ans;
    }

    @Override
    public void readString(String s) {
        if(Integer.parseInt(s) != -1){
            setCraft(Integer.parseInt(s));
        }
    }
}
