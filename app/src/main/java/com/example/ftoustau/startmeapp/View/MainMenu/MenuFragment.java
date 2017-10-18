package com.example.ftoustau.startmeapp.View.MainMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.AudioInterno.BlankFragment;
import com.example.ftoustau.startmeapp.View.GenreView.GenreFragment;
import com.example.ftoustau.startmeapp.View.ListSongs.AlbumFragment;
import com.example.ftoustau.startmeapp.View.Player.PlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {


    private View view;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        bundle = getArguments();
        createViewPager();
        createPlayFragment();
        return view;
    }


    public void createPlayFragment(){
        PlayerFragment playerFragment = new PlayerFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentPlayer,playerFragment);
        fragmentTransaction.commit();
    }

    public void createViewPager(){
        // FM
        FragmentManager fragmentManager = getChildFragmentManager();

        // Adapter
        ViewPagerAdapter miAdapter = new ViewPagerAdapter(fragmentManager, getFragmentList());

        // View Pager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(miAdapter);
        viewPager.setPageMargin(0);
    }

    private List<Fragment> getFragmentList(){
        //list Fragment

        GenreFragment genreFragment = new GenreFragment();
//        AlbumFragment albumFragment = AlbumFragment.createAlbumFragment();
//        BlankFragment blankFragment = new BlankFragment();
//        blankFragment.setArguments(bundle);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(genreFragment);
//        fragmentList.add(albumFragment);
//        fragmentList.add(blankFragment);

        return fragmentList;
    }
}
