package com.example.bazaarbargains;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class cartRecList extends AppCompatActivity  implements cartAdapter.OnRemoveItemClickListener {

    RecyclerView recyclerView;
    DatabaseReference database,database1;
    cartAdapter myAdapter;
    ArrayList<modelAddCart> list;
    String currentUser = loginActivity.currentUser;

    String value1;
    float toalprice;
    float value=0;

    TextView cartTotal,gstTotal,totaltot, payNowBtn;

    private DatabaseReference mDatabase;

    private DatabaseReference cartRef;
    private DatabaseReference amountRef;
    private ValueEventListener amountListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);
        bottom_bar.selectTabAt(3,true);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(cartRecList.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(cartRecList.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(cartRecList.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(cartRecList.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });



        toalprice=0;

        recyclerView = findViewById(R.id.rab);

        database = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/cart");
        database1 = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/cart/{unique_id}/perItemCost");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users/"+currentUser).child("amount").get().addOnCompleteListener(task -> {
            if (task == null || task.isSuccessful() && !task.getResult().exists()) {
                cartTotal.setText("$0");
                gstTotal.setText("$0");
                totaltot.setText("$0");
                Log.e("firebase", "Data does not exist");
            } else if (task.isSuccessful()) {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String s = String.valueOf(task.getResult().getValue());
                double doubleValue = Double.parseDouble(s);
                double multipliedValue = doubleValue * 0.15;
                double multipliedValue1 = doubleValue * 1.15;

                String multipliedString = String.format("%.2f", multipliedValue);
                String multipliedString1 = String.format("%.2f", multipliedValue1);


                cartTotal.setText("$"+String.valueOf(task.getResult().getValue()));

                gstTotal.setText("$"+multipliedString);

                totaltot.setText("$"+multipliedString1);

            } else {
                Log.e("firebase", "Error getting data", task.getException());
            }
        });



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new cartAdapter(this,list);
        myAdapter.setOnRemoveItemClickListener(this);
        recyclerView.setAdapter(myAdapter);

        cartTotal=findViewById((R.id.cartTota));
        gstTotal=findViewById((R.id.hiMess));
        totaltot=findViewById((R.id.TotalTotal));
        payNowBtn = findViewById(R.id.textView2);

        //cartTotal.setText((Float.toString(cartAdapter.myFloat)));

      //  cartTotal.setText(value1+"changed");




        //String formattedValue = String.format("%.2f", value+cartAdapter.myFloat *0.15f);
        //String formattedValue1 = String.format("%.2f", value+cartAdapter.myFloat* 1.15f);

       // gstTotal.setText(formattedValue);

      //  totaltot.setText(formattedValue1);



        payNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                //Getting database reference from firebase to delete the items from the cart once the user has clicked
                //on pay now button
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("cart");
                databaseRef.removeValue();

                //Moving the user to payment options class

                Intent intent = new Intent(cartRecList.this, payment_options.class);
                startActivity(intent);


                //Changing the total value to zero after user has clicked on paynow button
                showIT.myFloatVariable = 0;


                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/cart");
                dbr.removeValue();
                DatabaseReference dbrs = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/amount");
                dbrs.removeValue();
            }
        });

        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){



                    String value = dataSnapshot.getValue(String.class);
                    System.out.println(value);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Failed to read value from Firebase: " + error.getMessage());

            }
        });


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                toalprice=0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){



                    modelAddCart shoe = dataSnapshot.getValue(modelAddCart.class);

                   toalprice +=Float.parseFloat(shoe.getPerItemCost()) ;

                    list.add(shoe);


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            float f=toalprice;
        //cartTotal.setText((Float.toString(value+cartAdapter.myFloat)));



    }


    @Override
    public void onRemoveItemClicked(int position) {
        modelAddCart itemToRemove = list.get(position);
        double itemPrice = Double.parseDouble(itemToRemove.getPerItemCost());

        // Remove the item from your data source
        list.remove(position);
        myAdapter.notifyItemRemoved(position);

        // Remove the item from the database
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("cart");
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot cartItemSnapshot : snapshot.getChildren()) {
                    modelAddCart cartItem = cartItemSnapshot.getValue(modelAddCart.class);
                    if (cartItem != null && cartItem.getItemName().equals(itemToRemove.getItemName())) {
                        cartItemSnapshot.getRef().removeValue().addOnCompleteListener(removeTask -> {
                            if (removeTask.isSuccessful()) {
                                // Item removed successfully from the database

                                // Access the "users/amount" instance and subtract the cost of the deleted item
                                DatabaseReference amountRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("amount");
                                amountRef.get().addOnCompleteListener(amountTask -> {
                                    if (amountTask.isSuccessful() && amountTask.getResult().exists()) {
                                        double currentAmount = amountTask.getResult().getValue(Double.class);
                                        double newAmount = currentAmount - itemPrice;

                                        // Update the "users/amount" instance with the new amount
                                        amountRef.setValue(newAmount).addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                            
                                            } else {

                                            }
                                        });
                                    } else {

                                    }
                                });
                            } else {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error occurred while retrieving cart items
                // Handle the error or display an error message
            }
        });
    }

}
