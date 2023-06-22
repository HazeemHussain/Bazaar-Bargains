package com.example.bazaarbargains;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ratingAdapter extends RecyclerView.Adapter<ratingAdapter.NumberViewHolder> {

    private List<Integer> ratingList;
    private int clickedPosition = -1;
    public  int numberStar = 0;
    public static int numberStar1 ;


    public ratingAdapter(List<Integer> ratingList) {
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating, parent, false);
        return new NumberViewHolder(view);
    }

    @Override

        public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
            int im = ratingList.get(position);
            holder.star.setImageResource(im);

        if (numberStar < position + 1) {
            numberStar = position + 1;

        }

        if (position <= clickedPosition) {
            holder.star.setImageResource(R.drawable.starfill);



        } else {
            holder.star.setImageResource(im);
        }

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedPosition = holder.getAdapterPosition();
                Log.d("NumberStar Value", String.valueOf(numberStar));
                Log.d("NumberStar Value", String.valueOf(clickedPosition));
                numberStar1=clickedPosition+1;
                Log.d("NumberStar Value", String.valueOf(numberStar1));


                notifyDataSetChanged();
            }
        });

        }





    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        private ImageView star;


        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            star = itemView.findViewById(R.id.star);



        }


    }
}
