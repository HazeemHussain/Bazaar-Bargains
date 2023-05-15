package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class shoesPage extends AppCompatActivity {
    RecyclerView rec;



    shoeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_page);

        rec = (RecyclerView) findViewById(R.id.hatrec);

        rec.setLayoutManager(new GridLayoutManager(this, 2));

        rec.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
                new FirebaseRecyclerOptions.Builder<itemShoe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Shoes"), itemShoe.class)
                        .build();

        adapter = new shoeAdapter(options, 1);
        rec.setAdapter(adapter);
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