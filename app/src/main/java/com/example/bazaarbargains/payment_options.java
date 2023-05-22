package com.example.bazaarbargains;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class payment_options extends AppCompatActivity {
    private LinearLayout paymentfields;
    private boolean fieldsAdded = false;
    private EditText card_number, expiry_date, CVV;

    private ImageView logo, front_side, back_side, visa_2, mastercard;
    private TextView card, paypal, confirm, back, card_text,expire_text,CVV_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);
        confirm = (TextView) findViewById(R.id.confirm_button);
        back = (TextView) findViewById(R.id.back_button);
        logo = (ImageView) findViewById(R.id.logo);
        front_side = (ImageView) findViewById(R.id.front_side);
        back_side = (ImageView) findViewById(R.id.back_side);
        visa_2 = (ImageView) findViewById(R.id.visa_2);
        mastercard = (ImageView) findViewById(R.id.mastercard);
        card_text = (TextView) findViewById(R.id.card_text);
        expire_text = (TextView) findViewById(R.id.expire_text);
        CVV_text = (TextView) findViewById(R.id.CVV_text);
        card_number = (EditText) findViewById(R.id.card_number);
        expiry_date = (EditText) findViewById(R.id.expiration_date);
        CVV = (EditText) findViewById(R.id.security_code);
        card = (TextView) findViewById(R.id.visa);
        paypal = (TextView) findViewById(R.id.paypal);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        card.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        paymentfields = findViewById(R.id.linear_layout);


                                        // Clear any previously added fields
                                        paymentfields.removeAllViews();

                                        paymentfields.addView(logo);
                                        logo.setVisibility(View.VISIBLE);

                                        back.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(payment_options.this, payment_options.class);
                                                startActivity(intent);
                                            }
                                        });
                                        paymentfields.addView(back);
                                        back.setVisibility(View.VISIBLE);

                                        paymentfields.addView(card_text);
                                        card_text.setVisibility(View.VISIBLE);

                                        // card_number = findViewById(R.id.card_number);
                                        paymentfields.addView(card_number);
                                        card_number.setVisibility(View.VISIBLE);

                                        paymentfields.addView(front_side);
                                        front_side.setVisibility(View.VISIBLE);

                                        paymentfields.addView(visa_2);
                                        visa_2.setVisibility(View.VISIBLE);

                                        paymentfields.addView(mastercard);
                                        mastercard.setVisibility(View.VISIBLE);

                                        paymentfields.addView(expire_text);
                                        expire_text.setVisibility(View.VISIBLE);

                                        //  expiry_date = findViewById(R.id.expiration_date);
                                        paymentfields.addView(expiry_date);
                                        expiry_date.setVisibility(View.VISIBLE);

                                        paymentfields.addView(CVV_text);
                                        CVV_text.setVisibility(View.VISIBLE);

                                        //  CVV = findViewById(R.id.security_code);
                                        paymentfields.addView(CVV);
                                        CVV.setVisibility(View.VISIBLE);


                                        paymentfields.addView(back_side);
                                        back_side.setVisibility(View.VISIBLE);

                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                card_number = findViewById(R.id.card_number);
                                                String card = card_number.getText().toString();

                                                expiry_date = findViewById(R.id.expiration_date);
                                                String exp = expiry_date.getText().toString();

                                                CVV = findViewById(R.id.security_code);
                                                String cvv = CVV.getText().toString();

                                                if (card.isEmpty() || exp.isEmpty() || cvv.isEmpty()) {
                                                    Toast.makeText(payment_options.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }

                                                if (!card.matches("[0-9]{16}")) {
                                                    Toast.makeText(payment_options.this, "Please enter a valid card number", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }

                                                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
                                                Calendar cal = Calendar.getInstance();
                                                try {
                                                    cal.setTime(dateFormat.parse(exp));
                                                    Calendar minExpiryCalendar = Calendar.getInstance();
                                                    minExpiryCalendar.set(2024, Calendar.MARCH, 1); // Minimum expiry date is March 2024
                                                    if (cal.before(minExpiryCalendar)) {
                                                        Toast.makeText(payment_options.this, "Please enter an expiry date after March 2024", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(payment_options.this, "Invalid expiry date correct format is MM/yy", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }

                                                if (!cvv.matches("[0-9]{3}")) {
                                                    Toast.makeText(payment_options.this, "Please enter a valid CVV", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }

                                                Toast.makeText(payment_options.this, "Order Confirmed", Toast.LENGTH_SHORT).show();

                                                Message mastercard_visa = new Message(card, exp, cvv);
                                                myRef.push().setValue(mastercard_visa);

                                                Intent intent = new Intent(payment_options.this, payment_options.class);
                                                startActivity(intent);
                                                finish();

                                            }

                                        });
                                        paymentfields.addView(confirm);
                                        confirm.setVisibility(View.VISIBLE);



                                        paymentfields.setVisibility(View.VISIBLE);


                                    }


                                }

        );

        /*  paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentfields = findViewById(R.id.linear_layout);


                // Clear any previously added fields
                paymentfields.removeAllViews();

                paymentfields.addView(username);
                username.setVisibility(View.VISIBLE);


                paymentfields.addView(password);
                password.setVisibility(View.VISIBLE);

               confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        username = findViewById(R.id.paypal_username);
                        String user = username.getText().toString();


                        password = findViewById(R.id.paypal_password);
                        String pass = password.getText().toString();

                        if (user.isEmpty() || pass.isEmpty()) {
                            Toast.makeText(payment_options.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!pass.matches("^\\d{1,10}$")) {
                            Toast.makeText(payment_options.this, "Please enter valid password length", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(payment_options.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                        Message paypal = new Message(user, pass);
                        myRef.push().setValue(paypal);

                        Intent intent = new Intent(payment_options.this, payment_options.class);
                        startActivity(intent);
                        finish();

                    }


                });
                paymentfields.addView(confirm);
                confirm.setVisibility(View.VISIBLE);

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(payment_options.this, payment_options.class);
                        startActivity(intent);
                    }
                });
                paymentfields.addView(back);
                back.setVisibility(View.VISIBLE);


                paymentfields.setVisibility(View.VISIBLE);
            }


            });*/


    }

}