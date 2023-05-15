package com.example.bazaarbargains;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyHolder> {
    ArrayList<categoryModel> data;

    Context context;

    public categoryAdapter(Context context, ArrayList<categoryModel> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
       // holder.cat.setText(data.get(position));
    categoryModel mode = data.get(position);
        holder.image.setImageResource(mode.getCatImage());
        holder.cat.setText(mode.getCatName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
       TextView cat;
        ImageView image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //cat = itemView.findViewById(R.id.catTitle);

            image = itemView.findViewById(R.id.catimage);
            cat = itemView.findViewById(R.id.catTitle);
        }
    }

}
