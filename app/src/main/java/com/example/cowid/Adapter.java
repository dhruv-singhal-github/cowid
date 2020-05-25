package com.example.cowid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> states;
    ArrayList<String> cases;
    cardClickListener mcardclicklistener;

    public Adapter(Context context, ArrayList<String> states,ArrayList<String> cases,cardClickListener mcardclicklistener){
        this.context=context;
        this.states=states;
        this.cases=cases;
        this.mcardclicklistener=mcardclicklistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View row = inflater.inflate(R.layout.card, parent, false);

        Item item = new Item(row,mcardclicklistener);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcardclicklistener.onClick(row,((TextView)row.findViewById(R.id.state)).getText().toString());
            }
        });

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Item)holder).state.setText(states.get(position));
        ((Item)holder).confirmed_cases.setText(cases.get(position));

    }

    @Override
    public int getItemCount() {
        return states.size();
    }


    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener{
       public TextView state;
        TextView confirmed_cases;

        cardClickListener cardclicklistener;
       public Item(@NonNull View itemView,cardClickListener cardclicklistener) {
            super(itemView);
            this.cardclicklistener=cardclicklistener;
            state=(TextView) itemView.findViewById(R.id.state);
            confirmed_cases=(TextView) itemView.findViewById(R.id.confirmed_cases);



        }

        @Override
        public void onClick(View view) {
            cardclicklistener.onClick(view, state.getText().toString());
        }
    }
}
