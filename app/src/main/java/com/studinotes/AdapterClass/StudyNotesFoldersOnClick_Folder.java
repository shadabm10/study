package com.studinotes.AdapterClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.studinotes.Constant.AppConfig;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Fragments.StudyNotesFoldersOnClick;
import com.studinotes.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudyNotesFoldersOnClick_Folder extends RecyclerView.Adapter<StudyNotesFoldersOnClick_Folder.PlanetViewHolder> {
      String  TAG="StudyNotesFoldersOnClick_Folder";
      Context context;
      ProgressDialog pd;
      String id,type,folder_name;
    ArrayList<HashMap<String,String>> folderList;
    FragmentManager fragmentManager;
    StudyNotesFoldersOnClick fragment;

    public StudyNotesFoldersOnClick_Folder(ArrayList<HashMap<String,String>>folderList,
                                           Context context, FragmentManager fragmentManager,ProgressDialog pd,StudyNotesFoldersOnClick studyNotesFoldersOnClick) {
        this.folderList = folderList;
        this.context=context;
        this.fragment=studyNotesFoldersOnClick;
        this.pd=pd;
        this.fragmentManager=fragmentManager;
    }
    @Override
    public StudyNotesFoldersOnClick_Folder.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_adapter,parent,false);
        StudyNotesFoldersOnClick_Folder.PlanetViewHolder viewHolder=new StudyNotesFoldersOnClick_Folder.PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudyNotesFoldersOnClick_Folder.PlanetViewHolder holder, final int position) {

        View rowView;
          id=folderList.get(position).get("id");
          type=folderList.get(position).get("type");
          folder_name=folderList.get(position).get("folder_name");

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
        else if(colorCode.equals("#0082f2")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.blue_change));

        }

        else if(colorCode.equals("#008100")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.deep_green));

        }
        else if(colorCode.equals("#00f600")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.light_green));

        }
        else if(colorCode.equals("#ff7c00")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.orange));

        }
        else if(colorCode.equals("#ff00e6")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.purple));

        }
        else if(colorCode.equals("#ff0000")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.red));

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

    public  class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;




        public PlanetViewHolder(View itemView) {

            super(itemView);
            image= itemView.findViewById(R.id.item_icon);
            text= itemView.findViewById(R.id.item_title);
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
                                    DeleteFolder(folderList.get(getAdapterPosition()).get("id"));
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
                folderList.clear();
                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if (status.equals("1")) {


                             fragment.FolderList();

                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        pd.dismiss();
                    }
                    else {

                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();


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
                params.put("type",type);

                Log.d(TAG, "getParams: "+params);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }



}


