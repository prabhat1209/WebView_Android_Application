package com.example.jatin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);

            icon = findViewById(R.id.splash_logo);
            Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoom_in);
            icon.startAnimation(animation);

            final Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            String android_id = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
                android_id = Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }

            Log.d("Android", "Android ID : " + android_id);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                        startActivity(intent);         //options.toBundle()
                        finish();
                    }
                }
            }, SPLASH_SCREEN);
        }catch (Exception e){
            Toast.makeText(SplashActivity.this,e+"",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}