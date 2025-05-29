package com.example.surfacedrawexample;



import static com.example.surfacedrawexample.Map.ArrayId.getDrawableId;
import static com.example.surfacedrawexample.Map.ArrayId.updateButtons;
import static com.example.surfacedrawexample.Map.Crafts.craftsItem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.surfacedrawexample.Map.ArrayId;
import com.example.surfacedrawexample.Map.Craft;
import com.example.surfacedrawexample.Map.Crafts;
import com.example.surfacedrawexample.Map.Item;
import com.example.surfacedrawexample.Map.MapArray;
import com.example.surfacedrawexample.interfaces.CraftMenu;
import com.example.surfacedrawexample.interfaces.OnSwipeTouchListener;
import com.example.surfacedrawexample.interfaces.Storage;
import com.example.surfacedrawexample.menu.MenuActivity;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity   {
    Button button;
    TableLayout storage;
    Storage storageClass;
    CraftMenu craftMenuCl;
    TextView textView;
    public static boolean isEducationMode = false;

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String FIRST_RUN_KEY = "is_first_run";
    private static final String FIRST_SUBTITLES_KEY = "is_first_subtitles";
    public static MainActivity MAIN_ACTIVITY;
    public static boolean isFin = false;
    private MusicPlayer musicPlayer;
    Save save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
         save = new Save(this);
        if (isFirstRun()) {
            save.clearSave();
            markAppLaunched();
        }
        MAIN_ACTIVITY = this;
        ConstraintLayout mainLayout = findViewById(R.id.main);
        textView = findViewById(R.id.text2);
        super.onCreate(savedInstanceState);
        Crafts crafts = new Crafts();
        MySurfaceView mySurfaceView = (MySurfaceView) findViewById(R.id.mySurfaceView);
        new ArrayId(mySurfaceView, getResources());
        Player player = new Player(0, getResources(), 0, 0, textView, mySurfaceView);
        mySurfaceView.player = player;
        MapArray m = new MapArray(getResources(), mySurfaceView, player);
        LogicThread logicThread = new LogicThread();
        logicThread.setRunning(true);
        logicThread.start();
        TableLayout tableLayout = findViewById(R.id.storage);
        TableLayout craftMenu = findViewById(R.id.craftsMenu);
        button = new Button(this);
        storage = findViewById(R.id.storage);
        craftMenuCl = new CraftMenu(player);
        mySurfaceView.setOnTouchListener(new OnSwipeTouchListener(this, player, mySurfaceView) {});
        storageClass = new Storage(4, this, tableLayout,mainLayout,findViewById(R.id.scrollMenu), getResources(), player);
        storageClass.addRowStorage(0);
        storageClass.addRowStorage(1);
        player.storage = storageClass;
        updateButtons();
        for(Craft craft : craftsItem){
            if(craft != null)
                addRowCraft(craft, craftMenu);
        }
        save.readSave();
        Button saveButton;
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.recordSave();
            }
        });
        addSound();
        musicPlayer.playRandom();
    }
    private void addSound(){
        List<Integer> sounds = new ArrayList<>();
        sounds.add(R.raw.sound1);
        sounds.add(R.raw.sound2);
        sounds.add(R.raw.sound3);
        sounds.add(R.raw.sound4);
        sounds.add(R.raw.sound5);
        sounds.add(R.raw.sound6);
        sounds.add(R.raw.sound7);
        sounds.add(R.raw.sound8);
        sounds.add(R.raw.sound9);
        musicPlayer = new MusicPlayer(this, sounds);
    }
    public void setIsFin(boolean value) {
        isFin = value;
        if(isFin && isFirstSubtitles()){
            appLaunchedSubtitles();
            save.recordSave();
            Intent intent = new Intent(MainActivity.this, Subtitles.class);
            startActivity(intent);
             finish();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        musicPlayer.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!musicPlayer.isPlaying()) {
            musicPlayer.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicPlayer.releasePlayer();
    }
    public static List<Button> buttonsStorage = new ArrayList<>();

    int rowNumber = 0;

    public void addRow(Drawable[] view, TableLayout tableLayout) {//TODO добавление ряда в инвентарь
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);
        for(int i = 0; i < view.length; i++){
            Button bt = (Button) tr.getChildAt(i);
            bt.setText("10");
          //  bt.setTextSize(6);
            bt.setId(rowNumber * view.length + i + 1000);
            bt.setBackground(view[i]);
          //  bt.setCompoundDrawables(null, null, null, view[i]);

            bt.setOnClickListener(storageClass.onClickListener);
            buttonsStorage.add(bt);
            if(tr.getParent() != null) {
                ((ViewGroup)tr.getParent()).removeView(tr);
            }
            tableLayout.addView(tr);
        }
        rowNumber++;
    }
    public void addRowCraft(Craft craft, TableLayout tableLayout) {//TODO добавление ряда в крафт
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflater inflater1= (Inflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tr = (TableRow) inflater.inflate(R.layout.craft_row, null);
        for(Item el: craft.ingredients){
            Button bt = (Button) inflater.inflate(R.layout.button_icon, null, false);
            bt.setText(Integer.toString(el.num));
            bt.setLayoutParams(new TableRow.LayoutParams(dpToPx(48), dpToPx(48)));
           bt.setBackground(getDrawableId(el.id));
            tr.addView(bt);
            bt.setOnClickListener(craftMenuCl.onClickListener);
        }

        Button btIn  = (Button) inflater.inflate(R.layout.button_icon, null);
        btIn.setOnClickListener(craftMenuCl.onClickListener);
        btIn.setText("");
        btIn.setBackground(getResources().getDrawable(R.drawable.gui_rotation));
        btIn.setLayoutParams(new TableRow.LayoutParams(dpToPx(48), dpToPx(48)));
        tr.addView(btIn);
        Button btCraft  = (Button) inflater.inflate(R.layout.button_icon, null);
        btCraft .setOnClickListener(craftMenuCl.onClickListener);
        btCraft .setText("");
        btCraft .setBackground(getDrawableId(craft.idCraft[0]));
        btCraft .setLayoutParams(new TableRow.LayoutParams(dpToPx(48), dpToPx(48)));
        tr.addView(btCraft );
        Button btOut  = (Button) inflater.inflate(R.layout.button_icon, null);
        btOut.setText("");
        btOut.setBackground(getResources().getDrawable(R.drawable.gui_rotation));
        btOut.setLayoutParams(new TableRow.LayoutParams(dpToPx(48), dpToPx(48)));
        btOut.setOnClickListener(craftMenuCl.onClickListener);
        tr.addView(btOut);

        Button bt2  = (Button) inflater.inflate(R.layout.button_icon, null);
        bt2.setText(Integer.toString(craft.product.num));
        bt2.setLayoutParams(new TableRow.LayoutParams(dpToPx(48), dpToPx(48)));
        bt2.setBackground(getDrawableId(craft.product.id));
        bt2.setOnClickListener(craftMenuCl.onClickListener);
        tr.addView(bt2);
        if(tr.getParent() != null) {
            ((ViewGroup)tr.getParent()).removeView(tr);
        }
        craftMenuCl.tableRows[tableLayout.getChildCount()] = tr;
       // tr.setOnClickListener(craftMenuCl.onClickListener);
        tableLayout.addView(tr);
    }
    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
    private boolean isFirstRun() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(FIRST_RUN_KEY, true);
    }
    private void markAppLaunched() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_RUN_KEY, false);
        editor.apply();
    }
    private boolean isFirstSubtitles() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(FIRST_SUBTITLES_KEY, true);
    }
    private void appLaunchedSubtitles() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_SUBTITLES_KEY, false);
        editor.apply();
    }
}