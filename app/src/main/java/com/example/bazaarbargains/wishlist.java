package com.example.bazaarbargains;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class wishlist extends AppCompatActivity {

    RecyclerView recyview1;
    shoeAdapter adapter;
    String currentUser = loginActivity.currentUser;
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

        recyview1= findViewById(R.id.wishrec);
        recyview1.setLayoutManager(new GridLayoutManager(this,2));

        recyview1.setItemAnimator(null);


        FirebaseRecyclerOptions<shopItem> options =
                new FirebaseRecyclerOptions.Builder<shopItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("wishList"), shopItem.class)
                        .build();

        adapter = new shoeAdapter(options,1);
        recyview1.setAdapter(adapter);
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