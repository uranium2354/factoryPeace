package com.example.surfacedrawexample;

import static android.content.Context.MODE_PRIVATE;

import static com.example.surfacedrawexample.Map.ArrayId.SIZE_ID;
import static com.example.surfacedrawexample.Map.ArrayId.getClassId;
import static com.example.surfacedrawexample.Map.ArrayId.getScaleId;
import static com.example.surfacedrawexample.Map.ArrayId.numItem;
import static com.example.surfacedrawexample.Map.ArrayId.setDefaultStateNumItem;
import static com.example.surfacedrawexample.Map.ArrayId.updateButtons;
import static com.example.surfacedrawexample.Map.MapArray.map;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.surfacedrawexample.Map.Element.MapElement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Save {

    private final static String FILE_NAME_MAP = "Map.txt";
    private final static String FILE_NAME_STORAGE = "Storage.txt";
    private final static String FILE_NAME_EDUCATION = "Education";
    private final int maxLesson = 8;
    private int currentLesson = 0;
    Context context;
    public Save(Context context){
        this.context = context;
    }
    public void recordSave(){
        FileOutputStream fos = null, fos2 = null;
        try {
            fos = context.openFileOutput(FILE_NAME_MAP, MODE_PRIVATE);
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[0].length; j++){
                    if(map[i][j] != null && map[i][j].ArrayX == i && map[i][j].ArrayY == j){
                        String save = "";
                        save += Integer.toString(map[i][j].id) ;
                        save += " ";
                        save += Integer.toString(i) + " " + Integer.toString(j) + " " + Integer.toString(map[i][j].rotation) + "\n";
                        save += map[i][j].saveString() + "\n";
                        try {
                            fos.write(save.getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            fos2 = context.openFileOutput(FILE_NAME_STORAGE, MODE_PRIVATE);
            for(int i = 0; i < SIZE_ID; i++){
                String save = "";
                save += Integer.toString(i) ;
                save += " ";
                save += Integer.toString(numItem[i]) + "\n";
                try {
                    fos2.write(save.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (Looper.getMainLooper() == Looper.myLooper()) {
                Toast.makeText(context, "Файл сохранен", Toast.LENGTH_SHORT).show();
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            if(fos!=null)
                fos.close();
        }
        catch(IOException ex){

            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void readSave(){
       // clearMap();
        readSaveMapFileName(FILE_NAME_MAP, false);
        readSaveStorageFileName(FILE_NAME_STORAGE);
    }
    public void readSaveMapFileName(String fileName, boolean inAssets){
        FileInputStream fin = null;
        InputStream is = null;
        try {
            BufferedReader reader = null;

            if(!inAssets){

                fin = context.openFileInput(fileName);
                 reader = new BufferedReader(new InputStreamReader(fin, StandardCharsets.UTF_8));

            }
            else {

                is = context.getAssets().open(fileName);
                reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                for(int i = 0; i < 20; i++)
                    Log.e("Read", "новый елемент ");
            }


            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 4) {
                    Log.e("Read", "Некорректный формат строки: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    int posX = Integer.parseInt(parts[1]);
                    int posY = Integer.parseInt(parts[2]);
                    int rotation = Integer.parseInt(parts[3]);
                    MapElement newEl = new MapElement(id, rotation, posX, posY, false, getClassId(id)).object;
                    for(int i = posX; i < posX + getScaleId(id).x; i++){
                        for(int j = posY; j < posY + getScaleId(id).y; j++){
                            map[i][j] = newEl;
                        }
                    }

                    line = reader.readLine();
                    newEl.readString(line);
                } catch (NumberFormatException e) {
                    Log.e("Read", "Ошибка парсинга чисел: " + line);
                }
            }
            reader.close();
            updateButtons();
        }
        catch(IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
                if(is!=null)
                    is.close();
            }
            catch(IOException ex){
                if(!inAssets)
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void clearMap(){
        for (MapElement[] mapElements : map) {
            Arrays.fill(mapElements, null);
        }
    }
    public void readSaveStorageFileName(String fileName){
        FileInputStream fin = null;
        try {

            fin = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin, StandardCharsets.UTF_8));
            boolean isEmpty = true;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    Log.e("Read", "Некорректный формат строки: " + line);
                    continue;
                }
                isEmpty = false;
                try {
                    int indx = Integer.parseInt(parts[0]);
                    int num = Integer.parseInt(parts[1]);
                    numItem[indx] = num;
                } catch (NumberFormatException e) {
                    Log.e("Read", "Ошибка парсинга чисел: " + line);
                }
            }
            if(isEmpty){
                setDefaultStateNumItem();
            }
            updateButtons();
            reader.close();
        }
        catch(IOException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void clearSave(){
        FileOutputStream fos = null, fos2 = null;
        try {
            fos = context.openFileOutput(FILE_NAME_MAP, MODE_PRIVATE);
            fos2 = context.openFileOutput(FILE_NAME_STORAGE, MODE_PRIVATE);
            Toast.makeText(context, "Файл удалён", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            if(fos != null)
                fos.close();
            if(fos2 != null)
                fos2.close();
        }
        catch(IOException ex){

            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void showNextLesson() {
        currentLesson++;
        if(currentLesson > maxLesson){
            return;
        }
        clearMap();
        readSaveMapFileName(FILE_NAME_EDUCATION + Integer.toString(currentLesson)+ ".txt", true);
    }
}

