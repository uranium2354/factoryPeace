package com.example.surfacedrawexample.interfaces;

import static com.example.surfacedrawexample.Map.ArrayId.getClassId;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;


import com.example.surfacedrawexample.Map.ArrayId;
import com.example.surfacedrawexample.R;

import java.util.List;

public class Storage  {
    List<Integer> idCell;
    List<Drawable> drawables;
   static public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
   int numberOfItemRow = 4;

   public Storage(int numberOfItemRow){
        idCell.add(1);
        idCell.add(2);
        idCell.add(3);
        idCell.add(4);
       //  drawable.add(R.drawable.)

        this.numberOfItemRow = numberOfItemRow;
        Object obj = getClassId(5);
   }
   private void addRowStorage(int row){
       int pc = row * numberOfItemRow;
       List<Drawable> lD;
       for(int i = pc; i < pc + numberOfItemRow; i++){

       }
   }
}
