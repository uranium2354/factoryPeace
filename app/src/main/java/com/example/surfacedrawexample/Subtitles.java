package com.example.surfacedrawexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.surfacedrawexample.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;

public class Subtitles extends AppCompatActivity {
    private static final int SCROLL_DURATION = 100000;
    TextView creditsTextView;
    private ValueAnimator scrollAnimator;
    Button closeButton;
    ScrollView scrollView;
    private MusicPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtitles);

       creditsTextView = findViewById(R.id.credits_text);

        String creditsText = getString(R.string.subtitles);
        creditsTextView.setText(creditsText);
        closeButton = findViewById(R.id.btnClose);
        List<Integer> sounds = new ArrayList<>();
        sounds.add(R.raw.titles);

        musicPlayer = new MusicPlayer(this, sounds);
        musicPlayer.playRandom();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subtitles.this, MenuActivity.class);
                startActivity(intent);

                finish();
            }
        });
         initScrollAnimation();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private void initScrollAnimation() {
        creditsTextView.post(() -> {
            int textHeight = creditsTextView.getHeight();
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int screenHeight = metrics.heightPixels;
            creditsTextView.setTranslationY(screenHeight);
            creditsTextView.setVisibility(View.VISIBLE);
            creditsTextView.setVisibility(View.VISIBLE);
            scrollAnimator = ValueAnimator.ofFloat(screenHeight, -textHeight);
            scrollAnimator.setDuration(SCROLL_DURATION);
            scrollAnimator.setInterpolator(new LinearInterpolator());
            scrollAnimator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                creditsTextView.setTranslationY(value);
            });
            scrollAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    closeButton.setVisibility(View.VISIBLE);
                }
            });
            scrollAnimator.start();
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
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