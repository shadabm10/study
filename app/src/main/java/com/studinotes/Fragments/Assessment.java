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
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Activities.Setting;

import com.studinotes.AdapterClass.AdapterListFolder;
import com.studinotes.AdapterClass.Assessment_adapter1;
import com.studinotes.AdapterClass.Examination_adapter;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Utils.GlobalClass;
import com.studinotes.Utils.Shared_Preference;
import com.studinotes.Utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Calendar;

import static com.studinotes.Fragments.TipsnTricks.TAG;

/**
 * Created by Shadab Mallick on 2/3/2016.
 */

//Our class extending fragment
public class Assessment extends Fragment {

    public static final String TAG = "MyFragment";

    Examination_adapter examination_adapter;
    Assessment_adapter1 assesment_adapter;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1 ;
    ProgressDialog pd;
    GlobalClass globalClass;
    ArrayList<HashMap<String,String>> examination_arraylist;
    ArrayList<HashMap<String,String>> assessment_arraylist;
    RecyclerView recyclerView,recyclerView1;
    RelativeLayout main_layout2;
    Sharedpreference sharedpreference;
    Shared_Preference preference;
    Calendar myCalendar = Calendar.getInstance();
    String str_exam_date,exam_time,access_date,asses_time,exam_notify,assess_notify,ass_datetime,
            time_notify,date_notify_exam,notify_date_time,exam_date;
    ArrayList<HashMap<String,String>> SearchExam;
    ArrayList<HashMap<String,String>> SearchAsses;
    EditText area,time;

    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;


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

        area=rootView.findViewById(R.id.area);
        recyclerView1=rootView.findViewById(R.id.recycle1);
        function1();
        sharedpreference = new Sharedpreference(getActivity());
        main_layout2 = rootView.findViewById(R.id.main_layout2);

        final ImageButton add =  rootView.findViewById(R.id.add);
        final ImageButton search =  rootView.findViewById(R.id.search);
        final LinearLayout searchfield =  rootView.findViewById(R.id.searchfield);
        final ImageButton cross = rootView.findViewById(R.id.cross);
        final ImageButton add1 =  rootView.findViewById(R.id.add1);
        final Button exam =  rootView.findViewById(R.id.exam);
        final Button assess = rootView.findViewById(R.id.assess);
        final ImageView edit1 =  rootView.findViewById(R.id.edit1);

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
        area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(globalClass.isNetworkAvailable()){
                    String string = charSequence.toString();
                    if(string.equals("")){
                        browseJob();
                    }
                    else {


                        ViewList_new(area.getText().toString());
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


        return rootView;
    }

    private void function() {
        examination_arraylist = new ArrayList<>();
        SearchExam = new ArrayList<>();
        SearchAsses = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

    }
    private void function1() {
        assessment_arraylist = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(mLayoutManager1);

    }


    public void browseJob() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"listExamsAndAssessments", response -> {
                    Log.d(TAG, "JOB RESPONSE: " + response.toString());

                    pd.dismiss();
            examination_arraylist.clear();
            assessment_arraylist.clear();

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
                                DateFormat df = new SimpleDateFormat("HH:mm");
                                DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
                                Date date = null;

                                String output = null;

                                try{
                                    //Conversion of input String to date
                                    date= df.parse(exam_time);

                                    //old date format to new date format
                                    output = outputformat.format(date);

                                    System.out.println(output);

                                }catch(ParseException pe){
                                    pe.printStackTrace();
                                }
                                // globalClass.setCat_id(id);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("user_id", user_id);
                                hashMap.put("exam_name", exam_name);
                                hashMap.put("exam_date", exam_date);
                                hashMap.put("exam_time", output);
                                hashMap.put("exam_subject", exam_subject);
                                hashMap.put("exam_details", exam_details);
                                hashMap.put("exam_notify", exam_notify);
                                hashMap.put("exam_notify_time", exam_notify_time);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);

                                examination_arraylist.add(hashMap);
                                Log.d(TAG, "Exam " + hashMap);

                            }

                            examination_adapter = new Examination_adapter(getContext(), examination_arraylist,Assessment.this,pd);
                            recyclerView.setAdapter(examination_adapter);
                            examination_adapter.notifyDataSetChanged();



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
                                String[] ass_time_update = ass_time.split(" ");
                                String ay=ass_time_update[1];
                                Log.d(TAG, "ass_time_update: "+ay);

                                DateFormat df = new SimpleDateFormat("HH:mm");
                                DateFormat outputformat = new SimpleDateFormat("hh:mm aa");
                                Date date = null;

                                String output = null;

                                try{
                                    //Conversion of input String to date
                                    date= df.parse(ay);

                                    //old date format to new date format
                                    output = outputformat.format(date);

                                    System.out.println(output);

                                }catch(ParseException pe){
                                    pe.printStackTrace();
                                }
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id",id);
                                hashMap.put("user_id",user_id);
                                hashMap.put("ass_name", ass_name);
                                hashMap.put("ass_date", ass_date);
                                hashMap.put("ass_time", output);
                                hashMap.put("ass_subject", ass_subject);
                                hashMap.put("ass_details", ass_details);
                                hashMap.put("ass_notify", ass_notify);
                                hashMap.put("ass_notify_time", ass_notify_time);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);

                                assessment_arraylist.add(hashMap);
                                Log.d(TAG, "Asses " + hashMap);

                            }

                            assesment_adapter = new Assessment_adapter1(getContext(), assessment_arraylist,Assessment.this,pd);
                            recyclerView1.setAdapter(assesment_adapter);
                            assesment_adapter.notifyDataSetChanged();


                        } else
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



                    } catch (Exception e) {
                        Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

                    }


                }, error -> {
                    Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                    Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
                    pd.dismiss();
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


    EditText examdate, examtime,notify_edittext,notify_bell,examname,subjectname,examdetails;

               /* Dialog for Examination  */

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.assessment_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        examdate = dialogView.findViewById(R.id.examdate);
        examdetails = dialogView.findViewById(R.id.examdetails);
        subjectname = dialogView.findViewById(R.id.subjectname);
        examname = dialogView.findViewById(R.id.examname);
        examtime = dialogView.findViewById(R.id.examtime);
        notify_edittext = dialogView.findViewById(R.id.notify3);
        notify_bell = dialogView.findViewById(R.id.notify4);


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
                String str_exam_name=examname.getText().toString().trim();
                String str_subject= subjectname.getText().toString().trim();
                String exam_date= examdate.getText().toString().trim();
                String str_exam_time=examtime.getText().toString().trim();
                 notify_date_time=date_notify_exam+" "+time_notify;
                Log.d(TAG, "notify_date_time: "+notify_date_time);
                if(!examname.getText().toString().isEmpty()){
                    if(!subjectname.getText().toString().isEmpty()){
                        if(!examdate.getText().toString().isEmpty()){
                            if(!examtime.getText().toString().isEmpty()){
                                SaveExamination(str_exam_name,str_subject,exam_date,str_exam_time);
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

      private void SaveExamination(final String str_exam_name, final String str_subject,final String exam_date, final String exam_time)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"addExaminations", response -> {
            Log.d(TAG, "JOB RESPONSE: " + response.toString());

            pd.dismiss();
            //  productlist.clear();

            Gson gson = new Gson();

            try {


                JsonObject jobj = gson.fromJson(response, JsonObject.class);
                String result = jobj.get("status").toString().replaceAll("\"", "");
                String message = jobj.get("message").toString().replaceAll("\"", "");


                if (result.equals("1")) {

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    browseJob();







                } else
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

            }


        }, error -> {
            Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams() {
            // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid",globalClass.getId());
                params.put("exam_name",str_exam_name);
                params.put("exam_date",exam_date);
                params.put("exam_time",exam_time);
                params.put("exam_subject",str_subject);



                    params.put("exam_details", examdetails.getText().toString());
                    params.put("exam_notify_time", notify_edittext.getText().toString()+ " "+notify_bell.getText().toString());


                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    /*   Dialog for Assessment*/

    EditText accessdate,accesstime,notify5,notify6,asses_name,subjectname_assess,access_details;
    private void dialogAccessCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.assessment_dialog1, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        asses_name=dialogView.findViewById(R.id.asses_name);
        subjectname_assess=dialogView.findViewById(R.id.asses_subject_name);
        accessdate = dialogView.findViewById(R.id.assessdate);
        accesstime = dialogView.findViewById(R.id.assesstime);
        notify5 = dialogView.findViewById(R.id.notify5);
        notify6 = dialogView.findViewById(R.id.notify6);
        time=dialogView.findViewById(R.id.time);
        access_details = dialogView.findViewById(R.id.access_details);


        ImageButton calender1 = dialogView.findViewById(R.id.calendar1);
        ImageButton clock1 = dialogView.findViewById(R.id.clock1);
        ImageButton bell3 = dialogView.findViewById(R.id.bell3);


        calender1.setOnClickListener(view -> {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(getActivity(), datePickerListener1, mYear, mMonth, mDay).show();


        });

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker2();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener4, mYear, mMonth, mDay).show();
            }
        });

        bell3.setOnClickListener(v -> {

            timePicker3();
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(getActivity(), datePickerListener3, mYear, mMonth, mDay).show();

        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes1 = dialogView.findViewById(R.id.btn_yes1);
        btn_yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_exam_name=asses_name.getText().toString().trim();
                String str_subject= subjectname_assess.getText().toString().trim();
               String exam_date= accessdate.getText().toString().trim();
                notify_date_time=date_notify_exam+" "+time_notify;
                Log.d(TAG, "notify_date_time: "+notify_date_time);
                if(!asses_name.getText().toString().isEmpty()){
                    if(!subjectname_assess.getText().toString().isEmpty()){
                        if(!accessdate.getText().toString().isEmpty()){
                            if(!accesstime.getText().toString().isEmpty()){
                                SaveAssesment(str_exam_name,str_subject,exam_date,assess_notify);
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

        Button btn_no1 = dialogView.findViewById(R.id.btn_no1);
        btn_no1.setOnClickListener(v -> alertDialog.dismiss());

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
            date_notify_exam = sdf1.format(myCalendar.getTime());
            String date_to_show = sdf.format(myCalendar.getTime());
            Log.d(TAG, "date_notify_exam: "+date_notify_exam);
            examdate.setText(date_notify_exam);

        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener1 =
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
                    accessdate.setText(exam_date);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener2 =
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
                    notify_edittext.setText(exam_date);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener3 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    myCalendar.set(Calendar.YEAR, selectedYear);
                    myCalendar.set(Calendar.MONTH, selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    String myFormat = "MMM dd, yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    access_date = sdf1.format(myCalendar.getTime());
                    String date_to_show = sdf.format(myCalendar.getTime());
                    notify5.setText(access_date);

                    //notify5.setText(date);

                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener4 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    myCalendar.set(Calendar.YEAR, selectedYear);
                    myCalendar.set(Calendar.MONTH, selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    String myFormat = "MMM dd, yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    ass_datetime = sdf1.format(myCalendar.getTime());
                    String date_to_show = sdf.format(myCalendar.getTime());
                    time.setText(ass_datetime);

                    //notify5.setText(date);

                }
            };

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                examtime.setText( ""+selectedHour + ":" + selectedMinute);
            }
        }, mHour, mMinute,true);

        mTimePicker.show();

    }

    private void timePicker1(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

      /*  // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    "" ;
            notify_bell.setText(exam_time);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                notify_bell.setText( ""+selectedHour + ":" + selectedMinute);
            }
        }, mHour, mMinute,true);

        mTimePicker.show();
    }


    private void timePicker2(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
       /* MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            assess_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    "" ;
            accesstime.setText(assess_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                accesstime.setText( ""+selectedHour + ":" + selectedMinute);
            }
        }, mHour, mMinute,true);

        mTimePicker.show();
    }

    private void timePicker3(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

       /* // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    "" ;
            notify6.setText(time_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                notify6.setText( ""+selectedHour + ":" + selectedMinute);
            }
        }, mHour, mMinute,true);

        mTimePicker.show();
    }



    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout2, sharedpreference.getStyle());
        super.onResume();
    }
    private void SaveAssesment(final String str_exam_name, final String str_subject,final String exam_date, final String exam_time)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"addAssessments", response -> {
            Log.d(TAG, "JOB RESPONSE: " + response.toString());

            pd.dismiss();
            //  productlist.clear();

            Gson gson = new Gson();

            try {


                JsonObject jobj = gson.fromJson(response, JsonObject.class);
                String result = jobj.get("status").toString().replaceAll("\"", "");
                String message = jobj.get("message").toString().replaceAll("\"", "");


                if (result.equals("1")) {

                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    browseJob();







                } else
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

            }


        }, error -> {
            Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid",globalClass.getId());
                params.put("ass_name",str_exam_name);
                params.put("ass_start_date",exam_date);
                params.put("due_date_time",time.getText().toString()+" "+accesstime.getText().toString());
                params.put("ass_subject",str_subject);



                params.put("ass_details", access_details.getText().toString());
                params.put("ass_notify_time", notify5.getText().toString()+" "+notify6.getText().toString());


                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    /* Search from the examination nad Assessment Listing*/
    public void ViewList_new(final String search_text ) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"searchExamAndAssessments", response -> {
            Log.d(TAG, "JOB RESPONSE: " + response.toString());

            pd.dismiss();
            SearchExam.clear();
            SearchAsses.clear();

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

                        SearchExam.add(hashMap);
                        Log.d(TAG, "Exam " + hashMap);

                    }

                    examination_adapter = new Examination_adapter(getContext(), SearchExam,Assessment.this,pd);
                    recyclerView.setAdapter(examination_adapter);
                    examination_adapter.notifyDataSetChanged();



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

                        SearchAsses.add(hashMap);
                        Log.d(TAG, "Asses " + hashMap);

                    }

                    assesment_adapter = new Assessment_adapter1(getContext(), SearchAsses,Assessment.this,pd);
                    recyclerView1.setAdapter(assesment_adapter);
                    assesment_adapter.notifyDataSetChanged();


                } else
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();



            } catch (Exception e) {
                Toast.makeText(getContext(), "issue", Toast.LENGTH_LONG).show();

            }


        }, error -> {
            Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("userid",globalClass.getId());
                params.put("search_name",search_text);

                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}
