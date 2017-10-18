package com.example.ftoustau.startmeapp.View.Song;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.Controller.MyMediaPlayer;
import com.example.ftoustau.startmeapp.Model.Song;
import com.example.ftoustau.startmeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerSongFragment extends Fragment {
    public static String POSITION_KEY = "position";
    public static String LISTSONG_KEY = "myList";

    private ArrayList<Song> list;
    private Integer item;
    private SongAdapter songAdapter;
    private MediaPlayer mediaPlayer;

    public static ViewPagerSongFragment createViewPagerSongFragment(ArrayList<Song> list, Integer position){
        ViewPagerSongFragment viewPagerSongFragment = new ViewPagerSongFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerSongFragment.POSITION_KEY,position);
        bundle.putSerializable(ViewPagerSongFragment.LISTSONG_KEY,list);

        viewPagerSongFragment.setArguments(bundle);
        return viewPagerSongFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_view_pager_song, container, false);

        Bundle bundle = getArguments();
        item = bundle.getInt(POSITION_KEY);
        list = (ArrayList<Song>) bundle.getSerializable(LISTSONG_KEY);


        songAdapter = new SongAdapter(getActivity().getSupportFragmentManager(),list);

        mediaPlayer = MediaPlayer.create(view.getContext(), list.get(item).getIdSong());
        mediaPlayer.start();

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPagerSongs);
        viewPager.setAdapter(songAdapter);
        viewPager.setCurrentItem(item);
        viewPager.setClipToPadding(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Song actualSong = ((SongAdapter)viewPager.getAdapter()).getSongItem(position);

                //Configuracione de release y como se va a comportar el reproductor si ya se encuentra en play
                MyMediaPlayer.playResurce(view.getContext(),actualSong);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

}
