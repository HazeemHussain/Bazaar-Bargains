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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shoeAdapter extends FirebaseRecyclerAdapter<itemShoe,shoeAdapter.veiwshoeholder>
{

    private int j;
    View view;

    HashMap<String, List<Boolean>> childItemCheckedState;


    public shoeAdapter(@NonNull FirebaseRecyclerOptions<itemShoe> options,int i) {
        super(options);
        j = i;


    }
    public shoeAdapter(@NonNull FirebaseRecyclerOptions<itemShoe> options,int i,HashMap<String, List<Boolean>> childItemCheckedState) {
        super(options);
        j = i;
        this.childItemCheckedState = childItemCheckedState;

    }
    public shoeAdapter(@NonNull FirebaseRecyclerOptions<itemShoe> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull veiwshoeholder holder, int position, @NonNull itemShoe itemShoe) {


        holder.name.setText(itemShoe.getName());
        holder.price.setText("$" + itemShoe.getPrice());
        Glide.with(holder.image2.getContext()).load(itemShoe.getImage()).into(holder.image2);
        Log.d("RecyclerView", "Item added: " + itemShoe.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), showIT.class);
                intent.putExtra("itemname", itemShoe.getName());
                intent.putExtra("itemprice", itemShoe.getPrice());
                intent.putExtra("itemimage", itemShoe.getImage());
                intent.putExtra("itemdesc", itemShoe.getDescription());
                intent.putExtra("itemsize", itemShoe.getSize());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
//    protected void onBindViewHolder(@NonNull veiwshoeholder holder, int position, @NonNull itemShoe itemShoe) {
//
//        int option = j; // can be 1 or 2
//
//        switch (option) {
//            case 1:
//                System.out.println("Option 1 selected.");
//                break;
//            case 2:
//                holder.name.setText(itemShoe.getName());
//                holder.price.setText("$"+ itemShoe.getPrice());
//                Glide.with(holder.image2.getContext()).load(itemShoe.getImage()).into(holder.image2);
//                break;
//            default:
//                System.out.println("Invalid option selected.");
//                break;
//        }
//
//       holder.name.setText(itemShoe.getName());
//        holder.price.setText("$"+ itemShoe.getPrice());
//        Glide.with(holder.image2.getContext()).load(itemShoe.getImage()).into(holder.image2);
//
//        holder.veiwbut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 Intent intent = new Intent(holder.itemView.getContext(), showIT.class);
//
//                 intent.putExtra("itemname", itemShoe.getName());
//                intent.putExtra("itemprice", itemShoe.getPrice());
//                intent.putExtra("itemimage", itemShoe.getImage());
//
//                    holder.itemView.getContext().startActivity(intent);
//
//            }
//        });
//
//    }



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
