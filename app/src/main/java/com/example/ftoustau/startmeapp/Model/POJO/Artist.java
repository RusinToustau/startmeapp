package com.example.ftoustau.startmeapp.Model.POJO;

/**
 * Created by ftoustau on 26/06/17.
 */

public class Artist {
    private Integer id;
    private String name;
    private String picture_medium;

    public Artist(Integer id, String name, String picture_medium) {
        this.id = id;
        this.name = name;
        this.picture_medium = picture_medium;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture_medium() {
        return picture_medium;
    }
}
