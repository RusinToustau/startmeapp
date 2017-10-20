package com.example.ftoustau.startmeapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.CustomViewPager.ChartsFragment;
import com.example.ftoustau.startmeapp.View.CustomViewPager.ViewPagerCustomFragment;
import com.example.ftoustau.startmeapp.View.GenreView.GenreFragment;
import com.example.ftoustau.startmeapp.View.NavegationMenu.FavoritesAlbumsActivity;
import com.example.ftoustau.startmeapp.View.NavegationMenu.FavoritesTracksActivity;
import com.example.ftoustau.startmeapp.View.NavegationMenu.ProfileActivity;
import com.example.ftoustau.startmeapp.View.Player.PlayerFragment;
import com.example.ftoustau.startmeapp.View.TrackListView.TrackListFragment;
import com.example.ftoustau.startmeapp.View.TrackView.ViewTrackActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        ChartsFragment.Notify,
        PlayerFragment.Notify,
        GenreFragment.NotifyGenre{

    public static final String ACTION_BROADCAST_PLAY_NEW_AUDIO = "com.example.ftoustau.startmeapp.PlayNewAudio";

    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        uploadFragments();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.user:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;

                    case R.id.fav_songs:
                        startActivity(new Intent(MainActivity.this, FavoritesAlbumsActivity.class));
                        break;

                    case R.id.playlist:;
                        startActivity(new Intent(MainActivity.this, FavoritesTracksActivity.class));
                        break;
                }

                drawerLayout.closeDrawers();

                return false;
            }
        });
        uploadToolbar();
    }

    public void uploadFragments(){
        GenreFragment genreFragment = new GenreFragment();
        PlayerFragment playerFragment = new PlayerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentElse,genreFragment);
        fragmentTransaction.replace(R.id.contentPlayer,playerFragment);
        fragmentTransaction.commit();
    }

    public void uploadToolbar(){
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        toolbar.setTitle(R.string.app_name);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void toBuildTheCustomerStyle(Genre genre) {
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerCustomFragment.ID_KEY,genre.getId());
        Intent intent = new Intent(MainActivity.this,SelectActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void goTrackLsit(Album album) {
        TrackListFragment trackListFragment = new TrackListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TrackListFragment.ID_KEY, album.getId());
        bundle.putString(TrackListFragment.TITLE_KEY, album.getTitle());
        bundle.putString(TrackListFragment.TRACKLIST_KEY, album.getTracklist());
        bundle.putString(TrackListFragment.PICTURE_KEY, album.getCover_medium());
        bundle.putString(TrackListFragment.SHARE_KEY, album.getShare());
        bundle.putString(TrackListFragment.COVERBIG_KEY, album.getCover_big());
        bundle.putString(TrackListFragment.COVERXL_KEY, album.getCover_xl());

        trackListFragment.setArguments(bundle);
        trackListFragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contentElse,trackListFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void returnToViewTrack(ArrayList<Track> tracks, Integer position) {
        Intent intent = new Intent(this,ViewTrackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ViewTrackActivity.KEYPOSITION,position);
        bundle.putSerializable(ViewTrackActivity.KEYLIST,tracks);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
