package com.example.studinotes;

/*import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class Tab1_adapter extends RecyclerView.Adapter<Tab1_adapter.PlanetViewHolder> {

    ArrayList<String> planetList;
    Context context;
    // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public Tab1_adapter(ArrayList<String> planetList, Context context) {
        this.planetList = planetList;
    }

    *//*Tab1_adapter(Context context,View v){
        this.context=context;
    }*//*

    @Override
    public Tab1_adapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.foldergrid,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Tab1_adapter.PlanetViewHolder holder, int position) {
        //holder.image.setColorFilter(R.color.terracotta);
        View rowView;

        holder.image.setImageResource(R.mipmap.folderblue);
       *//* holder.image1.setImageResource(R.mipmap.foldernavy);
        holder.image2.setImageResource(R.mipmap.folderblack);
        holder.image3.setImageResource(R.mipmap.folderdarkgery);
        holder.image4.setImageResource(R.mipmap.folderterracotta);
        holder.image.setImageResource(imageId[position]);
        holder.image1.setImageResource(imageId1[position]);
        holder.image2.setImageResource(imageId2[position]);
        holder.image3.setImageResource(imageId3[position]);
        holder.image4.setImageResource(imageId4[position]);*//*
        holder.text.setText(planetList.get(position).toString());

       *//* if(position == 0){
            holder.image.setImageResource(R.mipmap.foldermustard);
        }
        if(position == 1){
            holder.image1.setImageResource(R.mipmap.foldernavy);
        }
        if(position ==2){
            holder.image2.setImageResource(R.mipmap.folderblack);
        }
        if(position ==3){
            holder.image3.setImageResource(R.mipmap.folderdarkgery);
        }
        if(position ==4){
            holder.image4.setImageResource(R.mipmap.folderterracotta);
        }*//*
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

            *//*itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent myIntent = null;
                    if(position == 0){
                        myIntent = new Intent(v.getContext(), Setting.class);
                    }
                    if(position == 1){
                        myIntent = new Intent(v.getContext(), Setting.class);
                    }
                    if(position ==2){
                        myIntent = new Intent(v.getContext(), Setting.class);
                    }
                    context.startActivity(myIntent);

                }
            });*//*

        }

      *//*  public PlanetViewHolder(View v) {
            super(v);
        }*//*
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // PlanetViewHolder holder=new PlanetViewHolder();
        View rowView;

        rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foldergrid,parent,false);

        //holder.image.setImageResource(R.mipmap.folderblue);
        *//*holder.text=(TextView) rowView.findViewById(R.id.item_title);
      //  holder.tv=(TextView) rowView.findViewById(R.id.textView2);
        holder.image=(ImageView) rowView.findViewById(R.id.item_icon);
       // holder.img1=(ImageView) rowView.findViewById(R.id.SingleView1);

        holder.text.setText(planetList[position]);
        //holder.tv.setText(result1[position]);
        holder.image.setImageResource(imageId[position]);*//*
        // holder.img1.setImageResource(imageId1[position]);

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = null;
                if(position == 0){
                    myIntent = new Intent(v.getContext(), Setting.class);
                }
                if(position == 1){
                    myIntent = new Intent(v.getContext(), Setting.class);
                }
                if(position ==2){
                    myIntent = new Intent(v.getContext(), Setting.class);
                }
                context.startActivity(myIntent);

            }
        });

        return rowView;
    }


}*/
