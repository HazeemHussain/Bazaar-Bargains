package com.example.bazaarbargains;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;


public class checkout  extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
   // DatabaseReference shoesRef = database.getReference("Shoes");

    RecyclerView recyview;
    shoeAdapter adapter;

    int i = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        recyview= findViewById(R.id.rab);
        recyview.setLayoutManager(new LinearLayoutManager(this));

        recyview.setItemAnimator(null);

        FirebaseRecyclerOptions<shopItem> options =
                new FirebaseRecyclerOptions.Builder<shopItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart"), shopItem.class)
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
