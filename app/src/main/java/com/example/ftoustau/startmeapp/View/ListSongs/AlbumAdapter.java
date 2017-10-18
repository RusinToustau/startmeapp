package com.example.ftoustau.startmeapp.View.ListSongs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.Model.Song;

import java.util.ArrayList;

/**
 * Created by ftoustau on 09/06/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<Song> list;
    private View.OnClickListener listener;

    public AlbumAdapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_list_songs,parent,false);
        view.setOnClickListener(this);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setList(ArrayList<Song> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Song song = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.upLoadData(song);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public Song getItem(Integer position) {
        return list.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewBandName;
        TextView textViewSongName;
        TextView textViewAlbumName;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ListFrag_ImageViewAlbum);
            textViewBandName = (TextView) itemView.findViewById(R.id.ListFrag_TV_BandName);
            textViewSongName = (TextView) itemView.findViewById(R.id.ListFrag_TV_SongName);
            textViewAlbumName = (TextView) itemView.findViewById(R.id.ListFrag_TV_AlbumName);
        }

        public void upLoadData(Song song){
            imageView.setImageResource(song.getIdPhotoAlbum());
            textViewAlbumName.setText(song.getAlbumName());
            textViewBandName.setText(song.getBandName());
            textViewSongName.setText(song.getSongName());
        }

    }
}
