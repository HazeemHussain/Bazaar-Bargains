package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class wishlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);
        bottom_bar.selectTabAt(2,true);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(wishlist.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(wishlist.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(wishlist.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(wishlist.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                // Handle reselection of the same tab (optional)
            }
        });
    }
}