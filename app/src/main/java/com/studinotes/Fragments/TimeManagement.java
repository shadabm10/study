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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.studinotes.Activities.Setting;
import com.studinotes.AdapterClass.AdapterWord;
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
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
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
    ArrayList<HashMap<String,String>> planList;
    ArrayList<HashMap<String,String>> planSubList;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String message;

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

       recyclerView  =  rootView.findViewById(R.id.recycle2);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);

         recyclerView1=  rootView.findViewById(R.id.recycle3);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView1.setLayoutManager(mLayoutManager);

          ScheduleList();
        final ImageButton add2 = (ImageButton) rootView.findViewById(R.id.add2);

        /*add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeManagement_Dialog cdd = new TimeManagement_Dialog(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });*/

        final Button plan = (Button) rootView.findViewById(R.id.plan);
        final LinearLayout green1 = (LinearLayout) rootView.findViewById(R.id.green1);
        final LinearLayout green = (LinearLayout) rootView.findViewById(R.id.ll1);
        final LinearLayout tool1 = (LinearLayout) rootView.findViewById(R.id.tool1);
        final RecyclerView recycler3 = (RecyclerView) rootView.findViewById(R.id.recycle3);
        final RecyclerView recycler2 = (RecyclerView) rootView.findViewById(R.id.recycle2);
        final ImageButton back = (ImageButton) rootView.findViewById(R.id.back);
        final ImageButton search = (ImageButton) rootView.findViewById(R.id.search);
        final LinearLayout searchfield = (LinearLayout) rootView.findViewById(R.id.searchfield);
        final ImageButton cross = (ImageButton) rootView.findViewById(R.id.cross);

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
        final ImageButton setting1 = (ImageButton) rootView.findViewById(R.id.settings1);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });
        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Setting.class);
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
                //  productlist.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    //  String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product=jobj.getAsJsonArray("main");

                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String day = images1.get("day").toString().replaceAll("\"", "");
                            String date = images1.get("date").toString().replaceAll("\"", "");
                            String subject=day.substring(0,3);
                            JsonArray data=images1.getAsJsonArray("data");
                            for (int j=0;j<data.size();j++){
                                JsonObject data_array= data.get(j).getAsJsonObject();
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
                                hashMap.put("id",id);
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

                                planSubList.add(hashMap);
                                Log.d(TAG, "planSubList " + hashMap);

                            }
                            for (int j = 0; j < data.size(); j++) {
                                JsonObject object = data.get(j).getAsJsonObject();
                                String file_type = object.get("day").toString().replaceAll("\"", "");
                                String date1 = object.get("date").toString().replaceAll("\"", "");

                                JsonArray file = object.getAsJsonArray("data");

                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("file_type", file_type);
                                hashMap1.put("date1", date1);
                                hashMap1.put("file", file.toString());


                                planSubList.add(hashMap1);
                            }


                            setMainView();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("day",subject);
                            hashMap.put("date", date);

                            planList.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapter1 = new TimeManagement_adapter1(getContext(), planList,planSubList);
                        recyclerView1.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();


                    }

                    else
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
                params.put("userid","3");

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


      //  Log.d(TAG, "list = " + getListFromJson(hashMap.get("file")));
       // Log.d(TAG, "list size = " + getListFromJson(hashMap.get("file")).size());

        horizontalListAdapter = new RecyclerViewHorizontalListAdapter(getListFromJson(hashMap.get("file")),getActivity());
        dynamic_recycler.setAdapter(horizontalListAdapter);
        horizontalListAdapter.notifyDataSetChanged();




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


    private void ScheduleList() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV +"userSchedulelist", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                //  productlist.clear();

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
                            DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                            Date d = f1.parse(start_time);
                            DateFormat f2 = new SimpleDateFormat("h:mma");
                           String date2= f2.format(d).toLowerCase();

                            DateFormat f3 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                            Date d2 = f3.parse(end_time);
                            DateFormat f4 = new SimpleDateFormat("h:mma");
                            String date3= f4.format(d2).toLowerCase();
                            Log.d(TAG, "end_time_new: "+date2 );

                           HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("subject_name", subject_name);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);
                            hashMap.put("day", day);
                            hashMap.put("start_time", date2);
                            hashMap.put("end_time", date3);
                            hashMap.put("notify_me", notify_me);
                            hashMap.put("notify_before", notify_before);
                            hashMap.put("random", random);
                            hashMap.put("social", social);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("fullname", subject);

                            scheduleList.add(hashMap);
                            Log.d(TAG, "Hashmap " + hashMap);

                        }

                        adapter = new TimeManagement_adapter(getContext(), scheduleList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    } else
                         message = jobj.get("message").toString().replaceAll("\"", "");
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

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    EditText day, start,end,notify3,notify4;

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timemanagement_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        day = dialogView.findViewById(R.id.day);
        start = dialogView.findViewById(R.id.start);
        end = dialogView.findViewById(R.id.end);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);



        ImageButton calender2 = dialogView.findViewById(R.id.calendar2);
        ImageButton clock2 = dialogView.findViewById(R.id.clock2);
        ImageButton clock3 = dialogView.findViewById(R.id.clock3);
     //   ImageButton bell2 = dialogView.findViewById(R.id.bell2);


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

       /* bell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker2();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener1, mYear, mMonth, mDay).show();

            }
        });*/


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes = dialogView.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
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


    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    day.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener1 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify3.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };


    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                        //  et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                        start.setText(hourOfDay + ":" + minute);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void timePicker1(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                        //  et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                        end.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void timePicker2(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                        //  et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                        notify4.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout3, sharedpreference.getStyle());
        super.onResume();
    }
}
