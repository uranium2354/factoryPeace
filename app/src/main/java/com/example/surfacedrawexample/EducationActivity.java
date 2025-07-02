package com.example.surfacedrawexample;

import static com.example.surfacedrawexample.Map.ArrayId.getDrawableId;
import static com.example.surfacedrawexample.Map.ArrayId.updateButtons;
import static com.example.surfacedrawexample.Map.Crafts.craftsItem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
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
import com.example.surfacedrawexample.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

    CraftMenu craftMenuCl;
    TextView textView;

    private MusicPlayer musicPlayer;
    String[] eduText = new String[8];
    int currentText = 0;
    Button menu, next;
    Save save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_education);
        save = new Save(this);
        super.onCreate(savedInstanceState);
        Crafts crafts = new Crafts();
        MySurfaceView mySurfaceView = (MySurfaceView) findViewById(R.id.mySurfaceView);
        new ArrayId(mySurfaceView, getResources());
        Player player = new Player(0, getResources(), 0, 0, textView, mySurfaceView);
        player.educationMode = true;
        mySurfaceView.player = player;
        textView = findViewById(R.id.educationtext);
        MapArray m = new MapArray(getResources(), mySurfaceView, player, 20);
        generateOres(m);
        LogicThread logicThread = new LogicThread();
        logicThread.setRunning(true);
        logicThread.start();
        craftMenuCl = new CraftMenu(player);
        mySurfaceView.setOnTouchListener(new OnSwipeTouchListener(this, player, mySurfaceView) {});
        updateButtons();
        //save.readSave();
        menu = findViewById(R.id.btnMenu);
        next = findViewById(R.id.btnNext);
        eduText[0] = getString(R.string.eduText0);
        eduText[1] = getString(R.string.eduText1);
        eduText[2] = getString(R.string.eduText2);
        eduText[3] = getString(R.string.eduText3);
        eduText[4] = getString(R.string.eduText4);
        eduText[5] = getString(R.string.eduText5);
        eduText[6] = getString(R.string.eduText6);
        eduText[7] = getString(R.string.eduText7);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.showNextLesson();
                nextText();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducationActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        save.showNextLesson();
        addSound();
        musicPlayer.playRandom();
    }
    private void generateOres(MapArray m){
        m.generateOre(7, new Point[]{new Point(2,2),
        new Point(2, 3),
        new Point(3, 2),
        new Point(3, 2),
        new Point(4, 2)});
        m.generateOre(5, new Point[]{new Point(6,4),
                new Point(6, 5),
                new Point(7, 4),
                new Point(7, 5),
                new Point(7, 6)});
        m.generateSpriteOre();
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
    private void nextText(){
        currentText = Math.min(eduText.length - 1, currentText + 1);
        textView.setText(eduText[currentText]);
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

}