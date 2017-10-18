package com.example.ftoustau.startmeapp.View.NavegationMenu;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ftoustau.startmeapp.Controller.ControlerTrackList;
import com.example.ftoustau.startmeapp.Model.DAO.DAOFavoritesAlbum.DAOFavoriteAlbumDataBase;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.Util.ResultListener;
import com.example.ftoustau.startmeapp.View.TrackListView.TrackListFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {
    private DAOFavoriteAlbumDataBase daoFavoriteAlbumDataBase;
    private RecyclerView recyclerView;
    private Notify notify;
    private ArrayList<Album> myFavoritsAlbums;
    private MiAdapterFavorito adapter;
    private Context context;
    Album selectAlbum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        daoFavoriteAlbumDataBase = new DAOFavoriteAlbumDataBase(getContext());
        myFavoritsAlbums = daoFavoriteAlbumDataBase.getListAlbumInDataBase();

        context = view.getContext();

        adapter = new MiAdapterFavorito(myFavoritsAlbums);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFavoritos);


        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerView.getChildAdapterPosition(v);
                selectAlbum = adapter.getAlbum(position);

                notify = (Notify) getActivity();
                notify.toViewAlbum(selectAlbum);
            }
        });

        // Set Layout Manager
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        // Set Adapter
        recyclerView.setAdapter(adapter);
        // Tell the recycler size will not change
        recyclerView.setHasFixedSize(true);

        return view;
    }

    public interface Notify{
        void toViewAlbum(Album album);
    }

}
