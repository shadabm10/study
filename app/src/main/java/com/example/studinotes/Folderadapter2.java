package com.example.studinotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Folderadapter2 extends RecyclerView.Adapter<Folderadapter2.PlanetViewHolder> {

    ArrayList<String> planetList;
    // Context context;
    // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public Folderadapter2(ArrayList<String> planetList, Context context) {
        this.planetList = planetList;
    }
    @Override
    public Folderadapter2.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.folderdoc,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Folderadapter2.PlanetViewHolder holder, int position) {

        View rowView;

        holder.image.setImageResource(R.mipmap.document);
        holder.text.setText(planetList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text,text1;




        public PlanetViewHolder(View itemView) {

            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.item_icon1);
            text= (TextView) itemView.findViewById(R.id.item_title1);
            text1= (TextView) itemView.findViewById(R.id.item_date1);


        }

    }


}



