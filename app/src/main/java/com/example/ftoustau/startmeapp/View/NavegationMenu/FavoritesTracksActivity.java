package com.example.ftoustau.startmeapp.View.NavegationMenu;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.TrackView.ViewTrackActivity;

import java.util.ArrayList;

public class FavoritesTracksActivity extends AppCompatActivity
        implements FavoriteTracksFragment.Notify{
    private FavoriteTracksFragment favoriteTracksFragment;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_tracks);
        favoriteTracksFragment = new FavoriteTracksFragment();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer_fTracks,favoriteTracksFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void next(ArrayList<Track> list, Integer position) {
        Intent intent = new Intent(this,ViewTrackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ViewTrackActivity.KEYPOSITION,position);
        bundle.putSerializable(ViewTrackActivity.KEYLIST,list);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
