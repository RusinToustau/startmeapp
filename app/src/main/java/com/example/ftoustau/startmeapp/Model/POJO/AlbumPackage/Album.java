package com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage;

import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;

/**
 * Created by ftoustau on 09/06/17.
 */

public class Album {
    private Integer id;
    private String title;
    private String cover_medium;
    private Integer genre_id;
    private String tracklist;
    private String share;
    private String cover_big;
    private String cover_xl;

    public Album(Integer id, String title, String cover_medium, Integer genre_id,
                 String tracklist, String share, String cover_big, String cover_xl) {
        this.id = id;
        this.title = title;
        this.cover_medium = cover_medium;
        this.genre_id = genre_id;
        this.tracklist = tracklist;
        this.share = share;
        this.cover_big = cover_big;
        this.cover_xl = cover_xl;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public String getTracklist() {
        return tracklist;
    }

    public String getShare() {
        return share;
    }

    public String getCover_big() {
        return cover_big;
    }

    public String getCover_xl() {
        return cover_xl;
    }
}
