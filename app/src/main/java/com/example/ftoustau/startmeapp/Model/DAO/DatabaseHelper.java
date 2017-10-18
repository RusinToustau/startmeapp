package com.example.ftoustau.startmeapp.Model.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ftoustau.startmeapp.Model.DAO.DAOChartContainer.DaoChartXGenreDatabase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOFavoritesAlbum.DAOFavoriteAlbumDataBase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOGenre.DAOGenreDatabase;
import com.example.ftoustau.startmeapp.Model.DAO.DAOFavTracks.DAOTracksFavoritos;
import com.example.ftoustau.startmeapp.Model.DAO.DAOTracklist.DAOTracklistDatabase;

/**
 * Created by ftoustau on 22/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tcg_db";
    public static final Integer DATABASE_Version = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DAOGenreDatabase.TABLE_NAME + " (" +
                DAOGenreDatabase.COLUMN_NAME + " TEXT, " +
                DAOGenreDatabase.COLUMN_ID + " TEXT PRIMARY KEY, " +
                DAOGenreDatabase.COLUMN_PICTURE + " TEXT);";
        db.execSQL(query);

        String queryAlbum = "CREATE TABLE " + DaoChartXGenreDatabase.TABLE_NAME + " (" +
                DaoChartXGenreDatabase.COLUMN_TITLE + " TEXT NOT NULL, " +
                DaoChartXGenreDatabase.COLUMN_ID + " TEXT PRIMARY KEY, " +
                DaoChartXGenreDatabase.COLUMN_GENREID + " TEXT, " +
                DaoChartXGenreDatabase.COLUMN_PICTURE + " TEXT, " +
                DaoChartXGenreDatabase.COLUMN_TRACKLIST + " TEXT, " +
                DaoChartXGenreDatabase.COLUMN_SHARE + " TEXT, " +
                DaoChartXGenreDatabase.COLUMN_COVERBIG + " TEXT, " +
                DaoChartXGenreDatabase.COLUMN_COVERXL + " TEXT);";
        db.execSQL(queryAlbum);

        String queryFavoriteAlbum = "CREATE TABLE " + DAOFavoriteAlbumDataBase.TABLE_NAME + " (" +
                DAOFavoriteAlbumDataBase.COLUMN_TITLE + " TEXT NOT NULL, " +
                DAOFavoriteAlbumDataBase.COLUMN_ID + " TEXT PRIMARY KEY, " +
                DAOFavoriteAlbumDataBase.COLUMN_GENREID + " TEXT, " +
                DAOFavoriteAlbumDataBase.COLUMN_PICTURE + " TEXT, " +
                DAOFavoriteAlbumDataBase.COLUMN_TRACKLIST + " TEXT, " +
                DAOFavoriteAlbumDataBase.COLUMN_SHARE + " TEXT, " +
                DAOFavoriteAlbumDataBase.COLUMN_COVERBIG + " TEXT, " +
                DAOFavoriteAlbumDataBase.COLUMN_COVERXL + " TEXT);";
        db.execSQL(queryFavoriteAlbum);


        String queryTrack = "CREATE TABLE " + DAOTracklistDatabase.TABLE_NAME + " (" +
                DAOTracklistDatabase.COLUMN_TITLE + " TEXT, " +
                DAOTracklistDatabase.COLUMN_ID + " TEXT PRIMARY KEY, " +
                DAOTracklistDatabase.COLUMN_PREVIEW + " TEXT);";
        db.execSQL(queryTrack);

        String queryFavoriteTracks = "CREATE TABLE " + DAOTracksFavoritos.TABLE_NAME + " (" +
                DAOTracksFavoritos.COLUMN_TITLE + " TEXT, " +
                DAOTracksFavoritos.COLUMN_ALBUMPHOTO + " TEXT, " +
                DAOTracksFavoritos.COLUMN_ALBUMPHOTO_MINI + " TEXT, " +
                DAOTracksFavoritos.COLUMN_ALBUMTITE + " TEXT, " +
                DAOTracksFavoritos.COLUMN_ID + " TEXT PRIMARY KEY, " +
                DAOTracksFavoritos.COLUMN_PREVIEW + " TEXT);";
        db.execSQL(queryFavoriteTracks);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
