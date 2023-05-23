package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

import java.util.Arrays;
import java.util.List;

public class showIT extends AppCompatActivity  {

    private TextView desName,desPrice,quant,addtocartbut,textView3,cartTotal,description;
    private String size;
    private ImageView addbut,minusbut,imageitemView,book;
    int  quantity = 1;
    double totalprice = 0;
    private  String sizec;


    String data1, data2, data,data3,data4;

    public static float myFloatVariable;

    public static final String EXTRA_PRODUCT_NAME = "productName";


    private RecyclerView recyclerViewsize;
    private sizeAdapter sizeadapter;


    String currentUser = loginActivity.currentUser;



  //  DatabaseReference urlRef = FirebaseDatabase.getInstance().getReference().child("path/to/url/node");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_it);

        initView();
        getBundele();

    }
    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView numberTextView = view.findViewById(R.id.sizeTitle);
            sizec = numberTextView.getText().toString();

            Toast.makeText(showIT.this, "Selected Size: " + sizec, Toast.LENGTH_SHORT).show();
        }
    };


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

                if (sizec != null && !sizec.isEmpty()) {

                    DatabaseReference cartUserRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart");

                    String itemId = cartUserRef.push().getKey();

                    modelAddCart checkoutItem = new modelAddCart(data, strNumber, data1, data2, Double.toString(totalprice), sizec);

                    // Add the checkout item to the cart
                    cartUserRef.child(itemId).setValue(checkoutItem);


                    // cartTotal.setText((Float.toString(totalprice)));

                    Toast.makeText(showIT.this, "ITEM ADDED TO CART", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(showIT.this, "SELECT SIZE", Toast.LENGTH_SHORT).show();
                }
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNumber = Integer.toString(quantity);
                double doubleValue = Double.parseDouble(data1);
                // float numberAsFloat = Float.parseFloat(data1);
                totalprice = quantity*doubleValue;


                DatabaseReference cartUserRef = FirebaseDatabase.getInstance().getReference("Users/"+currentUser+"/wishList");



                String itemId = cartUserRef.push().getKey();

                itemShoe checkoutItem1 = new itemShoe(data,data1, data2);
                // Add the checkout item to the cart
                cartUserRef.child(itemId).setValue(checkoutItem1);


                // cartTotal.setText((Float.toString(totalprice)));

                Toast.makeText(showIT.this, "ITEM ADDED TO WISHLIST", Toast.LENGTH_SHORT).show();

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
      //  cartTotal=findViewById((R.id.cartTota));
        textView3=findViewById((R.id.textView3));
        description = findViewById(R.id.descbox);
        book = findViewById(R.id.imageView4);





    }

}