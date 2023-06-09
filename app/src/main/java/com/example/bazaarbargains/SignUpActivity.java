package com.example.bazaarbargains;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bazaarbargains.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    //Declaration of variables
    ActivitySignUpBinding binding;
    String firstName, lastName, userName, password;
    FirebaseDatabase db;
    DatabaseReference reference;
    CheckBox showPassword;
    EditText getPassword;
    Button haveAnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showPassword = findViewById(R.id.showPassword_checkbox);
        getPassword = findViewById(R.id.password);
        haveAnAccount = findViewById(R.id.haveAnAccountBtn);

        showPassword(); //Calling show password method
        sendingDataToFirebase();
        haveAnAccountBtn();

    }

    private void haveAnAccountBtn() {
        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Talking the user data and storing it to firebase
    private void sendingDataToFirebase() {
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = binding.firstName.getText().toString();
                lastName = binding.lastName.getText().toString();
                userName = binding.userName.getText().toString();
                password = binding.password.getText().toString();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !(password.length() < 6) && !(userName.length() < 6)) {
                    Users users = new Users(firstName, lastName, userName, password);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");

                    // Check if the username already exists
                    reference.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(SignUpActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                // Save the new user data
                                reference.child(userName).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        binding.firstName.setText("");
                                        binding.lastName.setText("");
                                        binding.userName.setText("");
                                        binding.password.setText("");
                                        Toast.makeText(SignUpActivity.this, "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();

                                        // IF THE SIGNUP IS SUCCESSFUL, IT TAKES USERS TO THE LOGIN PAGE
                                        Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle onCancelled event if needed
                        }
                    });


                } else if (userName.length() < 6) {


                    Toast.makeText(SignUpActivity.this, "THE USERNAME SHOULD BE MORE THAN 6 CHARACTERS", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "THE PASSWORD SHOULD BE MORE THAN 6 CHARACTERS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "PLEASE FILL IN ALL THE DETAILS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Showing the password when user click on show password button
    private void showPassword() {
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int inputType = getPassword.getInputType();
                if (showPassword.isChecked()) {
                    getPassword.setTransformationMethod(null);
                } else {
                    getPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }
}