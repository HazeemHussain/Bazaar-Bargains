package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bazaarbargains.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
     String DBName = "Users"; //Name of the database parent
    private CheckBox showPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        registerBtn = findViewById(R.id.register_Btn);
        inputPassword = findViewById(R.id.password);
        inputUsername = findViewById(R.id.userName);
        showPassword = findViewById(R.id.showPassword_checkbox);

        showPassword();
        registerBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                checkingInputFields();
            }
        });
    }

    public void showPassword() {
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int inputType = inputPassword.getInputType();
                if (showPassword.isChecked()) {
                    inputPassword.setTransformationMethod(null);
                } else {
                    inputPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }



    private void checkingInputFields() {
        String username = inputUsername.getText().toString().trim();
        String pass = inputPassword.getText().toString().trim();

        if (username.isEmpty()) {
           // Toast.makeText(ForgotPasswordActivity.this, "PLEASE ENTER YOUR USERNAME", Toast.LENGTH_SHORT).show();
            inputUsername.setError("PLEASE ENTER YOUR USERNAME");
        } else if (pass.isEmpty()) {
            inputPassword.setError("PLEASE ENTER YOUR PASSWORD");
            //Toast.makeText(ForgotPasswordActivity.this, "PLEASE ENTER YOUR NEW PASSWORD", Toast.LENGTH_SHORT).show();
        } else {
            updatingPassword(username, pass);
        }
    }
    public void publiccheckingInputFields(String username, String pass) {
        username = inputUsername.getText().toString().trim();
        pass = inputPassword.getText().toString().trim();

        if (username.isEmpty()) {
            // Toast.makeText(ForgotPasswordActivity.this, "PLEASE ENTER YOUR USERNAME", Toast.LENGTH_SHORT).show();
            inputUsername.setError("PLEASE ENTER YOUR USERNAME");
        } else if (pass.isEmpty()) {
            inputPassword.setError("PLEASE ENTER YOUR PASSWORD");
            //Toast.makeText(ForgotPasswordActivity.this, "PLEASE ENTER YOUR NEW PASSWORD", Toast.LENGTH_SHORT).show();
        } else {
            updatingPassword(username, pass);
        }
    }
    public void updatingPassword(String userName, String password) {
        final DatabaseReference ref;

        //getting the reference by going into users and getting the user name
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
        HashMap user = new HashMap();
        user.put("password", password);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //checking if the username the user entered on the app exists on firebase
                if (snapshot.exists()) {
                    // String value = snapshot.getValue(String.class);

                    //If the username exists then changing the password to the new one
                    ref.child("password").setValue(password);

                    //Displaying message
                    Toast.makeText(ForgotPasswordActivity.this, "PASSWORD HAS BEEN CHANGED", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, loginActivity.class);
                    startActivity(intent);
                } else {

                    //If the username is not found
                    Toast.makeText(ForgotPasswordActivity.this, "USERNAME DOESNT EXIST", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void updatingUserName(String userName) {
        final DatabaseReference ref;

        //getting the reference by going into users and getting the user name
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("userName").setValue(userName);
        HashMap user = new HashMap();
        user.put("userName", userName);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //checking if the username the user entered on the app exists on firebase
                if (snapshot.exists()) {
                    // String value = snapshot.getValue(String.class);

                    //If the username exists then changing the password to the new one
                    ref.child("userName").setValue(userName);

                    //Displaying message
                    Toast.makeText(ForgotPasswordActivity.this, "USERNAME HAS BEEN CHANGED", Toast.LENGTH_SHORT).show();

                } else {

                    //If the username is not found
                    Toast.makeText(ForgotPasswordActivity.this, "USERNAME DOESNT EXIST", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
