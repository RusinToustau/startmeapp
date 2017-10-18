package com.example.ftoustau.startmeapp.View.CustomViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartsFragment extends Fragment {
    private Album myAlbum;
    private Notify notify;
    private Integer id;
    private String title;
    private String cover_medium;
    private Integer genre_id;
    private String tracklist;

    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String IMAGE_KEY = "cover_medium";
    private static final String GENRE_ID_KEY = "genre_id";
    private static final String TRACKLIST_KEY = "tracklist";


    public static ChartsFragment fragmentFactory(Album album) {
        // Required empty public constructor

        Bundle bundle = new Bundle();
        bundle.putInt(ID_KEY,album.getId());
        bundle.putString(TITLE_KEY,album.getTitle());
        bundle.putString(IMAGE_KEY,album.getCover_medium());
        /*no tocar esta linea que tira null y rompe everything*/
//        bundle.putInt(GENRE_ID_KEY,album.getGenre_id());
        bundle.putString(TRACKLIST_KEY,album.getTracklist());

        ChartsFragment chartsFragment = new ChartsFragment();

        chartsFragment.setMyAlbum(album);
        chartsFragment.setArguments(bundle);
        return chartsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        Bundle bundle = getArguments();

        id = bundle.getInt(ID_KEY);
        title = bundle.getString(TITLE_KEY);
        cover_medium = bundle.getString(IMAGE_KEY);
        genre_id = bundle.getInt(GENRE_ID_KEY);
        tracklist = bundle.getString(TRACKLIST_KEY);

        ImageView imageView = (ImageView) view.findViewById(R.id.AlbumPicture);
        Picasso.with(view.getContext()).load(cover_medium).into(imageView);

        TextView textView = (TextView) view.findViewById(R.id.chartsAlbumName);
        textView.setText(title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = (Notify) getActivity();
                notify.goTrackLsit(myAlbum);
            }
        });

        return view;
    }

    public void setMyAlbum(Album myAlbum) {
        this.myAlbum = myAlbum;
    }

    public interface Notify{
        void goTrackLsit (Album album);
    }
}
