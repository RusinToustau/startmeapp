package com.example.ftoustau.startmeapp.View.SplashActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.Login.SignInActivity;
import com.example.ftoustau.startmeapp.View.MainActivity;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
