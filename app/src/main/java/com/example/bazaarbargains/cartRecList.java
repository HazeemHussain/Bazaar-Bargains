package com.example.bazaarbargains;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class cartRecList extends AppCompatActivity  implements cartAdapter.OnRemoveItemClickListener {

    RecyclerView recyclerView;
    DatabaseReference database, database1;
    cartAdapter myAdapter;
    ArrayList<modelAddCart> list = new ArrayList<>();
    String currentUser = loginActivity.currentUser;

    String value1;
    float toalprice;
    float value = 0;

    TextView cartTotal, gstTotal, totaltot, payNowBtn, textt;
    String PublishableKey = "pk_test_51NArkPBhqOODxEQT7uvPA6lRkqz8vmPdoBI0onVNJe22MjgGgPBGtqfnR0ksg1EDGBWyPmHWCrOKdVqR2m8RfmZb00CuhMLDha";
    String SecretKey = "sk_test_51NArkPBhqOODxEQTq3d8e8Kd7MvSKcjMwCYWmkpRbluvfbnyWc2UEeaXMt3Lxhwi7GKj7bGJSmaZXe5oaV8Rh6W0007gYqAoqq";
    String CustomerId;
    String EphericalKey;
    String ClientSecret;
    PaymentSheet paymentSheet;

    private DatabaseReference mDatabase;

    private DatabaseReference cartRef;
    private DatabaseReference amountRef;
    private ValueEventListener amountListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.AppTheme_NoAnimation);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);
        bottom_bar.selectTabAt(3, true);
        PaymentConfiguration.init(this, PublishableKey);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            CustomerId = object.getString("id");

                         //   Toast.makeText(cartRecList.this, CustomerId, Toast.LENGTH_SHORT).show();

                            getEmphericalKey();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cartRecList.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(cartRecList.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(cartRecList.this, mainPage.class));


                } else if (id == 2) {


                    startActivity(new Intent(cartRecList.this, wishlist.class));


                } else if (id == 3) {


                    startActivity(new Intent(cartRecList.this, cartRecList.class));


                }
            }


            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });


        recyclerView = findViewById(R.id.rab);

        database = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart");
        database1 = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart/{unique_id}/perItemCost");

        mDatabase = FirebaseDatabase.getInstance().getReference();

   /*     mDatabase.child("Users/"+currentUser).child("amount").get().addOnCompleteListener(task -> {
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


                cartTotal.setText("$"+ task.getResult().getValue());

                gstTotal.setText("$"+multipliedString);

                totaltot.setText("$"+multipliedString1);
                textt.setText("$"+multipliedString1);


            } else {
                Log.e("firebase", "Error getting data", task.getException());
            }
        });*/


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new cartAdapter(this, list);
        myAdapter.setOnRemoveItemClickListener(this);
        recyclerView.setAdapter(myAdapter);

        // cartTotal=findViewById((R.id.cartTota));

      //  gstTotal = findViewById((R.id.hiMess));
       // totaltot = findViewById((R.id.TotalTotal));
        payNowBtn = findViewById(R.id.textView2);
        textt = findViewById((R.id.textt));
        TextView emptyCartTextView = findViewById(R.id.emptyCartTextView);

       // gstTotal = findViewById((R.id.hiMess));
       // totaltot = findViewById((R.id.TotalTotal));
        payNowBtn = findViewById(R.id.textView2);
        textt = findViewById((R.id.textt));



        //cartTotal.setText((Float.toString(cartAdapter.myFloat)));

        //  cartTotal.setText(value1+"changed");


        //String formattedValue = String.format("%.2f", value+cartAdapter.myFloat *0.15f);
        //String formattedValue1 = String.format("%.2f", value+cartAdapter.myFloat* 1.15f);

        // gstTotal.setText(formattedValue);

        //  totaltot.setText(formattedValue1);

        ProgressBar bar = findViewById(R.id.loading_bar2);
        payNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting database reference from firebase to delete the items from the cart once the user has clicked
                //on pay now button
//                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("cart");
//                databaseRef.removeValue();

                bar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        paymentflow();
                        bar.setVisibility(View.GONE);
                        // Enable the button after the delay
                        payNowBtn.setEnabled(true);
                    }
                }, 1000); // Delay for 2 seconds (2000 milliseconds)


                //Moving the user to payment options class



                //Changing the total value to zero after user has clicked on paynow button
               // showIT.myFloatVariable = 0;





//                  DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart");
//                  dbr.removeValue();
//                  DatabaseReference dbrs = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/amount");
//                  dbrs.removeValue();

            }
        });

        /* *//*      database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){



                    String value = dataSnapshot.getValue(String.class);
                    System.out.println(value);

                }


            }*//*

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Failed to read value from Firebase: " + error.getMessage());

            }
        });*/


        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    modelAddCart shoe = dataSnapshot.getValue(modelAddCart.class);

                    // toalprice +=Float.parseFloat(shoe.getPerItemCost()) ;

                    list.add(shoe);


                }


                myAdapter.notifyDataSetChanged();

                setTotalView();

                if (list.isEmpty()) {
                    emptyCartTextView.setVisibility(View.VISIBLE);
                } else {
                    emptyCartTextView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }








    private void paymentflow() {
        try {
            paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Bazaar Bargains", new PaymentSheet.CustomerConfiguration(
                    CustomerId,
                    EphericalKey
            )));
        } catch (NullPointerException e) {
            Toast.makeText(cartRecList.this,"Cannot Checkout With Nothing", Toast.LENGTH_SHORT).show();

        }


    }




    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

    if(paymentSheetResult instanceof PaymentSheetResult.Completed){
        //Getting database reference from firebase to delete the items from the cart once the user has clicked
        //on pay now button
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/cart");
        DatabaseReference invoiceRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/invoice");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot cartSnapshot : snapshot.getChildren()) {
                        Object data = cartSnapshot.getValue();

                        // Generate a new unique key for each invoice item
                        String invoiceItemKey = invoiceRef.push().getKey();

                        // Create a new child node under the "invoice" node with the unique key and the item data
                        invoiceRef.child(invoiceItemKey).setValue(data);
                    }


                // Remove the cart data
                    databaseRef.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation if needed
            }
        });


        Toast.makeText(this,"Payment Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(cartRecList.this, invoicePage.class);
        startActivity(intent);


    }

    }

    @Override
    public void onRemoveItemClicked(int position) {
        modelAddCart itemToRemove = list.get(position);

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
                                setTotalView();


                                Intent intent = getIntent();
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(intent);
                                overridePendingTransition(0, 0);

                            } else {
                                // Handle the remove failure
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

    private void getEmphericalKey() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            EphericalKey = object.getString("id");



                            getClientSecret(CustomerId,EphericalKey);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cartRecList.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(cartRecList.this, cartRecList.class);
            }
        }){

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put( "Authorization", "Bearer " + SecretKey);
                header.put("Stripe-Version","2022-11-15");
                return header;
            }

            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> param = new HashMap<>();
                param.put("customer",CustomerId);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void getClientSecret(String customerId, String ephericalKey) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            ClientSecret = object.getString("client_secret");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    Toast.makeText(cartRecList.this,"Cannot Checkout With Nothing", Toast.LENGTH_SHORT).show();

            }
        }){

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put( "Authorization", "Bearer " + SecretKey);
                return header;
            }
            public Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> param = new HashMap<>();
                param.put("customer",CustomerId);
                float total = setTotalView();
                float total1= total*10;
                String totals = String.valueOf(total1);


                String[] parts = totals.split("\\.");
                String wholePart = parts[0];
                String decimalPart = parts[1];
                String FullPart = wholePart ;
                param.put("amount", FullPart + decimalPart);
                param.put("currency", "NZD");
                param.put("automatic_payment_methods[enabled]","true" );

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }




    public float setTotalView() {

        float total = 0;
        for (modelAddCart item : list) {
            float itemPrice = Float.parseFloat(item.getPerItemCost());
            total += itemPrice;
        }

        textt.setText(String.format("$%.2f", total));


        return total;
    }


    public float setTotalView( List<modelAddCart> itemList) {
        float total = 0;
        for (modelAddCart item : itemList) {
            float itemPrice = Float.parseFloat(item.getPerItemCost());
            total += itemPrice;
        }

        textt.setText(String.format("$%.2f", total));


        return total;
    }

    private float TotalView() {
        float total = 0;
        for (modelAddCart item : list) {
            float itemPrice = Float.parseFloat(item.getPerItemCost());
            total += itemPrice;
        }

         return total;


    }

    private String getTextFromTextView() {
        TextView textView = findViewById(R.id.textt);
        String text = textView.getText().toString();
        text = text.replace("$", "");
        return text;
    }

}
