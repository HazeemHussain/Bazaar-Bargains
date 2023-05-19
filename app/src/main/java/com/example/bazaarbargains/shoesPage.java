package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class shoesPage extends AppCompatActivity {
    RecyclerView rec;



    shoeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_page);

        rec = findViewById(R.id.hatrec);

        rec.setLayoutManager(new GridLayoutManager(this, 2));

        rec.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shoes"), itemShoe.class)
                        .build();

        adapter = new shoeAdapter(options, 1);
        rec.setAdapter(adapter);

        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);


        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(shoesPage.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(shoesPage.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(shoesPage.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(shoesPage.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                int id = index;
                if (id == 1) {

                    startActivity(new Intent(shoesPage.this, mainPage.class));

                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}