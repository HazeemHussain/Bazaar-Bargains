package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    //Checking if the user name and passwords field are empty
    private void checkingLoginFields() {
        String userName = inputUserName.getText().toString();
        String password = inputPassword.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(loginActivity.this, "PLEASE ENTER YOUR USERNAME", Toast.LENGTH_SHORT).show();

        } else if (password.isEmpty()) {
            Toast.makeText(loginActivity.this, "PLEASE ENTER YOUR PASSWORD", Toast.LENGTH_SHORT).show();
        } else {

            loadingbar.setTitle("LOGGING IN");
            loadingbar.setMessage("VERIFYING YOUR DETAILS, PLEASE BE PATIENT");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();


            verifyingLogin(userName, password);
        }

    }

    //This method will go through the data in the firebase to verifying the
    //details user entered to login
    private void verifyingLogin(String userName, String password) {

        final DatabaseReference dbref;
        dbref = FirebaseDatabase.getInstance().getReference();
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Checking if the user name exists in firebase
                if (snapshot.child(parentDBName).child(userName).exists()) {
                    Users userData = snapshot.child(parentDBName).child(userName).getValue(Users.class);

                    //Checking if the username and password are correct by searching through firebase
                    if (userData.getUserName().equals(userName)) {
                        if (userData.getPassword().equals(password)) {
                            loadingbar.dismiss();
                            Toast.makeText(loginActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();


                            //NEED TO PUT HOMEPAGE LINK HERE SO IF THE LOGIN IS SUCCESSFUL IT TAKES USERS TO THE LOGIN PAGE
                            Intent intent = new Intent(loginActivity.this, ForgotPasswordActivity.class);
                            startActivity(intent);
                        }
                    } else {

                        //if the password is incorrect then displaying the message and dismissing the loading bar
                        loadingbar.dismiss();
                        Toast.makeText(loginActivity.this, "INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //Displaying msg if user name doesnt exits in firebase
                    loadingbar.dismiss();
                    Toast.makeText(loginActivity.this, "USERNAME DOESN'T EXIST", Toast.LENGTH_SHORT).show();
                    Toast.makeText(loginActivity.this, "PLEASE CREATE A NEW ACCOUNT", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}