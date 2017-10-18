package com.example.ftoustau.startmeapp.Controller;

import android.content.Context;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Model.DAO.DAOChartContainer.DAOInternetChartXGenre;
import com.example.ftoustau.startmeapp.Model.DAO.DAOChartContainer.DaoChartXGenreDatabase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOGenre.DAOGenreDatabase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOGenre.DAOGenreInternet;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Util.HTTPConnectionManager;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * Created by ftoustau on 26/06/17.
 */

public class ControllerAlbumXGenre {
    public void getAlbums (final Context context, final ResultListener<ArrayList<Album>> viewLsitener, Integer id){
        if(HTTPConnectionManager.isNetworkinOnline(context)){
            //pedir a internet
            DAOInternetChartXGenre daoInternetChartXGenre = new DAOInternetChartXGenre();
            daoInternetChartXGenre.getAlbumByGenre(new ResultListener<ArrayList<Album>>() {
                @Override
                public void finish(ArrayList<Album> resultado) {
                    DaoChartXGenreDatabase daoChartXGenreDatabase =new DaoChartXGenreDatabase(context);
                    daoChartXGenreDatabase.addListAlbum(resultado);
                    viewLsitener.finish(resultado);

                }
            },id);

        }else {
            //Pedir a base de datos
            DaoChartXGenreDatabase daoChartXGenreDatabase = new DaoChartXGenreDatabase(context);
            ArrayList<Album> albums = daoChartXGenreDatabase.getListAlbumInDataBase();
            Toast.makeText(context, albums.toString(), Toast.LENGTH_SHORT);
            viewLsitener.finish(albums);
        }
    }

}
