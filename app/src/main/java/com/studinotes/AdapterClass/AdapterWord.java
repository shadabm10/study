package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static com.studinotes.Fragments.TipsnTricks.TAG;

public class AdapterWord extends RecyclerView.Adapter<AdapterWord.PlanetViewHolder> {

        ArrayList<HashMap<String,String>> videoList;    // Context context;
 Context context;
// int [] imageId,imageId1,imageId2,imageId3,imageId4;

public AdapterWord(ArrayList<HashMap<String,String>>videoList, Context context) {
        this.videoList = videoList;
        this.context=context;
        }

@Override
public AdapterWord.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_video,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(AdapterWord.PlanetViewHolder holder, int position) {

        View rowView;

        String file_type = videoList.get(position).get("file_type_inner");

    Log.d(TAG, "file_type = " +file_type);

       // holder.image.setImageResource(R.mipmap.document);
    if(videoList.get(position).get("file_type_inner").equals("image"))
    {
        Picasso.with(context).load(videoList.get(position).get("file_url")).into(holder.image);
    }
    else if(videoList.get(position).get("file_type_inner").equals("video")){
        holder.image.setImageResource(R.mipmap.video);
    }
    else if(videoList.get(position).get("file_type_inner").equals("pdf")){
        holder.image.setImageResource(R.mipmap.document);
    }
    else if(videoList.get(position).get("file_type_inner").equals("MSWord")){
        holder.image.setImageResource(R.mipmap.document);
    }
    else if(videoList.get(position).get("file_type_inner").equals("Power Point")){
        holder.image.setImageResource(R.mipmap.document);
    }
    holder.text.setText(videoList.get(position).get("file_name"));
        /*holder.text.setText(videoList.get(position).toString());
        if(videoList.get(position).get("file_type_inner").equals("video")){
            holder.image.setImageResource(R.mipmap.video);
        }*/

        }

@Override
public int getItemCount() {
        return videoList.size();
        }

public static class PlanetViewHolder extends RecyclerView.ViewHolder{

    protected ImageView image;
    protected TextView text;

    public PlanetViewHolder(View itemView) {

        super(itemView);
        image= (ImageView) itemView.findViewById(R.id.item_icon2);
        text= (TextView) itemView.findViewById(R.id.item_title2);


    }

}


}
