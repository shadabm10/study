package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class StudyNotesFoldersOnClick_Video extends RecyclerView.Adapter<StudyNotesFoldersOnClick_Video.PlanetViewHolder> {

    ArrayList<HashMap<String,String>> videoList;    // Context context;
    // Context context;
    // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public StudyNotesFoldersOnClick_Video(ArrayList<HashMap<String,String>>videoList, Context context) {
        this.videoList = videoList;
    }
    @Override
    public StudyNotesFoldersOnClick_Video.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_video,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudyNotesFoldersOnClick_Video.PlanetViewHolder holder, int position) {

        View rowView;

       holder.image.setImageResource(R.mipmap.video);
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



