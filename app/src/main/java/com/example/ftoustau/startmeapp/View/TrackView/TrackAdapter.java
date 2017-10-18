package com.example.ftoustau.startmeapp.View.TrackView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftoustau on 17/07/17.
 */

public class TrackAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaDeFragments = new ArrayList<>();
    private List<Track> myList;

    public TrackAdapter(FragmentManager fm ,List<Track> list) {
        super(fm);
        myList= list;
        for(Track track : list){
            TrackItemView trackItemView = TrackItemView.fragmentFactory(track);
            listaDeFragments.add(trackItemView);
        }
    }

    public Track getTrackItem(Integer position){
        return myList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listaDeFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaDeFragments.size();
    }
}
