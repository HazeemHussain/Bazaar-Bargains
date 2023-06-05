package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {

    RecyclerView orderhistoryView;
    cartAdapter adapter;
    String currentUser = loginActivity.currentUser;
    private TextView amountTotal, amountHeading;

    private static ArrayList<modelAddCart> invoiceList;

    public static void setInvoiceList(ArrayList<modelAddCart> list) {
        invoiceList = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        amountTotal = (TextView) findViewById(R.id.totalAmount);
        amountHeading = (TextView)  findViewById(R.id.totalHeading);
        calculatingTotalAmount();

        orderhistoryView = findViewById(R.id.orderhistoryRec);
        orderhistoryView.setLayoutManager(new LinearLayoutManager(this));

        //Calling the invoice list and adding it to the recycler view using cart adapter
        ArrayList<modelAddCart> invoiceList = (ArrayList<modelAddCart>) getIntent().getSerializableExtra("invoiceList");
        boolean hideRemoveButton = true; // Set the flag to true to hide the remove button

        if (invoiceList.isEmpty()) {
            Toast.makeText(OrderHistory.this, "No order history", Toast.LENGTH_SHORT).show();
            amountTotal.setVisibility(View.GONE);
            amountHeading.setText("No Order History");
        } else {
            adapter = new cartAdapter(this, invoiceList);
            adapter.setHideRemoveButton(true);
            orderhistoryView.setAdapter(adapter);
        }

    }

    private void calculatingTotalAmount() {

        DatabaseReference invoiceRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/invoice");

        invoiceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float totalAmount = 0;

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    modelAddCart item = childSnapshot.getValue(modelAddCart.class);
                    float itemPrice = Float.parseFloat(item.getitemPrice());
                    totalAmount += itemPrice;
                }

                // Set the calculated totalAmount to the TextView
                amountTotal.setText(String.valueOf(totalAmount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if retrieval is canceled
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (invoiceList != null && !invoiceList.isEmpty()) {
            adapter.setList(invoiceList);
        }
    }
}
