package com.example.bazaarbargains;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dashboard);


        BottomNavigationView appBottomNavigationView = findViewById(R.id.bottom_navigation);
        appBottomNavigationView.setSelectedItemId(R.id.dashboard);
        appBottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.home:

                    startActivity(new Intent(Dashboard.this, mainPage.class));
                    return true;
                case R.id.cart:

                    startActivity(new Intent(Dashboard.this, cartRecList.class));
                    return true;
                case R.id.dashboard:

                    startActivity(new Intent(Dashboard.this, Dashboard.class));
                    return true;
            }
            return false;
        });
    }
}