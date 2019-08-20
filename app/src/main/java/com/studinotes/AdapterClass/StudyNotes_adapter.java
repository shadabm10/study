package com.studinotes.AdapterClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Activities.HomePage;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.FeedItem;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudyNotes_adapter extends RecyclerView.Adapter<StudyNotes_adapter.PlanetViewHolder> {

    private ArrayList<FeedItem> feedItemList;
    static Context context;
    String TAG="StudyNotes_adapter";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;

    // Context context;
   // int [] imageId,imageId1,imageId2,imageId3,imageId4;

    public StudyNotes_adapter(ArrayList<FeedItem>feedItemList, Context context, ProgressDialog pd) {
        this.feedItemList = feedItemList;
        this.context=context;
        this.pd=pd;
        globalClass = ((GlobalClass) context.getApplicationContext());
        preference = new Shared_Preference(context);
    }

    /*StudyNotes_adapter(Context context,View v){
        this.context=context;
    }*/

    @Override
    public StudyNotes_adapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_adapter,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }


    /*public MyViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        super(itemView);
        title=(TextView)itemView.findViewById(R.id.listText);
        icon=(ImageView)itemView.findViewById(R.id.listIcon);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

    }*/

    @Override
    public void onBindViewHolder(StudyNotes_adapter.PlanetViewHolder holder, final int position) {
        //holder.image.setColorFilter(R.color.terracotta);
        View rowView;
        //Context context = null;

        holder.image.setImageResource(R.mipmap.folderdarkgery);

       String name=feedItemList.get(position).getNAME();

        holder.text.setText(feedItemList.get(position).getNAME());

        String colorCode=feedItemList.get(position).getColorCode();
        Log.d(TAG, "colorCode: "+colorCode);
        if(colorCode.equals("#FAE876")){
          //  holder.image.setImageResource(R.mipmap.foldermustard);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.mustard));
            AddFolder( name,colorCode);

        }
        else if(colorCode.equals("#182458")){
           // holder.image.setImageResource(R.mipmap.foldernavy);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.navy));
            AddFolder( name,colorCode);
        }
        else if(colorCode.equals("#000000")){
            //holder.image.setImageResource(R.mipmap.folderblack);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.black));
            AddFolder( name,colorCode);
        }
        else if(colorCode.equals("#3F3F3F")){
           // holder.image.setImageResource(R.mipmap.folderdarkgery);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.darkgrey));
            AddFolder( name,colorCode);
        }
       else if(colorCode.equals("#C92700")){
           // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.terracotta));
            AddFolder( name,colorCode);
        }

       else if(colorCode.equals("#0082f2")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.blue_change));
            AddFolder( name,colorCode);
        }
       else if(colorCode.equals("#008100")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.deep_green));
            AddFolder( name,colorCode);
        }
       else if(colorCode.equals("#00f600")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.light_green));
            AddFolder( name,colorCode);
        }
       else if(colorCode.equals("#ff7c00")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.orange));
            AddFolder( name,colorCode);
        }
       else if(colorCode.equals("#ff00e6")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.purple));
            AddFolder( name,colorCode);
        }
       else if(colorCode.equals("#ff0000")){
            // holder.image.setImageResource(R.mipmap.folderterracotta);
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.red));
            AddFolder( name,colorCode);
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StudyNotesFoldersOnClick_tabs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });



    }
    private void AddFolder(final String name,final String colorcode) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"addFolder", new Response.Listener<String>() {


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
                        JsonArray product=jobj.getAsJsonArray("data");

                        for (int i = 0; i < product.size(); i++) {




                        }
                      //  FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(context, HomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);

                        pd.dismiss();
                    }
                    else {

                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(context,"Issue in adding folder", Toast.LENGTH_LONG).show();
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

                params.put("userid", globalClass.getId());
                params.put("folder_name", name);
                params.put("color_code", colorcode);
                params.put("parent_id", "0");
                Log.d(TAG, "getParams: "+params);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    @Override
    public int getItemCount() {
        return feedItemList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected ImageView image;
        protected TextView text;
        protected  String text_str,color;




        public PlanetViewHolder(View itemView) {

            super(itemView);
            image= itemView.findViewById(R.id.item_icon);
            text= itemView.findViewById(R.id.item_title);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
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



}

