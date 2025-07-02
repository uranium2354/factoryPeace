package com.example.surfacedrawexample.menu;





import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.surfacedrawexample.EducationActivity;
import com.example.surfacedrawexample.MainActivity;
import com.example.surfacedrawexample.MusicPlayer;
import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;
import com.example.surfacedrawexample.Save;
import com.example.surfacedrawexample.Subtitles;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private MySurfaceView2 surfaceView;
    private MusicPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        surfaceView = findViewById(R.id.surfaceView);

        MaterialButton btnPlay = findViewById(R.id.btnPlay);
        MaterialButton btnCreator = findViewById(R.id.btnCreator);
        MaterialButton btnEducation = findViewById(R.id.btnEducation);
        List<Integer> sounds = new ArrayList<>();
        sounds.add(R.raw.sound1);
        sounds.add(R.raw.sound7);
        musicPlayer = new MusicPlayer(this, sounds);
        musicPlayer.playRandom();
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        btnEducation.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, EducationActivity.class);
            startActivity(intent);
            finish();
        });
        Button btnClearSave = findViewById(R.id.btnClear);
        LinearLayout dialog = findViewById(R.id.dialog_delete_save);
        dialog.setVisibility(View.INVISIBLE);
        dialog.setVisibility(View.GONE);
        Save save = new Save(this);
        btnClearSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setVisibility(View.VISIBLE);
            }
        });
        Button btnCreators = findViewById(R.id.btnCreator);
        btnCreators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Subtitles.class);
                startActivity(intent);

                finish();
            }
        });
        Button btnNo = findViewById(R.id.btnNo);
        Button btnYes = findViewById(R.id.btnYes);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setVisibility(View.INVISIBLE);
                dialog.setVisibility(View.GONE);
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.clearSave();
                dialog.setVisibility(View.INVISIBLE);
                dialog.setVisibility(View.GONE);
            }
        });
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