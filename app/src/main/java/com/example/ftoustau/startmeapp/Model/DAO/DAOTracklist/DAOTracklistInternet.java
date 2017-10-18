package com.example.ftoustau.startmeapp.Model.DAO.DAOTracklist;

import com.example.ftoustau.startmeapp.Model.DAO.DAOGenre.TareaGenre;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * Created by ftoustau on 29/06/17.
 */

public class DAOTracklistInternet {
    public void getTrackListFromInternet (ResultListener<ArrayList<Track>> controllerListener, String url){
        TareaTracklist tarea = new TareaTracklist (controllerListener);
        tarea.execute(url);
    }
}
