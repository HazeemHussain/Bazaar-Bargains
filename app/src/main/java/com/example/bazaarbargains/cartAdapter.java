package com.example.bazaarbargains;

import android.content.Context;
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

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyViewHolder> {

    Context context;

    ArrayList<modelAddCart> list;

    public static float myFloat;

    private OnRemoveItemClickListener onRemoveItemClickListener;


    public cartAdapter(Context context, ArrayList<modelAddCart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemcartlay,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        modelAddCart modelAddCar = list.get(position);



       holder.name.setText(modelAddCar.getItemName());
       holder.price.setText("$"+ modelAddCar.getitemPrice());
        holder.quantity.setText("Total Quantity: "+ modelAddCar.getQuantity());

        myFloat+= Float.parseFloat(modelAddCar.getPerItemCost());



        Glide.with(holder.image2.getContext()).load(modelAddCar.getUrl()).into(holder.image2);

        holder.b.setOnClickListener(v -> {
            if (onRemoveItemClickListener != null) {
                onRemoveItemClickListener.onRemoveItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnRemoveItemClickListener {
        void onRemoveItemClicked(int position);
    }

    public void setOnRemoveItemClickListener(OnRemoveItemClickListener listener) {
        this.onRemoveItemClickListener = listener;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, quantity, veiwbut;
        ImageView image2;
        Button b;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.itemName);
            price = (TextView)itemView.findViewById(R.id.itemPrice);
            quantity = (TextView)itemView.findViewById(R.id.quanti);
            veiwbut = (TextView)itemView.findViewById(R.id.viewbutton);
            image2=(ImageView) itemView.findViewById(R.id.checkImage);
            b=(Button) itemView.findViewById(R.id.removebut);


        }
    }

}
