package com.example.studinotes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Folderadapter1 extends RecyclerView.Adapter<Folderadapter1.PlanetViewHolder> {

    ArrayList<String> planetList;
    // Context context;
    // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public Folderadapter1(ArrayList<String> planetList, Context context) {
        this.planetList = planetList;
    }
    @Override
    public Folderadapter1.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.foldergrid,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Folderadapter1.PlanetViewHolder holder, int position) {

        View rowView;

        holder.image.setImageResource(R.mipmap.foldermustard);
        holder.text.setText(planetList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;




        public PlanetViewHolder(View itemView) {

            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.item_icon);
            text= (TextView) itemView.findViewById(R.id.item_title);


        }

    }


}


