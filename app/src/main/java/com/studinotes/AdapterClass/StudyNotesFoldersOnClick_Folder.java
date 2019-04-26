package com.studinotes.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studinotes.Fragments.StudyNotesFoldersOnClick;

import java.util.ArrayList;
import java.util.HashMap;

public class StudyNotesFoldersOnClick_Folder extends RecyclerView.Adapter<StudyNotesFoldersOnClick_Folder.PlanetViewHolder> {
      String  TAG="StudyNotesFoldersOnClick_Folder";
      Context context;
    ArrayList<HashMap<String,String>> folderList;
    FragmentManager fragmentManager;

    public StudyNotesFoldersOnClick_Folder(ArrayList<HashMap<String,String>>folderList,
                                           Context context, FragmentManager fragmentManager) {
        this.folderList = folderList;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }
    @Override
    public StudyNotesFoldersOnClick_Folder.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_adapter,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudyNotesFoldersOnClick_Folder.PlanetViewHolder holder, final int position) {

        View rowView;

      //  holder.image.setImageResource(R.mipmap.foldermustard);
      //  holder.text.setText(folderList.get(position).get(""));
        holder.text.setText(folderList.get(position).get("folder_name"));

        String colorCode=folderList.get(position).get("color_code");
        Log.d(TAG, "colorCode: "+colorCode);
        if(colorCode.equals("#FAE876")){
            //  holder.image.setImageResource(R.mipmap.foldermustard);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.mustard));
            // AddFolder( name,colorCode);

        }
        else if(colorCode.equals("#182458")){
            // holder.image.setImageResource(R.mipmap.foldernavy);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.navy));
            // AddFolder( name,colorCode);
        }
        else if(colorCode.equals("#000000")){
            //holder.image.setImageResource(R.mipmap.folderblack);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.black));
            // AddFolder( name,colorCode);
        }
        else if(colorCode.equals("#3F3F3F")){
            // holder.image.setImageResource(R.mipmap.folderdarkgery);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.darkgrey));
            //  AddFolder( name,colorCode);
        }
        else if(colorCode.equals("#C92700")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.terracotta));
            // AddFolder( name,colorCode);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id =  folderList.get(position).get("id");
                String folder_name =  folderList.get(position).get("folder_name");
                Bundle bundle =new Bundle();
                bundle.putString("id",id);
                bundle.putString("folder_name",folder_name);
                StudyNotesFoldersOnClick fragment = new StudyNotesFoldersOnClick();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;




        public PlanetViewHolder(View itemView) {

            super(itemView);
            image= itemView.findViewById(R.id.item_icon);
            text= itemView.findViewById(R.id.item_title);


        }

    }


}


