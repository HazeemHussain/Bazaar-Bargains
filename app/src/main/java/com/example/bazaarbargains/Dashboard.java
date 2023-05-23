package com.example.bazaarbargains;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Dashboard extends AppCompatActivity {

    //Declaration of variables
    private Button logout, deleteAccount;
    private Button passwordChange, usernameChange, imageChangeBtn;
    private TextView userName, fullName;

    private ImageView newImage;
    private String default_profile_image = "@drawable/usericon";
    private static final int PICK_IMAGE_REQUEST = 1;

    String currentUser = loginActivity.currentUser;

    //OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_dashboard);

        initView();
        bottomBar();
        retrievingProfileImage();
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

        imageChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a file picker or image gallery intent
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        //Logout button that will log out the user when pressed
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this, loginActivity.class);
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

    /*
    This method stores the profile picture user selects to the database under that user's node
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

            // Get the current user's username (replace "hhus" with the actual username)

            // Create a new child reference under the username to store the image URI
            DatabaseReference userRef = usersRef.child(currentUser);

            // Set the image URI value to the child reference
            userRef.child("imageUri").setValue(imageUri.toString());

            newImage.setImageURI(imageUri);

        }

    }

    private void retrievingProfileImage() {
        if (currentUser != null && !currentUser.isEmpty()) {
            //userName.setText(currentUser);
            //Retrieve the data from the database
            final DatabaseReference dbref;
            dbref = FirebaseDatabase.getInstance().getReference("Users");

            dbref.child(currentUser).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String imageUri = dataSnapshot.getValue(String.class);
                        if (imageUri != null) {
                            // Set the image URI to the image change button
                            Glide.with(Dashboard.this).load(imageUri).into(newImage);
                        } else {
                            // Load the default image
                            Glide.with(Dashboard.this).load(R.drawable.usericon).into(newImage);
                        }
                    } else {
                        // Load the default image
                        Glide.with(Dashboard.this).load(R.drawable.usericon).into(newImage);
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {

        }

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
        imageChangeBtn = (Button) findViewById(R.id.imageChangeBtn);
        newImage = (ImageView) findViewById(R.id.imageView);
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