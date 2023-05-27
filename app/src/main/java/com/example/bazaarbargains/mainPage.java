package com.example.bazaarbargains;


import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.RelativeLayout;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class mainPage  extends AppCompatActivity  {
    private Button button;

    Button showItemButton;

    ArrayList<categoryModel> category;

    RecyclerView recyview;

    RecyclerView catRv;

    shoeAdapter adapter;

    categoryAdapter adap;
    String currentUser = loginActivity.currentUser;


    private Button searchBtn;
    private EditText searchBar;
    private ListView searchListView;
    private ArrayAdapter<String> searchAdapter;
    private int backButtonCount = 0;
    private static final int MAX_BACK_BUTTON_COUNT = 3;

    private TextView filter;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        //Calling search button and search fields
        searchBar = findViewById(R.id.SearchField);
        searchListView = findViewById(R.id.searchListView);
        searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        searchListView.setAdapter(searchAdapter);
        filter = (TextView) findViewById(R.id.filter_button);
       // frameLayout = (FrameLayout) findViewById(R.id.frame_layout);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                searchingData(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        AnimatedBottomBar bottom_bar = findViewById(R.id.navBar);

filter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mainPage.this,Filter.class);
        startActivity(intent);
    }
});
        bottom_bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {

                int id = newIndex;

                if (id == 0) {

                    startActivity(new Intent(mainPage.this, Dashboard.class));
                } else if (id == 1) {


                    startActivity(new Intent(mainPage.this, mainPage.class));


                }else if (id == 2) {


                    startActivity(new Intent(mainPage.this, wishlist.class));


                }else if (id == 3) {


                    startActivity(new Intent(mainPage.this, cartRecList.class));


                }
            }



            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {

            }
        });



        //Calling search button and search fields

         searchBar = findViewById(R.id.SearchField);
         searchListView = findViewById(R.id.searchListView);
         searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
         searchListView.setAdapter(searchAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                searchingData(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        catRv = findViewById(R.id.catrecyclerView);

        category = new ArrayList<>();
        category.add(new categoryModel("Shoes",R.drawable.wewe));
        category.add(new categoryModel("Hats",R.drawable.hatsphoto));
        category.add(new categoryModel("Tops",R.drawable.shirtphoto));
        category.add(new categoryModel("Bottoms",R.drawable.pantsphoto));


        adap = new categoryAdapter(this,category);

        catRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        catRv.setAdapter(adap);


        recyview= findViewById(R.id.recyclerViewShoes);
        recyview.setLayoutManager(new GridLayoutManager(this,2));

        recyview.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
        new FirebaseRecyclerOptions.Builder<itemShoe>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Trending"), itemShoe.class)
                .build();

        adapter = new shoeAdapter(options,1);
        recyview.setAdapter(adapter);


    }

    private void searchingData(String query) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shoes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> searchResults = new ArrayList<>();
                boolean productExists = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String shoeName = snapshot.child("name").getValue(String.class);
                    if (shoeName != null && shoeName.toLowerCase().contains(query.toLowerCase())) {
                        searchResults.add(shoeName);
                        productExists = true;
                    }
                }



                //Displaying the msg if search result doesn't exist in the database
                if (!productExists || query.isEmpty()) {
                    searchResults.clear();
                    searchResults.add("The product does not exist");
                }

                //Checking if the search field is empty
                if (searchResults.isEmpty() || query.isEmpty()) {

                    //Removing the view list if the search list is empty
                    searchListView.setVisibility(View.GONE);
                } else {
                    searchListView.setVisibility(View.VISIBLE);
                }

                // Logging to check the contents of searchResults
                //  Log.d("Search", "Search results: " + searchResults.toString());

                //creating an array adapter and adding all the searched and using it display the results
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mainPage.this, android.R.layout.simple_list_item_1, searchResults);
                searchListView.setAdapter(adapter);

                // Set the height of the ListView based on the number of items
                setListViewHeight(searchListView);

                searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedProduct = (String) parent.getItemAtPosition(position);
                        // Perform the action to navigate to the selected product
                        navigateToProduct(selectedProduct);

//                        showIT getBundle = new showIT();
//                        getBundle.getBundele();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Search", "DatabaseError: " + databaseError.getMessage());
            }
        });


    }



    //This method calculates the total height required for the list view by measuring
    //Each item individually
    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void navigateToProduct(String productName) {
        //Getting shoe reference from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shoes");

        //Creating a query to find the specific product in the "Shoes" section in database
        Query query = ref.orderByChild("name").equalTo(productName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Iterating through the data that represent matched products
                    //and also retrieving the name, price and image of that product
                    String name = snapshot.child("name").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String size = snapshot.child("size").getValue(String.class);

                    //Creating an intent to navigate to showIT activity
                    Intent intent = new Intent(mainPage.this, showIT.class);
                    intent.putExtra(showIT.EXTRA_PRODUCT_NAME, productName); //Passing product name
                    intent.putExtra("itemname", name);
                    intent.putExtra("itemprice", price);
                    intent.putExtra("itemimage", image);
                    intent.putExtra("itemdesc", description);
                    intent.putExtra("itemsize", size);
                    startActivityForResult(intent, 1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Handles any errors that might occur during the retrieval of data from database
                Log.e("Search", "DatabaseError: " + databaseError.getMessage());
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    /*
    If back button is pressed the user shouldn't be able to go back to login page
    The only way to go back is to press the logout button on accounts page
     */

    @Override
    public void onBackPressed() {
        backButtonCount++;

        if (backButtonCount > MAX_BACK_BUTTON_COUNT) {
            // Displaying the message when back button is pressed three or more times
            Toast.makeText(this, "To go back, Please logout from the account tab", Toast.LENGTH_SHORT).show();
            // Resetting the counter if back button is pressed more than three times
            backButtonCount = 0;
        } else {

        }
    }

}
