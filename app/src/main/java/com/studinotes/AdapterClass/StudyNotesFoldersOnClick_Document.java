package com.studinotes.AdapterClass;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

public class StudyNotesFoldersOnClick_Document extends RecyclerView.Adapter<StudyNotesFoldersOnClick_Document.PlanetViewHolder> {
    String  TAG="StudyNotesFoldersOnClick_Document";
    Context context;
    ArrayList<HashMap<String,String>> documentList;    // Context context;
    // Context context;
    // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public StudyNotesFoldersOnClick_Document(ArrayList<HashMap<String,String>>documentList, Context context) {
        this.documentList = documentList;
        this.context=context;
    }
    @Override
    public StudyNotesFoldersOnClick_Document.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_viewer_subfolder,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudyNotesFoldersOnClick_Document.PlanetViewHolder holder, int position) {

        View rowView;
        Picasso.with(context).load(documentList.get(position).get("file_url")).
               into(holder.image);
     //  holder.image.setImageResource(documentList.get(position).get("file_url"));
        //holder.text.setText(documentList.get(position).toString());
        holder.text.setText(documentList.get(position).get("file_name"));




       /* String colorCode=documentList.get(position).get("color_code");
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
        }*/


    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text,text1;




        public PlanetViewHolder(View itemView) {

            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.img_subfolder);
            text= (TextView) itemView.findViewById(R.id.tv_image_name);
          //  text1= (TextView) itemView.findViewById(R.id.item_date1);


        }

    }


}



