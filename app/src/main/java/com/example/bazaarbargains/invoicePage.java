package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class invoicePage extends AppCompatActivity {

    TextView backbutton;
    private int backButtonCount = 0;
    private static final int MAX_BACK_BUTTON_COUNT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_page);

        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(invoicePage.this, mainPage.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {

        backButtonCount++;

        if (backButtonCount > MAX_BACK_BUTTON_COUNT) {
            // Displaying the message when back button is pressed three or more times
            Toast.makeText(this, "Click back button on top left to go back to main page", Toast.LENGTH_SHORT).show();
            // Resetting the counter if back button is pressed more than three times
            backButtonCount = 0;
        } else {

        }
    }
}
