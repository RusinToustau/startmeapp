package com.example.ftoustau.startmeapp.View;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Controller.MediaPlayerService;
import com.example.ftoustau.startmeapp.Model.DAO.StorageUtil;
import com.example.ftoustau.startmeapp.Model.POJO.AlbumPackage.Album;
import com.example.ftoustau.startmeapp.Model.POJO.Audio;
import com.example.ftoustau.startmeapp.Model.POJO.GenrePackage.Genre;
import com.example.ftoustau.startmeapp.Model.POJO.TrackPackage.Track;
import com.example.ftoustau.startmeapp.Model.Song;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.AudioInterno.BlankFragment;
import com.example.ftoustau.startmeapp.View.CustomViewPager.ChartsFragment;
import com.example.ftoustau.startmeapp.View.CustomViewPager.ViewPagerCustomFragment;
import com.example.ftoustau.startmeapp.View.GenreView.GenreFragment;
import com.example.ftoustau.startmeapp.View.ListSongs.AlbumFragment;
import com.example.ftoustau.startmeapp.View.Login.SignInActivity;
import com.example.ftoustau.startmeapp.View.MainMenu.MenuFragment;
import com.example.ftoustau.startmeapp.View.NavegationMenu.FavoritosFragment;
import com.example.ftoustau.startmeapp.View.NavegationMenu.FavoriteTracksFragment;
import com.example.ftoustau.startmeapp.View.NavegationMenu.UserFragment;
import com.example.ftoustau.startmeapp.View.Player.PlayerFragment;
import com.example.ftoustau.startmeapp.View.Song.ItemSongFragment;
import com.example.ftoustau.startmeapp.View.Song.ViewPagerSongFragment;
import com.example.ftoustau.startmeapp.View.TrackListView.TrackListFragment;
import com.example.ftoustau.startmeapp.View.TrackView.ViewTrackActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity implements
        UserFragment.Notify,
        AlbumFragment.Notify,
        ChartsFragment.Notify,
        TrackListFragment.Notify,
        FavoritosFragment.Notify,
        ItemSongFragment.Notify,
        FavoriteTracksFragment.Notify,
        PlayerFragment.Notify,
        BlankFragment.Notify,
        GenreFragment.NotifyGenre{

    private static final String TAG_MENU_FRAGMENT = "menuFragment";
    private static final String TAG_USER_FRAGMENT = "userFragment";
    private static final String TAG_FAVS_FRAGMENT = "favsFragment";
    private static final String TAG_PLAYLIST_FRAGMENT = "playListFragment";
    private static final String TAG_VIEWPAGER_CUSTOM_FRAGMENT = "viewPagerCustomFragment";

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final String ACTION_BROADCAST_PLAY_NEW_AUDIO = "com.example.ftoustau.startmeapp.PlayNewAudio";
    public static final String SERVICE_STATUS_KEY = "serviceStatus";

    private MediaPlayerService player;
    private Boolean serviceBound = false;
    private ArrayList<Audio> audioList;
    private MenuFragment menuFragment;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check Permissions
        if (checkAndRequestPermissions()) {
            loadAudioList();
        }

        menuFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BlankFragment.LISTAKEY,audioList);
        menuFragment.setArguments(bundle);
        changeFragment(menuFragment, TAG_MENU_FRAGMENT, false);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.user:
                        UserFragment userFragment = new UserFragment();
                        changeFragment(userFragment, TAG_USER_FRAGMENT, false);
                        break;

                    case R.id.fav_songs:
                        FavoritosFragment favoritosFragment = new FavoritosFragment();
                        changeFragment(favoritosFragment, TAG_FAVS_FRAGMENT, false);
                        break;

                    case R.id.playlist:
                        FavoriteTracksFragment favoriteTracksFragment = new FavoriteTracksFragment();
                        changeFragment(favoriteTracksFragment, TAG_PLAYLIST_FRAGMENT, false);
                        break;

                }

                drawer.closeDrawers();

                return false;
            }
        });
    }


    public void changeFragment(Fragment fragment, String tag, Boolean forceAddToBackstack){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.contentElse);

        if (currentFragment == null || currentFragment.getTag() == null || !currentFragment.getTag().equals(tag)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentElse, fragment, tag);
            if (forceAddToBackstack ||
                    (currentFragment != null && currentFragment.getTag() != null && currentFragment.getTag().equals(TAG_MENU_FRAGMENT))) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void goViewPagerSong(ArrayList<Song> songs, Integer position) {
        changeFragment(ViewPagerSongFragment.createViewPagerSongFragment(songs, position), null, true);
    }


    @Override
    public void toBuildTheCustomerStyle(Genre genre) {
        ViewPagerCustomFragment fragment = new ViewPagerCustomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerCustomFragment.ID_KEY,genre.getId());
        fragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentElse,fragment, TAG_VIEWPAGER_CUSTOM_FRAGMENT);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void goTrackLsit(Album album) {
        TrackListFragment trackListFragment = new TrackListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TrackListFragment.ID_KEY, album.getId());
        bundle.putString(TrackListFragment.TITLE_KEY, album.getTitle());
        bundle.putString(TrackListFragment.TRACKLIST_KEY, album.getTracklist());
        bundle.putString(TrackListFragment.PICTURE_KEY, album.getCover_medium());
        bundle.putString(TrackListFragment.SHARE_KEY, album.getShare());
        bundle.putString(TrackListFragment.COVERBIG_KEY, album.getCover_big());
        bundle.putString(TrackListFragment.COVERXL_KEY, album.getCover_xl());


        trackListFragment.setArguments(bundle);
        trackListFragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contentElse,trackListFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,SignInActivity.class);
        startActivity(intent);
    }

    private void loadAudioList() {
        loadAudio();
    }

    private Boolean checkAndRequestPermissions() {
        // Si la version del android es superior o igual a Marshmallow
        if (SDK_INT >= Build.VERSION_CODES.M) {

            //Tengo permiso de ver el estado del telefono?
            Integer permissionReadPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            //Tengo permiso de leer el almacenamiento?
            Integer permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);


            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                // Tengo que pedir permisos

                // Convierto el arrayList en una lista estatica
                String[] staticListPermissionNeed = listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]);
                ActivityCompat.requestPermissions(this, staticListPermissionNeed, REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            } else {
                // Esta ok, tengo todos los permisos
                return true;
            }
        }

        // Si es menor a MarshMallow soné, me quede sin permisos
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        String TAG = "LOG_PERMISSION";
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {

                    for (Integer i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    // Check for both permissions

                    if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d(TAG, "Phone state and storage permissions granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                        loadAudioList();
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                      //shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("Phone state and storage permissions required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


    private void initRecyclerView() {

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(SERVICE_STATUS_KEY, serviceBound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean(SERVICE_STATUS_KEY);
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Toast.makeText(getApplicationContext(), "onServiceConnected", Toast.LENGTH_SHORT).show();
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
            Toast.makeText(getApplicationContext(), "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };

    // Carga la lista con Audio
    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            audioList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                // Save to audioList
                audioList.add(new Audio(data, title, album, artist));
            }
        }
        cursor.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

    @Override
    public void playAudiio(Integer position) {
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil.storeAudio(this, audioList);
            StorageUtil.storeAudioIndex(this, position);

            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil.storeAudioIndex(this, position);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(ACTION_BROADCAST_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void goToViewTrackActivity(ArrayList<Track> list, Integer position) {
        Intent intent = new Intent(this,ViewTrackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ViewTrackActivity.KEYPOSITION,position);
        bundle.putSerializable(ViewTrackActivity.KEYLIST,list);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void toViewAlbum(Album album) {
        goTrackLsit(album);
    }

    @Override
    public void goHome() {
        changeFragment(menuFragment, TAG_MENU_FRAGMENT, false);
    }


    @Override
    public void next(ArrayList<Track> list, Integer position) {
        goToViewTrackActivity(list,position);
    }

    @Override
    public void returnToViewTrack(ArrayList<Track> tracks, Integer position) {
        goToViewTrackActivity(tracks,position);
    }
}
