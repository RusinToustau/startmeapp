package com.example.ftoustau.startmeapp.View.TrackView;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Controller.MyMediaPlayer;
import com.example.ftoustau.startmeapp.Model.DAO.DAOFavTracks.DAOTracksFavoritos;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.R;
import com.squareup.picasso.Picasso;

import info.abdolahi.CircularMusicProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackItemView extends Fragment {
    private View view;
    private Notify notify;
    private ImageView fondo;
    private ImageView trackImageView;
    private TextView textViewSong;
    private MediaPlayer mediaPlayer;
    private ImageButton btnPlay, btnFav , btnHome;
    private DAOTracksFavoritos daoFavoritos;
    private Integer id;
    private String title;
    private String preview;
    private String albumFoto;
    private String albumTitle;
    private String pictureMini;

    public static final String IDKEY = "id";
    public static final String TITLEKEY = "title";
    public static final String URLKEY = "url";
    public static final String PICTUREURL = "pictureUrl";
    public static final String PICTUREMINI_KEY = "mini";
    public static final String ALBUMTITLE_KEY = "albumtitle";

    public static TrackItemView fragmentFactory(Track track) {
        // Required empty public constructor
        Bundle bundle = new Bundle();
        bundle.putInt(IDKEY,track.getId());
        bundle.putString(TITLEKEY, track.getTitle());
        bundle.putString(URLKEY,track.getPreview());
        bundle.putString(PICTUREURL,track.getAlbumPhoto());
        bundle.putString(PICTUREMINI_KEY,track.getAlbumPhoto());
        bundle.putString(ALBUMTITLE_KEY,track.getAlbumPhoto());

        TrackItemView trackItemView = new TrackItemView();
        trackItemView.setArguments(bundle);
        return trackItemView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_track_item_view, container, false);

        mediaPlayer = MyMediaPlayer.getMediaPlayer();

        daoFavoritos = new DAOTracksFavoritos(view.getContext());

        textViewSong = (TextView) view.findViewById(R.id.TrackName);
        trackImageView = (ImageView) view.findViewById(R.id.TrackImageView);
        fondo = (ImageView) view.findViewById(R.id.IVportada);
        btnPlay = (ImageButton) view.findViewById(R.id.Track_Btn_PlayPause);
        btnFav = (ImageButton) view.findViewById(R.id.favBoton);
        btnHome = (ImageButton) view.findViewById(R.id.goHomefromTrack);

        Bundle bundle = getArguments();
        id = bundle.getInt(IDKEY);
        title = bundle.getString(TITLEKEY);
        preview = bundle.getString(URLKEY);
        albumFoto = bundle.getString(PICTUREURL);
        pictureMini = bundle.getString(PICTUREMINI_KEY);
        albumTitle = bundle.getString(ALBUMTITLE_KEY);

        textViewSong.setText(title);
        Picasso.with(getContext()).load(albumFoto).into(trackImageView);
        Picasso.with(getContext()).load(albumFoto).into(fondo);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = (Notify) getActivity();
                notify.returnHome();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    changePlayButton();
                    MyMediaPlayer.pause();
                } else {
                    changePauseButton();
                    MyMediaPlayer.resume();
                }
            }
        });

        ImageButton next = (ImageButton) view.findViewById(R.id.Player_Btn_Next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = (Player) getActivity();
                player.next();
            }
        });

        ImageButton previous = (ImageButton) view.findViewById(R.id.Btn_Previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = (Player) getActivity();
                player.previous();
            }
        });

        esFavorito();

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!esFavorito()){
                        btnFav.setImageResource(R.drawable.ic_favorite_24dp);
                        daoFavoritos.addTrack(getActualTrack());
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
        return view;
    }

    public Track getActualTrack(){
        Track mytrack = new Track(id,title,preview);
        mytrack.setAlbumPhoto(albumFoto);
        mytrack.setAlbumTitle(albumTitle);
        mytrack.setPhotoMini(pictureMini);
        return mytrack;
    }

    private Boolean esFavorito() {
        if (daoFavoritos.estaEnLADB(id)){
            btnFav.setImageResource(R.drawable.ic_favorite_24dp);
            return true;
        }else{
            btnFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            return false;
        }
    }

    public interface Player{
        void next();
        void previous();
    }

    public interface Notify{
        void returnHome();
    }
    public void changePauseButton() {
        btnPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);}

    public void changePlayButton() {
        btnPlay.setImageResource(R.drawable.icoplayron);
    }

    public void changeFavButton() {
        btnFav.setImageResource(R.drawable.ic_favorite_24dp);
    }

}
