package com.example.studinotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Tab2_adapter1 extends RecyclerView.Adapter<Tab2_adapter1.PlanetViewHolder> {

    ArrayList<String> mData3;
    ArrayList<String> mData4;
    ArrayList<String> mData5;

    // data is passed into the constructor
    public Tab2_adapter1(ArrayList<String> data3, ArrayList<String> data4, ArrayList<String> data5, Context context) {
        this.mData3 = data3;
        this.mData4 = data4;
        this.mData5 = data5;
    }

    // inflates the row layout from xml when needed
    @Override
    public Tab2_adapter1.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab2_single1, parent, false);
        Tab2_adapter1.PlanetViewHolder PlanetViewHolder = new Tab2_adapter1.PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        holder.text3.setText(mData3.get(position).toString());
        holder.text4.setText(mData4.get(position).toString());
        holder.text5.setText(mData5.get(position).toString());





        /*String animal = mData.get(position);
        holder.myTextView.setText(animal);

        String animal1 = mData1.get(position);
        holder.myTextView1.setText(animal1);

        String animal2 = mData2.get(position);
        holder.myTextView2.setText(animal2);*/
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData3.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text3,text4,text5;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text3= (TextView) itemView.findViewById(R.id.user1);
            text4= (TextView) itemView.findViewById(R.id.date1);
            text5= (TextView) itemView.findViewById(R.id.time1);
        }
    }


    // stores and recycles views as they are scrolled off screen
   /* public class PlanetViewHolder extends RecyclerView.PlanetViewHolder implements View.OnClickListener {

        TextView myTextView, myTextView1, myTextView2;

        PlanetViewHolder(View itemView) {
            super(itemView);

            myTextView = itemView.findViewById(R.id.user);
            itemView.setOnClickListener(this);
            myTextView1 = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
            myTextView2 = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }
    }*/
}
