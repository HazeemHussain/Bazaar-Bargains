package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class showIT extends AppCompatActivity {

    private TextView desName,desPrice,quant,addtocartbut,cartTotal;
    private ImageView addbut,minusbut,imageitemView;
    int  quantity = 1;
    float totalprice = 0;

    public static float myFloatVariable;

  //  DatabaseReference urlRef = FirebaseDatabase.getInstance().getReference().child("path/to/url/node");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_it);

        initView();
        getBundele();

    }

    private void getBundele() {


            String data = getIntent().getStringExtra("itemname");
        desName.setText(data);

        String data1 = getIntent().getStringExtra("itemprice");
        desPrice.setText(data1);

        String data2 = getIntent().getStringExtra("itemimage");

        Glide.with(this).load(data2).into(imageitemView);


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
                float numberAsFloat = Float.parseFloat(data1);

                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");

                // Create a new checkout item
                String itemId = cartRef.push().getKey();

                modelAddCart checkoutItem = new modelAddCart(data,strNumber, data1, data2);

                // Add the checkout item to the cart
                cartRef.child(itemId).setValue(checkoutItem);

                totalprice = quantity*numberAsFloat;
                myFloatVariable = totalprice;
              //  cartTotal.setText((Float.toString(totalprice)));

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