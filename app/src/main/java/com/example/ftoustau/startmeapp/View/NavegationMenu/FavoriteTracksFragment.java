package com.example.ftoustau.startmeapp.View.NavegationMenu;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.Model.DAO.DAOFavTracks.DAOTracksFavoritos;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.TrackListView.TrackListAdapter;

import java.util.ArrayList;

public class FavoriteTracksFragment extends Fragment {
    Context context;
    DAOTracksFavoritos daoTracksFavoritos;
    ArrayList<Track> myFavoritesTracks;
    TrackListAdapter trackListAdapter;
    RecyclerView recyclerView;
    Notify notify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.favorites_tracks, container, false);

        context = getContext();
        daoTracksFavoritos = new DAOTracksFavoritos(context);
        myFavoritesTracks = daoTracksFavoritos.getListTrackInDataBase();

        trackListAdapter = new TrackListAdapter(myFavoritesTracks);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerFavoritesTracks);
        trackListAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerView.getChildAdapterPosition(v);
                notify = (Notify) getActivity();
                notify.next(myFavoritesTracks,position);
            }
        });
        recyclerView.setAdapter(trackListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        return view;
    }

    public interface Notify{
        void next(ArrayList<Track> list, Integer position);
    }

}
