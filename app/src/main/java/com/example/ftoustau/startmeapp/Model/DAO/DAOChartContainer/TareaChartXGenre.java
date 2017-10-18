package com.example.ftoustau.startmeapp.Model.DAO.DAOChartContainer;

import android.os.AsyncTask;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.AlbumContainer;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.GenreContainer;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.TrackContainer;
import com.example.ftoustau.startmeapp.Util.DAOException;
import com.example.ftoustau.startmeapp.Util.HTTPConnectionManager;
import com.example.ftoustau.startmeapp.Util.ResultListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ftoustau on 26/06/17.
 */

public class TareaChartXGenre extends AsyncTask<String,Void, ArrayList<Album>> {
    private ResultListener<ArrayList<Album>> listener;
    private ArrayList<Album> albums;

    public TareaChartXGenre(ResultListener<ArrayList<Album>> listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Album> doInBackground(String... params) {
        Object url = params[0];
        albums = new ArrayList<>();

        HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = httpConnectionManager.getRequestStream((String) url);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            AlbumContainer albumContainer = gson.fromJson(bufferedReader, AlbumContainer.class);
            albums = albumContainer.getData();

        } catch (DAOException e) {
            e.printStackTrace();
        }

        return albums;
    }

    @Override
    protected void onPostExecute(ArrayList<Album> album) {
        super.onPostExecute(album);
        listener.finish(album);
    }
}