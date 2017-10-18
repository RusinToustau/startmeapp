package com.example.ftoustau.startmeapp.Model;

import java.io.Serializable;

/**
 * Created by ftoustau on 09/06/17.
 */

public class Song implements Serializable {
    private String bandName;
    private String songName;
    private String albumName;
    private String musicStyle;
    private Integer idPhotoAlbum;
    private Integer idSong;

    public Song(String bandName, String songName, String albumName, String musicStyle, Integer idPhotoAlbum, Integer idSong) {
        this.bandName = bandName;
        this.songName = songName;
        this.albumName = albumName;
        this.musicStyle = musicStyle;
        this.idPhotoAlbum = idPhotoAlbum;
        this.idSong = idSong;
    }

    public String getBandName() {
        return bandName;
    }

    public String getSongName() {
        return songName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getMusicStyle() {
        return musicStyle;
    }

    public Integer getIdPhotoAlbum() {
        return idPhotoAlbum;
    }

    public Integer getIdSong() {
        return idSong;
    }
}
