package com.example.ftoustau.startmeapp.Model.DAO;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ftoustau.startmeapp.Model.POJO.Audio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// USA SHARE PREFERENCE. PODRIA USAR BASES DE DATOS
public class StorageUtil {

    private static final String STORAGE = "com.example.ftoustau.startmeapp.STORAGE";
    private static final String ARRAY_MUSIC_KEY = "audioArrayList";
    private static final String INDEX_MUSIC_KEY = "audioIndex";

    public static void storeAudio(Context context, ArrayList<Audio> arrayList) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(ARRAY_MUSIC_KEY, json);
        editor.apply();
    }

    public static ArrayList<Audio> loadAudio(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(ARRAY_MUSIC_KEY, null);
        Type type = new TypeToken<ArrayList<Audio>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void storeAudioIndex(Context context, Integer index) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(INDEX_MUSIC_KEY, index);
        editor.apply();
    }

    public static Integer loadAudioIndex(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return preferences.getInt(INDEX_MUSIC_KEY, -1);//return -1 if no data found
    }

    public static void clearCachedAudioPlaylist(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
