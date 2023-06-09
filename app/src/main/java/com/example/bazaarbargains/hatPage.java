package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class hatPage extends AppCompatActivity {

    private Spinner sizeSpinner;
    private Spinner brandSpinner;
    private Spinner priceSpinner;
    private String sizeString, brandString, priceString;
    private TextView filterReset;
    RecyclerView rec;

    shoeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hat_page);

        filterReset = findViewById(R.id.resetbutton);
        sizeSpinner = findViewById(R.id.brandSpinner1);
        brandSpinner = findViewById(R.id.brandSpinner);
        priceSpinner = findViewById(R.id.brandSpinner2);


        rec = findViewById(R.id.hatrec);

        rec.setLayoutManager(new GridLayoutManager(this, 2));

        rec.setItemAnimator(null);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Hats");


        FirebaseRecyclerOptions<shopItem> options =
                new FirebaseRecyclerOptions.Builder<shopItem>()
                        .setQuery(query, shopItem.class)
                        .build();

        adapter = new shoeAdapter(options, 1);
        rec.setAdapter(adapter);

        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandString = parent.getItemAtPosition(position).toString();

                Query query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Hats").orderByChild("brand").equalTo(brandString);


                FirebaseRecyclerOptions<shopItem> options =
                        new FirebaseRecyclerOptions.Builder<shopItem>()
                                .setQuery(query, shopItem.class)
                                .build();

                adapter.updateOptions(options);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizeString = parent.getItemAtPosition(position).toString();

                Query query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Hats")
                        .orderByChild(sizeString)
                        .equalTo(true);

                FirebaseRecyclerOptions<shopItem> options =
                        new FirebaseRecyclerOptions.Builder<shopItem>()
                                .setQuery(query, shopItem.class)
                                .build();

                adapter.updateOptions(options);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT).show();
            }
        });
        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priceString = parent.getItemAtPosition(position).toString();

                Query query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Hats");

                if (priceString.equals("Low To High")) {
                    query = query.orderByChild("price1");
                } else if (priceString.equals("High To Low")) {
                    query = query.orderByChild("price2");
                }

                FirebaseRecyclerOptions<shopItem> options =
                        new FirebaseRecyclerOptions.Builder<shopItem>()
                                .setQuery(query, shopItem.class)
                                .build();

                adapter.updateOptions(options);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT).show();
            }
        });

        filterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });


        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(hatPage.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(hatPage.this, mainPage.class));


                } else if (id == 2) {
                    startActivity(new Intent(hatPage.this, wishlist.class));


                } else if (id == 3) {


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