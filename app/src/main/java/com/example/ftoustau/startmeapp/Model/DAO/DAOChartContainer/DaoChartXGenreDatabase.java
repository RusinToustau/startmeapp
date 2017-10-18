package com.example.ftoustau.startmeapp.Model.DAO.DAOChartContainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ftoustau.startmeapp.Model.DAO.DatabaseHelper;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;

import java.util.ArrayList;

/**
 * Created by ftoustau on 26/06/17.
 */

public class DaoChartXGenreDatabase extends DatabaseHelper {
    public static final String TABLE_NAME = "Albums";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PICTURE = "cover_medium";
    public static final String COLUMN_GENREID = "genre_id";
    public static final String COLUMN_TRACKLIST = "tracklist";
    public static final String COLUMN_SHARE = "share";
    public static final String COLUMN_COVERBIG = "cover_big";
    public static final String COLUMN_COVERXL = "cover_xl";

    public DaoChartXGenreDatabase(Context context) {
        super(context);
    }

    public void addAlbum(Album album){
        if(!estaEnLADB(album.getId())){
            SQLiteDatabase database =getWritableDatabase();


            ContentValues row = new ContentValues();
            row.put(COLUMN_TITLE, album.getTitle());
            row.put(COLUMN_ID, album.getId());
            row.put(COLUMN_PICTURE, album.getCover_medium());
            row.put(COLUMN_GENREID, album.getGenre_id());
            row.put(COLUMN_TRACKLIST, album.getTracklist());
            row.put(COLUMN_SHARE, album.getShare());
            row.put(COLUMN_COVERBIG, album.getCover_big());
            row.put(COLUMN_COVERXL, album.getCover_xl());

            database.insert(TABLE_NAME,null,row);

            database.close();
        }
    }

    public void addListAlbum(ArrayList<Album> albums){
        for (Album album : albums){
            addAlbum(album);
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


    public ArrayList<Album> getListAlbumInDataBase(){
        ArrayList<Album> albums = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();

        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = database.rawQuery(query,null);

        while (cursor.moveToNext()){
            Integer genreId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String picture = cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE));
            Integer genre_id = cursor.getInt(cursor.getColumnIndex(COLUMN_GENREID));
            String tracklist = cursor.getString(cursor.getColumnIndex(COLUMN_TRACKLIST));
            String share = cursor.getString(cursor.getColumnIndex(COLUMN_SHARE));
            String big = cursor.getString(cursor.getColumnIndex(COLUMN_COVERBIG));
            String xl = cursor.getString(cursor.getColumnIndex(COLUMN_COVERXL));

            Album album = new Album(genreId,title,picture,genre_id,tracklist,share,big,xl);
            albums.add(album);
        }

        database.close();
        return albums;
    }

}
