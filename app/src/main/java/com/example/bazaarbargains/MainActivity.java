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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

//To Clean up code on mac press cmd+options+L

public class MainActivity extends AppCompatActivity {

    private ImageView appLogo;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appLogo = findViewById(R.id.app_logo);
        loadingBar = findViewById(R.id.loading_bar);

        startLoadingAnimation();

        // Delay for 3 seconds before transitioning to the next screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNextScreen();
            }
        }, 2000); // 3 seconds delay
    }

    private void startLoadingAnimation() {


        // Displaying the logo and the loading simulatenously
        appLogo.setAlpha(1.0f);
        loadingBar.setVisibility(View.VISIBLE);

        // Fade in the app logo
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000);
        fadeInAnimation.setFillAfter(true);
        appLogo.startAnimation(fadeInAnimation);

    }

    private void goToNextScreen() {
        // Once the animation is finished the moving the app to the login activity
        Intent intent = new Intent(MainActivity.this, loginActivity.class);
        startActivity(intent);
        finish();
    }

    //Ensuring that the user doesn't go back to this activity when back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}