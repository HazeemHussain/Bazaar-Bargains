package com.example.bazaarbargains;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class checkout  extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
   // DatabaseReference shoesRef = database.getReference("Shoes");

    RecyclerView recyview;
    shoeAdapter adapter;

    int i = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        recyview=(RecyclerView)findViewById(R.id.rab) ;
        recyview.setLayoutManager(new LinearLayoutManager(this));

        recyview.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart"), itemShoe.class)
                        .build();

        adapter = new shoeAdapter(options,2);
        recyview.setAdapter(adapter);


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
