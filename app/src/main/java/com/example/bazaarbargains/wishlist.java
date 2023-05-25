package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class wishlist extends AppCompatActivity {

    String currentUser = loginActivity.currentUser;
    private RecyclerView recyclerView1;

    ArrayList<wishItem> list1 = new ArrayList<>();

    DatabaseReference database12;

    wishAdapter wishAdapter;

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

        recyclerView1 = findViewById(R.id.hatrec);

        database12 = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/wishList");




        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        list1 = new ArrayList<>();
        wishAdapter = new wishAdapter(this,list1);
       // wishAdapter.setOnRemoveItemClickListener(this);
        recyclerView1.setAdapter(wishAdapter);






        database12.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){



                        wishItem shoe = dataSnapshot.getValue(wishItem.class);

                        // toalprice +=Float.parseFloat(shoe.getPerItemCost()) ;

                        list1.add(shoe);


                    }

                    wishAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });








}
}