package com.example.bazaarbargains;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Dashboard extends AppCompatActivity {

   private Button logout, deleteAccount;
   private Button passwordChange, usernameChange, newImage;
   private TextView userName, fullName;

    String currentUser = loginActivity.currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dashboard);

        initView();
        bottomBar();
        retrievingFullName();
        retrievingUserName();

        passwordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Displaying the dialog on the screen
                showDialogChangePassword();
            }
        });

        usernameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displaying the dialog on the screen
                showDialogChangeusername();
            }
        });

       // logout = findViewById(R.id.logoutBtn);
        //        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Dashboard.this, loginActivity.class );
//                startActivity(intent);
//
//            }
//        });


    }

    private void showDialogChangeusername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change User Name");
        builder.setIcon(R.drawable.passwordkeylogo);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Creating an EditText for the new password
        EditText userNameEditText = new EditText(this);
        userNameEditText.setHint("Enter your new username");
        layout.addView(userNameEditText);

        builder.setView(layout);

        // Set positive button and its click listener
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered password from the EditText
                String newUserName = userNameEditText.getText().toString();

                if (currentUser != null && !currentUser.isEmpty() && !newUserName.isEmpty()) {
                    ForgotPasswordActivity activity = new ForgotPasswordActivity();
                    activity.updatingUserName(currentUser);

                } else if (newUserName.isEmpty()) {
                    Toast.makeText(Dashboard.this, "Please enter your new username", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Dashboard.this, "User needs to login to change the username", Toast.LENGTH_SHORT).show();

                }


                // Dismissing the dialog box
                dialog.dismiss();
            }
        });

        // Set negative button and its click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Retrieving user name of the logged in user from database and displaying it in username
    text field*/
    private void retrievingUserName() {
        if (currentUser != null && !currentUser.isEmpty()) {
            userName.setText(currentUser);
        } else {
            userName.setText("Log In");
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Dashboard.this, loginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    /* Retrieving full name of the logged in user from database and displaying it in full name
    text field*/
    private void retrievingFullName() {

        if (currentUser != null && !currentUser.isEmpty()) {
            //userName.setText(currentUser);
            //Retrieve the data from the database
            final DatabaseReference dbref;
            dbref = FirebaseDatabase.getInstance().getReference("Users");

            dbref.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String firstName = dataSnapshot.child("firstName").getValue(String.class);
                        String lastName = dataSnapshot.child("lastName").getValue(String.class);

                        String fullNameString = firstName + " " + lastName;
                        fullName.setText(fullNameString);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            fullName.setText("Log In");
            fullName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Dashboard.this, loginActivity.class);
                    startActivity(intent);
                }
            });
        }



    }

    /* Changing the user password if the user clicks on the password change button on the
     accounts page*/
    private void showDialogChangePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Password");
        builder.setIcon(R.drawable.passwordkeylogo);

        // Create the password change view programmatically
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Creating an EditText for the new password
        EditText newPasswordEditText = new EditText(this);
        newPasswordEditText.setHint("Enter your new password");
        layout.addView(newPasswordEditText);

        builder.setView(layout);

        // Set positive button and its click listener
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered password from the EditText
                String newPassword = newPasswordEditText.getText().toString();

                if (currentUser != null && !currentUser.isEmpty() && !newPassword.isEmpty()) {
                    ForgotPasswordActivity activity = new ForgotPasswordActivity();
                    activity.updatingPassword(currentUser, newPassword);

                } else if (newPassword.isEmpty()) {
                    Toast.makeText(Dashboard.this, "Please enter your new password", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Dashboard.this, "User is not logged in", Toast.LENGTH_SHORT).show();

                }


                // Dismissing the dialog box
                dialog.dismiss();
            }
        });

        // Set negative button and its click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void initView() {
        //Declarations
        logout = (Button) findViewById(R.id.logoutBtn);
        deleteAccount = (Button) findViewById(R.id.deleteAccount);
        passwordChange = (Button) findViewById(R.id.passwordChangeBtn);
        usernameChange = (Button) findViewById(R.id.userNameBtn);
        userName = (TextView) findViewById(R.id.userNameField);
        fullName = (TextView) findViewById(R.id.fullnameField);
        newImage = (Button) findViewById(R.id.imageChangeBtn);
    }

    private void bottomBar() {
        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);
        bottom_bar.selectTabAt(0,true);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(Dashboard.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(Dashboard.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(Dashboard.this, wishlist.class));


                }else if (id == 3) {

                    startActivity(new Intent(Dashboard.this, cartRecList.class));

                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });
    }
}