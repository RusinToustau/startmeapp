package com.example.ftoustau.startmeapp.Util;

import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;

import java.util.ArrayList;

public interface ResultListener<T> {

    void finish(T resultado);
}