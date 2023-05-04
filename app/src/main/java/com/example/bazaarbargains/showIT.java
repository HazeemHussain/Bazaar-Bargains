package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showIT extends AppCompatActivity {

    private TextView desName,desPrice,quant,addtocartbut;
    private ImageView addbut,minusbut,imageitemView;
    int  quantity = 0;
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


        minusbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
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

        addbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quant.setText(Integer.toString(quantity));
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



    }

}