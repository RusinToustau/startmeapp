package com.example.ftoustau.startmeapp.View.ListSongs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.Model.Song;
import com.example.ftoustau.startmeapp.R;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {
    public static final String SONG_KEY= "listaCanciones";
    private Notify notify;
    private RecyclerView recyclerViewListSongs;
    private AlbumAdapter albumAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        final ArrayList<Song> arrayList = (ArrayList<Song>) bundle.getSerializable(SONG_KEY);/*reemplazar*/

        View view = inflater.inflate(R.layout.fragment_album, container, false);

        recyclerViewListSongs = (RecyclerView) view.findViewById(R.id.ReciclerViewListSongs);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewListSongs.setLayoutManager(layoutManager);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerViewListSongs.getChildAdapterPosition(v);
                notify = (Notify) getActivity();
                notify.goViewPagerSong(arrayList,position);
            }
        };

        albumAdapter = new AlbumAdapter(getContext(), arrayList);
        albumAdapter.setListener(listener);
        recyclerViewListSongs.setAdapter(albumAdapter);

        return view;
    }

    public interface Notify{
        void goViewPagerSong(ArrayList<Song> list, Integer position);
    }
}
