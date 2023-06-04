package com.example.bazaarbargains;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class sizeAdapter extends RecyclerView.Adapter<sizeAdapter.NumberViewHolder> {

    private List<String> numbersList;

    public  String sizec ;
    //private String sizec="";
    private int selectedItem = -1;
    private View.OnClickListener clickListener;

    public sizeAdapter(List<String> numbersList, View.OnClickListener clickListener) {
        this.numbersList = numbersList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sizemodel, parent, false);
        return new NumberViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        String number = numbersList.get(position);

        if (position == selectedItem) {
            holder.card.setBackgroundColor(Color.BLACK);
            holder.numberTextView.setTextColor(Color.WHITE);
        } else {
            holder.card.setBackgroundColor(Color.WHITE);
            holder.numberTextView.setTextColor(Color.BLACK);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = holder.getBindingAdapterPosition();
                notifyDataSetChanged();

                String selectedSize = numbersList.get(selectedItem);
                sizec=selectedSize;


            }
        });

        holder.bind(number);

    }


    @Override
    public int getItemCount() {
        return numbersList.size();
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        private TextView numberTextView;
        private CardView card;

        public NumberViewHolder(@NonNull View itemView, View.OnClickListener clickListener) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.sizeTitle);
            card = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(clickListener);
        }

        public void bind(String number) {
            numberTextView.setText(number);
        }
    }
}
