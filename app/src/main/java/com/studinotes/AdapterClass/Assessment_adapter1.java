package com.studinotes.AdapterClass;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.studinotes.Utils.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Assessment_adapter1 extends RecyclerView.Adapter<Assessment_adapter1.PlanetViewHolder> {
    String TAG="";
    private Context context;
    ArrayList<HashMap<String,String>> assessment;
    String text;
    ProgressDialog pd;
    LayoutInflater inflater;
    Assessment assessment_ass;
    String notify_date_time,date_notify_exam,time_notify,assess_notify,access_date,str_id;
    String str_assess_name,id,str_asses_subject_name,str_asses_date,str_asses_time,str_asses_details,str_asses_notify;
    Calendar myCalendar = Calendar.getInstance();
    GlobalClass globalClass;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;
    EditText accessdate, accesstime, notify5, notify6,asses_name,subjectname_assess,access_details;

    public Assessment_adapter1(Context context, ArrayList<HashMap<String, String>> assessment,Assessment assessment_ass,ProgressDialog pd) {

        this.context = context;
        this.assessment = assessment;
        this.assessment_ass=assessment_ass;
        this.pd=pd;
        globalClass = ((GlobalClass) context.getApplicationContext());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // inflates the row layout from xml when needed
    @Override
    public PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_adapter1, parent, false);
        PlanetViewHolder PlanetViewHolder = new PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {
           id=assessment.get(position).get("id");
        holder.text3.setText(assessment.get(position).get("ass_name"));
        holder.text4.setText(assessment.get(position).get("ass_date"));
        holder.text5.setText(assessment.get(position).get("ass_time"));
        Log.d(TAG, "NAME NEW: "+text);
        String exam_notify=assessment.get(position).get("ass_notify");
        if(exam_notify.equals("Y")){
            holder.notify2.setVisibility(View.VISIBLE);
        }
        else {
            holder.notify2.setVisibility(View.GONE);
        }
        Log.d(TAG, "NAME NEW: "+text);

        holder.edit1.setOnClickListener(v -> {
            str_assess_name=assessment.get(position).get("ass_name");
            str_asses_subject_name=assessment.get(position).get("ass_subject");
            str_asses_date=assessment.get(position).get("ass_date");
            str_asses_time=assessment.get(position).get("ass_time");
            str_asses_details=assessment.get(position).get("ass_details");
            str_asses_notify=assessment.get(position).get("ass_notify_time");
            str_id=assessment.get(position).get("id");
            dialogAccessCreateDate();
        });


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return assessment.size();
    }

    public  class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text3, text4, text5;
        ImageView edit1,notify2;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text3 = itemView.findViewById(R.id.user1);
            text4 = itemView.findViewById(R.id.date1);
            text5 = itemView.findViewById(R.id.time1);
            edit1 = itemView.findViewById(R.id.edit2);
            notify2 = itemView.findViewById(R.id.notify2);
            itemView.setOnLongClickListener(v -> {
                //  Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                // set title

                // set dialog message
                alertDialogBuilder
                        .setMessage("Want to delete the Assessment ")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity

                                DeleteFolder();
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

    private void DeleteFolder() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"deleteAssessments", new Response.Listener<String>() {


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

                        assessment_ass.browseJob();


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

                params.put("ass_id", id);
                //  params.put("type", type);

                Log.d(TAG, "getParams: "+params);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void dialogAccessCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.assessment_dialog1, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        asses_name=dialogView.findViewById(R.id.asses_name);
        subjectname_assess=dialogView.findViewById(R.id.asses_subject_name);
        accessdate = dialogView.findViewById(R.id.assessdate);
        accesstime = dialogView.findViewById(R.id.assesstime);
        notify5 = dialogView.findViewById(R.id.notify5);
        notify6 = dialogView.findViewById(R.id.notify6);
        access_details = dialogView.findViewById(R.id.access_details);
        /*   Setting the values */
        asses_name.setText(str_assess_name);
        access_details.setText(str_asses_details);
        subjectname_assess.setText(str_asses_subject_name);
        accessdate.setText(str_asses_date);
        accesstime.setText(str_asses_time);
        notify5.setText(str_asses_notify);

        ImageButton calender1 = dialogView.findViewById(R.id.calendar1);
        ImageButton clock1 = dialogView.findViewById(R.id.clock1);
        ImageButton bell3 = dialogView.findViewById(R.id.bell3);


        calender1.setOnClickListener(view -> {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(context, datePickerListener1, mYear, mMonth, mDay).show();


        });

        clock1.setOnClickListener(v -> timePicker2());

        bell3.setOnClickListener(v -> {

            timePicker3();
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(context, datePickerListener3, mYear, mMonth, mDay).show();

        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes1 = dialogView.findViewById(R.id.btn_yes1);
        btn_yes1.setOnClickListener(v -> {

            String str_exam_name=asses_name.getText().toString().trim();
            String str_subject= subjectname_assess.getText().toString().trim();
            String exam_date= accessdate.getText().toString().trim();
          //  notify_date_time=date_notify_exam+" "+time_notify;
           // Log.d(TAG, "notify_date_time: "+notify_date_time);
            if(!asses_name.getText().toString().isEmpty()){
                if(!subjectname_assess.getText().toString().isEmpty()){
                    if(!accessdate.getText().toString().isEmpty()){
                        if(!accesstime.getText().toString().isEmpty()){
                            EditAsses();
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




        });

        Button btn_no1 = dialogView.findViewById(R.id.btn_no1);
        btn_no1.setOnClickListener(v -> alertDialog.dismiss());

    }



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
                    access_date = sdf1.format(myCalendar.getTime());
                    String date_to_show = sdf.format(myCalendar.getTime());
                    accessdate.setText(access_date);
                }
            };


    private void timePicker2(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond =c.get(Calendar.SECOND);

        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            assess_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            accesstime.setText(assess_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }

    private void timePicker3(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            notify6.setText(time_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }
    private void EditAsses()
    {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"editAssessments", response -> {
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
                    String ass_name=data.get("ass_name").toString().replaceAll("\"", "");
                    String ass_date=data.get("ass_date").toString().replaceAll("\"", "");
                    String ass_time=data.get("ass_time").toString().replaceAll("\"", "");
                    String ass_subject=data.get("ass_subject").toString().replaceAll("\"", "");
                    String ass_details=data.get("ass_details").toString().replaceAll("\"", "");
                    String ass_notify=data.get("ass_notify").toString().replaceAll("\"", "");
                    String ass_notify_time=data.get("ass_notify_time").toString().replaceAll("\"", "");
                    String delete_flag=data.get("delete_flag").toString().replaceAll("\"", "");
                    String is_active=data.get("is_active").toString().replaceAll("\"", "");
                    String entry_date=data.get("entry_date").toString().replaceAll("\"", "");
                    String modified_date=data.get("modified_date").toString().replaceAll("\"", "");
                    assessment_ass.browseJob();







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

                params.put("ass_id",str_id);
                params.put("ass_name",asses_name.getText().toString().trim());
                params.put("ass_date",accessdate.getText().toString().trim());
                params.put("ass_time",time_notify);
                params.put("ass_subject",subjectname_assess.getText().toString().trim());



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

}
