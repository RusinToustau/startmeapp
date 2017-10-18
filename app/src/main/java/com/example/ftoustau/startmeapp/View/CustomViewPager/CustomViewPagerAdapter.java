package com.example.ftoustau.startmeapp.View.CustomViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftoustau on 27/06/17.
 */

public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> listaDeFragments = new ArrayList<>();

    public CustomViewPagerAdapter(FragmentManager fm, List<Album> list) {
        super(fm);
        for (Album album : list){
            ChartsFragment chartsFragment = ChartsFragment.fragmentFactory(album);
            listaDeFragments.add(chartsFragment);
        }
    }

    public void setMyList(List<Album> myList) {
        listaDeFragments.clear();
        for (Album album : myList){
            ChartsFragment chartsFragment = ChartsFragment.fragmentFactory(album);
            listaDeFragments.add(chartsFragment);
        }
        notifyDataSetChanged();
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
