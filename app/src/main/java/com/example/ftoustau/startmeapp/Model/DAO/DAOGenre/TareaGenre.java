package com.example.ftoustau.startmeapp.Model.DAO.DAOGenre;

import android.os.AsyncTask;

import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.GenreContainer;
import com.example.ftoustau.startmeapp.Util.DAOException;
import com.example.ftoustau.startmeapp.Util.HTTPConnectionManager;
import com.example.ftoustau.startmeapp.Util.ResultListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ftoustau on 22/06/17.
 */

public class TareaGenre extends AsyncTask<String,Void,ArrayList<Genre>>{
    private ResultListener<ArrayList<Genre>> controllerListener;
    ArrayList<Genre> genreArrayList;

    public TareaGenre(ResultListener<ArrayList<Genre>> controllerListener) {
        this.controllerListener = controllerListener;
    }


    @Override
    protected ArrayList<Genre> doInBackground(String... params) {
        String url = params [0];
        genreArrayList = new ArrayList<>();

        HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = httpConnectionManager.getRequestStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            GenreContainer trackContainer = gson.fromJson(bufferedReader, GenreContainer.class);
            genreArrayList = trackContainer.getData();



        } catch (DAOException e) {
            e.printStackTrace();
        }

        return genreArrayList;

    }

    @Override
    protected void onPostExecute(ArrayList<Genre> genres) {
        super.onPostExecute(genres);
        controllerListener.finish(genres);
    }
}
