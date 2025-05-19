package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.interfaces.Storage.onClickListener;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.surfacedrawexample.Map.MapArray;
import com.example.surfacedrawexample.interfaces.OnSwipeTouchListener;
import com.example.surfacedrawexample.interfaces.Storage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity   {
    Button button;
    TableLayout storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        ConstraintLayout mainLayout = findViewById(R.id.main);
        super.onCreate(savedInstanceState);
        Player player = new Player(0, getResources(), 0, 0);
        MySurfaceView mySurfaceView = (MySurfaceView) findViewById(R.id.mySurfaceView);
        mySurfaceView.player = player;
       MapArray m = new MapArray(getResources(), mySurfaceView);
        LogicThread logicThread = new LogicThread();
        logicThread.setRunning(true);
        logicThread.start();
        TableLayout tableLayout = findViewById(R.id.storage);
        //setContentView(R.layout.activity_main);
        button = new Button(this);
        storage = findViewById(R.id.storage);
        Drawable[] drawable = {getResources().getDrawable(R.drawable.water),
                getResources().getDrawable(R.drawable.water),
                getResources().getDrawable(R.drawable.water)};
        TextView textView = findViewById(R.id.text2);
        mySurfaceView.setOnTouchListener(new OnSwipeTouchListener(this, player, findViewById(R.id.text2), mySurfaceView) {
            public void onSwipeLeft() {
                //обработка свайпа влево
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        });
        Storage storageClass = new Storage(4);
     //   addRow(drawable, tableLayout);

    }
    List<Button> buttons = new ArrayList<>();

    int rowNumber = 0;

    public void addRow(Drawable[] view, TableLayout tableLayout) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);
        for(int i = 0; i < view.length; i++){
            Button bt = (Button) tr.getChildAt(i);
            bt.setText(Integer.toString(rowNumber) + " " + Integer.toString(i));
            bt.setTextSize(6);
            bt.setId(rowNumber * i + 1000);
          //  bt.setBackground(view[i]);
            bt.setCompoundDrawables(null, null, null, view[i]);

            bt.setOnClickListener(onClickListener);
            buttons.add(bt);
            if(tr.getParent() != null) {
                ((ViewGroup)tr.getParent()).removeView(tr);
            }
            tableLayout.addView(tr);
        }
        rowNumber++;
    }
}