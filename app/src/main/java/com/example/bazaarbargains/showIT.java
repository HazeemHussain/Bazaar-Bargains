package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showIT extends AppCompatActivity {

    private TextView desName,desPrice,quant,addtocartbut,cartTotal;
    private ImageView addbut,minusbut,imageitemView;
    int  quantity = 1;
    double totalprice = 0;

    String data1, data2, data;

    public static float myFloatVariable;

    String currentUser = loginActivity.currentUser;
    public static final String EXTRA_PRODUCT_NAME = "productName";

  //  DatabaseReference urlRef = FirebaseDatabase.getInstance().getReference().child("path/to/url/node");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_it);

        initView();
        getBundele();

    }

    private void getBundele() {

        //Hazeem part starts here
        //This part is included to successfully implement the search bar feature
        //Getting the data from the mainPage class which user searched
        //Then taking the user to particular item page which has been clicked
        Intent intent = getIntent();
        if (intent != null) {

            //Retrieving the data passed from the main activity
            //String productName = intent.getStringExtra(EXTRA_PRODUCT_NAME);
            data = intent.getStringExtra("itemname");
            data1 = intent.getStringExtra("itemprice");
            data2 = intent.getStringExtra("itemimage");

            desName.setText(data); //Setting the item name
            desPrice.setText(data1); //setting the item price
            Glide.with(this).load(data2).into(imageitemView); //Loading and displaying the item page
            quant.setText(Integer.toString(quantity));
        }
        //Ends here


        quant.setText(Integer.toString(quantity));


        minusbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    quant.setText(Integer.toString(quantity));
                }
            }
        });

        addbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quant.setText(Integer.toString(quantity));
            }
        });

        addtocartbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNumber = Integer.toString(quantity);
                double doubleValue = Double.parseDouble(data1);
               // float numberAsFloat = Float.parseFloat(data1);
                totalprice = quantity*doubleValue;
               // String formattedNum = String.format("%.2f", totalprice);
                //float num = Float.parseFloat(formattedNum);

                //myFloatVariable = totalprice;

                DatabaseReference cartUserRef = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/cart");

               DatabaseReference cartUserRef1 = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/amount");

                cartUserRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Node exists, do something

                            double amount = snapshot.getValue(double.class);

                            double newprice = amount+totalprice;

                            cartUserRef1.setValue(newprice);

                        } else {
                            // Node does not exist, create it
                            cartUserRef1.setValue(totalprice, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error != null) {
                                        // Error occurred while creating node
                                    } else {
                                        // Node created successfully
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Error occurred while reading data
                    }
                });
                String itemId = cartUserRef.push().getKey();

                modelAddCart checkoutItem = new modelAddCart(data,strNumber, data1, data2,Double.toString(totalprice));

                // Add the checkout item to the cart
                cartUserRef.child(itemId).setValue(checkoutItem);


               // cartTotal.setText((Float.toString(totalprice)));

                Toast.makeText(showIT.this, "ITEM ADDED TO CART", Toast.LENGTH_SHORT).show();

            }
        });


       // imageitemView.setText(data);




    }

    private void initView() {
        desName=findViewById((R.id.desName));
        desPrice=findViewById((R.id.desPrice));
        quant=findViewById((R.id.quant));
        addtocartbut=findViewById((R.id.addtocartbut));
        addbut=findViewById((R.id.addbut));
        minusbut=findViewById((R.id.minusbut));
        imageitemView=findViewById((R.id.imageitemView));
        cartTotal=findViewById((R.id.cartTota));



    }

}