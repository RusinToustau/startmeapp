package com.example.ftoustau.startmeapp.Model.DAO.DAOGenre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ftoustau.startmeapp.Model.DAO.DatabaseHelper;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;

import java.util.ArrayList;

/**
 * Created by ftoustau on 22/06/17.
 */

public class DAOGenreDatabase extends DatabaseHelper {
    public static final String TABLE_NAME = "Genres";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PICTURE = "picture_medium";


    public DAOGenreDatabase(Context context) {
        super(context);
    }

    public void addGenre(Genre genre){
        if(!estaEnLADB(genre.getId())){
            SQLiteDatabase database =getWritableDatabase();


            ContentValues row = new ContentValues();
            row.put(COLUMN_NAME, genre.getName());
            row.put(COLUMN_ID, genre.getId());
            row.put(COLUMN_PICTURE, genre.getPicture_medium());

            database.insert(TABLE_NAME,null,row);

            database.close();
        }
    }

    public void addListGenre(ArrayList<Genre> genres){
        for (Genre genre : genres){
            addGenre(genre);
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

    public ArrayList<Genre> getListGenreInDataBase(){
        ArrayList<Genre> genres = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();

        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = database.rawQuery(query,null);

        while (cursor.moveToNext()){
            Integer genreId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String picture = cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE));
            Genre genre = new Genre(genreId,name,picture);
            genres.add(genre);
        }

        database.close();
        return genres;
    }
}
