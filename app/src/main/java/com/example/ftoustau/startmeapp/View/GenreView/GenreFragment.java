package com.example.ftoustau.startmeapp.View.GenreView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.Controller.ControllerGenre;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment {
    private static final Integer COLUMNS_RECYCLER = 2;
    private GenreAdapter adapter;
    private NotifyGenre notifyGenre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genre, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ReciclerViewGenre);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), COLUMNS_RECYCLER);

        ArrayList<Genre> genreList = new ArrayList<>();

        adapter = new GenreAdapter(getContext(),genreList);

        ControllerGenre controllerGenre = new ControllerGenre();

        controllerGenre.getGenres(getContext(), new ResultListener<ArrayList<Genre>>() {
            @Override
            public void finish(ArrayList<Genre> resultado) {
                adapter.setGenreList(resultado);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerView.getChildAdapterPosition(v);
                notifyGenre = (NotifyGenre) getContext();
                notifyGenre.toBuildTheCustomerStyle(adapter.getItemAtPosition(position));
            }
        };

        adapter.setListener(listener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public interface NotifyGenre{
        void toBuildTheCustomerStyle(Genre genre);
    }
}
