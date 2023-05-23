package com.example.bazaarbargains;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class sizeAdapter extends RecyclerView.Adapter<sizeAdapter.NumberViewHolder> {

    private List<String> numbersList;
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
        holder.bind(number);
    }

    @Override
    public int getItemCount() {
        return numbersList.size();
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        private TextView numberTextView;

        public NumberViewHolder(@NonNull View itemView, View.OnClickListener clickListener) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.sizeTitle);
            itemView.setOnClickListener(clickListener);
        }

        public void bind(String number) {
            numberTextView.setText(number);
        }
    }
}
