package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button home_btn;
    ImageView image;
    TextView appname;
    ProgressBar loadingBar; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.app_logo);
        //appname = findViewById(R.id.app_name);
        loadingBar = findViewById(R.id.loading_bar); // Add this line

        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        Animation nameAnimation = AnimationUtils.loadAnimation(this, R.anim.name_animation);
        image.startAnimation(logoAnimation);
        appname.startAnimation(nameAnimation);

        runWithDelay();
    }

    public void runWithDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<>(image, "app_logo");
                pairs[1] = new Pair<>(appname, "app_name");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

                // Hide the loading bar after animation
                loadingBar.setVisibility(View.GONE);
            }
        }, 3000);
    }

}