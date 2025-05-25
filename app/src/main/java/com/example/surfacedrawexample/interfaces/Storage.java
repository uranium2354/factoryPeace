package com.example.surfacedrawexample.interfaces;

import static androidx.core.view.ViewKt.setVisible;
import static com.example.surfacedrawexample.MainActivity.buttonsStorage;
import static com.example.surfacedrawexample.Map.ArrayId.getClassId;
import static com.example.surfacedrawexample.Map.ArrayId.getDrawableId;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.example.surfacedrawexample.MainActivity;
import com.example.surfacedrawexample.Map.ArrayId;
import com.example.surfacedrawexample.Player;
import com.example.surfacedrawexample.R;

import java.util.ArrayList;
import java.util.List;

public class Storage  {
    static List<Integer> idCell= new ArrayList<>();
    TableLayout tableLayout;
   Player player;
   public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            id-=1000;
            if(id >= 0 && id <idCell.size() ){
                player.setSelectedMapPlace(idCell.get(id ));
            }
        }
    };

   public static void setTextButtonId(int id, String text){
       for (Button el : buttonsStorage ){
           int idNumIdCell = el.getId();
           if(idCell.get(idNumIdCell - 1000) == id){
               el.setText(text);
           }
       }
   }

   int numberOfItemRow = 4;
    MainActivity main;
    Resources resources;
    Button buttonRotate;
    Button buttonAccept;
    Button buttonDestroy;
    Button buttonCraft;
    ScrollView craftMenu;

   public Storage(int numberOfItemRow, MainActivity main, TableLayout tableLayout, ConstraintLayout constraintLayout, ScrollView craftMenu, Resources resources, Player player){
        idCell.add(1);
        idCell.add(2);
        idCell.add(3);
        idCell.add(8);
        buttonRotate = constraintLayout.findViewById(R.id.rotateButton);
       idCell.add(9);
       idCell.add(10);
       idCell.add(11);
       idCell.add(11);
        this.craftMenu = craftMenu;
        buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.rotationPlace();
                buttonRotate.setRotation(player.rotationPlace * 90);
            }
        });
        buttonAccept = constraintLayout.findViewById(R.id.acceptButton);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIsPlace();
                updateImage();
            }

        });
        buttonDestroy = constraintLayout.findViewById(R.id.destroyButton);
        buttonDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setIsDestroy();
                updateImage();
            }
        });
        buttonCraft = constraintLayout.findViewById(R.id.craftButton);
        buttonCraft.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               player.setIsCraft();
               updateImage();
           }
       });
        this.resources = resources;
        this.tableLayout = tableLayout;
        this.main = main;
        this.numberOfItemRow = numberOfItemRow;
        this.player = player;
       updateImage();
   }
    public void  updateImage(){
        if(player.isPlace)
            buttonAccept.setBackground(resources.getDrawable(R.drawable.gui_accept_a));
        else
            buttonAccept.setBackground(resources.getDrawable(R.drawable.gui_accept));
        if(player.isDestroy)
            buttonDestroy.setBackground(resources.getDrawable(R.drawable.gui_destroy_a));
        else
            buttonDestroy.setBackground(resources.getDrawable(R.drawable.gui_destroy));
        if(!player.isCraft){
            craftMenu.setVisibility(View.INVISIBLE);
            craftMenu.setVisibility(View.GONE);
        }
        else{
            craftMenu.setVisibility(View.VISIBLE);
        }

    }
   public void addRowStorage(int row){
       int pc = row * numberOfItemRow;
       Drawable[] lD = new Drawable[numberOfItemRow];
       for(int i = pc; i < pc + numberOfItemRow; i++){
           lD[i - pc] = getDrawableId(idCell.get(i));
       }
       main.addRow(lD, tableLayout);
   }
}
