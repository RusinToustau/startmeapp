package com.example.ftoustau.startmeapp.View.AudioInterno;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.Model.POJO.Audio;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener{

    private List<Audio> audioList = new ArrayList<>();
    private View.OnClickListener viewListener;

    public RecyclerViewAdapter(List<Audio> audioList) {
        if (audioList != null) {
            this.audioList = audioList;
        }
    }

    public void setListener(View.OnClickListener viewListener){
        this.viewListener = viewListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.loadViewHolder(RecyclerViewAdapter.this, audioList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return audioList.size();
    }

    @Override
    public void onClick(View view) {
        if (viewListener != null) {
            viewListener.onClick(view);
        }
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewTitle;
    private ImageView play_pause;

    public ViewHolder(View itemView) {
        super(itemView);
        textViewTitle = (TextView) itemView.findViewById(R.id.title);
        play_pause = (ImageView) itemView.findViewById(R.id.play_pause);
    }

    public void loadViewHolder(final RecyclerViewAdapter recyclerViewAdapter, String title){
        textViewTitle.setText(title);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.onClick(itemView);
            }
        });
    }
}