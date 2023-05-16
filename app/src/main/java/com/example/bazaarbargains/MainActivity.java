package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button joinBtn, login_Btn, home_btn;
    ImageView image;
    TextView appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removing the top bar so that the activity covers full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



      //  login_Btn = (Button) findViewById(R.id.login_Btn);
       // joinBtn = (Button) findViewById(R.id.join_Btn);
      //  home_btn = (Button) findViewById(R.id.home_btn);
        image = (ImageView) findViewById(R.id.app_logo) ;
        appname = (TextView) findViewById(R.id.app_name) ;

        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        Animation nameAnimation = AnimationUtils.loadAnimation(this, R.anim.name_animation);
        image.startAnimation(logoAnimation);
        appname.startAnimation(nameAnimation);

        runWithDelay();


    }


    public void runWithDelay() {
        // Call next screen after a delay of 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                // Attach all the elements you want to animate in the design
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<>(image, "app_logo");
                pairs[1] = new Pair<>(appname, "app_name");
                // Wrap the call in API level 21 or higher
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        }, 3000); // 2 seconds delay (3000 milliseconds)
    }


}