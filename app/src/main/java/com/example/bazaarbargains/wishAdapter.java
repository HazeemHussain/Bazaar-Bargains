package com.example.bazaarbargains;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class wishAdapter extends RecyclerView.Adapter<wishAdapter.MyHolder>  {
    ArrayList<wishItem> data;



    Context context;

    public wishAdapter( Context context,ArrayList<wishItem> data) {
        this.data = data;

        this.context = context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemwishlay, parent, false);
        return new wishAdapter.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        wishItem wish = data.get(position);
        holder.name.setText(wish.getItemName());
        holder.price.setText("$"+ wish.getItemPrice());
        Glide.with(holder.image2.getContext()).load(wish.getUrl()).into(holder.image2);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image2;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //cat = itemView.findViewById(R.id.catTitle);
            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            image2= itemView.findViewById(R.id.checkImage);


        }
    }

}
