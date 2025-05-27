package com.example.surfacedrawexample.menu;





import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.surfacedrawexample.MainActivity;
import com.example.surfacedrawexample.MusicPlayer;
import com.example.surfacedrawexample.MySurfaceView;
import com.example.surfacedrawexample.R;
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
        List<Integer> sounds = new ArrayList<>();
        sounds.add(R.raw.sound1);
        sounds.add(R.raw.sound7);
        musicPlayer = new MusicPlayer(this, sounds);
        musicPlayer.playRandom();
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
            musicPlayer.pause();
            finish();
        });



    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

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