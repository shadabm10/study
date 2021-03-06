package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudyNotesFoldersOnClick_Audio extends RecyclerView.Adapter<StudyNotesFoldersOnClick_Audio.PlanetViewHolder> {

    ArrayList<String> planetList;
    // Context context;
    // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public StudyNotesFoldersOnClick_Audio(ArrayList<String> planetList, Context context) {
        this.planetList = planetList;
    }
    @Override
    public StudyNotesFoldersOnClick_Audio.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_audio,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudyNotesFoldersOnClick_Audio.PlanetViewHolder holder, int position) {

        View rowView;

        holder.image.setImageResource(R.mipmap.audio);
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
            image= (ImageView) itemView.findViewById(R.id.item_icon3);
            text= (TextView) itemView.findViewById(R.id.item_title3);


        }

    }


}
