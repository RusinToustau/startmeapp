package com.example.ftoustau.startmeapp.View.NavegationMenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MiAdapterFavorito extends RecyclerView.Adapter implements View.OnClickListener{
    private ArrayList<Album> lista;
    private View.OnClickListener listener;

    public MiAdapterFavorito(ArrayList<Album> lista){
        this.lista = lista;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public ArrayList<Album> getLista() {
        return lista;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_favorito, parent, false);
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

    public Album getAlbum(Integer position){
       return lista.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.IV_Album_Favorito);
        }

        public void cargarDato(Album album){

            Picasso.with(iv.getContext()).load(album.getCover_big())
                    .placeholder(R.drawable.icono_disco)
                    .into(iv);
        }
    }
}
