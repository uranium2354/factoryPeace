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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.surfacedrawexample.Map.ArrayId;
import com.example.surfacedrawexample.Map.Craft;
import com.example.surfacedrawexample.Map.Crafts;
import com.example.surfacedrawexample.Map.Item;
import com.example.surfacedrawexample.Map.MapArray;
import com.example.surfacedrawexample.interfaces.CraftMenu;
import com.example.surfacedrawexample.interfaces.OnSwipeTouchListener;
import com.example.surfacedrawexample.interfaces.Storage;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

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
        isEducationMode = true;
        setContentView(R.layout.activity_education);
        save = new Save(this);
        if (isFirstRun()) {
            save.clearSave();
            markAppLaunched();
        }
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
        button = new Button(this);
        storage = findViewById(R.id.storage);
        craftMenuCl = new CraftMenu(player);
        mySurfaceView.setOnTouchListener(new OnSwipeTouchListener(this, player, mySurfaceView) {});
        updateButtons();
        //save.readSave();
        save.showNextLesson();

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
}