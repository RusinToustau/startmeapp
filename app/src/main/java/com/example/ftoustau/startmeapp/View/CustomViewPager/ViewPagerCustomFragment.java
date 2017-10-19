package com.example.ftoustau.startmeapp.View.CustomViewPager;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.Controller.ControllerAlbumXGenre;
import com.example.ftoustau.startmeapp.Controller.ControllerGenre;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerCustomFragment extends Fragment {
    private View view;
    private Context context;
    private FragmentManager fragmentManager;
    private CustomViewPagerAdapter myAdapter;
    private Integer genre_id;
    private String genre_name;
    private String genre_picture_medium;

    public static final String ID_KEY = "id";
    public static final String GENRENAME_KEY = "name";
    public static final String GENREPICTURE_KEY = "picture";

    public ViewPagerCustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_view_pager_custom, container, false);

        Bundle bundle = getArguments();
        genre_id = bundle.getInt(ID_KEY);
        genre_name = bundle.getString(GENRENAME_KEY);
        genre_picture_medium = bundle.getString(GENREPICTURE_KEY);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        fragmentManager = getActivity().getSupportFragmentManager();

        context = view.getContext();
        ArrayList<Album> albumlist = new ArrayList<>();
        myAdapter = new CustomViewPagerAdapter(fragmentManager, albumlist);

        ControllerAlbumXGenre caXgen = new ControllerAlbumXGenre();
        caXgen.getAlbums(context, new ResultListener<ArrayList<Album>>() {
            @Override
            public void finish(ArrayList<Album> resultado) {

                myAdapter.setMyList(resultado);

            }
        },genre_id);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.CustomViewPager);
        viewPager.setAdapter(myAdapter);
        viewPager.setClipToPadding(false);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
