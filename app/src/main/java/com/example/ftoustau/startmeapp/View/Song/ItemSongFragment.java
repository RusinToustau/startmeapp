package com.example.ftoustau.startmeapp.View.Song;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.Model.Song;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.NavegationMenu.FavoritosFragment;

import info.abdolahi.CircularMusicProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemSongFragment extends Fragment {
    private View view;
    private CircularMusicProgressBar progressBar;
    private ImageView fondo , portada;
    private ImageView goHome;
    private Notify notify;

    public static final String SONG_NAME = "song name";
    public static final String ALBUM_NAME = "album name";
    public static final String BAND_NAME = "band name";
    public static final String STYLE = "style";
    public static final String ID_PHOTO_ALBUM  = "id photo album";


    public static ItemSongFragment fragmentFactory(Song song) {
        // Required empty public constructor
        Bundle mybundle = new Bundle();
        mybundle.putString(SONG_NAME,song.getSongName());
        mybundle.putString(ALBUM_NAME,song.getAlbumName());
        mybundle.putString(BAND_NAME,song.getBandName());
        mybundle.putString(ID_PHOTO_ALBUM,song.getIdPhotoAlbum().toString());

        ItemSongFragment itemSongFragment = new ItemSongFragment();
        itemSongFragment.setArguments(mybundle);
        return itemSongFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_item_song, container, false);

        portada = (ImageView) view.findViewById(R.id.album_art);
        TextView textViewBandName = (TextView) view.findViewById(R.id.ItemSFrag_TextView_BandName);
        TextView textViewAlbumName = (TextView) view.findViewById(R.id.ItemSFrag_TextView_AlbumName);
        TextView textViewSong = (TextView) view.findViewById(R.id.ItemSFrag_TextView_SongName);
        fondo=(ImageView)view.findViewById(R.id.fondoPortada);
        goHome = (ImageView) view.findViewById(R.id.goHome);

        Bundle bundle = getArguments();
        Integer idPhoto = Integer.parseInt(bundle.getString(ID_PHOTO_ALBUM));
        String bandName = bundle.getString(BAND_NAME);
        String albumName = bundle.getString(ALBUM_NAME);
        String songName = bundle.getString(SONG_NAME);

        goHome.setOnClickListener(listener);
        fondo.setImageResource(idPhoto);
        portada.setImageResource(idPhoto);
        textViewBandName.setText(bandName);
        textViewAlbumName.setText(albumName);
        textViewSong.setText(songName);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.goHome:
                    notify = (Notify) getActivity();
                    notify.goHome();
                    break;
            }

        }
    };

    public interface Notify{
        void goHome();
    }
}
