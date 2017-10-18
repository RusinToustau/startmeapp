package com.example.ftoustau.startmeapp.View.Song;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ftoustau.startmeapp.Model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftoustau on 14/06/17.
 */

public class SongAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaDeFragments = new ArrayList<>();
    private List<Song> myList;

    public SongAdapter(FragmentManager fm, List<Song> list) {
        super(fm);
        myList=list;
        for (Song song : list){
            ItemSongFragment itemSongFragment = ItemSongFragment.fragmentFactory(song);
            listaDeFragments.add(itemSongFragment);
        }
    }

    public Song getSongItem(Integer position){
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