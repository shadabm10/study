package com.studinotes.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.studinotes.Activities.Login;
import com.studinotes.Activities.Setting;
import com.studinotes.AdapterClass.AdapterListFolder;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.FeedItem;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.DialogClass.StudyNotes_Dialog;
import com.studinotes.AdapterClass.StudyNotes_adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.studinotes.Fragments.TipsnTricks.TAG;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class StudyNotes extends Fragment  {

    StudyNotes_adapter adapter;
    AdapterListFolder adapterListFolder;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;
    GlobalClass globalClass;
    Shared_Preference prefrence;
    ArrayList<HashMap<String,String>> productDetaiils;
    ArrayList<HashMap<String,String>> newSeacrh;
    String colorCode;
    EditText search_value;
    FragmentManager fragmentManager;

    public ArrayList<FeedItem> feedlist;
    ProgressDialog pd;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.studynotes, container, false);

        sharedpreference = new Sharedpreference(getActivity());
        main_layout = rootView.findViewById(R.id.main_layout);
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        prefrence = new Shared_Preference(getActivity());
        prefrence.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pd.setMessage("Loading...");
        fragmentManager = getActivity().getSupportFragmentManager();

        final TextView text3 = (TextView) rootView.findViewById(R.id.text3);
        text3.setText(globalClass.getFname()+"  "+globalClass.getLname());
        final TextView text4 = rootView.findViewById(R.id.text4);
        text4.setText(globalClass.getSchool_name());
        search_value = rootView.findViewById(R.id.area);

        feedlist = new ArrayList<>();
        productDetaiils=new ArrayList<>();
        newSeacrh=new ArrayList<>();
        FolderList();




        recyclerView =rootView.findViewById(R.id.recycle);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new StudyNotes_adapter(feedlist,getActivity(),pd);
        recyclerView.setAdapter(adapter);


        final ImageButton search =  rootView.findViewById(R.id.search);
        final LinearLayout searchfield =  rootView.findViewById(R.id.searchfield);
        final ImageButton cross =  rootView.findViewById(R.id.cross);
        final ImageButton settings =  rootView.findViewById(R.id.settings);
        final ImageButton add =  rootView.findViewById(R.id.add);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.VISIBLE);
            }
        });
        search_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              //  FolderList();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){
                    if (search_value.getText().equals("")){
                        FolderList();
                    }
                    else {
                        ViewList_new(search_value.getText().toString());
                    }

                }else{
                    FancyToast.makeText(getActivity(), "Check Internet Connecton", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                FolderList();

            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.INVISIBLE);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                 final Dialog dialog = new Dialog(getActivity());
                 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.studynotes_dialog);
                final Button yes,no,mustard,navy,black,darkgrey,terracotta;

                 final Button mainbutton;
               final EditText name;
                final LinearLayout colors =  dialog.findViewById(R.id.colors);
                yes = dialog.findViewById(R.id.btn_yes);
              //  name =  dialog.findViewById(R.id.folder_name);
                name =  dialog.findViewById(R.id.folder_name);
                no = dialog.findViewById(R.id.btn_no);
                mustard = dialog.findViewById(R.id.mustardbutton);
                navy = dialog.findViewById(R.id.navybutton);
                black = dialog. findViewById(R.id.blackbutton);
                darkgrey = dialog. findViewById(R.id.darkgreybutton);
                terracotta = dialog. findViewById(R.id.terracottabutton);
                mainbutton =  dialog.findViewById(R.id.mainbutton);
                yes.setOnClickListener(this);
                no.setOnClickListener(this);
                mustard.setOnClickListener(this);
                navy.setOnClickListener(this);
                black.setOnClickListener(this);
                darkgrey.setOnClickListener(this);
                terracotta.setOnClickListener(this);
                mainbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        colors.setVisibility(View.VISIBLE);
                    }
                });
                mustard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mainbutton.setBackgroundResource(R.color.mustard);
                        colorCode ="#FAE876";
                       // Log.d(TAG, "colorCode: "+ colorCode);
                    }
                });
                navy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mainbutton.setBackgroundResource(R.color.navy);
                        colorCode ="#182458";
                       // Log.d(TAG, "colorCode: "+ colorCode);

                    }
                });
                black.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mainbutton.setBackgroundResource(R.color.black);
                        colorCode ="#000000";
                       // Log.d(TAG, "colorCode: "+ colorCode);

                    }
                });
                darkgrey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mainbutton.setBackgroundResource(R.color.darkgrey);
                        colorCode ="#3F3F3F";
                       // Log.d(TAG, "colorCode: "+ colorCode);
                    }
                });
                terracotta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mainbutton.setBackgroundResource(R.color.terracotta);
                        colorCode ="#C92700";
                       // Log.d(TAG, "colorCode: "+ colorCode);
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String NAME=name.getText().toString();

                        if(NAME.equals(""))
                        {

                            Toast.makeText(getActivity(),"please insert Name",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Log.d(TAG, "Color Code: " + colorCode);
                            if (colorCode == null) {
                                colorCode = "#FAE876";
                            } else {

                                //   ArrayList feed = new ArrayList<FeedItem>();
                                FeedItem Feed = new FeedItem(NAME, colorCode);
                                adapter = new StudyNotes_adapter(feedlist, getActivity(), pd);
                                recyclerView.setAdapter(adapter);
                                feedlist.add(Feed);
                                dialog.dismiss();
                                if (feedlist.size() > 0) {
                                    FeedItem feedlast = feedlist.get(feedlist.size() - 1);
                                    Log.d(TAG, "onClick: " + feedlast.getColorCode());
                                }
                            }
                        }

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }



        });

        final ImageButton setting =  rootView.findViewById(R.id.settings);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        //Returning the layout file after inflating
        //Change R.layout.studynotes in you classes
        return rootView;

    }

    private void ViewList_new(final String search_text) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"searchFolderAndFiles", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                newSeacrh.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("folderData");
                        JsonArray filedata=jobj.getAsJsonArray("fileData");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String parent_id = images1.get("parent_id").toString().replaceAll("\"", "");
                            String folder_name = images1.get("folder_name").toString().replaceAll("\"", "");
                            String color_code = images1.get("color_code").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("parent_id", parent_id);
                            hashMap.put("folder_name", folder_name);
                            hashMap.put("color_code", color_code);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);
                         //   globalClass.setFolderid(id);
                          //  globalClass.setFolderanme(folder_name);

                            newSeacrh.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapterListFolder = new AdapterListFolder(getActivity(),newSeacrh,fragmentManager);
                        recyclerView.setAdapter(adapterListFolder);
                        adapterListFolder.notifyDataSetChanged();


                    } else
                        FancyToast.makeText(getActivity(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid", globalClass.getId());
                params.put("folder_id", "0");
                params.put("search_name", search_text);

                Log.d(TAG, "getParams: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void FolderList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"folderList", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                productDetaiils.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("data");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String parent_id = images1.get("parent_id").toString().replaceAll("\"", "");
                            String folder_name = images1.get("folder_name").toString().replaceAll("\"", "");
                            String color_code = images1.get("color_code").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("parent_id", parent_id);
                            hashMap.put("folder_name", folder_name);
                            hashMap.put("color_code", color_code);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);
                            globalClass.setFolderid(id);
                            globalClass.setFolderanme(folder_name);

                            productDetaiils.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapterListFolder = new AdapterListFolder(getActivity(),productDetaiils,fragmentManager);
                        recyclerView.setAdapter(adapterListFolder);
                        adapterListFolder.notifyDataSetChanged();


                    } else
                        FancyToast.makeText(getActivity(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).cancel();



                } catch (Exception e) {
                    FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                FancyToast.makeText(getActivity(), "DATA NOT FOUND", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid", globalClass.getId());
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
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout, sharedpreference.getStyle());

        super.onResume();
    }


}
