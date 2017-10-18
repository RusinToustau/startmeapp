package com.example.ftoustau.startmeapp.View.Player;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Controller.MyMediaPlayer;
import com.example.ftoustau.startmeapp.Model.DAO.DAOFavTracks.DAOTracksFavoritos;
import com.example.ftoustau.startmeapp.Model.DAO.DAOTracklist.DAOTracklistDatabase;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;

import java.util.ArrayList;

public class PlayerFragment extends Fragment{
    private View view;
    private ImageButton btnPlay, btnNext, btnPrevious, btnFav, btnAdd;
    private TextView albumTitle, songName, timeProgress, finalTime;
    private MediaPlayer mediaPlayer;
    private DAOTracksFavoritos daoFavoritos;
    private SeekBar seekBar;
    private Handler handler;
    private Notify notify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player, container, false);

        daoFavoritos = new DAOTracksFavoritos(getContext());
        mediaPlayer = MyMediaPlayer.getMediaPlayer();
        albumTitle = (TextView) view.findViewById(R.id.Player_TextView_albumTitle);
        songName = (TextView) view.findViewById(R.id.Player_TextView_SongName);
        btnFav = (ImageButton) view.findViewById(R.id.favBtn);
        seekBar = (SeekBar) view.findViewById(R.id.songDetailSeekBar) ;
        timeProgress = (TextView) view.findViewById(R.id.songTimeActualProgress);
        finalTime = (TextView) view.findViewById(R.id.songTimerMediaPlayerLength);

        finalTime.setText(milisecondsFormatter(MyMediaPlayer.getMediaPlayer().getDuration()));
        audioControl();

        try {
            esFavorito();
            songName.setText(MyMediaPlayer.getTrackname());
            albumTitle.setText(MyMediaPlayer.getAlbumTitle());
        } catch (NullPointerException e){
            Toast.makeText(view.getContext(),
                    "Debe Seleccionar un Track",Toast.LENGTH_SHORT).show();
        }

        if (mediaPlayer.isPlaying()){
            changePauseButton();
        }

        handler = new Handler();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MyMediaPlayer.getMediaPlayer() != null) {
                    int mCurrentPosition = (MyMediaPlayer.getMediaPlayer().getCurrentPosition() * 100) / 30000;
                    seekBar.setProgress(mCurrentPosition);
                    timeProgress.setText(milisecondsFormatter(MyMediaPlayer.getMediaPlayer().getCurrentPosition()));
                }
                handler.postDelayed(this, 1000);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            Integer counter = 0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (MyMediaPlayer.getMediaPlayer() != null && fromUser) {
                    MyMediaPlayer.getMediaPlayer().seekTo(progress * 300);
                }

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Track> myList = MyMediaPlayer.getTracks();
                    Integer position = MyMediaPlayer.getPosition();
                    notify = (Notify) getActivity();
                    notify.returnToViewTrack(myList,position);
                }catch (NullPointerException e){
                    Toast.makeText(view.getContext(),
                            "Debe Seleccionar un Track",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private Boolean esFavorito() {
        if (daoFavoritos.estaEnLADB(MyMediaPlayer.getTrackId())){
            btnFav.setImageResource(R.drawable.ic_favorite_24dp);
            return true;
        }else{
            btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            return false;
        }
    }


    public void audioControl() {
        btnAdd = (ImageButton) view.findViewById(R.id.addList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add Playlist", Toast.LENGTH_SHORT).show();
            }
        });

        btnPlay = (ImageButton) view.findViewById(R.id.Player_Btn_PlayPause);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyMediaPlayer.getMediaPlayer()!=null){
                    if (mediaPlayer.isPlaying()){
                        changePlayButton();
                        MyMediaPlayer.pause();
                    } else {
                        changePauseButton();
                        MyMediaPlayer.resume();
                    }
                } else {
                    Toast.makeText(view.getContext(),
                            "Debe Seleccionar un Track",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnNext = (ImageButton) view.findViewById(R.id.Player_Btn_Next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (MyMediaPlayer.getMediaPlayer()!=null){
                        MyMediaPlayer.nextTrack();
                        changePauseButton();
                        songName.setText(MyMediaPlayer.getTrackname());
                        esFavorito();
                    } else {
                        Toast.makeText(view.getContext(),
                                "Debe Seleccionar un Track",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(view.getContext(),
                            "No hay mas tracks en la lista",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPrevious = (ImageButton) view.findViewById(R.id.Player_Btn_Previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (MyMediaPlayer.getMediaPlayer()!=null){
                        MyMediaPlayer.previusTrack();
                        changePauseButton();
                        songName.setText(MyMediaPlayer.getTrackname());
                        esFavorito();
                    } else {
                        Toast.makeText(view.getContext(),
                                "Debe Seleccionar un Track",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(view.getContext(),
                            "No hay mas tracks en la lista",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!esFavorito()){
                        btnFav.setImageResource(R.drawable.ic_favorite_24dp);
                        daoFavoritos.addTrack(MyMediaPlayer.getActualTrack());
                        Toast.makeText(v.getContext(),"Se Agrego de la lista de favoritos",Toast.LENGTH_SHORT).show();
                    }else {
                        btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        daoFavoritos.deleteTrack(MyMediaPlayer.getTrackId());
                        Toast.makeText(v.getContext(),"Se Elimino de la lista de favoritos",Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e){
                    Toast.makeText(view.getContext(),
                            "Debe Seleccionar un Track",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private String milisecondsFormatter(Integer totalDurationInMS) {

        Integer seconds = (totalDurationInMS / 1000) % 60;
        Integer minutes = ((totalDurationInMS / 1000) / 60) % 60;
        String durationFormatted = "";

        if(seconds < 10) {
            durationFormatted = minutes + ":0" + seconds;
        } else {
            durationFormatted = minutes + ":" + seconds;
        }

        return durationFormatted;
    }

    public void changePauseButton() {
        btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
    }

    public void changePlayButton() {
        btnPlay.setImageResource(R.drawable.icoplayron);
    }

    public void changeFavButton() {
        btnFav.setImageResource(R.drawable.ic_favorite_24dp);
    }

    public interface Notify{
        void returnToViewTrack(ArrayList<Track> tracks,Integer position);
    }
}
