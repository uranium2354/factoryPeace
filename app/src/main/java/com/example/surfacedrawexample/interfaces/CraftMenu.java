package com.example.surfacedrawexample.interfaces;

import android.view.View;
import android.widget.TableRow;

import com.example.surfacedrawexample.Player;

public class CraftMenu {
    Player player;
    public TableRow[] tableRows = new TableRow[20];
    public CraftMenu(Player player){
        this.player = player;
    }
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TableRow tr = (TableRow) v.getParent();
            for(int i = 0; i < tableRows.length; i++){
                TableRow el = tableRows[i];
                if(el != null && el == tr){
                    player.setCraft(i);
                }
            }
        }
    };
}
