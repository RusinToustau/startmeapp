package com.example.ftoustau.startmeapp.View.TrackListView;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Controller.ControlerTrackList;
import com.example.ftoustau.startmeapp.Model.DAO.DAOFavoritesAlbum.DAOFavoriteAlbumDataBase;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Model.Song;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.Util.ResultListener;
import com.example.ftoustau.startmeapp.View.ListSongs.AlbumAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackListFragment extends Fragment {
    public static final String ID_KEY= "id";
    public static final String TRACKLIST_KEY = "backGround";
    public static final String TITLE_KEY = "title";
    public static final String GENREID_KEY = "genre_id";
    public static final String PICTURE_KEY = "picture";
    public static final String SHARE_KEY = "share";
    public static final String COVERBIG_KEY = "cover_big";
    public static final String COVERXL_KEY = "cover_xl";

    private Notify notify;
    private ArrayList<Track> arrayList;
    private Context context;
    private RecyclerView recyclerView;
    private View view;
    private TrackListAdapter adapter;
    private Album myAlbum;
    private String pictureXl;
    private String albumTitle;
    private String picture;
    private DAOFavoriteAlbumDataBase daoFavoriteAlbumDataBase;
    private FloatingActionButton fabPlayAll, fabFavoriteAlbum, fabNativeShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.track_list, container, false);
        context = getContext();
        Bundle bundle = getArguments();

        Integer idAlbum = bundle.getInt(ID_KEY);
        String title = bundle.getString(TITLE_KEY);
        String tracklist = bundle.getString(TRACKLIST_KEY);
        picture = bundle.getString(PICTURE_KEY);
        String share = bundle.getString(SHARE_KEY);
        String pictureBig = bundle.getString(COVERXL_KEY);
        pictureXl = bundle.getString(COVERBIG_KEY);
        albumTitle = bundle.getString(TITLE_KEY);

        myAlbum = new Album(idAlbum,title,picture,null,tracklist,share,pictureBig,pictureXl);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        daoFavoriteAlbumDataBase = new DAOFavoriteAlbumDataBase(context);

        fabFavoriteAlbum = (FloatingActionButton) view.findViewById(R.id.accion_favorito);
        if(daoFavoriteAlbumDataBase.estaEnLADB(myAlbum.getId())){
            fabFavoriteAlbum.setImageResource(R.drawable.ic_favorite_24dp);
        }else {
            fabFavoriteAlbum.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        fabFavoriteAlbum.setOnClickListener(onClickListener);

        fabPlayAll = (FloatingActionButton) view.findViewById(R.id.playAllTracks);
        fabPlayAll.setOnClickListener(onClickListener);

        fabNativeShare = (FloatingActionButton) view.findViewById(R.id.accion_share);
        fabNativeShare.setOnClickListener(onClickListener);
//        fabPlayAll = (FloatingActionButton) view.findViewById(R.id.accion_favorito);

        // Find the Collapse ToolBar component
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.colapseToolbar);
        // Set Title
        collapsingToolbarLayout.setTitle(title);
        // Set Title Expanded Color
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        // Set Scrim Color
        collapsingToolbarLayout.setContentScrimResource(R.color.colorPrimary);
        collapsingToolbarLayout.setStatusBarScrimResource(R.color.colorPrimaryDark);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTrackList);
        context = getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        arrayList = new ArrayList<>();

        adapter = new TrackListAdapter(arrayList);

        ControlerTrackList controlerTrackList = new ControlerTrackList();
        controlerTrackList.getTrackList(context, new ResultListener<ArrayList<Track>>() {
            @Override
            public void finish(ArrayList<Track> resultado) {
                for(Track track : resultado){
                    track.setAlbumPhoto(pictureXl);
                    track.setPhotoMini(picture);
                    track.setAlbumTitle(albumTitle);
                }
                adapter.setList(resultado);
                arrayList = resultado;
            }
        },tracklist);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = recyclerView.getChildAdapterPosition(v);
                notify = (Notify) getActivity();
                arrayList = (ArrayList<Track>) adapter.getLista();
                notify.goToViewTrackActivity(arrayList,position);/*cambiar array por el obtenido del adapter*/
            }
        };
        adapter.setListener(listener);
        ImageView backGround = (ImageView) view.findViewById(R.id.imageViewTrackList);
        Picasso.with(context).load(pictureXl).into(backGround);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.accion_favorito:
                    if(!daoFavoriteAlbumDataBase.estaEnLADB(myAlbum.getId())){
                        fabFavoriteAlbum.setImageResource(R.drawable.ic_favorite_24dp);
                        daoFavoriteAlbumDataBase.addAlbum(myAlbum);
                        Toast.makeText(context,"Se agrego de la lista de favoritos",Toast.LENGTH_SHORT).show();
                    }else {
                        fabFavoriteAlbum.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        daoFavoriteAlbumDataBase.deleteAlbum(myAlbum.getId());
                        Toast.makeText(context,"Se elimino de la lista de favoritos",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.accion_share:

                    //Creamos un share de tipo ACTION_SENT
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    //Indicamos que voy a compartir texto
                    share.setType("text/plain");
                    //Le agrego un título
                    share.putExtra(Intent.EXTRA_SUBJECT, "Share");
                    //Le agrego el texto a compartir (Puede ser un link tambien)
                    share.putExtra(Intent.EXTRA_TEXT, "Les comparto alto tema desde StartmeApp");
                    //Hacemos un start para que comparta el contenido.
                    startActivity(Intent.createChooser(share, "https://www.digitalhouse.com/"));

                    break;

                case R.id.playAllTracks:
                    notify = (Notify) getActivity();
                    notify.goToViewTrackActivity(arrayList,0);

                    break;
            }
        }
    };

    public interface Notify{
        void goToViewTrackActivity(ArrayList<Track> list, Integer position);
    }

}
