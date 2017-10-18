package com.example.ftoustau.startmeapp.Model.DAO.DAOTracklist;

import android.os.AsyncTask;

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
 * Created by ftoustau on 29/06/17.
 */

public class TareaTracklist extends AsyncTask<String,Void,ArrayList<Track>> {
    private ResultListener<ArrayList<Track>> controllerListener;
    ArrayList<Track> trackArrayList;

    public TareaTracklist(ResultListener<ArrayList<Track>> controllerListener) {
        this.controllerListener = controllerListener;
    }

    @Override
    protected ArrayList<Track> doInBackground(String... params) {
        String url = params [0];
        trackArrayList = new ArrayList<>();

        HTTPConnectionManager httpConnectionManager = new HTTPConnectionManager();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {

            inputStream = httpConnectionManager.getRequestStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Gson gson = new Gson();
            TrackContainer trackContainer = gson.fromJson(bufferedReader, TrackContainer.class);
            trackArrayList = trackContainer.getData();


        } catch (DAOException e) {
            e.printStackTrace();
        }

        return trackArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        super.onPostExecute(tracks);
        controllerListener.finish(tracks);
    }
}
