package com.example.cowid;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> states;
    ArrayList<String> cases;
    cardClickListener mcardclicklistener;
    btClickListener btclicklistener;

    public Adapter(Context context, ArrayList<String> states,ArrayList<String> cases,cardClickListener mcardclicklistener,btClickListener mbtClickListener){
        this.context=context;
        this.states=states;
        this.cases=cases;
        this.mcardclicklistener=mcardclicklistener;
        this.btclicklistener=mbtClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View row = inflater.inflate(R.layout.card, parent, false);

        Item item = new Item(row,mcardclicklistener,btclicklistener);
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
        ImageButton bt;
        btClickListener btclickListener;
        cardClickListener cardclicklistener;
       public Item(@NonNull View itemView, cardClickListener cardclicklistener, final btClickListener btclickListener) {
            super(itemView);
            this.btclickListener=btclickListener;
            this.cardclicklistener=cardclicklistener;
            state=(TextView) itemView.findViewById(R.id.state);
            confirmed_cases=(TextView) itemView.findViewById(R.id.confirmed_cases);
            bt=itemView.findViewById(R.id.btp);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btclickListener.onBTClick(view,state.getText().toString());

                }
            });






        }

        @Override
        public void onClick(View view) {
            cardclicklistener.onClick(view, state.getText().toString());
        }
    }
}
