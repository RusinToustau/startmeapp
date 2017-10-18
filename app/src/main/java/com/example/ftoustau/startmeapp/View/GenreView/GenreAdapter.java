package com.example.ftoustau.startmeapp.View.GenreView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ftoustau on 18/06/17.
 */

public class GenreAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<Genre> genreList;
    private View.OnClickListener listener;

    public GenreAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = (ArrayList<Genre>) genreList;
    }

    public void setGenreList(ArrayList<Genre> genreList) {
        this.genreList = genreList;
        notifyDataSetChanged();

    }

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_list_genre, parent, false);
        view.setOnClickListener(this);

        GenreViewHolder viewHolder = new GenreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        GenreViewHolder viewHolder = (GenreViewHolder) holder;
        viewHolder.UpDateData(genre);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public Genre getItemAtPosition(Integer position){
        return genreList.get(position);
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public GenreViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ItemGenre_ImageView);
            textView = (TextView) itemView.findViewById(R.id.ItemGenre_TextView);
        }

        public void UpDateData(Genre genre){
            textView.setText(genre.getName());

            Picasso.with(imageView.getContext()).load(genre.getPicture_medium())
                    .placeholder(R.drawable.icono_disco)
                    .into(imageView);
        }
    }
}
