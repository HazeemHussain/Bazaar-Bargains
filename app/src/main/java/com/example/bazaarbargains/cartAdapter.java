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

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyViewHolder> {

    static Context context;
    ArrayList<modelAddCart> list;
    public static float myFloat;

    private OnRemoveItemClickListener onRemoveItemClickListener;
    private boolean hideRemoveButton;

    public cartAdapter(Context context, ArrayList<modelAddCart> list) {
        this.context = context;
        this.list = list;
        this.hideRemoveButton = false; // By default, set hideRemoveButton to false
    }

    public void setList(ArrayList<modelAddCart> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //Setting the hide feature on the remove button
    public void setHideRemoveButton(boolean hideRemoveButton) {
        this.hideRemoveButton = hideRemoveButton;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemcartlay, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        // Pass the hideRemoveButton flag and call setRemoveButtonVisibility
        viewHolder.setRemoveButtonVisibility(hideRemoveButton);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        modelAddCart modelAddCar = list.get(position);



       holder.name.setText(modelAddCar.getItemName());
       holder.price.setText("$"+ modelAddCar.getitemPrice());
        holder.quantity.setText("Total Quantity: "+ modelAddCar.getQuantity());
        holder.sizec.setText("Size: "+ modelAddCar.getSizec());

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


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, quantity, veiwbut,sizec;
        ImageView image2;
        Button b;


        //Setting the remove button to invisible if user is viewing products through order history activity
        public void setRemoveButtonVisibility(boolean hideRemoveButton) {
            if (hideRemoveButton) {
                b.setVisibility(View.GONE);
            } else {
                b.setVisibility(View.VISIBLE);
            }
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            quantity = itemView.findViewById(R.id.quanti);
            image2= itemView.findViewById(R.id.checkImage);
            b= itemView.findViewById(R.id.removebut);
            sizec= itemView.findViewById(R.id.sizec);

        }
    }

}
