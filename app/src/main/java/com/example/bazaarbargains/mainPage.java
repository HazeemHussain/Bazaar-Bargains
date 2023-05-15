package com.example.bazaarbargains;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
public class mainPage  extends AppCompatActivity  {
    private Button button;

    Button showItemButton;

    ArrayList<categoryModel> category;

    RecyclerView recyview;

    RecyclerView catRv;

    shoeAdapter adapter;

    categoryAdapter adap;
    String currentUser = loginActivity.currentUser;

    private Button searchBtn;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        BottomNavigationView appBottomNavigationView = findViewById(R.id.bottom_navigation);
        appBottomNavigationView.setSelectedItemId(R.id.home);
        appBottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.home:
                    // Navigate to the Home activity
                    startActivity(new Intent(mainPage.this, mainPage.class));
                    return true;
                case R.id.cart:
                    // Navigate to the Profile activity
                    startActivity(new Intent(mainPage.this, cartRecList.class));
                    return true;
                case R.id.dashboard:
                    // Navigate to the Settings activity
                    startActivity(new Intent(mainPage.this, Dashboard.class));
                    return true;
            }
            return false;
        });


        //Calling search button and search fields
       //  searchBtn = (Button) findViewById(R.id.SearchButton);
         searchBar = (EditText) findViewById(R.id.SearchField);

     /*   TextView hiText = findViewById(R.id.hiMess);

        hiText.setText("Hi "+currentUser+"!");*/

        catRv = findViewById(R.id.catrecyclerView);

        category = new ArrayList<>();
        category.add(new categoryModel("Shoes",R.drawable.runningshoe));
        category.add(new categoryModel("Hats",R.drawable.hatsphoto));
        category.add(new categoryModel("Tops",R.drawable.shirtphoto));
        category.add(new categoryModel("Bottoms",R.drawable.pantsphoto));


        adap = new categoryAdapter(this,category);

        catRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        catRv.setAdapter(adap);

   /*     searchBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 searchingData();
             }
         });*/

        recyview=(RecyclerView)findViewById(R.id.recyclerViewShoes) ;
        recyview.setLayoutManager(new GridLayoutManager(this,2));

        recyview.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
        new FirebaseRecyclerOptions.Builder<itemShoe>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Shoes"), itemShoe.class)
                .build();

        adapter = new shoeAdapter(options,1);
        recyview.setAdapter(adapter);

       // button = (Button) findViewById(R.id.cartButton);

     /*   button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(mainPage.this, cartRecList.class );
                startActivity(intent);
            }
        });*/

    }

    private void searchingData() {
        String searchText = searchBar.getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shoes");
        //Query query = ref.orderByChild("name").startAt(searchText).endAt(searchText + "/uf8ff");
        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(ref.orderByChild("name").startAt(searchText).endAt(searchText + "/uf8ff"), itemShoe.class)
                        .build();

//        adapter = new shoeAdapter(options);
        recyview.setAdapter(adapter);


        if (searchText.isEmpty()) {
            searchBar.setError("ENTER PRODUCT NAME");
            onStart();
        } else {





//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                        itemShoe shoe = userSnapshot.getValue(itemShoe.class);
//                        options.add(shoe);
//                    }
//
//                  //  shoeAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });



        }

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
