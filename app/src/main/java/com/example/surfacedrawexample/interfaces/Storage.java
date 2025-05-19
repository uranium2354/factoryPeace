package com.example.surfacedrawexample.interfaces;

import static com.example.surfacedrawexample.Map.ArrayId.getClassId;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;


import com.example.surfacedrawexample.MainActivity;
import com.example.surfacedrawexample.Map.ArrayId;
import com.example.surfacedrawexample.Player;
import com.example.surfacedrawexample.R;

import java.util.ArrayList;
import java.util.List;

public class Storage  {
    List<Integer> idCell= new ArrayList<>();
    List<Drawable> drawables= new ArrayList<>();
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

   int numberOfItemRow = 4;
    MainActivity main;
    Resources resources;
   public Storage(int numberOfItemRow, MainActivity main, TableLayout tableLayout, Resources resources, Player player){
        idCell.add(1);
        idCell.add(2);
        idCell.add(2);
        idCell.add(2);
        drawables.add(resources.getDrawable(R.drawable.storage_transportbelt));
       drawables.add(resources.getDrawable(R.drawable.storage_transportbelt));
       drawables.add(resources.getDrawable(R.drawable.storage_transportbelt));
       drawables.add(resources.getDrawable(R.drawable.storage_transportbelt));
        this.resources = resources;
       this.tableLayout = tableLayout;
        this.main = main;
        this.numberOfItemRow = numberOfItemRow;
        this.player = player;
   }
   public void addRowStorage(int row){
       int pc = row * numberOfItemRow;
       Drawable[] lD = new Drawable[numberOfItemRow];
       for(int i = pc; i < pc + numberOfItemRow; i++){
           lD[i - pc] = (drawables.get(i));
       }
       main.addRow(lD, tableLayout);
   }
}
