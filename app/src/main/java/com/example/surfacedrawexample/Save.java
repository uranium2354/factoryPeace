package com.example.surfacedrawexample;

import static android.content.Context.MODE_PRIVATE;

import static com.example.surfacedrawexample.Map.ArrayId.getClassId;
import static com.example.surfacedrawexample.Map.ArrayId.getScaleId;
import static com.example.surfacedrawexample.Map.MapArray.map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.surfacedrawexample.Map.MapElement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Save {

    private final static String FILE_NAME_MAP = "Map.txt";
    Context context;
    public Save(Context context){
        this.context = context;
    }
    public void recordSave(){
        FileOutputStream fos = null;
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
            Toast.makeText(context, "Файл сохранен", Toast.LENGTH_SHORT).show();

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
        FileInputStream fin = null;
        try {
            fin = context.openFileInput(FILE_NAME_MAP);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin, StandardCharsets.UTF_8));
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
}

