package com.studinotes.AdapterClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
import com.studinotes.Activities.ActivityNotepad;
import com.studinotes.Activities.ViewFile;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Fragments.StudyNotesFoldersOnClick;
import com.studinotes.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.studinotes.Fragments.TipsnTricks.TAG;

public class AdapterWord extends RecyclerView.Adapter<AdapterWord.PlanetViewHolder> {

        ArrayList<HashMap<String,String>> videoList;    // Context context;
 Context context;
 ProgressDialog pd;
    String file_type,file_name,type,file_url;
    StudyNotesFoldersOnClick studyNotesFoldersOnClick;
 String id,parent_id;
 FragmentManager fragmentManager;
// int [] imageId,imageId1,imageId2,imageId3,imageId4;

public AdapterWord(ArrayList<HashMap<String,String>>videoList, Context context, ProgressDialog pd,FragmentManager fragmentManager,StudyNotesFoldersOnClick studyNotesFoldersOnClick,String parent_id) {
        this.videoList = videoList;
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.studyNotesFoldersOnClick=studyNotesFoldersOnClick;
        this.pd=pd;
        this.parent_id=parent_id;
        }

@Override
public AdapterWord.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_video,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(AdapterWord.PlanetViewHolder holder, final int position) {

        View rowView;

        file_type = videoList.get(position).get("file_type_inner");
        file_url = videoList.get(position).get("file_url");
        type = videoList.get(position).get("type");

          id=videoList.get(position).get("id");
          file_name=videoList.get(position).get("file_name");
       // String  folder_name=videoList.get(position).get("folder_name");

    Log.d(TAG, "file_type = " +file_type);
    Log.d(TAG, "file_id = " +id);
    Log.d(TAG, "file_name = " +file_name);

       // holder.image.setImageResource(R.mipmap.document);
    if(videoList.get(position).get("file_type_inner").equals("IMAGE"))
    {
        Picasso.with(context).load(videoList.get(position).get("file_url")).into(holder.image);

    }
    else if(videoList.get(position).get("file_type_inner").equals("VIDEO")){
        holder.image.setImageResource(R.mipmap.video);
    }
    else if(videoList.get(position).get("file_type_inner").equals("PDF")){
        holder.image.setImageResource(R.mipmap.document);
    }
    else if(videoList.get(position).get("file_type_inner").equals("Microsoft Word")){
        holder.image.setImageResource(R.mipmap.document);
    }
    else if(videoList.get(position).get("file_type_inner").equals("Microsoft Powerpoint")){
        holder.image.setImageResource(R.mipmap.document);
    }
    else if(videoList.get(position).get("file_type_inner").equals("AUDIO")){
        holder.image.setImageResource(R.mipmap.audio);
    }
    else if(videoList.get(position).get("file_type_inner").equals("Microsoft Excel")){
        holder.image.setImageResource(R.mipmap.document);
    }else if(videoList.get(position).get("file_type_inner").equals("NotePad")){
        holder.image.setImageResource(R.mipmap.icn_notes);
    }

    holder.text.setText(videoList.get(position).get("file_name"));
        /*holder.text.setText(videoList.get(position).toString());
        if(videoList.get(position).get("file_type_inner").equals("video")){
            holder.image.setImageResource(R.mipmap.video);
        }*/

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if(videoList.get(position).get("file_type_inner").equals("NotePad")){

              Intent intent = new Intent(context, ActivityNotepad.class);
              intent.putExtra("file_url", videoList.get(position).get("file_url"));
              intent.putExtra("from", "edit");
              intent.putExtra("file_id", id);
              intent.putExtra("parent_id", parent_id);
              // startActivity(intent);
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              context.startActivity(intent);
          }
          else {
              Intent intent = new Intent(context, ViewFile.class);
              intent.putExtra("file_url", videoList.get(position).get("file_url"));
              intent.putExtra("file_type_inner", videoList.get(position).get("file_type_inner"));
              // startActivity(intent);
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

              context.startActivity(intent);
          }

        }
    });


}

@Override
public int getItemCount() {
        return videoList.size();
        }

public  class PlanetViewHolder extends RecyclerView.ViewHolder{

    protected ImageView image;
    protected TextView text;

    public PlanetViewHolder(View itemView) {

        super(itemView);
        image= (ImageView) itemView.findViewById(R.id.item_icon2);
        text= (TextView) itemView.findViewById(R.id.item_title2);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //  Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete this folder? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                DeleteFolder(videoList.get(getAdapterPosition()).get("id"));
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return false;
            }

        });



    }
    private void DeleteFolder(String id) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"deleteFolderOrFile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if (status.equals("1")) {

                        // studyNotesFoldersOnClick.FolderList();


                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        studyNotesFoldersOnClick.FolderList();


                        pd.dismiss();
                    }
                    else {

                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(context,"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(context,
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("type", type);

                Log.d(TAG, "getParams: "+params);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}


}
