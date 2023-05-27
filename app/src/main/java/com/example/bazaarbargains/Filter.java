package com.example.bazaarbargains;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Filter extends AppCompatActivity {

    private ExpandableListView filter_view;
    private ExpandableListAdapter filter_adapter;
    private List<String> groups;
    private HashMap<String, List<String>> childItems;
    private HashMap<String, List<Boolean>> childItemCheckedState;
    private Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        confirm = findViewById(R.id.confirm);
        filter_view = findViewById(R.id.expandableListView);
        prepareData();



        filter_adapter = new Filter_Adapter(this,groups,childItems);
        filter_view.setAdapter(filter_adapter);

confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       // Intent intent = new Intent(Filter.this, filtered_items.class);
       // startActivity(intent);



    }
});
    }

    private void prepareData() {
        groups = new ArrayList<>();
        childItems = new HashMap<>();

        groups.add("Brands");
        groups.add("Categories");
        groups.add("Sizes");


        List<String> childItemsGroup1 = new ArrayList<>();
        childItemsGroup1.add("Jordan");
        childItemsGroup1.add("Adidas");
        childItemsGroup1.add("Nike");

        List<String> childItemsGroup2 = new ArrayList<>();
        childItemsGroup2.add("Hats");
        childItemsGroup2.add("Shirts");
        childItemsGroup2.add("Pants");
        childItemsGroup2.add("Shoes");

        List<String> childItemsGroup3 = new ArrayList<>();
        childItemsGroup3.add("Size 11");
        childItemsGroup3.add("Size 12");
        childItemsGroup3.add("Size 13");


        childItems.put(groups.get(0), childItemsGroup1);
        childItems.put(groups.get(1), childItemsGroup2);
        childItems.put(groups.get(2), childItemsGroup3);




    }
}



