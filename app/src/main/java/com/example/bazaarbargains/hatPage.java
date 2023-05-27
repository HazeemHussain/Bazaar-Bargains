package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class hatPage extends AppCompatActivity {


    RecyclerView rec;
    shoeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hat_page);

        rec = findViewById(R.id.hatrec);

        rec.setLayoutManager(new GridLayoutManager(this,2));

        rec.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hats"), itemShoe.class)
                        .build();

        adapter = new shoeAdapter(options,1);
        rec.setAdapter(adapter);

        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(hatPage.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(hatPage.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(hatPage.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(hatPage.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                int id = index;
                if (id == 1) {

                    startActivity(new Intent(hatPage.this, mainPage.class));

                }
            }
        });


/*        appBottomNavigationView.setSelectedItemId(R.id.home);
        appBottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.home:
                    // Navigate to the Home activity
                    startActivity(new Intent(hatPage.this, mainPage.class));
                    return true;
                case R.id.cart:
                    // Navigate to the Profile activity
                    startActivity(new Intent(hatPage.this, cartRecList.class));
                    return true;
                case R.id.dashboard:
                    // Navigate to the Settings activity
                    startActivity(new Intent(hatPage.this, Dashboard.class));
                    return true;
            }
            return false;
        });*/
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