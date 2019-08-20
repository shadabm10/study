package com.studinotes.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Activities.Setting;
import com.studinotes.AdapterClass.AdapterWord;
import com.studinotes.AdapterClass.Assessment_adapter1;
import com.studinotes.AdapterClass.Examination_adapter;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.RecyclerViewHorizontalListAdapter;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.AdapterClass.TimeManagement_adapter;
import com.studinotes.AdapterClass.TimeManagement_adapter1;
import com.studinotes.AdapterClass.TipsnTricks_adapter;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Shadab Mallick on 2/3/2016.
 */

public class TimeManagement extends Fragment {
     String TAG="Schedule List";
    TimeManagement_adapter adapter;
    TimeManagement_adapter1 adapter1;
    RecyclerViewHorizontalListAdapter horizontalListAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout main_layout3;
    Sharedpreference sharedpreference;
    ProgressDialog pd;
    GlobalClass globalClass;
    Shared_Preference preference;
    RecyclerView recyclerView,recyclerView1;
    ArrayList<HashMap<String,String>> scheduleList;
    ArrayList<HashMap<String,String>> searchList;

    ArrayList<HashMap<String,String>> planList;
    ArrayList<HashMap<String,String>> planSubList;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;
    String notify_before="";
    String notify_me="";
    String message,end_time,exam_time,exam_date,value_notify;
    Calendar myCalendar = Calendar.getInstance();
    JsonArray data;
    EditText search_sc;
    String randonYes="N";
    String Social="N";
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.timemanagement, container, false);
        preference = new Shared_Preference(getActivity().getBaseContext());
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading");
        sharedpreference = new Sharedpreference(getActivity());
        main_layout3 = rootView.findViewById(R.id.main_layout3);


        scheduleList=new ArrayList<>();
        planList=new ArrayList<>();
        planSubList=new ArrayList<>();
        searchList=new ArrayList<>();

       recyclerView  =  rootView.findViewById(R.id.recycle2);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);

         recyclerView1=  rootView.findViewById(R.id.recycle3);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView1.setLayoutManager(mLayoutManager);

          ScheduleList();
        search_sc=  rootView.findViewById(R.id.area);
        final ImageButton add2 =  rootView.findViewById(R.id.add2);



        final Button plan = rootView.findViewById(R.id.plan);
        final LinearLayout green1 =rootView.findViewById(R.id.green1);
        final LinearLayout green =  rootView.findViewById(R.id.ll1);
        final LinearLayout tool1 =  rootView.findViewById(R.id.tool1);
        final RecyclerView recycler3 = rootView.findViewById(R.id.recycle3);
        final RecyclerView recycler2 =  rootView.findViewById(R.id.recycle2);
        final ImageButton back =  rootView.findViewById(R.id.back);
        final ImageButton search = rootView.findViewById(R.id.search);
        final LinearLayout searchfield =  rootView.findViewById(R.id.searchfield);
        final ImageButton cross = rootView.findViewById(R.id.cross);



        search_sc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){
                    String string = charSequence.toString();
                    if(string.equals("")){
                        ScheduleList();
                    }
                    else {


                        ViewList_new(search_sc.getText().toString());
                    }


                }else{
                    FancyToast.makeText(getActivity(), "Check Internet Connecton", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //  FolderList();

            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               green1.setVisibility(View.VISIBLE);
                green.setVisibility(View.GONE);
               recycler3.setVisibility(View.VISIBLE);
                recycler2.setVisibility(View.GONE);
                plan.setVisibility(View.GONE);
                tool1.setVisibility(View.VISIBLE);

                ShowPlan();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.VISIBLE);

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.INVISIBLE);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                green1.setVisibility(View.INVISIBLE);
                green.setVisibility(View.VISIBLE);
                recycler3.setVisibility(View.INVISIBLE);
                recycler2.setVisibility(View.VISIBLE);
                plan.setVisibility(View.VISIBLE);
                tool1.setVisibility(View.INVISIBLE);
            }
        });

        final ImageButton setting = (ImageButton) rootView.findViewById(R.id.settings);
      //  final ImageButton setting1 = (ImageButton) rootView.findViewById(R.id.settings1);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialogExamCreateDate();

            }

        });

        return rootView;



    }

    public void ViewList_new(final String search_text) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();
        searchList.clear();
      //  scheduleList.clear();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"searchUserwiseSchedulelist", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();



                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    //  String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("data");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String subject_name = images1.get("subject_name").toString().replaceAll("\"", "");
                            String day = images1.get("day").toString().replaceAll("\"", "");
                            String start_time = images1.get("start_time").toString().replaceAll("\"", "");
                            String end_time = images1.get("end_time").toString().replaceAll("\"", "");
                            String notify_me = images1.get("notify_me").toString().replaceAll("\"", "");
                            String notify_before = images1.get("notify_before").toString().replaceAll("\"", "");
                            String random = images1.get("random").toString().replaceAll("\"", "");
                            String social = images1.get("social").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "").trim();
                            String fullname = images1.get("fullname").toString().replaceAll("\"", "");
                            String subject=fullname.substring(0,3);
                            Log.d(TAG, "subject: "+subject);
                            /* Convert Date to 12 Hours AM/PM*/
                            //  DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)

                            DateFormat df = new SimpleDateFormat("HH:mm");
                            DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
                            Date date = null;
                            Date date1 = null;
                            String output = null;
                            String output1 = null;
                            try{
                                //Conversion of input String to date
                                date= df.parse(start_time);
                                date1= df.parse(end_time);
                                //old date format to new date format
                                output = outputformat.format(date);
                                output1 = outputformat.format(date1);
                                System.out.println(output);
                                System.out.println(output1);
                            }catch(ParseException pe){
                                pe.printStackTrace();
                            }

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("subject_name", subject_name);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);
                            hashMap.put("day", day);
                            hashMap.put("start_time", output);
                            hashMap.put("end_time", output1);
                            hashMap.put("notify_me", notify_me);
                            hashMap.put("notify_before", notify_before);
                            hashMap.put("random", random);
                            hashMap.put("social", social);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("fullname", subject);

                            searchList.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapter = new TimeManagement_adapter(getContext(), searchList,pd,TimeManagement.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    } else
                        message = jobj.get("message").toString().replaceAll("\"", "");
                   //  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
          // Posting parameters to login url
                Map<String, String> params = new HashMap<>();


                params.put("userid",globalClass.getId());
                params.put("subject_name",search_text);

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void ShowPlan() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"userSchedulelistbyday", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                planList.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    //  String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product = jobj.getAsJsonArray("main");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String day = images1.get("day").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String subject = day.substring(0, 3);
                            data = images1.getAsJsonArray("data");
                            for (int j = 0; j < data.size(); j++) {
                                JsonObject  data_array = data.get(j).getAsJsonObject();
                                String id = data_array.get("id").toString().replaceAll("\"", "");
                                String user_id = data_array.get("user_id").toString().replaceAll("\"", "");
                                String subject_name = data_array.get("subject_name").toString().replaceAll("\"", "");
                                String day_new = data_array.get("day").toString().replaceAll("\"", "");
                                String start_time = data_array.get("start_time").toString().replaceAll("\"", "");
                                String end_time = data_array.get("end_time").toString().replaceAll("\"", "");
                                String notify_me = data_array.get("notify_me").toString().replaceAll("\"", "");
                                String notify_before = data_array.get("notify_before").toString().replaceAll("\"", "");
                                String random = data_array.get("random").toString().replaceAll("\"", "");
                                String social = data_array.get("social").toString().replaceAll("\"", "");
                                String entry_date = data_array.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = data_array.get("modified_date").toString().replaceAll("\"", "");
                                String delete_flag = data_array.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = data_array.get("is_active").toString().replaceAll("\"", "").trim();

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("user_id", user_id);
                                hashMap.put("subject_name", subject_name);
                                hashMap.put("day_new", day_new);
                                hashMap.put("start_time", start_time);
                                hashMap.put("end_time", end_time);
                                hashMap.put("notify_me", notify_me);
                                hashMap.put("notify_before", notify_before);
                                hashMap.put("random", random);
                                hashMap.put("social", social);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);

                            //    planSubList.add(hashMap);
                                Log.d(TAG, "subject_name_qwerty " + subject_name);

                            }



                            setMainView();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("day", subject);
                            hashMap.put("date", date);

                            planList.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapter1 = new TimeManagement_adapter1(getContext(), planList, data);
                        recyclerView1.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();


                    } else {
                        message = jobj.get("message").toString().replaceAll("\"", "");
                        //  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }


            } catch(
            Exception e)

            {
                e.printStackTrace();
                Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

            }

        }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
// Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                //   params.put("userid",globalClass.getId() );
                params.put("userid",globalClass.getId());

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    public void setMainView(){

        recyclerView1.removeAllViews();
        // fileDataList.clear();


        for (int i = 0; i < planSubList.size(); i++){

            recyclerView1.addView(getMainView(planSubList.get(i)));

        }



    }
    public View getMainView(HashMap<String, String> hashMap){


        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.new_list, null);

        // In order to get the view we have to use the new view with text_layout in it
        TextView textView = (TextView)view.findViewById(R.id.tv_name);
        textView.setText(hashMap.get("file_type"));
        RecyclerView dynamic_recycler = view.findViewById(R.id.recycler_main);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        dynamic_recycler.setLayoutManager(mLayoutManager);






        return view;
    }
    public ArrayList<HashMap<String, String>> getListFromJson(String json){

        Log.d(TAG, "json 1 = " + json);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(json);

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject subfile = jsonArray.getJSONObject(j);
                String id = subfile.getString("id").replaceAll("\"", "");
                String user_id = subfile.get("user_id").toString().replaceAll("\"", "");
                String folder_id = subfile.get("folder_id").toString().replaceAll("\"", "");
                String file_name = subfile.get("file_name").toString().replaceAll("\"", "");
                String delete_flag = subfile.get("delete_flag").toString().replaceAll("\"", "");
                String is_active = subfile.get("is_active").toString().replaceAll("\"", "");
                String entry_date = subfile.get("entry_date").toString().replaceAll("\"", "");
                String modified_date = subfile.get("modified_date").toString().replaceAll("\"", "");
                String file_type_inner = subfile.get("file_type").toString().replaceAll("\"", "");
                String file_url = subfile.get("file_url").toString().replaceAll("\"", "");


                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", id);
                hashMap.put("user_id", user_id);
                hashMap.put("folder_id", folder_id);
                hashMap.put("file_name", file_name);
                hashMap.put("file_type_inner", file_type_inner);
                hashMap.put("delete_flag", delete_flag);
                hashMap.put("is_active", is_active);
                hashMap.put("entry_date", entry_date);
                hashMap.put("modified_date", modified_date);
                hashMap.put("file_url", file_url);
                hashMap.put("type", "file");


                list.add(hashMap);
                Log.d(TAG, "getListFromJson: "+list);


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


    public void ScheduleList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();
        scheduleList.clear();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"userSchedulelist", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
              //  scheduleList.clear();


                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                  //  String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("data");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String subject_name = images1.get("subject_name").toString().replaceAll("\"", "");
                            String day = images1.get("day").toString().replaceAll("\"", "");
                            String start_time = images1.get("start_time").toString().replaceAll("\"", "");
                            String end_time = images1.get("end_time").toString().replaceAll("\"", "");
                            String notify_me = images1.get("notify_me").toString().replaceAll("\"", "");
                            String notify_before = images1.get("notify_before").toString().replaceAll("\"", "");
                            String random = images1.get("random").toString().replaceAll("\"", "");
                            String social = images1.get("social").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "").trim();
                            String fullname = images1.get("fullname").toString().replaceAll("\"", "");
                                String subject=fullname.substring(0,3);
                            Log.d(TAG, "subject: "+subject);

                            DateFormat df = new SimpleDateFormat("HH:mm");
                            DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
                            Date date = null;
                            Date date1 = null;
                            String output = null;
                            String output1 = null;
                            try{
                                //Conversion of input String to date
                                date= df.parse(start_time);
                                date1= df.parse(end_time);
                                //old date format to new date format
                                output = outputformat.format(date);
                                output1 = outputformat.format(date1);
                                System.out.println(output);
                                System.out.println(output1);
                            }catch(ParseException pe){
                                pe.printStackTrace();
                            }
                           HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("subject_name", subject_name);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);
                            hashMap.put("day", day);
                            hashMap.put("start_time", output);
                            hashMap.put("end_time", output1);
                            hashMap.put("notify_me", notify_me);
                            hashMap.put("notify_before", notify_before);
                            hashMap.put("random", random);
                            hashMap.put("social", social);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("fullname", subject);

                            scheduleList.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapter = new TimeManagement_adapter(getContext(), scheduleList,pd,TimeManagement.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    } else
                         message = jobj.get("message").toString().replaceAll("\"", "");
                      //  Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
// Posting parameters to login url
                Map<String, String> params = new HashMap<>();

               //   params.put("userid",globalClass.getId() );
                  params.put("userid",globalClass.getId());

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    EditText day, start,end,notify3,notify4,subjectname;

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timemanagement_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        day = dialogView.findViewById(R.id.day);
        subjectname = dialogView.findViewById(R.id.subjectname);
        start = dialogView.findViewById(R.id.start);
        end = dialogView.findViewById(R.id.end);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);
        RadioGroup rg = dialogView.findViewById(R.id.radio1);
        RadioGroup radio_group_notify = dialogView.findViewById(R.id.radio_group_notify);



        ImageButton calender2 = dialogView.findViewById(R.id.calendar2);
        ImageButton clock2 = dialogView.findViewById(R.id.clock2);
        ImageButton clock3 = dialogView.findViewById(R.id.clock3);
        Switch onOffSwitch = dialogView. findViewById(R.id.switch2);
        Switch switch_notify = dialogView. findViewById(R.id.switch3);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked==true){
                    Social="Y";
                    Log.d(TAG, "onCheckedChanged: "+Social);
                }
                else {
                    Social="N";
                    Log.d(TAG, "onCheckedChangedN: "+Social);

                }
            }

        });
        switch_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked==true){
                    notify_me="Y";
                    radio_group_notify.setVisibility(View.VISIBLE);
                    radio_group_notify.setOnCheckedChangeListener((group, checkedId) -> {
                        RadioButton radioButton = dialogView. findViewById(checkedId);
                        // Toast.makeText(getActivity(),radioButton.getText(), Toast.LENGTH_SHORT).show();
                         notify_before= (String) radioButton.getText();
                        Log.d(TAG, "notify_before: "+notify_before);
                        if(notify_before.equals("5 mins")){
                            value_notify="5";
                        }
                        else if(notify_before.equals("10 mins")){
                            value_notify="10";
                        }
                        else if(notify_before.equals("20 mins")){
                            value_notify="20";
                        }


                    });

                }
                else {
                    notify_me="N";
                    radio_group_notify.setVisibility(View.GONE);

                }
            }

        });

        rg.setOnCheckedChangeListener((group, checkedId) -> {
           RadioButton radioButton = dialogView. findViewById(checkedId);
           // Toast.makeText(getActivity(),radioButton.getText(), Toast.LENGTH_SHORT).show();
            String buttonValue= (String) radioButton.getText();
            if (buttonValue.equals("Random")){
                randonYes="Y";
                Log.d(TAG, "dialogExamCreateDate: "+randonYes);

            }
            else {
                randonYes="N";
                Log.d(TAG, "dialogExamCreateDate: "+randonYes);
            }

        });

        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener, mYear, mMonth, mDay).show();


            }


        });

        clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker();

            }
        });

        clock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker1();

            }
        });



        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes = dialogView.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String day_name=day.getText().toString().trim();
                String str_subject= subjectname.getText().toString().trim();
                String exam_date= start.getText().toString().trim();
                String end_date= end.getText().toString().trim();


                if(!day.getText().toString().isEmpty()){
                    if(!subjectname.getText().toString().isEmpty()){
                        if(!start.getText().toString().isEmpty()){
                            if(!end.getText().toString().isEmpty()){
                                AddSchedule(day_name,str_subject,exam_date,end_date);
                                alertDialog.dismiss();

                            }

                            else {
                                Toast.makeText(getContext(),getResources().getString(R.string.enter_exam_time), Toast.LENGTH_LONG).show();

                            }
                        }
                        else {
                            Toast.makeText(getContext(),getResources().getString(R.string.enter_exam_date), Toast.LENGTH_LONG).show();

                        }

                    }
                    else {
                        Toast.makeText(getContext(),getResources().getString(R.string.enter_subject), Toast.LENGTH_LONG).show();

                    }

                }
                else {
                    Toast.makeText(getContext(),getResources().getString(R.string.enter_exam_name), Toast.LENGTH_LONG).show();



                }




            }
        });




        Button btn_no = dialogView.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

    }

    private void AddSchedule(String day_name, String str_subject, String exam_date, String end_date) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"addSchedule", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
              //  scheduleList.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                     String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        ScheduleList();


                    } else
                       // message = jobj.get("message").toString().replaceAll("\"", "");
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
// Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                //   params.put("userid",globalClass.getId() );
                params.put("userid",globalClass.getId());
                params.put("day",day_name);
                params.put("start_time",exam_date);
                params.put("start_time",exam_date);
                params.put("end_time",end_date);
                params.put("subject_name",str_subject);
                params.put("notify_me",notify_me);
                params.put("random",randonYes);
                params.put("social",Social);
                params.put("notify_before",notify_before);

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    myCalendar.set(Calendar.YEAR, selectedYear);
                    myCalendar.set(Calendar.MONTH, selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    String myFormat = "MMM dd, yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    exam_date = sdf1.format(myCalendar.getTime());
                    String date_to_show = sdf.format(myCalendar.getTime());
                    day.setText(exam_date);

                }
            };

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        /*MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            notify6.setText(time_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                start.setText( ""+selectedHour + ":" + selectedMinute);
            }
        }, mHour, mMinute,true);

        mTimePicker.show();
    }


/*
    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond=c.get(Calendar.SECOND);


        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            exam_time= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            start.setText(exam_time);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }
*/
            private void timePicker1(){
    // Get Current Time
    final Calendar c = Calendar.getInstance();
    mHour = c.get(Calendar.HOUR_OF_DAY);
    mMinute = c.get(Calendar.MINUTE);

    // Launch Time Picker Dialog
        /*MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            notify6.setText(time_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
    TimePickerDialog mTimePicker;
    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            end.setText( ""+selectedHour + ":" + selectedMinute);
        }
    }, mHour, mMinute,true);

    mTimePicker.show();
}

/*
    private void timePicker1(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond=c.get(Calendar.SECOND);


        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            end_time= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            end.setText(end_time);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }
*/


    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout3, sharedpreference.getStyle());
        super.onResume();
    }
}
