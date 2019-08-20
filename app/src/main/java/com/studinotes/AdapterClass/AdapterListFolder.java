package com.studinotes.AdapterClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Fragments.StudyNotesFoldersOnClick;
import com.studinotes.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterListFolder extends RecyclerView.Adapter<AdapterListFolder.MyViewHolder> {
    String TAG="AdapterList";
    GlobalClass globalClass;
    Context context;
    ArrayList<HashMap<String,String>> list_claim;
    ArrayList<HashMap<String,String>> image;
    ProgressDialog progressDialog;
    String type;
    String id,folder_name,folder_name1;
    LayoutInflater inflater;
    FragmentManager fragmentManager;


    public AdapterListFolder(Context c, ArrayList<HashMap<String,String>>list_claim, FragmentManager manager_ , ProgressDialog progressDialog
                             ) {
        this.context = c;
        this.list_claim = list_claim;
         this.progressDialog=progressDialog;
        this.fragmentManager=manager_;


        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    @Override
    public AdapterListFolder.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.studynotesfoldersonclick_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters

// pass the view to View Holder
        AdapterListFolder.MyViewHolder vh = new AdapterListFolder.MyViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(AdapterListFolder.MyViewHolder holder, final int position) {

        Log.d(TAG, "folder and file type: "+list_claim.get(position).get("type"));
       type=list_claim.get(position).get("type");
       id=list_claim.get(position).get("id");
       folder_name1=list_claim.get(position).get("folder_name");
       folder_name1=list_claim.get(position).get("folder_name");

       folder_name=list_claim.get(position).get("type");
        holder.text.setText(list_claim.get(position).get("folder_name"));

        String colorCode=list_claim.get(position).get("color_code");
        Log.d(TAG, "colorCode: "+colorCode);
        if(colorCode.equals("#FAE876")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.mustard));

        }
        else if(colorCode.equals("#182458")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.navy));
        }
        else if(colorCode.equals("#000000")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.black));
        }
        else if(colorCode.equals("#3F3F3F")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.darkgrey));
        }
        else if(colorCode.equals("#C92700")){
            holder.image.setColorFilter(ContextCompat.getColor(context,
                    R.color.terracotta));

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

                String id =  list_claim.get(position).get("id");
                String folder_name=list_claim.get(position).get("folder_name");
                Bundle bundle =new Bundle();
                bundle.putString("id",id);
                bundle.putString("folder_name",folder_name);
                StudyNotesFoldersOnClick fragment = new StudyNotesFoldersOnClick();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

              /*  Intent intent = new Intent(context, StudyNotesFoldersOnClick_tabs.class);
                intent.putExtra("id",list_claim.get(position).get("id"));
                intent.putExtra("folder_name",list_claim.get(position).get("folder_name"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_claim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        protected ImageView image;
        protected TextView text;
        protected  String text_str,color;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            image= itemView.findViewById(R.id.item_icon);
            text= (TextView) itemView.findViewById(R.id.item_title);
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
                                    DeleteFolder(list_claim.get(getAdapterPosition()).get("id"));
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

        progressDialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"deleteFolderOrFile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                progressDialog.dismiss();

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


                        Bundle bundle =new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("folder_name",folder_name);
                        StudyNotes fragment = new StudyNotes();
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contentContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        progressDialog.dismiss();
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
                progressDialog.dismiss();
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
