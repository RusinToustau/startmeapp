package com.example.ftoustau.startmeapp.Model.DAO.DAOChartContainer;

import com.example.ftoustau.startmeapp.Model.DAO.DeezerHelper;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.AlbumContainer;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * Created by ftoustau on 26/06/17.
 */

public class DAOInternetChartXGenre {
    private DeezerHelper deezerHelper = new DeezerHelper();

    public void getAlbumByGenre(ResultListener< ArrayList<Album> > listener, Integer id){
        TareaChartXGenre tareaChartXGenre = new TareaChartXGenre(listener);
        tareaChartXGenre.execute(deezerHelper.chartAlbumsPorGenero(id));
    }

}
