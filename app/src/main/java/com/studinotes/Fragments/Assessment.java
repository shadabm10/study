package com.studinotes.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.util.ArrayList;
import android.app.ProgressDialog;
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
import com.studinotes.AdapterClass.Assessment_adapter;
import com.studinotes.AdapterClass.Assessment_adapter1;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

/**
 * Created by SHadab on 2/3/2016.
 */

//Our class extending fragment
public class Assessment extends Fragment {

    public static final String TAG = "MyFragment";

    Assessment_adapter adapter;
    Assessment_adapter1 adapter1;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1 ;
    ProgressDialog pd;
    GlobalClass globalClass;
    ArrayList<HashMap<String,String>> assessment;
    ArrayList<HashMap<String,String>> assessment1;
    RecyclerView recyclerView,recyclerView1;
    RelativeLayout main_layout2;
    Sharedpreference sharedpreference;
    Shared_Preference preference;
    private int mYear, mMonth, mDay, mHour, mMinute;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.assessment, container, false);
        preference = new Shared_Preference(getActivity().getBaseContext());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading");
        recyclerView=rootView.findViewById(R.id.recycle);
        globalClass = (GlobalClass)getActivity().getApplicationContext();

        browseJob();
        function();
        recyclerView1=rootView.findViewById(R.id.recycle1);
        function1();
        sharedpreference = new Sharedpreference(getActivity());
        main_layout2 = rootView.findViewById(R.id.main_layout2);

        final ImageButton add = (ImageButton) rootView.findViewById(R.id.add);
        final ImageButton search = (ImageButton) rootView.findViewById(R.id.search);
        final LinearLayout searchfield = (LinearLayout) rootView.findViewById(R.id.searchfield);
        final ImageButton cross = (ImageButton) rootView.findViewById(R.id.cross);
        final ImageButton add1 = (ImageButton) rootView.findViewById(R.id.add1);
        final Button exam = (Button) rootView.findViewById(R.id.exam);
        final Button assess = (Button) rootView.findViewById(R.id.assess);
        final ImageView edit1 = (ImageView) rootView.findViewById(R.id.edit1);

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

        add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    dialogExamCreateDate();

                }

        });

        assess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                add1.setVisibility(View.VISIBLE);
                exam.setBackgroundResource(R.drawable.cancel_button);
                exam.setTextColor(Color.parseColor("#000000"));
                assess.setBackgroundResource(R.drawable.save_button);
                assess.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        exam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                add1.setVisibility(View.GONE);
                assess.setBackgroundResource(R.drawable.cancel_button);
                assess.setTextColor(Color.parseColor("#000000"));
                exam.setBackgroundResource(R.drawable.save_button);
                exam.setTextColor(Color.parseColor("#FFFFFF"));




            }
        });
        add1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dialogAccessCreateDate();


                }
            });
               /* Assessment_Dialog1 cdd = new Assessment_Dialog1(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();*/

        final ImageButton setting = (ImageButton) rootView.findViewById(R.id.settings);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

    private void function() {
        assessment = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

    }
    private void function1() {
        assessment1 = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(mLayoutManager1);

    }


    private void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"listExamsAndAssessments", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JOB RESPONSE: " + response.toString());

                pd.dismiss();
                //  productlist.clear();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String result = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");


                    if (result.equals("1")) {
                        JsonArray product = jobj.getAsJsonArray("examData");


                        for (int i = 0; i < product.size(); i++) {
                            JsonObject images1 = product.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String exam_name = images1.get("exam_name").toString().replaceAll("\"", "");
                            String exam_date = images1.get("exam_date").toString().replaceAll("\"", "").trim();
                            String exam_time = images1.get("exam_time").toString().replaceAll("\"", "");
                            String exam_subject = images1.get("exam_subject").toString().replaceAll("\"", "");
                            String exam_details = images1.get("exam_details").toString().replaceAll("\"", "");
                            String exam_notify = images1.get("exam_notify").toString().replaceAll("\"", "");
                            String exam_notify_time = images1.get("exam_notify_time").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");

                            // globalClass.setCat_id(id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("user_id", user_id);
                            hashMap.put("exam_name", exam_name);
                            hashMap.put("exam_date", exam_date);
                            hashMap.put("exam_time", exam_time);
                            hashMap.put("exam_subject", exam_subject);
                            hashMap.put("exam_details", exam_details);
                            hashMap.put("exam_notify", exam_notify);
                            hashMap.put("exam_notify_time", exam_notify_time);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);

                            assessment.add(hashMap);
                            Log.d(TAG, "Exam " + hashMap);

                        }

                        adapter = new Assessment_adapter(getContext(), assessment);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();



                        JsonArray product1=jobj.getAsJsonArray("assessmentData");
                        for (int i = 0; i < product1.size(); i++) {
                            JsonObject images1 = product1.get(i).getAsJsonObject();
                            String id = images1.get("id").toString().replaceAll("\"", "");
                            String user_id = images1.get("user_id").toString().replaceAll("\"", "");
                            String ass_name = images1.get("ass_name").toString().replaceAll("\"", "");
                            String ass_date = images1.get("ass_date").toString().replaceAll("\"", "").trim();
                            String ass_time = images1.get("ass_time").toString().replaceAll("\"", "");
                            String ass_subject = images1.get("ass_subject").toString().replaceAll("\"", "");
                            String ass_details = images1.get("ass_details").toString().replaceAll("\"", "");
                            String ass_notify = images1.get("ass_notify").toString().replaceAll("\"", "");
                            String ass_notify_time = images1.get("ass_notify_time").toString().replaceAll("\"", "");
                            String delete_flag = images1.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = images1.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = images1.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = images1.get("modified_date").toString().replaceAll("\"", "");

                            // globalClass.setCat_id(id);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("user_id",user_id);
                            hashMap.put("ass_name", ass_name);
                            hashMap.put("ass_date", ass_date);
                            hashMap.put("ass_time", ass_time);
                            hashMap.put("ass_subject", ass_subject);
                            hashMap.put("ass_details", ass_details);
                            hashMap.put("ass_notify", ass_notify);
                            hashMap.put("ass_notify_time", ass_notify_time);
                            hashMap.put("delete_flag", delete_flag);
                            hashMap.put("is_active", is_active);
                            hashMap.put("entry_date", entry_date);
                            hashMap.put("modified_date", modified_date);

                            assessment1.add(hashMap);
                            Log.d(TAG, "Asses " + hashMap);

                        }

                        adapter1 = new Assessment_adapter1(getContext(), assessment1);
                        recyclerView1.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();


                    } else
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



                } catch (Exception e) {
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

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    EditText examdate, examtime,notify3,notify4;

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.assessment_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        examdate = dialogView.findViewById(R.id.examdate);
        examtime = dialogView.findViewById(R.id.examtime);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);


        ImageButton calender = dialogView.findViewById(R.id.calendar);
        ImageButton clock = dialogView.findViewById(R.id.clock);
        ImageButton bell2 = dialogView.findViewById(R.id.bell2);


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener, mYear, mMonth, mDay).show();


            }


        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker();

            }
        });

        bell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker1();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener2, mYear, mMonth, mDay).show();


            }
        });

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

    EditText accessdate,accesstime,notify5,notify6;
    private void dialogAccessCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.assessment_dialog1, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);


        accessdate = dialogView.findViewById(R.id.assessdate);
        accesstime = dialogView.findViewById(R.id.assesstime);
        notify5 = dialogView.findViewById(R.id.notify5);
        notify6 = dialogView.findViewById(R.id.notify6);


        ImageButton calender1 = dialogView.findViewById(R.id.calendar1);
        ImageButton clock1 = dialogView.findViewById(R.id.clock1);
        ImageButton bell3 = dialogView.findViewById(R.id.bell3);


        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener1, mYear, mMonth, mDay).show();


            }


        });

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker2();

            }
        });

        bell3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker3();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener3, mYear, mMonth, mDay).show();

            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes1 = dialogView.findViewById(R.id.btn_yes1);
        btn_yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        Button btn_no1 = dialogView.findViewById(R.id.btn_no1);
        btn_no1.setOnClickListener(new View.OnClickListener() {
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

            examdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener1 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    accessdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener2 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify3.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener3 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify5.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

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

                        examtime.setText(hourOfDay + ":" + minute);


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

                        notify4.setText(hourOfDay + ":" + minute);

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

                        accesstime.setText(hourOfDay + ":" + minute);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void timePicker3(){
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

                        notify6.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }



    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout2, sharedpreference.getStyle());
        super.onResume();
    }
}
