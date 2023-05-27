package com.example.bazaarbargains;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class Filter_Adapter extends BaseExpandableListAdapter  {

    private Context context;
    private List<String> groups;
    private HashMap<String, List<String>> childItems;

    RecyclerView rec;

    shoeAdapter adapter;
    AppCompatActivity activity;



public Filter_Adapter(Context context, List<String> groups, HashMap<String,List<String>> childItems){
    this.context = context;
    this.groups = groups;
    this.childItems = childItems;

}
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int g_position) {
        String group = groups.get(g_position);
        List<String> children = childItems.get(group);
        return children != null ? children.size() : 0;
    }

    @Override
    public Object getGroup(int g_position) {
        return groups.get(g_position);
    }

    @Override
    public Object getChild(int g_position, int c_position) {
            String group = groups.get(g_position);
            List<String> children = childItems.get(group);
            return children != null ? children.get(c_position) : null;
    }

    @Override
    public long getGroupId(int g_position) {
        return g_position;
    }

    @Override
    public long getChildId(int g_position, int c_position) {
        return c_position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int g_position, boolean expand, View view, ViewGroup vg) {
        String group = (String) getGroup(g_position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group_item, null);
        }

        TextView groupTextView = view.findViewById(R.id.groupTextView);
        groupTextView.setText(group);

        return view;
    }

    @Override
    public View getChildView(int g_position, int c_position, boolean expand, View view, ViewGroup vg) {
        String child = (String) getChild(g_position, c_position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_child_item, null);
        }

        TextView childTextView = view.findViewById(R.id.childTextView);
        childTextView.setText(child);

       if (g_position == 1 && c_position == 0) {
            // Apply the click listener to the specific child item
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, hatPage.class);

                    context.startActivity(intent);
                }
            });
        }
        else if (g_position == 1 && c_position == 1) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Tops.class);

                    context.startActivity(intent);
                }
            });
        }
              else if (g_position == 1 && c_position == 2){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, bottomPage.class);
                        context.startActivity(intent);
                    }
                });
        }
        else if (g_position == 1 && c_position == 3){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, shoesPage.class);

                    context.startActivity(intent);
                }
            });
        }
        else if (g_position == 0 && c_position == 0){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });
       }
        return view;
    }




    @Override
    public boolean isChildSelectable(int g_position, int c_position) {
        return true;
    }
}
