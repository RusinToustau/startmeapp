package com.example.ftoustau.startmeapp.View.TrackView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.ftoustau.startmeapp.Controller.MyMediaPlayer;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Model.Song;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.MainActivity;
import com.example.ftoustau.startmeapp.View.Song.SongAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class ViewTrackActivity extends AppCompatActivity implements TrackItemView.Player,TrackItemView.Notify{
    public static final String KEYLIST = "list";
    public static final String KEYPOSITION = "position";
    private MediaPlayer mediaPlayer;
    private TrackAdapter adapter;
    private ArrayList<Track> tracks;
    private ViewPager viewPager;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_track);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        tracks = (ArrayList<Track>) bundle.getSerializable(KEYLIST);
        position = bundle.getInt(KEYPOSITION);

        mediaPlayer = MyMediaPlayer.getMediaPlayer();
        adapter = new TrackAdapter(getSupportFragmentManager(),tracks);

        viewPager = (ViewPager) findViewById(R.id.TrackViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.setClipToPadding(false);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Track actualTrack = adapter.getTrackItem(position);
                MyMediaPlayer.setTracks(tracks);
                MyMediaPlayer.setPosition(position);
                MyMediaPlayer.play(actualTrack.getPreview());
            }


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void next() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    @Override
    public void previous() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }


    @Override
    public void returnHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
