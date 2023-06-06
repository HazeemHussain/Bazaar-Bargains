package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class showIT extends AppCompatActivity {

    private TextView desName, desPrice, quant, addtocartbut, textView3, cartTotal, description, rateview, rateText;
    private String size;
    private int ratingvalue;

    private CardView card;
    private ImageView addbut, minusbut, imageitemView, book, arrow;
    int quantity = 1;
    double totalprice = 0;
    //private    String sizec;

    String sizec;
    int rate;
    String data1, data2, data, data3, data4;

    public static float myFloatVariable;

    public static final String EXTRA_PRODUCT_NAME = "productName";
    boolean isInWishList;

    private RecyclerView recyclerViewsize;
    private RecyclerView ratingrecycler;
    private sizeAdapter sizeadapter;
    private ratingAdapter ratingAdapter;
    private List<Integer> ratingList;


    String currentUser = loginActivity.currentUser;


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
            textView3.setText("$" + data1); //setting the item price
            description.setText((data3));
            Glide.with(this).load(data2).into(imageitemView); //Loading and displaying the item page
            quant.setText(Integer.toString(quantity));
            size = data4;


        }
        //Ends here
        View.OnClickListener itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


        recyclerViewsize.setLayoutManager(new GridLayoutManager(this, 3));

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

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("rating");


                ratingRef.orderByChild("name").equalTo(data).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                int currentRating = snapshot.child("rating").getValue(Integer.class);


                                int newRating = (currentRating + rate) / 2;


                                snapshot.getRef().child("rating").setValue(newRating);
                            }
                        } else {

                            DatabaseReference newRatingRef = ratingRef.push();
                            newRatingRef.child("name").setValue(data);
                            newRatingRef.child("rating").setValue(ratingAdapter.numberStar1);
                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ratingrecycler.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);
                rateText.setVisibility(View.GONE);

                Toast.makeText(showIT.this, "Review Submitted ", Toast.LENGTH_SHORT).show();
            }
        });


        addtocartbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNumber = Integer.toString(quantity);
                double doubleValue = Double.parseDouble(data1);

                totalprice = quantity * doubleValue;

                sizec = sizeadapter.sizec;

                Log.d("Rate Value", String.valueOf(rate));

                if (sizec != null && !sizec.isEmpty()) {

                    DatabaseReference cartUserRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart");
                    String itemId = cartUserRef.push().getKey();
                    modelAddCart checkoutItem = new modelAddCart(data, strNumber, data1, data2, Double.toString(totalprice), sizec);
                    // Add the checkout item to the cart
                    cartUserRef.child(itemId).setValue(checkoutItem);


                    Toast.makeText(showIT.this, "ITEM ADDED TO CART", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(showIT.this, "SELECT SIZE", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }


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
                            shopItem checkoutItem1 = new shopItem(data, data1, data3, data2, data4);
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
        desName = findViewById((R.id.desName));
        desPrice = findViewById((R.id.desPrice));
        quant = findViewById((R.id.quant));
        addtocartbut = findViewById((R.id.addtocartbut));
        addbut = findViewById((R.id.addbut));
        minusbut = findViewById((R.id.minusbut));
        imageitemView = findViewById((R.id.imageitemView));
        textView3 = findViewById((R.id.textView3));
        description = findViewById(R.id.descbox);
        book = findViewById(R.id.imageView4);
        ratingrecycler = findViewById(R.id.ratingrecycler);
        arrow = findViewById(R.id.arrowimage);
        rateText = findViewById(R.id.rateText);


    }


}