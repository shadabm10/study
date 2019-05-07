package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeManagement_adapter1 extends RecyclerView.Adapter<TimeManagement_adapter1.PlanetViewHolder> {
    String TAG="Lsir";

    Context context;
    ArrayList<HashMap<String,String>> tipsntricks;
    ArrayList<HashMap<String,String>> planList;
    ArrayList<HashMap<String,String>> planSubList;
    private RecyclerViewHorizontalListAdapter groceryAdapter;

    // data is passed into the constructor
    public TimeManagement_adapter1( Context context,ArrayList<HashMap<String, String>> planList,ArrayList<HashMap<String, String>> planSubList) {
        this.context=context;
        this.planList=planList;
        this.planSubList=planSubList;
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
        holder.text.setText(planList.get(position).get("day"));
        holder.date.setText(planList.get(position).get("date"));
        String subarray=planSubList.get(position).get("subject_name");
        Log.d(TAG, "subarray: "+subarray);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return planList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2,date;
        protected RecyclerView groceryRecyclerView;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.day);
           groceryRecyclerView=itemView.findViewById(R.id.rcv);
            date= (TextView) itemView.findViewById(R.id.date);


        }
    }

}
