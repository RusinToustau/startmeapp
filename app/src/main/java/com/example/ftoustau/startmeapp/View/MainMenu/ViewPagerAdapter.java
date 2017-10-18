package com.example.ftoustau.startmeapp.View.MainMenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftoustau on 09/06/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.listFragments = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}
