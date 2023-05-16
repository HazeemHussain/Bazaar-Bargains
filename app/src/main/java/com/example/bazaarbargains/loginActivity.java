package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private Button forgotPassword,  joinBtn;
    private EditText inputUserName, inputPassword;
    private String parentDBName = "Users";
    private ProgressDialog loadingbar;

    private static String userloggedin = "name";
    public static String currentUser;

    CheckBox showPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Variables
        Button loginBtn = (Button) findViewById(R.id.login_Btn);
        forgotPassword = (Button) findViewById(R.id.forgotPasswordBtn);
        inputUserName = (EditText) findViewById(R.id.userName);
        inputPassword = (EditText) findViewById(R.id.password);
        joinBtn = (Button) findViewById(R.id.join_Btn);
        showPassword = (CheckBox) findViewById(R.id.showPassword_checkbox);
        loadingbar = new ProgressDialog(this);

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

        //Signup Button
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, SignUpActivity.class );
                startActivity(intent);
            }
        });

    }

    //Checking if the user name and passwords field are empty
    private void checkingLoginFields() {
        String userName = inputUserName.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (userName.isEmpty()) {
            inputUserName.setError("ENTER YOUR USERNAME");
           // Toast.makeText(loginActivity.this, "PLEASE ENTER YOUR USERNAME", Toast.LENGTH_SHORT).show();

        } else if (password.isEmpty()) {
            inputPassword.setError("ENTER YOUR PASSWORD");
            //Toast.makeText(loginActivity.this, "PLEASE ENTER YOUR PASSWORD", Toast.LENGTH_SHORT).show();
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
                            currentUser = userName;
                            //IF THE LOGIN IS SUCCESSFUL IT TAKES USERS TO THE LOGIN PAGE
                            Intent intent = new Intent(loginActivity.this, mainPage.class);
                            startActivity(intent);
                            
                        } else if (!userData.getPassword().equals(password)) {
                            loadingbar.dismiss();
                            Toast.makeText(loginActivity.this, "INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
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
                   // Toast.makeText(loginActivity.this, "PLEASE CREATE A NEW ACCOUNT", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}