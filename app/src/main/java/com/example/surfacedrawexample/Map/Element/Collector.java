package com.example.surfacedrawexample.Map.Element;

import static com.example.surfacedrawexample.Map.ArrayId.crossBimap;
import static com.example.surfacedrawexample.Map.ArrayId.getImageId;
import static com.example.surfacedrawexample.Map.ArrayId.getTextureId;
import static com.example.surfacedrawexample.Map.Crafts.craftsItem;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.surfacedrawexample.Map.Craft;
import com.example.surfacedrawexample.Map.Item;
import com.example.surfacedrawexample.MySurfaceView;

import java.util.Objects;

public class Collector extends MapElement {
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
        if(isDestroy){
            src = new Rect(0, 0,
                    crossBimap.getWidth(),  crossBimap.getHeight());
            dst = new Rect((int)ArrayX * TEXTURE_SIZE - 1, (int)ArrayY* TEXTURE_SIZE - 1,
                    ( ArrayX + 3) * TEXTURE_SIZE, (ArrayY + 3)* TEXTURE_SIZE);
            canvas.drawBitmap(crossBimap,src, dst , paint);
        }
    }
    @Override
    synchronized public void updateState(long frame){
        this.frame = frame;
        if(craft != null && System.currentTimeMillis() - lastUpdateTime >= speed * craft.time ){
            if(enoughIngredientsForCrafting()){
                lastUpdateTime = System.currentTimeMillis();
                for(int i = 0; i < craft.ingredients.length; i++){
                    ingredients[i].num -= craft.ingredients[i].num;
                }
                product.num += craft.product.num;
                if(!enoughIngredientsForCrafting()){
                    isCrafting = false;
                }
            }
            else{
                isCrafting = false;
            }
        }
    }
    private  boolean enoughIngredientsForCrafting(){
        boolean f = true;
        for(int i = 0; i < craft.ingredients.length; i++){
            if(ingredients[i].num < craft.ingredients[i].num)
                f = false;
        }
        if(product.num + craft.product.num >= maxNum)
            f = false;
        return f;
    }
    @Override
    public  boolean pullItem(TransportBeltItem it, boolean isChange, int x, int y){
        if(craft == null){
            return false;
        }
        for(Item el : ingredients){
            if(el.id == it.id && el.num < maxNum){
                if(isChange)
                    el.num++;

                if(!isCrafting && enoughIngredientsForCrafting()){
                    lastUpdateTime = System.currentTimeMillis();
                    isCrafting = true;
                }
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
        if(craftId == -1)
            return ans;
        for(int i = 0; i < ingredients.length; i++){
            ans += " ";
            ans += Integer.toString(ingredients[i].id);
            ans += " ";
            ans += Integer.toString(ingredients[i].num);
        }
        ans += " ";
        ans += product.id;
        ans += " ";
        ans += product.num;
        if(!isCrafting && enoughIngredientsForCrafting()){
            lastUpdateTime = System.currentTimeMillis();
            isCrafting = true;
        }
        return ans;
    }

    @Override
    public void readString(String s) {
        String[] parts = s.split(" ");
        if(Objects.equals(parts[0], "-1")){
            return;
        }
        setCraft(Integer.parseInt(parts[0]));
        if(parts.length  - 3 == craft.ingredients.length * 2){
            for(int i = 1; i < parts.length - 2; i += 2){
                int id = Integer.parseInt(parts[i]);
                int num = Integer.parseInt(parts[i + 1]);
                ingredients[(i - 1) / 2] = new Item(id, num);
            }
            int id = Integer.parseInt(parts[parts.length - 2]);
            int num = Integer.parseInt(parts[parts.length - 1]);
            product = new Item(id, num);
            if(!isCrafting && enoughIngredientsForCrafting()){
                lastUpdateTime = System.currentTimeMillis();
                isCrafting = true;
            }
        }
    }
}
