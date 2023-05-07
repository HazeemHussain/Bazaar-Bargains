package com.example.bazaarbargains;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class cartRecList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    cartAdapter myAdapter;
    ArrayList<modelAddCart> list;

    float value = showIT.myFloatVariable;

    TextView cartTotal,gstTotal,totaltot, payNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView = findViewById(R.id.rab);
        database = FirebaseDatabase.getInstance().getReference("cart");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new cartAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        cartTotal=findViewById((R.id.cartTota));
        gstTotal=findViewById((R.id.hiMess));
        totaltot=findViewById((R.id.TotalTotal));
        payNowBtn = findViewById(R.id.textView2);

        cartTotal.setText((Float.toString(value)));

        String formattedValue = String.format("%.2f", value* 0.15f);
        String formattedValue1 = String.format("%.2f", value* 1.15f);

        gstTotal.setText(formattedValue);

        totaltot.setText(formattedValue1);



        payNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cartRecList.this, payment_options.class);
                startActivity(intent);
                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("cart");
                dbr.removeValue();
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    modelAddCart shoe = dataSnapshot.getValue(modelAddCart.class);
                    list.add(shoe);


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}
