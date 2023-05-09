package com.example.bazaarbargains;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class cartRecList extends AppCompatActivity {

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
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        toalprice=0;

        recyclerView = findViewById(R.id.rab);

        database = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/cart");
        database1 = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/cart/{unique_id}/perItemCost");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users/"+currentUser).child("amount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                   // String.format("%.2f", value+cartAdapter.myFloat* 1.15f)

                    cartTotal.setText(String.valueOf(task.getResult().getValue()));
                    //value1=String.valueOf(task.getResult().getValue());
                }
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new cartAdapter(this,list);
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




}
