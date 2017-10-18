package com.example.ftoustau.startmeapp.Controller;

import android.content.Context;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Model.DAO.DAOGenre.DAOGenreDatabase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOGenre.DAOGenreInternet;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Util.HTTPConnectionManager;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * Created by ftoustau on 22/06/17.
 */

public class ControllerGenre {

    public void getGenres (final Context context, final ResultListener<ArrayList<Genre>> viewLsitener){
        if(HTTPConnectionManager.isNetworkinOnline(context)){
            //pedir a internet
            DAOGenreInternet daoGenreInternet = new DAOGenreInternet();


            daoGenreInternet.getTrackListFromInternet(new ResultListener<ArrayList<Genre>>() {
                @Override
                public void finish(ArrayList<Genre> resultado) {
                    //respuesta Internet
                    DAOGenreDatabase daoGenreDatabase = new DAOGenreDatabase(context);
                    daoGenreDatabase.addListGenre(resultado);
                    viewLsitener.finish(resultado);
                }
            });
        }else {
            //Pedir a base de datos

            DAOGenreDatabase daoGenreDatabase = new DAOGenreDatabase(context);
            ArrayList<Genre> genres = daoGenreDatabase.getListGenreInDataBase();

            Toast.makeText(context, genres.toString(), Toast.LENGTH_SHORT);

            viewLsitener.finish(genres);
        }
    }

}
