package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Dashboard extends AppCompatActivity {

    //Declaration of variables
    private Button logout, deleteAccount;
    private Button passwordChange, usernameChange, viewHistoryBtn;
    private TextView userName, fullName;

    private ImageView newImage;
    private String default_profile_image = "@drawable/usericon";
    private static final int PICK_IMAGE_REQUEST = 1;

    String currentUser = loginActivity.currentUser;
    //String userfullname = loginActivity.fullName;

    //OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dashboard);

        initView();
        bottomBar();
       // retrievingProfileImage();
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

        viewHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting the data from the firebase when user clicks on the order history view button
                DatabaseReference invoiceRef = FirebaseDatabase.getInstance().getReference("Users/" + currentUser + "/invoice");
                invoiceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Creating an array list and storing the data retrieved from the database in arraylist
                        ArrayList<modelAddCart> invoiceList = new ArrayList<>();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            modelAddCart invoiceItem = childSnapshot.getValue(modelAddCart.class);
                            invoiceList.add(invoiceItem);
                        }
                        Intent intent = new Intent(Dashboard.this, OrderHistory.class);
                        intent.putExtra("invoiceList", invoiceList);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error if retrieval is canceled
                    }
                });
            }
        });

        //Logout button that will log out the user when pressed
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this, loginActivity.class);
                //Clearing the activity stack to make sure the user can not come back to this page
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletingUserAccount();
            }
        });

    }



    /* This method will delete the user account if user clicks on
        the delete account button*/
    private void deletingUserAccount() {
        // Retrieve the entered username from the EditText
        if (currentUser != null && !currentUser.isEmpty() && !currentUser.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation"); //Setting title
            builder.setMessage("Are you sure you want to delete your account?"); //Setting message

            //If user selects yes then its deletes the user account
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {

                                // Deleting the data from the user's node
                                reference.removeValue();

                                //When the account gets deleted it takes the user back to login page
                                Intent intent = new Intent(Dashboard.this, loginActivity.class);
                                //Clear stash of all the activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                // Displaying the success message
                                Toast.makeText(Dashboard.this, "Account has been deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                // If the old node does not exist
                                Toast.makeText(Dashboard.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled
                        }
                    });
                }
            });

            //If user selects no then it doesn't delete the account
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });

            builder.show();
        } else if (currentUser.isEmpty()) {
            Toast.makeText(Dashboard.this, "Please login to delete your account", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Dashboard.this, "User needs to login to delete the account", Toast.LENGTH_SHORT).show();
        }
    }


    /* Method that changes the the name of the node to the new user name user input
     * and also updates the "user name" child in the node*/
    private void showDialogChangeusername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change User Name");
        builder.setIcon(R.drawable.passwordkeylogo);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Creating an EditText for the new username
        EditText userNameEditText = new EditText(this);
        userNameEditText.setHint("Enter your new username");
        layout.addView(userNameEditText);

        builder.setView(layout);

        // Set positive button and its click listener
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered username from the EditText
                final String newUserName = userNameEditText.getText().toString();

                if (currentUser != null && !currentUser.isEmpty() && !newUserName.isEmpty()) {
                    if (newUserName.equals(currentUser)) {
                        // If the new username is the same as the current username
                        Toast.makeText(Dashboard.this, "Please enter a different username", Toast.LENGTH_SHORT).show();
                    } else {
                        final DatabaseReference oldRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);

                        oldRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    // Get the data from the old node
                                    Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                                    if (data != null) {
                                        // Create a new node with the new username
                                        DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("Users").child(newUserName);

                                        // Transfer the data from the old node to the new node
                                        newRef.setValue(data);

                                        // Delete the old node
                                        oldRef.removeValue();

                                        //setting the user name child to the updated user name
                                        newRef.child("userName").setValue(newUserName);

                                        //Updating the user name and the full name on accounts page
                                        currentUser = newUserName;
                                        retrievingFullName();
                                        retrievingUserName();

                                        // Displaying the success message
                                        Toast.makeText(Dashboard.this, "Username has been changed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If the old node does not exist
                                    Toast.makeText(Dashboard.this, "User does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle onCancelled
                            }
                        });
                    }
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
        userName.setText(currentUser);
    }

    /* Retrieving full name of the logged in user from database and displaying it in full name
    text field*/
    private void retrievingFullName() {

            final DatabaseReference dbref;
            dbref = FirebaseDatabase.getInstance().getReference("Users");

            fullName.setText(""); // Set an empty string initially

            dbref.child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String firstName = dataSnapshot.child("firstName").getValue(String.class);
                        String lastName = dataSnapshot.child("lastName").getValue(String.class);

                        String fullNameString = firstName + " " + lastName;
                        fullName.setText(fullNameString); // Update the full name TextView
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });



        //fullName.setText(userfullname);

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
                    final DatabaseReference ref;

                    //getting the reference by going into users and getting the user name
                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
                    HashMap user = new HashMap();
                    user.put("password", newPassword);

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //checking if the username the user entered on the app exists on firebase
                            if (snapshot.exists()) {
                                // String value = snapshot.getValue(String.class);

                                //If the username exists then changing the password to the new one
                                ref.child("password").setValue(newPassword);

                                //Displaying message
                                Toast.makeText(Dashboard.this, "PASSWORD HAS BEEN CHANGED", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(ForgotPasswordActivity.this, loginActivity.class);
//                                startActivity(intent);
                            } else {

                                //If the username is not found
                                Toast.makeText(Dashboard.this, "USERNAME DOESNT EXIST", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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
        viewHistoryBtn = (Button) findViewById(R.id.viewHistoryBtn);
        //newImage = (ImageView) findViewById(R.id.imageView);
    }

    private void bottomBar() {
        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);
        bottom_bar.selectTabAt(0, true);

        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(Dashboard.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(Dashboard.this, mainPage.class));


                } else if (id == 2) {


                    startActivity(new Intent(Dashboard.this, wishlist.class));


                } else if (id == 3) {

                    startActivity(new Intent(Dashboard.this, cartRecList.class));

                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });
    }
}