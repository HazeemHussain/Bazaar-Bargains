package com.example.bazaarbargains;


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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class mainPage  extends AppCompatActivity  {
    private Button button;

    Button showItemButton;

    ArrayList<String> category;

    RecyclerView recyview;

    RecyclerView catRv;

    shoeAdapter adapter;

    categoryAdapter adap;
    String currentUser = loginActivity.currentUser;

     Button searchBtn;
     EditText searchBar;
    private ListView searchListView;
    private ArrayAdapter<String> searchAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


        //Calling search button and search fields
         searchBar = (EditText) findViewById(R.id.SearchField);
         searchListView = (ListView) findViewById(R.id.searchListView);
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
        category.add("Shoes");
        category.add("Hats");
        category.add("Tops");
        category.add("Bottoms");

        adap = new categoryAdapter(this,category);

        catRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        catRv.setAdapter(adap);


        recyview=(RecyclerView)findViewById(R.id.recyclerViewShoes) ;
        recyview.setLayoutManager(new GridLayoutManager(this,2));

        recyview.setItemAnimator(null);

        FirebaseRecyclerOptions<itemShoe> options =
        new FirebaseRecyclerOptions.Builder<itemShoe>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Shoes"), itemShoe.class)
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

                //Displaying the msg if search result doesnt exist in the database
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
        Intent intent = new Intent(mainPage.this, showIT.class);
        intent.putExtra(showIT.EXTRA_PRODUCT_NAME, productName);
        startActivity(intent);
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



}
