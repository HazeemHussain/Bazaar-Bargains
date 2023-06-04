package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class invoicePage extends AppCompatActivity {
    RecyclerView rec;


    ArrayList<modelAddCart> list = new ArrayList<>();
    String currentUser = loginActivity.currentUser;

    cartAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_page);
        rec = findViewById(R.id.invoiceRec);
        DatabaseReference invoiceRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/invoice");

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new cartAdapter(this, list);

        rec.setAdapter(myAdapter);



    /*    rec = findViewById(R.id.invoiceRec);

        rec.setLayoutManager(new GridLayoutManager(this,2));

        rec.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("invoice"), itemShoe.class)
                        .build();

        DatabaseReference invoiceRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/invoice");

        myAdapter = new cartAdapter(this,list);
        rec.setAdapter(adapter);
*/

        invoiceRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    modelAddCart shoe = dataSnapshot.getValue(modelAddCart.class);



                    list.add(shoe);


                }


                myAdapter.notifyDataSetChanged();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(invoicePage.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(invoicePage.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(invoicePage.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(invoicePage.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                int id = index;
                if (id == 1) {

                    startActivity(new Intent(invoicePage.this, mainPage.class));

                }
            }
        });*/


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


    }

