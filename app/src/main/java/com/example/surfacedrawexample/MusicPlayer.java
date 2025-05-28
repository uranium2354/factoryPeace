package com.example.surfacedrawexample;

import android.content.Context;
import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private final List<Integer> soundResources;
    private final Random random;
    private final Context context;
    private int currentIndex = -1;

    public MusicPlayer(Context context, List<Integer> soundResources) {
        this.context = context.getApplicationContext();
        this.soundResources = new ArrayList<>(soundResources);
        this.random = new Random();
    }

    public void playRandom() {
        releasePlayer();

        if (soundResources.isEmpty()) {
            return;
        }

        int newIndex;
        do {
            newIndex = random.nextInt(soundResources.size());
        } while (soundResources.size() > 1 && newIndex == currentIndex);

        currentIndex = newIndex;

        try {
            mediaPlayer = MediaPlayer.create(context, soundResources.get(currentIndex));
            mediaPlayer.setOnCompletionListener(mp -> playRandom());
            mediaPlayer.start();
        } catch (Exception e) {

        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}