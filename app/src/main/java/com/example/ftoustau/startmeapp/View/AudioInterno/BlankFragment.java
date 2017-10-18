package com.example.ftoustau.startmeapp.View.AudioInterno;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftoustau.startmeapp.Model.POJO.Audio;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.CustomViewPager.ChartsFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    public static final String LISTAKEY = "lista";
    private ArrayList<Audio> audioList;
    private RecyclerView recyclerView;
    private Notify notify;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_blank, container, false);

        Bundle bundle = getArguments();
        audioList = (ArrayList<Audio>) bundle.getSerializable(LISTAKEY);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewMusic);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(audioList);
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = recyclerView.getChildAdapterPosition(view);
                notify.playAudiio(position);
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notify = (Notify) getActivity();
    }

    public interface Notify{
        void playAudiio(Integer position);
    }

}
