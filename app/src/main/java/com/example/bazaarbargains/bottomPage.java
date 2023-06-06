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

public class bottomPage extends AppCompatActivity {
    //declaring instance variables used
    RecyclerView rec;
    //spinner for each filter type
    private Spinner sizeSpinner;
    private Spinner brandSpinner;
    private Spinner priceSpinner;
    private TextView filterReset;
    private String sizeString, brandString, priceString;

    //ShopAdapter variable
    shoeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_page);

        //Initialising the variables by using findViewById to corresponding component in xml file
        filterReset = findViewById(R.id.resetbutton);
        sizeSpinner = findViewById(R.id.brandSpinner1);
        brandSpinner = findViewById(R.id.brandSpinner);
        priceSpinner = findViewById(R.id.brandSpinner2);


        rec = findViewById(R.id.hatrec);
    //setting layout to grid with two columns
        rec.setLayoutManager(new GridLayoutManager(this, 2));

        rec.setItemAnimator(null);
    //creating query to retrieve items from child Bottoms
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Bottoms");

    // creating options which is used to bind data to recyclerView
        FirebaseRecyclerOptions<shopItem> options =
                new FirebaseRecyclerOptions.Builder<shopItem>()
                        .setQuery(query, shopItem.class)
                        .build();
//passing options and switch case choice int 1 into shoeAdapter
        adapter = new shoeAdapter(options, 1);
        //setting adapter to recyclerview
        rec.setAdapter(adapter);

        //if brand selected in filter than
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandString = parent.getItemAtPosition(position).toString();

                Query query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Bottoms").orderByChild("brand").equalTo(brandString);


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
                        .child("Bottoms")
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
                Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT).show();
            }
        });
        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priceString = parent.getItemAtPosition(position).toString();

                Query query = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Bottoms");

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
                Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_SHORT).show();
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

                    startActivity(new Intent(bottomPage.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(bottomPage.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(bottomPage.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(bottomPage.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                int id = index;
                if (id == 1) {

                    startActivity(new Intent(bottomPage.this, mainPage.class));

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
