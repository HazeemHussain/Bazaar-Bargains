package com.example.bazaarbargains;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class mainPage  extends AppCompatActivity  {
    private Button button;

    Button showItemButton;

    RecyclerView recyview;
    shoeAdapter adapter;

    private Button searchBtn;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        //Calling search button and search fields
         searchBtn = (Button) findViewById(R.id.SearchButton);
         searchBar = (EditText) findViewById(R.id.SearchField);

         searchBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 searchingData();
             }
         });

        recyview=(RecyclerView)findViewById(R.id.recyclerViewShoes) ;
        recyview.setLayoutManager(new LinearLayoutManager(this));

        recyview.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
        new FirebaseRecyclerOptions.Builder<itemShoe>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Shoes"), itemShoe.class)
                .build();

        adapter = new shoeAdapter(options,1);
        recyview.setAdapter(adapter);

        button = (Button) findViewById(R.id.cartButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(mainPage.this, cartRecList.class );
                startActivity(intent);
            }
        });

    }

    private void searchingData() {
        String searchText = searchBar.getText().toString().trim();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shoes");
        //Query query = ref.orderByChild("name").startAt(searchText).endAt(searchText + "/uf8ff");
        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(ref.orderByChild("name").startAt(searchText).endAt(searchText + "/uf8ff"), itemShoe.class)
                        .build();

        adapter = new shoeAdapter(options);
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
