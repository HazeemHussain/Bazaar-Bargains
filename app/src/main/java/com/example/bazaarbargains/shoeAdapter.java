package com.example.bazaarbargains;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class shoeAdapter extends FirebaseRecyclerAdapter<itemShoe,shoeAdapter.veiwshoeholder>
{


    public shoeAdapter(@NonNull FirebaseRecyclerOptions<itemShoe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull veiwshoeholder holder, int position, @NonNull itemShoe itemShoe) {


       holder.name.setText(itemShoe.getName());
        holder.price.setText("$"+ itemShoe.getPrice());
        Glide.with(holder.image2.getContext()).load(itemShoe.getImage()).into(holder.image2);

        holder.veiwbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(holder.itemView.getContext(), showIT.class);

                 intent.putExtra("itemname", itemShoe.getName());
                intent.putExtra("itemprice", itemShoe.getPrice());
                intent.putExtra("itemimage", itemShoe.getImage());

                    holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public veiwshoeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemviewlayout,parent,false);
        return new veiwshoeholder(view);
    }

    class veiwshoeholder extends RecyclerView.ViewHolder
    {
        ImageView image2;
        TextView name, price, veiwbut;

        public veiwshoeholder(@NonNull View itemView) {
            super(itemView);
            image2=(ImageView) itemView.findViewById(R.id.imageView1);
            name = (TextView)itemView.findViewById(R.id.itemName);
            price = (TextView)itemView.findViewById(R.id.itemPrice);
            veiwbut = (TextView)itemView.findViewById(R.id.viewbutton);


        }
    }
}
