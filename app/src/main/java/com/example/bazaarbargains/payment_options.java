package com.example.bazaarbargains;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class payment_options extends AppCompatActivity {
private Spinner payment_option_spinner;
private LinearLayout paymentfields;
private boolean fieldsAdded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        payment_option_spinner = findViewById(R.id.spinner);
        paymentfields= findViewById(R.id.linear_layout);


        String[] choice = {"Please Select a Payment Option","Visa","MasterCard", "Paypal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, choice);
        payment_option_spinner.setAdapter(adapter);

        payment_option_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedOption = adapterView.getItemAtPosition(position).toString();

                if (selectedOption.equals("Please Select a Payment Option")) {
                    paymentfields.setVisibility(View.VISIBLE);
                    return;
                } else {
                    paymentfields.setVisibility(View.GONE);
                }
                // Clear any previously added fields
                paymentfields.removeAllViews();


                if(selectedOption.equals("Visa")){
                    EditText visa_number = new EditText(payment_options.this);
                    visa_number.setHint("Enter Card Number");
                    paymentfields.addView(visa_number);


                    EditText expiry_date = new EditText(payment_options.this);
                    expiry_date.setHint("Enter Expiry Date");
                    paymentfields.addView(expiry_date);


                    EditText CVV = new EditText(payment_options.this);
                    CVV.setHint("Enter CVV");
                    paymentfields.addView(CVV);


                    paymentfields.setVisibility(View.VISIBLE);

                }
else if(selectedOption.equals("MasterCard")){
                    EditText mastercard_number = new EditText(payment_options.this);
                    mastercard_number.setHint("Enter Card Number");
                    paymentfields.addView(mastercard_number);


                    EditText expire = new EditText(payment_options.this);
                    expire.setHint("Expiry Date");
                    paymentfields.addView(expire);


                    EditText security = new EditText(payment_options.this);
                    security.setHint("Enter CVV");
                    paymentfields.addView(security);


                    paymentfields.setVisibility(View.VISIBLE);
                }
else if(selectedOption.equals("Paypal")){
                    EditText email = new EditText(payment_options.this);
                   email.setHint("Enter Email");
                    paymentfields.addView(email);


                    EditText password = new EditText(payment_options.this);
                    password.setHint("Enter Password");
                    paymentfields.addView(password);

                    paymentfields.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }




        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}