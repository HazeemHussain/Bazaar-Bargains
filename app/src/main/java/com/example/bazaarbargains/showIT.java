package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stripe.android.model.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class showIT extends AppCompatActivity  {

    private TextView desName,desPrice,quant,addtocartbut,textView3,cartTotal,description;
    private String size;

    private CardView card;
    private ImageView addbut,minusbut,imageitemView,book;
    int  quantity = 1;
    double totalprice = 0;
    //private    String sizec;

    String sizec ;
    int rate ;
    String data1, data2, data,data3,data4;

    public static float myFloatVariable;

    public static final String EXTRA_PRODUCT_NAME = "productName";
    boolean isInWishList;

    private RecyclerView recyclerViewsize;
    private RecyclerView ratingrecycler;
    private sizeAdapter sizeadapter;
    private ratingAdapter ratingAdapter;
    private List<Integer> ratingList;


    String currentUser = loginActivity.currentUser;



    //  DatabaseReference urlRef = FirebaseDatabase.getInstance().getReference().child("path/to/url/node");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_it);


        initView();
        getBundele();
        wishListButton();

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
            data3 = intent.getStringExtra("itemdesc");
            data4 = intent.getStringExtra("itemsize");


            desName.setText(data); //Setting the item name
            textView3.setText("$"+data1); //setting the item price
            description.setText((data3));
            Glide.with(this).load(data2).into(imageitemView); //Loading and displaying the item page
            quant.setText(Integer.toString(quantity));
            size=data4;


        }
        //Ends here
        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CardView card1 = view.findViewById(R.id.card);



                TextView numberTextView = view.findViewById(R.id.sizeTitle);



               // sizec = numberTextView.getText().toString();

              //  Toast.makeText(view.getContext(), "Selected Size: " + sizec, Toast.LENGTH_SHORT).show();
            }
        };

        ratingList = new ArrayList<>();
        ratingList.add(R.drawable.star);
        ratingList.add(R.drawable.star);
        ratingList.add(R.drawable.star);
        ratingList.add(R.drawable.star);
        ratingList.add(R.drawable.star);

        ratingrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));




        ratingAdapter = new ratingAdapter(ratingList);
        ratingrecycler.setAdapter(ratingAdapter);




        recyclerViewsize = findViewById(R.id.sizerecycler);



        recyclerViewsize.setLayoutManager(new GridLayoutManager(this,3));

        List<String> numbersList = Arrays.asList(size.split(","));

        sizeadapter = new sizeAdapter(numbersList, itemClickListener);
        recyclerViewsize.setAdapter(sizeadapter);





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

        rate = ratingAdapter.numberStar1;
        addtocartbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNumber = Integer.toString(quantity);
                double doubleValue = Double.parseDouble(data1);
                // float numberAsFloat = Float.parseFloat(data1);
                totalprice = quantity * doubleValue;
                // String formattedNum = String.format("%.2f", totalprice);
                //float num = Float.parseFloat(formattedNum);

                //myFloatVariable = totalprice;
                sizec = sizeadapter.sizec;

                Log.d("Rate Value", String.valueOf(rate));

                if (sizec != null && !sizec.isEmpty()) {

                    DatabaseReference cartUserRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart");
                    String itemId = cartUserRef.push().getKey();
                    modelAddCart checkoutItem = new modelAddCart(data, strNumber, data1, data2, Double.toString(totalprice), sizec);
                    // Add the checkout item to the cart
                    cartUserRef.child(itemId).setValue(checkoutItem);




                    DatabaseReference cartUserRef1 = FirebaseDatabase.getInstance().getReference("Shoes");
                    Query query = cartUserRef1.orderByChild("name").equalTo(data);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                DatabaseReference ratingRef = snapshot.getRef().child("rating");
                                ratingRef.setValue(rate);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    // cartTotal.setText((Float.toString(totalprice)));

                    Toast.makeText(showIT.this, "ITEM ADDED TO CART", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(showIT.this, "SELECT SIZE", Toast.LENGTH_SHORT).show();
                }
            }

        });


        // imageitemView.setText(data);


    }


    /*
      Wishlist method
      This method adds the item to the wishlist
      1st approach
    */

//    private void wishListButton() {
//
//        // The shared prefernces concept has been used
//        // to retrieve the current state of the item in the wishlist
//        //Naming the preference and keeping it private so it stays only in this class
//        SharedPreferences sharedPreferences = getSharedPreferences("wishlist", Context.MODE_PRIVATE);
//
//        //Also passing on the data (name of the item) and the status of it to see if it exists in wishlist
//        boolean isInWishList = sharedPreferences.getBoolean("isInWishList_" + data, false);
//
//        // Set the heart image resource based on the isInWishList variable. if isInwishlist is true
//        //the value is set to filled heart else its set to empty heart
//        book.setImageResource(isInWishList ? R.drawable.filled_heart : R.drawable.empty_heart);
//
//        book.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Getting wishlist db reference
//                DatabaseReference wishListRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/wishList");
//
//                //Reading the data from the database only once
//                wishListRef.orderByChild("name").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        boolean itemExists = dataSnapshot.exists();
//                        boolean isInWishList = false;
//
//                        //If item exist in database then removing the value of the product
//                        if (itemExists) {
//                            for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
//
//                                // Check if the item name matches exactly
//                                if (itemSnapshot.child("name").getValue(String.class).equals(data)) {
//                                    itemSnapshot.getRef().removeValue();
//                                    isInWishList = false;
//                                    break;
//                                }
//                            }
//
//                            //else adding the item to the wishlist when user clicks on the heart
//                        } else {
//                            String itemId = wishListRef.push().getKey();
//                            itemShoe checkoutItem1 = new itemShoe(data, data1, data3, data2, data4);
//                            wishListRef.child(itemId).setValue(checkoutItem1);
//                            isInWishList = true;
//                        }
//
//                        //The putBoolean() method is used to store the updated
//                        // isInWishList value for the specific item in the wishlist.
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putBoolean("isInWishList_" + data, isInWishList);
//                        editor.apply();
//
//                        // Set the heart image resource based on the isInWishList variable
//                        book.setImageResource(isInWishList ? R.drawable.filled_heart : R.drawable.empty_heart);
//
//                        // Show appropriate toast message
//                        Toast.makeText(showIT.this, isInWishList ? "ITEM ADDED TO WISHLIST" : "ITEM REMOVED FROM WISHLIST", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        // Handle error
//                    }
//                });
//            }
//        });
//    }

    /*
      Wishlist method
      This method adds the item to the wishlist
      Second approach that works on every device and is not just limited to one device
    */
    private void wishListButton() {
        DatabaseReference wishListRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/wishList");

        // Retrieve the current state of the item in the wishlist
        wishListRef.orderByChild("name").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean itemExists = dataSnapshot.exists();
                boolean isInWishList = itemExists; // Set the initial state based on item existence in the wishlist

                //Checking if the item exists in the database. if it exists then the hearts stays filled
                if (itemExists) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        if (itemSnapshot.child("name").getValue(String.class).equals(data)) {
                            isInWishList = true;
                            break;
                        }
                    }
                }

                //If the item exists the wishlist is set to true which fills the hearts else
                //if item doesnt exist it empties the heart
                book.setImageResource(isInWishList ? R.drawable.filled_heart : R.drawable.empty_heart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        //Now when the user clicks on the heart "save to wishlist button"
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishListRef.orderByChild("name").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean itemExists = dataSnapshot.exists();
                        boolean isInWishList = false;

                        //Checking if the item already exists the it removes the item from the wishlist
                        if (itemExists) {
                            for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                if (itemSnapshot.child("name").getValue(String.class).equals(data)) {
                                    itemSnapshot.getRef().removeValue();
                                    isInWishList = false;
                                    break;
                                }
                            }

                            //else if the item doesn't exist it passes on the item to the database
                            // and adds it to the wishlist class
                        } else {
                            String itemId = wishListRef.push().getKey();
                            itemShoe checkoutItem1 = new itemShoe(data, data1, data3, data2, data4);
                            wishListRef.child(itemId).setValue(checkoutItem1);
                            isInWishList = true;
                        }

                        //Setting the status of the heart based on if heart is filled "checked" or empty "unchecked"
                        book.setImageResource(isInWishList ? R.drawable.filled_heart : R.drawable.empty_heart);
                        Toast.makeText(showIT.this, isInWishList ? "ITEM ADDED TO WISHLIST" : "ITEM REMOVED FROM WISHLIST", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
            }
        });
    }







    private void initView() {
        desName=findViewById((R.id.desName));
        desPrice=findViewById((R.id.desPrice));
        quant=findViewById((R.id.quant));
        addtocartbut=findViewById((R.id.addtocartbut));
        addbut=findViewById((R.id.addbut));
        minusbut=findViewById((R.id.minusbut));
        imageitemView=findViewById((R.id.imageitemView));
        //  cartTotal=findViewById((R.id.cartTota));
        textView3=findViewById((R.id.textView3));
        description = findViewById(R.id.descbox);
        book = findViewById(R.id.imageView4);
        ratingrecycler = findViewById(R.id.ratingrecycler);







    }


}