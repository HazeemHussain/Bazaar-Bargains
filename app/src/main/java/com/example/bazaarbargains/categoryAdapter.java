package com.example.bazaarbargains;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyHolder> {
    ArrayList<categoryModel> data;


    Intent intent;
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

    categoryModel mode = data.get(position);
        holder.image.setImageResource(mode.getCatImage());
        holder.cat.setText(mode.getCatName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catName = holder.cat.getText().toString();


                switch (catName) {
                    case "Hats":
                        intent = new Intent(holder.itemView.getContext(), hatPage.class);
                        break;
                    case "Tops":
                        intent = new Intent(holder.itemView.getContext(), Tops.class);
                        break;
                    case "Bottoms":
                        intent = new Intent(holder.itemView.getContext(), bottomPage.class);
                        break;
                    case "Shoes":
                        intent = new Intent(holder.itemView.getContext(), shoesPage.class);
                        break;

                }

                holder.itemView.getContext().startActivity(intent);
            }
        });
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
