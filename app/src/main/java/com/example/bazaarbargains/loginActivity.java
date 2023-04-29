package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private Button forgotPassword;
    private EditText inputUserName, inputPassword;
    private String parentDBName = "Users";
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Variables
        Button loginBtn = (Button) findViewById(R.id.login_Btn);
        forgotPassword = (Button) findViewById(R.id.forgotPasswordBtn);
        inputUserName = (EditText) findViewById(R.id.userName);
        inputPassword = (EditText) findViewById(R.id.password);
        loadingbar = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkingLoginFields();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkingLoginFields() {
        String userName = inputUserName.getText().toString();
        String password = inputPassword.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(loginActivity.this, "PLEASE ENTER YOUR USERNAME", Toast.LENGTH_SHORT).show();

        } else if (password.isEmpty()) {
            Toast.makeText(loginActivity.this, "PLEASE ENTER YOUR PASSWORD", Toast.LENGTH_SHORT).show();
        } else {

            loadingbar.setTitle("LOGIN ACCOUNT");
            loadingbar.setMessage("VERIFYING YOUR DETAILS, PLEASE BE PATIENT");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();


            verifyingLogin(userName, password);
        }

    }

    private void verifyingLogin(String userName, String password) {

        
    }


}