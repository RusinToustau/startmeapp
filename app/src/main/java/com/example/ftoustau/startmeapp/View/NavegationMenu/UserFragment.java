package com.example.ftoustau.startmeapp.View.NavegationMenu;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ftoustau.startmeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserFragment extends Fragment {
    private Button signOut;
    private Notify notify;
    private FirebaseAuth mAuth;
    private TextView userName;
    private ImageView fotoPerfil;
    private TextView userMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        //  mAuth = FirebaseAuth.getInstance();

        userName = (TextView)view.findViewById(R.id.userNameMenu);
        userMail = (TextView)view.findViewById(R.id.userMailMenu);
        fotoPerfil = (ImageView)view.findViewById(R.id.fotoPerfilMenu);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            userName.setText(user.getDisplayName());
            userMail.setText(user.getEmail());
            Glide.with(view.getContext()).load(user.getPhotoUrl()).into(fotoPerfil);

        } else {
            userName.setText("No hay usuario activo");
                // No user is signed in

        }





        signOut = (Button) view.findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify.signOut();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notify = (Notify) getActivity();
    }

    public interface Notify {
        void signOut();
    }


}
