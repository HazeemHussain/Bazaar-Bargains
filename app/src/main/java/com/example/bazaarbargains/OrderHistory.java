package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {

    RecyclerView orderhistoryView;
    cartAdapter adapter;
    String currentUser = loginActivity.currentUser;

    private static ArrayList<modelAddCart> invoiceList;

    public static void setInvoiceList(ArrayList<modelAddCart> list) {
        invoiceList = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        orderhistoryView = findViewById(R.id.orderhistoryRec);
        orderhistoryView.setLayoutManager(new LinearLayoutManager(this));

        //Calling the invoice list and adding it to the recycler view using cart adapter
        ArrayList<modelAddCart> invoiceList = (ArrayList<modelAddCart>) getIntent().getSerializableExtra("invoiceList");
        boolean hideRemoveButton = true; // Set the flag to true to hide the remove button

        adapter = new cartAdapter(this, invoiceList);
        adapter.setHideRemoveButton(true);
        orderhistoryView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (invoiceList != null && !invoiceList.isEmpty()) {
            adapter.setList(invoiceList);
        }
    }
}
