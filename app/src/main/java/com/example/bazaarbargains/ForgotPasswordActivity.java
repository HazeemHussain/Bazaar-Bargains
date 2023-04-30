package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bazaarbargains.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ForgotPasswordActivity extends AppCompatActivity {


    private Button registerBtn;
    private EditText inputUsername, inputPassword;
    private String DBName = "Users"; //Name of the database parent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        registerBtn = (Button) findViewById(R.id.register_Btn);
        inputPassword = (EditText) findViewById(R.id.password);
        inputUsername = (EditText) findViewById(R.id.userName);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkingInputFields();
            }
        });
    }

    private void checkingInputFields() {
        String username = inputUsername.getText().toString();
        String pass = inputPassword.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "PLEASE ENTER YOUR USERNAME", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "PLEASE ENTER YOUR NEW PASSWORD", Toast.LENGTH_SHORT).show();
        } else {
            updatingPassword(username, pass);
        }
    }

    private void updatingPassword(String userName, String password) {
        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("Users");
        HashMap user = new HashMap();
        user.put("password", password);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    String name = snapshot.child("userName").getValue().toString();

                    if (name.equals(userName)) {
                        ref.child("password").setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "PASSWORD HAS BEEN CHANGED", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "USERNAME DOESNT EXIST", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}