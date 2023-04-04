package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bazaarbargains.databinding.ActivityMainBinding;
import com.example.bazaarbargains.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    String firstName, lastName, userName, password;
    FirebaseDatabase db;
    DatabaseReference reference;
    private Button haveAnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        haveAnAccount = (Button) findViewById(R.id.haveAnAccountBtn);
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = binding.firstName.getText().toString();
                lastName = binding.lastName.getText().toString();
                userName = binding.userName.getText().toString();
                password = binding.password.getText().toString();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !userName.isEmpty() && !password.isEmpty()) {
                    Users users = new Users(firstName, lastName, userName, password);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child(userName).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.firstName.setText("");
                            binding.lastName.setText("");
                            binding.userName.setText("");
                            binding.password.setText("");
                            Toast.makeText(SignUpActivity.this, "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "PLEASE FILL IN ALL THE DETAILS", Toast.LENGTH_SHORT).show();
                }


            }
        });

        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });


    }
}