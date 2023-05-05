package com.example.bazaarbargains;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class mainPage  extends AppCompatActivity  {
    private Button button;

    Button showItemButton;

    RecyclerView recyview;
    shoeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

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
