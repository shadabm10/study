package com.studinotes.AdapterClass;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Fragments.Assessment;
import com.studinotes.Fragments.StudyNotes;
import com.studinotes.Utils.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Examination_adapter extends RecyclerView.Adapter<Examination_adapter.PlanetViewHolder> {
   String TAG="ass";
    Context context;
    ArrayList<HashMap<String,String>> assessment;
    String text,str_id,str_exam_name,str_subject_nam,str_exam_date,str_exam_time,str_exam_details,str_notify;
    LayoutInflater inflater;
    GlobalClass globalClass;
    Assessment assessment_class;
    String exam_time,date_notify_exam,id;
    ProgressDialog pd;
    Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;

    public Examination_adapter(Context context, ArrayList<HashMap<String, String>> assessment, Assessment assessment_class, ProgressDialog pd) {

        this.context = context;
        this.assessment = assessment;
         this.assessment_class=assessment_class;
         this.pd=pd;
        globalClass = ((GlobalClass) context.getApplicationContext());


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // inflates the row layout from xml when needed
    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_adapter, parent, false);
        PlanetViewHolder PlanetViewHolder = new PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        id=assessment.get(position).get("id");

        holder.text.setText(assessment.get(position).get("exam_name"));
        holder.text1.setText(assessment.get(position).get("exam_date"));
        holder.text2.setText(assessment.get(position).get("exam_time"));
        String exam_notify=assessment.get(position).get("exam_notify");
        if(exam_notify.equals("Y")){
            holder.notify1.setVisibility(View.VISIBLE);
        }
        else {
            holder.notify1.setVisibility(View.GONE);
        }
        Log.d(TAG, "NAME NEW: "+text);

        holder.edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_exam_name=assessment.get(position).get("exam_name");
                str_subject_nam=assessment.get(position).get("exam_subject");
                str_exam_date=assessment.get(position).get("exam_date");
                str_exam_time=assessment.get(position).get("exam_time");
                str_exam_details=assessment.get(position).get("exam_details");
                str_notify=assessment.get(position).get("exam_notify_time");
                str_id=assessment.get(position).get("id");
                dialogExamCreateDate();
            }

        });

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return assessment.size();
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2;
        protected ImageView edit1,notify1;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= itemView.findViewById(R.id.user);
            text1= itemView.findViewById(R.id.date);
            text2= itemView.findViewById(R.id.time);
            edit1 = itemView.findViewById(R.id.edit1);
            notify1 = itemView.findViewById(R.id.notify1);
            itemView.setOnLongClickListener(v -> {
                //  Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                // set title

                // set dialog message
                alertDialogBuilder
                        .setMessage("Want to delete the Examination")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity

                                DeleteFolder(assessment.get(getAdapterPosition()).get("id"));
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
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return false;
            });





        }
    }
    private void DeleteFolder(String id) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"deleteExaminations", new Response.Listener<String>() {


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

                           assessment_class.browseJob();


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

                params.put("exam_id", id);
              //  params.put("type", type);

                Log.d(TAG, "getParams: "+params);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        examdate.setText(str_exam_date);
        examdetails.setText(str_exam_details);
        subjectname.setText(str_subject_nam);
        examname.setText(str_exam_name);
        notify_edittext.setText(str_notify);
        examtime.setText(str_exam_time);

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

                new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay).show();


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

                new DatePickerDialog(context, datePickerListener1, mYear, mMonth, mDay).show();


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
               // notify_date_time=date_notify_exam+" "+time_notify;
              //  Log.d(TAG, "notify_date_time: "+notify_date_time);
                if(!examname.getText().toString().isEmpty()){
                    if(!subjectname.getText().toString().isEmpty()){
                        if(!examdate.getText().toString().isEmpty()){
                            if(!examtime.getText().toString().isEmpty()){
                                EditExamination(str_exam_name,str_subject,exam_date,str_exam_time);
                                alertDialog.dismiss();

                            }

                            else {
                                Toast.makeText(context,"Enter Exam Time", Toast.LENGTH_LONG).show();

                            }
                        }
                        else {
                            Toast.makeText(context,"Enter Exam date", Toast.LENGTH_LONG).show();

                        }

                    }
                    else {
                        Toast.makeText(context,"Enter Subject", Toast.LENGTH_LONG).show();

                    }

                }
                else {
                    Toast.makeText(context,"Enter Exam Name", Toast.LENGTH_LONG).show();



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

    private void EditExamination(final String str_exam_name, final String str_subject,final String exam_date, final String exam_time)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"editExaminations", response -> {
            Log.d(TAG, "JOB RESPONSE: " + response.toString());

            pd.dismiss();
            //  productlist.clear();

            Gson gson = new Gson();

            try {


                JsonObject jobj = gson.fromJson(response, JsonObject.class);
                String result = jobj.get("status").toString().replaceAll("\"", "");
                String message = jobj.get("message").toString().replaceAll("\"", "");


                if (result.equals("1")) {

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    JsonObject data = jobj.getAsJsonObject("data");
                    String id =data.get("id").toString().replaceAll("\"", "");
                    String user_id=data.get("user_id").toString().replaceAll("\"", "");
                    String exam_name=data.get("exam_name").toString().replaceAll("\"", "");
                    String exam_date_return=data.get("exam_date").toString().replaceAll("\"", "");
                    String exam_time_return=data.get("exam_time").toString().replaceAll("\"", "");
                    String exam_subject=data.get("exam_subject").toString().replaceAll("\"", "");
                    String exam_details=data.get("exam_details").toString().replaceAll("\"", "");
                    String exam_notify=data.get("exam_notify").toString().replaceAll("\"", "");
                    String exam_notify_time=data.get("exam_notify_time").toString().replaceAll("\"", "");
                    String delete_flag=data.get("delete_flag").toString().replaceAll("\"", "");
                    String is_active=data.get("is_active").toString().replaceAll("\"", "");
                    String entry_date=data.get("entry_date").toString().replaceAll("\"", "");
                    String modified_date=data.get("modified_date").toString().replaceAll("\"", "");
                    assessment_class.browseJob();







                } else
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "issue", Toast.LENGTH_LONG).show();

            }


        }, error -> {
            Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("exam_id",str_id);
                params.put("exam_name",str_exam_name);
                params.put("exam_date",exam_date);
                params.put("exam_time",exam_time);
                params.put("exam_subject",str_subject);



                params.put("exam_details", examdetails.getText().toString());
                params.put("exam_notify_time", notify_edittext.getText().toString()+" "+notify_bell.getText().toString());


                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    EditText examdate, examtime, notify3, notify4,examdetails,subjectname,examname,
            notify_edittext,notify_bell;


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
                    date_notify_exam = sdf1.format(myCalendar.getTime());
                    String date_to_show = sdf.format(myCalendar.getTime());
                    Log.d(TAG, "date_notify_exam: "+date_notify_exam);
                    notify_edittext.setText(date_notify_exam);

                }
            };


    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

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
        mSecond=c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            exam_time= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            notify_bell.setText(exam_time);
            Log.d(TAG, "timePicker: "+exam_time);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }

}