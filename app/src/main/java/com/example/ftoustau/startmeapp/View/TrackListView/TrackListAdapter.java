package com.example.ftoustau.startmeapp.View.TrackListView;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Target;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private ArrayList<Track> lista;
    private View.OnClickListener listener;

    public TrackListAdapter(ArrayList<Track> lista){
        this.lista = lista;
    }

    public void setList(ArrayList<Track> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }

    public List<Track> getLista() {
        return lista;
    }

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.cargarDato(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView fotoMini;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.TrackTitle);
            fotoMini = (ImageView)itemView.findViewById(R.id.fotito);

        }

        public void cargarDato(Track track){
            title.setText(track.getTitle());
            Picasso.with(itemView.getContext()).load(track.getPhotoMini()).into(fotoMini);
        }
    }
}
