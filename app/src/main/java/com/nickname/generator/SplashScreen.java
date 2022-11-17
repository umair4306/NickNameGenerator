package com.nickname.generator;

import static com.nickname.generator.MyApplication.showAdIfReady;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.nicknamegenerator.R;
import com.github.ybq.android.spinkit.SpinKitView;

public class SplashScreen extends AppCompatActivity {
    private SpinKitView progressBar;
    private TextView adLoadingTv;
    public static String PACKAGE_NAME;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        PACKAGE_NAME = getApplication().getPackageName();


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showAdIfReady();
                Intent intent = new Intent(SplashScreen.this, OnboardingScreen.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}