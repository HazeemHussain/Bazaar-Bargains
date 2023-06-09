package com.example.bazaarbargains;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class shoeAdapter extends FirebaseRecyclerAdapter<shopItem,shoeAdapter.veiwshoeholder>
{

    private int j;
    View view;


    public shoeAdapter(@NonNull FirebaseRecyclerOptions<shopItem> options, int i) {
        super(options);
        j = i;

    }

    public shoeAdapter(@NonNull FirebaseRecyclerOptions<shopItem> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull veiwshoeholder holder, int position, @NonNull shopItem shopItem) {
        holder.name.setText(shopItem.getName());
        holder.price.setText("$" + shopItem.getPrice());
        Glide.with(holder.image2.getContext()).load(shopItem.getImage()).into(holder.image2);
        Log.d("RecyclerView", "Item added: " + shopItem.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), showIT.class);
                intent.putExtra("itemname", shopItem.getName());
                intent.putExtra("itemprice", shopItem.getPrice());
                intent.putExtra("itemimage", shopItem.getImage());
                intent.putExtra("itemdesc", shopItem.getDescription());
                intent.putExtra("itemsize", shopItem.getSize());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @NonNull
    @Override
    public veiwshoeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int option = j; // can be 1 or 2


        switch (option) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemviewlayout,parent,false);

                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcartlay,parent,false);

                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }

        return new veiwshoeholder(view);
    }

    class veiwshoeholder extends RecyclerView.ViewHolder
    {
        ImageView image2;
        TextView name, price, veiwbut,description;
        CardView cardView;

        public veiwshoeholder(@NonNull View itemView) {
            super(itemView);
            image2= itemView.findViewById(R.id.checkImage);
            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            description = itemView.findViewById(R.id.descbox);



            //veiwbut = itemView.findViewById(R.id.viewbutton);
            cardView = itemView.findViewById(R.id.shoecard);




        }
    }
}