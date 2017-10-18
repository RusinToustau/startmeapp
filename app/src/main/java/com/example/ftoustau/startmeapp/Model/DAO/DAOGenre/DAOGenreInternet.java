package com.example.ftoustau.startmeapp.Model.DAO.DAOGenre;

import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * Created by ftoustau on 22/06/17.
 */

public class DAOGenreInternet {
    public void getTrackListFromInternet (ResultListener<ArrayList<Genre>> controllerListener){
        TareaGenre tarea = new TareaGenre (controllerListener);
        tarea.execute("http://api.deezer.com/genre");
    }
}
