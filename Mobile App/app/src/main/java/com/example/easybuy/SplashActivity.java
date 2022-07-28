package com.example.easybuy;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);

        logo.animate().alpha(1).setDuration(2000);
        title.animate().alpha(1).setDuration(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);

                finish();
            }
        },3000);
    }
}