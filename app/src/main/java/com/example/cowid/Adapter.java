package com.example.cowid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String[] states;
    String[] cases;

    public Adapter(Context context,String states[], String cases[]){
        this.context=context;
        this.states=states;
        this.cases=cases;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.card, parent, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).state.setText(states[position]);
        ((Item)holder).confirmed_cases.setText(cases[position]);
    }

    @Override
    public int getItemCount() {
        return states.length;
    }


    public class Item extends RecyclerView.ViewHolder{
        TextView state;
        TextView confirmed_cases;
        public Item(@NonNull View itemView) {
            super(itemView);
            state=(TextView) itemView.findViewById(R.id.state);
            confirmed_cases=(TextView) itemView.findViewById(R.id.confirmed_cases);

        }
    }
}
