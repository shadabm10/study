package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeManagement_adapter1 extends RecyclerView.Adapter<TimeManagement_adapter1.PlanetViewHolder> {

    ArrayList<String> data;
    ArrayList<String> data1;
    ArrayList<String> data2;

    // data is passed into the constructor
    public TimeManagement_adapter1(ArrayList<String> data, ArrayList<String> data1, ArrayList<String> data2, Context context) {
        this.data = data;
        this.data1 = data1;
        this.data2 = data2;
    }

    // inflates the row layout from xml when needed
    @Override
    public TimeManagement_adapter1.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timemanagement_adapter1, parent, false);
        TimeManagement_adapter1.PlanetViewHolder PlanetViewHolder = new TimeManagement_adapter1.PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        holder.text.setText(data.get(position).toString());
        holder.text1.setText(data1.get(position).toString());
        holder.text2.setText(data2.get(position).toString());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.day);
            text1= (TextView) itemView.findViewById(R.id.subject);
            text2= (TextView) itemView.findViewById(R.id.time2);

        }
    }

}
