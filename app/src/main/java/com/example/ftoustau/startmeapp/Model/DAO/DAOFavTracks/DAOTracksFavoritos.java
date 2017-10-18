package com.example.ftoustau.startmeapp.Model.DAO.DAOFavTracks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ftoustau.startmeapp.Model.DAO.DatabaseHelper;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;

import java.util.ArrayList;

/**
 * Created by ftoustau on 18/07/17.
 */

public class DAOTracksFavoritos extends DatabaseHelper {
    public static final String TABLE_NAME = "FavoritesTracks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PREVIEW = "preview";
    public static final String COLUMN_ALBUMPHOTO = "albumPhoto";
    public static final String COLUMN_ALBUMPHOTO_MINI = "photoMini";
    public static final String COLUMN_ALBUMTITE = "albumTitle";

    public DAOTracksFavoritos(Context context) {
        super(context);
    }

    public void addTrack(Track track){
        if(!estaEnLADB(track.getId())){
            SQLiteDatabase database =getWritableDatabase();


            ContentValues row = new ContentValues();
            row.put(COLUMN_TITLE, track.getTitle());
            row.put(COLUMN_ID, track.getId());
            row.put(COLUMN_PREVIEW, track.getPreview());
            row.put(COLUMN_ALBUMPHOTO, track.getAlbumPhoto());
            row.put(COLUMN_ALBUMPHOTO_MINI, track.getPhotoMini());
            row.put(COLUMN_ALBUMTITE, track.getAlbumTitle());

            database.insert(TABLE_NAME,null,row);

            database.close();
        }
    }
    public void deleteTrack(Integer id){
        SQLiteDatabase database = getWritableDatabase();

        database.delete(TABLE_NAME, COLUMN_ID + " = " + id, null);

        database.close();
    }

    public void addListTrack(ArrayList<Track> tracks){
        for (Track track : tracks){
            addTrack(track);
        }
    }

    public Boolean estaEnLADB(Integer id){

        SQLiteDatabase database = getWritableDatabase();

        String query = "SELECT "+ COLUMN_ID + " FROM " +
                TABLE_NAME + " where " + COLUMN_ID + " = " +
                + id;

        Cursor cursor = database.rawQuery(query, null);

        Boolean estaEnLaDB = cursor.moveToNext();

        database.close();

        return estaEnLaDB;
    }

    public ArrayList<Track> getListTrackInDataBase(){
        ArrayList<Track> tracks = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();

        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = database.rawQuery(query,null);

        while (cursor.moveToNext()){
            Integer genreId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String preview = cursor.getString(cursor.getColumnIndex(COLUMN_PREVIEW));
            String albumPhoto = cursor.getString(cursor.getColumnIndex(COLUMN_ALBUMPHOTO));
            String photoMini = cursor.getString(cursor.getColumnIndex(COLUMN_ALBUMPHOTO_MINI));
            String albumTitle = cursor.getString(cursor.getColumnIndex(COLUMN_ALBUMTITE));

            Track track = new Track(genreId,title,preview);
            track.setAlbumTitle(albumTitle);
            track.setPhotoMini(photoMini);
            track.setAlbumPhoto(albumPhoto);

            tracks.add(track);
        }

        database.close();
        return tracks;
    }

}
