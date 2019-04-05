package com.example.studinotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class Tab3_adapter extends RecyclerView.Adapter<Tab3_adapter.PlanetViewHolder> {

    ArrayList<String> mData;
    ArrayList<String> mData1;
    ArrayList<String> mData2;
    ArrayList<String> mData3;
    ArrayList<String> mData4;


    // data is passed into the constructor
    public Tab3_adapter(ArrayList<String> data, ArrayList<String> data1, ArrayList<String> data2,ArrayList<String> data3,ArrayList<String> data4, Context context) {
        this.mData = data;
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
        this.mData4 = data4;

    }

    // inflates the row layout from xml when needed
    @Override
    public Tab3_adapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab3_single, parent, false);
        Tab3_adapter.PlanetViewHolder PlanetViewHolder = new Tab3_adapter.PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        holder.text.setText(mData.get(position).toString());
        holder.text1.setText(mData1.get(position).toString());
        holder.text2.setText(mData2.get(position).toString());
        holder.text3.setText(mData3.get(position).toString());
        holder.text4.setText(mData4.get(position).toString());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2,text3,text4,text5;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.day);
            text1= (TextView) itemView.findViewById(R.id.subject);
            text2= (TextView) itemView.findViewById(R.id.str);
            text3= (TextView) itemView.findViewById(R.id.end1);
            text4= (TextView) itemView.findViewById(R.id.bell);
        }
    }

}
