package com.example.ftoustau.startmeapp.Model.DAO;

import java.net.URLClassLoader;

/**
 * Created by digitalhouse on 09/01/17.
 */
public class DeezerHelper {

    public static final String URL_BASE = "https://api.deezer.com/";



    public static String albumPorId(Integer albumId){
        return URL_BASE + "album/" + albumId;
    }

    public static String artistPorId(Integer artistId){
        return URL_BASE + "artist/" + artistId;
    }

    private static String chartPorGenero(Integer genreId){
        return URL_BASE + "chart/" + genreId;
    }

    public static String chartTracksPorGenero(Integer genreId){
        return  URL_BASE + "chart/" + genreId + "/tracks";
    }

    public static String chartArtistsPorGenero(Integer genreId){
        return URL_BASE + "chart/" + genreId + "/artists";
    }

    public static String chartAlbumsPorGenero(Integer genreId){
        return URL_BASE + "chart/" + genreId + "/albums";
    }

    public static String chartPlaylistsPorGenero(Integer genreId){
        return URL_BASE + "chart/" + genreId + "/playlists";
    }

    public static String listaDeGeneros(){
        return URL_BASE + "genre";
    }

    public static String playlistPorId(Integer playlistId){
        return URL_BASE + "playlist/" + playlistId;
    }

    public static String searchAlbum(String searchQuery){
        return URL_BASE + "search/album?q=" + searchQuery;
    }

    public static String searchArtist(String searchQuery){
        return URL_BASE + "search/artist?q=" + searchQuery;
    }

    public static String searchPlaylist(String searchQuery){
        return URL_BASE + "search/playlist?q=" + searchQuery;
    }

    public static String searchTrack(String searchQuery){
        return URL_BASE + "search/track?q=" + searchQuery;
    }

    public static String trackPorId(Integer trackId){
        return URL_BASE + "track/" + trackId;
    }

    public static String userPorId(Integer userId){
        return URL_BASE + "user/" + userId;
    }

}
