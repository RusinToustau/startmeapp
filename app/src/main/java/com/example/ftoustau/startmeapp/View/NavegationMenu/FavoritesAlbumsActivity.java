package com.example.ftoustau.startmeapp.View.NavegationMenu;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.TrackListView.TrackListFragment;
import com.example.ftoustau.startmeapp.View.TrackView.ViewTrackActivity;

import java.util.ArrayList;

public class FavoritesAlbumsActivity extends AppCompatActivity
        implements FavoritosFragment.Notify ,TrackListFragment.Notify{
    private FavoritosFragment favoritosFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_albums);

        favoritosFragment = new FavoritosFragment();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer_falbum,favoritosFragment).commit();
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
    public void toViewAlbum(Album album) {
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
        ft.add(R.id.fragmentContainer_falbum,trackListFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void goToViewTrackActivity(ArrayList<Track> list, Integer position) {
        Intent intent = new Intent(this,ViewTrackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ViewTrackActivity.KEYPOSITION,position);
        bundle.putSerializable(ViewTrackActivity.KEYLIST,list);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
