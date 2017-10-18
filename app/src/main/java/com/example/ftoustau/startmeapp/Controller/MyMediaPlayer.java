package com.example.ftoustau.startmeapp.Controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Model.Song;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ftoustau on 17/07/17.
 */

public class MyMediaPlayer {
    private static int length;
    private static ArrayList<Track> tracks;
    private static Integer position;
    private static MediaPlayer mediaPlayer;


    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
        return mediaPlayer;
    }

    public static ArrayList<Track> getTracks() {
        return tracks;
    }

    public static Integer getPosition() {
        return position;
    }
    public static void setTracks(ArrayList<Track> tracks) {
        MyMediaPlayer.tracks = tracks;
    }

    public static void setPosition(Integer position) {
        MyMediaPlayer.position = position;
    }

    public static void nextTrack(){
        position++;
        Track selectTrack = tracks.get(position);
        play(selectTrack.getPreview());
    }

    public static String getTrackname(){
        return tracks.get(position).getTitle();
    }

    public static String getAlbumTitle(){
        return tracks.get(position).getAlbumTitle();
    }

    public static void previusTrack(){
        position--;
        Track selectTrack = tracks.get(position);
        play(selectTrack.getPreview());
    }

    public static void play(String url) {
        MediaPlayer mediaPlayer = getMediaPlayer();
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
    }


    public static void playResurce(Context context, Song song){
        MediaPlayer mediaPlayer = getMediaPlayer();
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(context, song.getIdSong());
                mediaPlayer.start();
            }
        }
    }

    public static Integer getTrackId() {
        return tracks.get(position).getId();
    }

    public static Track getActualTrack() {
        return tracks.get(position);
    }
    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
    }

}
