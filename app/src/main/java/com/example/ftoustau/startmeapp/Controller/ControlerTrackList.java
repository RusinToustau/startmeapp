package com.example.ftoustau.startmeapp.Controller;

import android.content.Context;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Model.DAO.DAOTracklist.DAOTracklistDatabase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOTracklist.DAOTracklistInternet;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Util.HTTPConnectionManager;
import com.example.ftoustau.startmeapp.Util.ResultListener;

import java.util.ArrayList;

/**
 * Created by ftoustau on 29/06/17.
 */

public class ControlerTrackList {

    public void getTrackList(final Context context, final ResultListener<ArrayList<Track>> viewListener, String url){
        if(HTTPConnectionManager.isNetworkinOnline(context)){
            DAOTracklistInternet daoTracklistInternet = new DAOTracklistInternet();
            daoTracklistInternet.getTrackListFromInternet(new ResultListener<ArrayList<Track>>() {
                @Override
                public void finish(ArrayList<Track> resultado) {
                    DAOTracklistDatabase daoTracklistDatabase = new DAOTracklistDatabase(context);
                    daoTracklistDatabase.addListTrack(resultado);
                    viewListener.finish(resultado);
                }
            },url);
        } else {
            DAOTracklistDatabase daoTracklistDatabase = new DAOTracklistDatabase(context);
            ArrayList<Track> tracks = daoTracklistDatabase.getListTrackInDataBase();
            Toast.makeText(context, tracks.toString(), Toast.LENGTH_SHORT);
            viewListener.finish(tracks);
        }
    }



}
