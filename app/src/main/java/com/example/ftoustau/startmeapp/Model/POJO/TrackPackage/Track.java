package com.example.ftoustau.startmeapp.Model.POJO.TrackPackage;

import com.example.ftoustau.startmeapp.Model.POJO.Artist;

import java.io.Serializable;

/**
 * Created by ftoustau on 26/06/17.
 */

public class Track implements Serializable {

    private Integer id;
    private String title;
    private String preview;
    private String albumPhoto;
    private String photoMini;
    private String albumTitle;

    public Track(Integer id, String title, String preview) {
        this.id = id;
        this.title = title;
        this.preview = preview;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPreview() {
        return preview;
    }

    public String getAlbumPhoto() {
        return albumPhoto;
    }

    public void setAlbumPhoto(String albumPhoto) {
        this.albumPhoto = albumPhoto;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getPhotoMini() {
        return photoMini;
    }

    public void setPhotoMini(String photoMini) {
        this.photoMini = photoMini;
    }
}
