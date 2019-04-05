package com.example.studinotes;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class Tab2_adapter extends RecyclerView.Adapter<Tab2_adapter.PlanetViewHolder> {

    ArrayList<String> mData;
    ArrayList<String> mData1;
    ArrayList<String> mData2;

    // data is passed into the constructor
    public Tab2_adapter(ArrayList<String> data, ArrayList<String> data1, ArrayList<String> data2, Context context) {
        this.mData = data;
        this.mData1 = data1;
        this.mData2 = data2;
    }

    // inflates the row layout from xml when needed
    @Override
    public Tab2_adapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab2_single, parent, false);
        Tab2_adapter.PlanetViewHolder PlanetViewHolder = new Tab2_adapter.PlanetViewHolder(v);

        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {



        holder.text.setText(mData.get(position).toString());
        holder.text1.setText(mData1.get(position).toString());
        holder.text2.setText(mData2.get(position).toString());



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
        return mData.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2;
        ImageView edit1;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.user);
            text1= (TextView) itemView.findViewById(R.id.date);
            text2= (TextView) itemView.findViewById(R.id.time);
            edit1 = itemView.findViewById(R.id.edit1);


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