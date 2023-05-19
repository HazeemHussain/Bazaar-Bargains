package com.example.bazaarbargains;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Dashboard extends AppCompatActivity {

   private Button logout;
   private TextView userName, fullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dashboard);

        userName = findViewById(R.id.userNameField);
        fullName = findViewById(R.id.fullnameField);

       // logout = findViewById(R.id.logoutBtn);
        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);
        bottom_bar.selectTabAt(0,true);

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Dashboard.this, loginActivity.class );
//                startActivity(intent);
//
//            }
//        });

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(Dashboard.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(Dashboard.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(Dashboard.this, wishlist.class));


                }else if (id == 3) {

                    startActivity(new Intent(Dashboard.this, cartRecList.class));

                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });

    }
}